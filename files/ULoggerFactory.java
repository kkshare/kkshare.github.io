package com.cnc.common

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kk
 */
public class ULoggerFactory {

    /**
     * The prefix of all loggers
     */
    public static final String LOGGER_BASE_NAME = "smart";

    static{
        String SMART_HOME = System.getProperty("smart.home");
        String path = SMART_HOME+"/config/log4j.properties";
        PropertyConfigurator.configure(path);//指定配置文件路径
        PropertyConfigurator.configureAndWatch(path, 30000);//动态修改无需重启立即生效
    }
    
    public ULoggerFactory() {
        super();
    }

    public static Logger getLogger(String name) {
        if (name != null && !name.equals("root") && !name.startsWith(LOGGER_BASE_NAME)) {
            StringBuffer sb = new StringBuffer(LOGGER_BASE_NAME);
            sb.append('.').append(name);
            name = sb.toString();
        }
        return LoggerFactory.getLogger(name);
    }

    public static Logger getSmartLogger() {
        return LoggerFactory.getLogger("smart");
    }

    public static Logger getIfaceLogger() {//调用外部接口相关日志
        return LoggerFactory.getLogger("smart.iface");
    }

}
