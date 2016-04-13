package com.eben4a.common;

import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
*
* @author hetq
*/
public class EbenLoggerFactory {

    public EbenLoggerFactory() {
        super();
    }

    /**
     * The logger instances that have already been created, keyed by logger name.
     */
    protected static Hashtable<String, Logger> instances = new Hashtable();

    private static Logger getLogger(String name)  {
        Logger instance = instances.get(name);
        if (instance == null) {
            instance = newInstance(name);
            synchronized (instances) {
                instances.put(name, instance);
            }
        }
        return (instance);
    }

    public static Logger getHttpLogger() {
        return getLogger("http");
    }
   
    public static Logger getDaoLogger() {
        return getLogger("dao");
    }

    public static void release() {
        instances.clear();
    }

    private static Logger newInstance(String name) {
        Logger instance = Logger.getLogger(name);
        return instance;
    }
}

