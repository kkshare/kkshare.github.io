---
layout: post
title: "python闭包与匿名函数lambda"
description: "python闭包与匿名函数lambda"
category: "tech"
tags: [python,闭包/匿名函数/lambda]

---
{% include JB/setup %}

python中使用闭包时一段经典的错误代码（python规则指定所有在赋值语句左面的变量都是局部变量）

    def foo(): 
       a = 1 
       def bar(): 
            a = a + 1 #变量a在赋值符号"="的左面，被python认为是bar()中的局部变量,右边的a也认为是bar()中的局部变量
            return a 
       return bar

解决方法：

    def foo(): 
       a = [1] 
       def bar(): 
            a[0] = a[0] + 1 
            return a[0] 
       return bar

## lambda     匿名函数

    f = lambda x,y:x+y     #冒号前是参数，可以有多个，用逗号隔开，冒号右边的返回值
    print f(2,3)     #5
    在filter, map, reduce中方便使用
    foo = [2, 18, 9, 22, 17, 24, 8, 12, 27]
    print filter(lambda x: x % 3 == 0, foo)     #[18, 9, 24, 12, 27]
    print map(lambda x: x * 2 + 10, foo)     #[14, 46, 28, 54, 44, 58, 26, 34, 64]
    print reduce(lambda x, y: x + y, foo)     #139
    print reduce(lambda x,y:x*y,range(1,1001))     #1000的阶乘

闭包作用：类的简单实现；根据参数返回不同的函数  
修饰器作用：函数闭包最常用的功能之一，让你可以暂时放下那些繁琐的初始化环境，比如你可以把函数传入一个装饰器使得它自动携带一个连接

