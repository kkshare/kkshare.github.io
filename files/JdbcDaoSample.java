package com.eben4a.dao;

import com.eben4a.common.DBTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
*
* @author hetq
*/
public class JdbcDaoSample {
    private DataSource ds = null;
    private static JdbcDaoSample instance = null;

    private JdbcDaoSample() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/aaaa");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
    public synchronized static JdbcDaoSample i(){
        if (instance == null){
            instance = new JdbcDaoSample();
        }
        return instance;
    }

    public Map getData(String sn) throws DaoException{
        Map map = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT eid,serial_no FROM p_handset WHERE serial_no=?";
            conn = ds.getConnection();
            stat = conn.prepareStatement(sql);
            stat.setString(1, sn);
            rs = stat.executeQuery();
            if(rs.next()){
                map.put("eid", rs.getInt(1));
                map.put("serial_no", rs.getString(2));
            }
        } catch (Exception ex) {
            String errmsg = "get data error sn="+sn;
            ex.printStackTrace();
            throw new DaoException(errmsg, ex);
        } finally {
            DBTools.close(conn, stat, rs);
        }
        return map;
    }

     public void setData(Map map) throws DaoException {
        String sql = "UPDATE p_handset SET uid=?,name=? WHERE serial_no=?";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, (Integer)map.get("uid"));
            stat.setString(2, (String)map.get("name"));
            int n = stat.executeUpdate();
            System.out.println("affected rows:"+n);
        } catch (Exception ex) {
            String errmsg = "set data error uid="+map.get("uid");
            ex.printStackTrace();
            throw new DaoException(errmsg, ex);
        } finally {
            DBTools.close(conn, stat, rs);
        }
    }

     public void newData(Map map) throws DaoException {
        String sql = "insert into table1(f1,f2)values(?,?)";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            stat = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stat.setInt(1, (Integer)map.get("f1"));
            stat.setString(2, (String)map.get("f2"));
            int n = stat.executeUpdate();
            System.out.println("affected rows:"+n);
            rs = stat.getGeneratedKeys();
            if(rs.next()){
                int newid = rs.getInt(1);
                System.out.println("new data with new id="+newid);
            }
        } catch (Exception ex) {
            String errmsg = "new data error uid="+map.get("uid");
            ex.printStackTrace();
            throw new DaoException(errmsg, ex);
        } finally {
            DBTools.close(conn, stat, rs);
        }
    }

}
