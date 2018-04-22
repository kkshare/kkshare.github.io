title: java 日志
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# java 日志

### <font size=3>2017-03-25 何通庆 [主页](https://kkshare.github.io)</font>

[slide]
## 我们关心的问题
- 可分开:功能上、时间上
- 配置:日志级别、动态修改、其它
- 管理:自动归档、定时清理
- 系统的稳定、健壮

[slide]
## WHY NOT System.out.println
----
- 输出到控制台的速度比输出到文件系统的速度要慢
- 格式不规范：没有时间、level、source...
- 调试完成无法关闭，或忘记删除
- 关闭后无法再次打开，无法在线上调试
- 输出容易被忽略，导致无法登录服务器的危险
```
如果不重定向，输出内容会以邮件的形式发送给用户，内容存储在邮件文件：
/var/spool/mail/$user
如果输出内容较多，会使这个邮件文件不断追加内容，文件越来越大。
而邮件文件一般存放在根分区，根分区一般相对较小，
所以会造成根分区写满而无法登录服务器。
```
[slide]
## WHY NOT e.printStackTrace
----
- 有System.out.println的所有问题
- 剥夺了上层应用处理处理异常的机会
- 出现无法预知的问题
 * 请使用 {:&.rollIn}
```java
try{
    //...
}cache(Exception e){
    logger.error("msg",e);
    throw new ServiceException("msg",e[,status]);
}
```
[slide]
## log4j基本概念
----
- logger是源，appender是目标
![](../../images/loggerAndAppender.png)

[slide]
## 配置实例
```
log4j.rootLogger=INFO,console
log4j.logger.smart=INFO,smart #第一个smart是logger，第二个smart是appender
log4j.logger.com.cnc.dna=INFO,dna
log4j.additivity.smart=false  #false:阻止回流
# appender for console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Encoding=UTF-8
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %c %-5p: %m%n
# appender for smart
log4j.appender.smart=org.apache.log4j.DailyRollingFileAppender
log4j.appender.smart.File=./logs/smart.log
log4j.appender.smart.DatePattern='.'yyyy-MM-dd
log4j.appender.smart.layout=org.apache.log4j.PatternLayout
log4j.appender.smart.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %c %-5p: %m%n
# appender for dna
...
```
- log4j.logger.smart=INFO,smart {:&.flexbox.vleft}
 * 第一个smart是logger，第二个smart是appender

[slide]
## 提问
----
```java
private static final Logger log = LoggerFactory.getLogger("smart");
```
- "smart"是logger还是appender ？ {:&.flexbox.vleft}

[slide]
## 指定配置文件
----
```
static{
     String path = ConfigUtil.SMART_HOME+"/config/log4j.properties";
     PropertyConfigurator.configure(path);//指定配置文件路径
     PropertyConfigurator.configureAndWatch(path, 30000);//动态修改无需重启立即生效
}
```
[slide]
## logger规划
----
- LoggerFactory.getLogger(ClassName.class)
```
2016-11-23 09:19:45.820 WARN [com.cnc.portal.metrics.report.GDCollectorReporter] msg...
2016-11-23 09:19:45.820 WARN [com.cnc.portal.msgn.member.cluster.Heartbeat.logErrorMessage] msg...
2016-11-23 09:19:45.820 WARN [om.cnc.portal.msgn.member.client.ClientConnection.error] msg...
```
- OR LoggerFactory.getLogger(<常量>)?
```
2016-11-23 09:19:45.820 WARN [smart] msg...
2016-11-23 09:19:45.820 WARN [smart] msg...
2016-11-23 09:19:45.820 WARN [smart] msg...
```
[slide]
## logger规划
```
public class ULoggerFactory {
    public static final String BASE_NAME = "smart";
    public ULoggerFactory() { super(); } 
    public static Logger getLogger(String name) {
        if (name != null && !name.equals("root") && !name.startsWith(BASE_NAME)) {
            StringBuffer sb = new StringBuffer(LOGGER_BASE_NAME);
            sb.append('.').append(name);
            name = sb.toString();
        }
        return LoggerFactory.getLogger(name);
    }
    public static Logger getSmartLogger() { return LoggerFactory.getLogger("smart"); } 
}
```
- 避免忘记写BASE_NAME问题 {:&.flexbox.vleft}
 * Thanks! (The End) {:&.rollIn}
