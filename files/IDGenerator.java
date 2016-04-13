package com.eben4a.common;

import com.eben4a.dao.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
*
* @author hetq
*/
public class IDGenerator {

    /**
     * The default number of cached id
     */
    private static final int DEFAULT_INCREMENT_BY = 100;

    /**
     * Base query to get a value
     */
    private static final String SQL_GET_ID = "select counter, increment_by from tb_id where idspace=?";

    /**
     * The query to get a value using Microsoft SQL Server
     */
    private static final String SQL_GET_ID_SQLSERVER = "select counter, increment_by from tb_id (UPDLOCK) where idspace=?";

    /**
     * The query to create a new counter
     */
    private static final String SQL_INSERT_ID = "insert into tb_id (idspace, counter, increment_by) values (?,?,?)";

    /**
     * The driver name used to check if the underlying database is a Microsoft SQL Server
     */
    private static final String DRIVER_NAME_SQLSERVER = "SQLServer";

    // ------------------------------------------------------------ Private Data

    /**
     * The used logger
     */
    private static transient Logger log = EbenLoggerFactory.getDaoLogger();

    /**
     * How many values are available without to perform a new db access ?
     */
    private int availableIds = 0;

    /**
     * The last value returned
     */
    private long currentValue = 0;

    /**
     * Does the underlying database support <CODE>SELECT FOR UPDATE</CODE> ?
     */
    private boolean supportsSelectForUpdate = false;

    /**
     * The driver name
     */
    private String dbDriverName  = null;

    /**
     * The query to read a value
     */
    private String selectQuery   = null;

    /**
     * The DataSource used to access to the db
     */
    private DataSource dataSource;

    /**
     * The incrementBy properties
     */
    public int incrementBy = DEFAULT_INCREMENT_BY;

    /**
     * The name space
     */
    private String nameSpace = null;

    /**
     * Is this instance already initialized ? This parameter is used in order to
     * initialized the instance the first time. We could initialize the instance in
     * the constructor, but in this case, the constructor should throw a
     * DaoException that complicates the use becase a static instance
     * can not be created easly
     */
    private boolean isInitialized = false;

    // -------------------------------------------------------------- Properties

    // ------------------------------------------------------------ Constructors

    /**
     * Create a new IDGenerator using the given DataSource. The increment value
     * is set to <code>DEFAULT_CACHE_SIZE</code>
     * @param nameSpace the name space (counter name)
     * @param ds the datasource to use
     */
    public IDGenerator(String nameSpace) {

        if (nameSpace == null) {
            throw new IllegalArgumentException("The nameSpace must be not null");
        }

        this.nameSpace = nameSpace;
        this.incrementBy = DEFAULT_INCREMENT_BY;

        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/aaaa");
        } catch (NamingException ex) {
            throw new IllegalArgumentException("get dataSource error");
        }
    }

    /**
     * Returns the next value of the given space. If there is a value available,
     * no new value is read from the datastore, otherwise a query is performed.
     * @return the next generated value
     * @throws IDGeneratorException if an error occurs
     */
    public synchronized long next() throws DaoException {
        if (availableIds > 0) {
            availableIds--;
            return (++currentValue);
        } else {
            currentValue = read();
        }

        availableIds = incrementBy - 1;

        return currentValue;
    }

    private long read() throws DaoException {

        if (!isInitialized) {
            init();
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isAutoCommit = false;

        long value = 0;

        try {

            conn = dataSource.getConnection();

            if (supportsSelectForUpdate) {
                //
                // Checking the autocommit because using the "select for update"
                // the autocommit must be disable.
                // After the queries the original value is set.
                //
                isAutoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);

                stmt = conn.prepareStatement(selectQuery);
            } else {
                stmt = conn.prepareStatement(selectQuery);
            }

            stmt.setString(1, nameSpace);
            rs = stmt.executeQuery();

            if (rs.next()) {// IDSpace found
                setIncrementBy(rs.getInt(2));
            }else{
                //
                // IDSpace not found. A new one will be created
                //
                DBTools.close(null, stmt, rs);

                stmt = conn.prepareStatement(SQL_INSERT_ID);
                stmt.setString(1, nameSpace);
                stmt.setLong(2, 100);//1-99 preserved
                stmt.setInt(3, incrementBy);
                stmt.executeUpdate();
            }
           
            DBTools.close(null, stmt, null);
           
            String updateSql = "update  tb_id set counter=LAST_INSERT_ID(counter + " + incrementBy + ") where idspace = ? ";
            stmt = conn.prepareStatement(updateSql);
            stmt.setString(1, nameSpace);
            stmt.executeUpdate();
            if (supportsSelectForUpdate) {
                conn.commit();
            }
            stmt = conn.prepareStatement("SELECT LAST_INSERT_ID() for update");
            rs = stmt.executeQuery();
            if (rs.next()) {
                value = rs.getLong(1);
            }
            DBTools.close(null, stmt, rs);

        } catch (SQLException e) {

            try {
                if (conn != null && supportsSelectForUpdate) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                //
                // Nothing to do
                //
            }

            throw new DaoException("Error reading the counter: "
                    + nameSpace, e);
        } finally {
            if (conn != null) {
                try {
                    if (supportsSelectForUpdate) {
                        //
                        // Setting the original value for the autocommit
                        //
                        conn.setAutoCommit(isAutoCommit);
                    }
                } catch (SQLException e) {
                    //
                    // Nothing to do
                    //
                }
            }
            DBTools.close(conn, stmt, rs);
        }
        return value;
    }
    /**
     * Initializes the datasource and the queries
     * @throws com.funambol.framework.tools.id.DaoException if an error occurs
     */
    private void init() throws DaoException {
        if (!isInitialized) {
            initQueries();
        }
        isInitialized = true;
    }


    /**
     * Checking the underlying database, initializes the queries to use
     * @throws com.funambol.framework.tools.id.DaoException if an error occurs
     */
    private void initQueries() throws DaoException {
        Connection conn = null;
        try {

            conn = dataSource.getConnection();

            java.sql.DatabaseMetaData dmd = conn.getMetaData();

            supportsSelectForUpdate = dmd.supportsSelectForUpdate();
            dbDriverName = dmd.getDriverName();

            if (DRIVER_NAME_SQLSERVER.equals(dbDriverName)) {
                //
                // Running on SQL Server we have to use SQL_GET_ID_SQLSERVER.
                // Moreover on SQL Server, dmd.supportsSelectForUpdate() returns
                // false but we need to work as the "select for update" (with
                // a different query:
                // select counter from tb_id (UPDLOCK) where idspace=?)
                //
                selectQuery = SQL_GET_ID_SQLSERVER;
                supportsSelectForUpdate = true;

            } else {
                if (supportsSelectForUpdate) {
                    selectQuery = SQL_GET_ID + " for update";
                } else {
                    selectQuery = SQL_GET_ID;
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error checking if the database supports 'select for update'",
                    e);
        } finally {
            DBTools.close(conn, null, null);
        }

    }

    private void setIncrementBy(int incr) {
        if (incr < 1) {
            log.warn("The increment value is smaller than 1 (" + incr + "). Setting it to 1");
            incrementBy = 1;
        } else {
            incrementBy = incr;
        }

    }

}
