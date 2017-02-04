title: mytest-适合自己的单元测试
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# mytest
## 适合自己的测试

### <font size=3>2016-11-15 何通庆 [主页](https://kkshare.github.io)</font>

[slide]
# 目标
- 找到适合本部门（小组）的单元测试方法 {:&.rollIn}
- 找到适合自己个人的单元测试方法
- 提高代码质量
- 提高开发效率
- 让开发成为一个很有成就的事情

[note]
这里不包括适合整个公司的测试规范
[/note]

[slide]
# 单元测试优点
- 提高代码质量、减少BUG {:&.rollIn}
- 提升反馈速度，减少重复工作，提高开发效率
- 保证你最后的代码修改不会破坏之前代码的功能
- 让代码维护更容易
- 有助于改进代码质量和设计  
<small>我相信很多易于维护、设计良好的代码都是通过不断的重构才得到的</small>
- 真的有那么好吗？这些优点值得推敲!

[slide]
# 单元测试缺点
- 学习成本比较高:各种框架,框架本身也不断升级,框架本身稳定吗？ {:&.rollIn}
- 增加开发工作量:
  * 测试工作量甚至大于开发，因为一个函数多个测试 {:&.rollIn}
  * 由于代码重构，内部函数经常变化，测试用例也经常变化
  * 代码重构情况是很多的：名称、参数、业务变化、架构变化
  * 开发代码改一处，测试代码改多处
- 推广和运用单元测试需要比较大的投入:培训、制度、考核。效率呢？
- 多大程度提高代码质量？测试程序本身的质量呢？
- 开始<font size=20>头</font>大了

[note]
缺点可能都被一笔带过了，我们要思考的是为什么实践中大家没有这个做或做得不够？  
有一种情形：看代码发现某个函数名称影响阅读与理解，于是想修改，但又想想还有很多单元测试要改就算了吧
[/note]

[slide]
# 适合自己（部门／小组）的测试
- 对公共接口、外部接口测试 {:&.rollIn}
- 使用RESTful接口(curl)
- simpleTest:对没把握的算法、函数进行简单测试
- 熟悉常用工具、脚本(shell/python)
- 熟悉debug/remote debug
- 利用配置文件(config/hosts)
- 考虑本地测试

[slide]
# 对公共接口、外部接口测试
- 公共或外部接口变化小 {:&.rollIn}
- 对代码覆盖率高
- 开发量少，效率高
- 接近最终用户使用场景,更真实反应用户行为

[slide]
# 使用RESTful接口(curl)
- 支持命令行方式测试 {:&.rollIn}
- POST `curl -X POST url -d "id=2&name=x"`
- GET  `curl -G url -d "id=2&name=x"`
- POST JSON `curl -H "Content-type: application/json" -X POST url -d '{"id":"2"}'`
- curl有windows版本，或者使用MobaXterm/cygwin等
- curl可以在服务器测试，排除防火墙问题
- 容易实现自动化测试，各种语言都有httplib实现curl
- 使用脚本的自动化测试:简单不失灵活、容易理解、依赖少
- 便于后续性能测试

[slide]
# simpleTest
- 没有把握的算法、函数进行简单测试
- 比如substr函数、正则表达式匹配等
- 简单测试依赖少，容易编译通过
- javascript也可以写单个页面的测试

[slide]
# 熟悉常用工具与脚本(shell/python)
- 有助于分析日志 {:&.rollIn}
- 有助于制造测试数据  
有些测试数据生产比较困难，用一些工具模拟却很方便
- awk/sort/grep/sed/vim/editplus/notepad...
- python:简洁清晰、胶水语言、丰富强大的库...
- tcpdump/wireshark

[slide]
# 熟悉debug/remote debug
- 对复现困难的BUG {:&.rollIn}
- 本地环境不满足要求
- remote debug(netbeans)   
edit ${TOMCAT_HOME}/bin/catalina.sh
```
set JPDA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
set JAVA_OPTS=%JAVA_OPTS% %JPDA_OPTS%
```
netbeans->调试->连接调试器->port=8787

[slide]
# 利用配置文件
- 可以设置开关测试模式
- 配置文件可用文本工具查看，容易发现错误
- 善用hosts
- 改代码风险
 * 编辑器bug
 * 编译环境不一样
 * 重新上传代码的网络问题
 * 对其他代码的影响
 * 版本号问题可能导致传错文件
 * 过多的版本号增加管理复杂度

[slide]
# 考虑本地测试
- 开发人员需要在本地部署所有依赖组件 {:&.rollIn}
 * 开发人员对整个系统更加熟悉,并能够更全面的考虑问题
 * 使团队的协作也更紧密，整个系统更加简单、稳定
 * 有利于解决人员备份问题
- 提高开发效率
 * 方便调试
 * 方便增加临时日志、临时代码
 * 不干扰别人测试，也不受别人测试干扰
 * 不需要上传文件，避免网络问题的影响
 * 提高svn的代码的质量，避免svn代码的碎片化

[slide]
# 其他问题
- 制造测试数据无法立即生效 {:&.rollIn}
 * 提前制造测试数据，并对测试数据进行特殊(前缀)标记
 * 程序对特殊标记的测试数据进行特殊处理
- 计算复杂，需要比较长时间才能输出结果
 * 提高程序性能，多线程或多进程处理
 * 如果可以的话用少量数据测试
- 输出结果复杂，需要人工参与验证:趋势法
 * 第一个用例的输入输出进行人工确认
 * 微调第一个用例的输出，分析输出变化的趋势

[slide]
# 测试举例
```
main(){
    allTest();
    //testLogin(param);
}
void allTest(){
    before();//准备测试数据
    testCreateUser(param1);
    testCreateUser(param2);
    testDeleteUser(param2);
    testLogin(param1);
    ...
    after();//清除测试数据
}
```
与单元测试区别
- 方便测试参数边界(也可用JUnit的参数化测试)
- 方便进行性能测试
- 制造测试数据更加容易
- 容易了解系统整体业务流程

[note]
制造测试数据更加容易体现着以下方面：
1. 直接调用已经封装好的接口
2. 所有用例共享一份制造的数据，避免重复制造

# 不断改进
- facebook：让亲身实践者执行工作流程 {:&.rollIn}
 * 个人制定并执行的工作流程，会针对真实工作的情况进行更多优化调整。
管理者设计的工作流程，最多能接近实际工作流程，它需要管理、优化或整理。
这是许多愚蠢、低效的工作流程的来源。
 * 在人们亲自制定工作流程时，会感到更大的自主权。
在今后，随着情况不可避免地变化，也就更有权视情况对其调整，而不是任其僵化。
外部强加的（自上而下的）工作流程更加难以打破，而且往往会被神化，从而产生非常大的组织惯性。
[/note]

[slide]
# 目标
- 找到适合本部门（小组）的单元测试方法
- 找到适合自己个人的单元测试方法
- 提高代码质量
- 提高开发效率
- 让开发成为一个很有成就的事情

 * end of ppt,thanks! {:&.rollIn}
