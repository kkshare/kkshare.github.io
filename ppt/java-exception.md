title: java exception
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# Java异常
----
- 为何要关注异常
- 异常机制与分类
- Exception vs. Error
- 检查异常 vs. 非检查异常
- 常见问题
- 设计经验

 * <font size=3>2016-11-15 何通庆 [主页](https://kkshare.github.io)</font>

[slide]
## 为何要关注异常
----
- 异常不是小问题
- ![](http://www.ctocio.com/wp-content/uploads/2014/09/backblaze-ctocio.jpg)
- [Backblaze对其数据中心38000块硬盘的统计](http://www.ctocio.com/ccnews/16730.html)
[note]
小问题:影响小+小概率
[/note]

[slide]
## 为何要关注异常
- 我们身边异常每天都在发生
 * 设备多 {:&.rollIn}
 * 数据量大
 * 持续运行
 * 38000 * 2%=760 平均每天多于两块硬盘故障
 * 故障率 CPU>MEM>DISK>MAINBORD

[slide]
## 异常机制
----
- 提供程序退出的安全通道
- 程序的控制权转移到异常处理器
 * 方法立即结束,不返回值,同时抛出一个异常对象
 * 调用该方法的程序也不会继续执行下去

[slide]
## 异常分类
----
![](../../images/java-exception.jpeg)

[slide]
## Exception vs. Error
----
- Error:合理的应用程序不应该试图捕获的严重问题
- Exception:合理的应用程序想要捕获的条件

[slide]
## 检查异常 vs. 非检查异常
----
- 检查异常(Checked Exception)
 * 继承Exception
 * 必须通过try/catch处理，或者throws抛出，否则编译出错
- 非检查异常(Unchecked Exception)
 * 继承RuntimeException
 * 可以不经过try/catch处理，或者throws抛出
 * 一般由系统处理
 * 也可以由用户处理(主要针对业务系统，抛出友好的提示)

[slide]
## 常见问题
----
- 日志打印
 * 是否需要打印堆栈信息，哪个逻辑层打印日志
- 异常信息丢失
 * catch了未抛出，未打印日志等
- 未处理的RuntimeExceptoin
- 过度使用异常
- finally块中的异常处理
- *需要综合考虑功能、性能、可读(简洁)*

[slide]
## 过度使用异常
----
```bash
//方法1:
return new Response(status,code,msg1,msg2,obj1);
//方法2:
try{
    throw new MyException(status,code,msg1,msg2,obj1);
}catch(MyException e){
    return new Response(e.status,e.code,e.msg1,e.msg2,e.obj1);
}
```
- 原因：不愿意编写处理错误代码时抛出一个异常即可(方便)
- 对完全已知的错误，应编写处理这种错误的代码，增加鲁棒性
- *异常机制的效率很差*
- 方法2使异常设计复杂

[slide]
## 过度使用异常
- 关键是区分系统异常与业务异常
- 系统异常
 * 软件的缺陷，客户端对此通常无能为力
 * 通常报500错误即可
 * 一般是非检查异常
- 业务异常
 * 用户未按正常流程操作导致的异常
 * 非500错误
 * 通常是检查异常
- 建议：非500错误能不通过异常处理尽量不要通过异常方式

[slide]
## 设计经验-异常消息
----
```java
//方式1:
class Constants{
    public final static String MSG1 = "msg1";
    public final static String MSG2 = "msg2";
    public final static String MSG3 = "msg3";
}

throw new ServiceException(Constants.MSG1,400);//400表示状态码

//方式2:
Msg1Exception extends Exception{msg="msg1"}
Msg2Exception extends Exception{msg="msg2"}
Msg3Exception extends Exception{msg="msg3"}
```
- 方式一减少了异常类的维护,方便跟踪定位异常
- 如果信息需要展示用户，属于文案范畴，文案易变动，最好不要跟程序混在一起
- 如果信息属于多个组件共享内容，也应该属于文档范畴
[note]
解决方法： 
- 错误提示的文案统一放在一个配置文件里，根据异常类型获取对应的错误提示信息，若需要支持国际化还可以提供多个语言的版本
- 如果不能放到配置文件里，定义成常量放在同一个类中也是可以的
[/note]

[slide]
## 设计经验
- 异常设计需要统一规划
- 打印日志：只在一个地方打印日志，不重复打印日志。不遵守契约的异常不打印堆栈信息
- 检查型异常和非检查型异常
  * 如果希望客户程序员有意识地采取措施，那么抛出检查型异常。
  * 使用非检查异常代码会比较简洁(不需要throws,减少很多try/catch)

[slide]
## 设计经验(例子)
----
```java
public class Status {
    public int status;
    public String code;
    
    public static Status OK = new Status(200,"OK");
    public static Status NOT_FOUND = new Status(404,"NOT FOUND");
    public static Status REQUEST_TIMEOUT = new Status(408,"REQUEST TIMEOUT");
    public static Status RETRY_LATER = new Status(417,"RETRY LATER");
    public static Status SREVER_INTERNAL_ERROR = new Status(500,"SERVER INTERNAL ERROR");
    public static Status PROCESSING_ERROR = new Status(506,"PROCESSING ERROR");
}
public class ServiceException extends Exception {
    private Status status;
    public Status getStatus() { return status; }

    public ServiceException(String msg);
    public ServiceException(String msg, Status status);
    public ServiceException(String msg, Throwable cause);
    public ServiceException(Throwable cause);
    public ServiceException(String msg, Throwable cause, Status status);
}
```

* Thanks! (The End)
