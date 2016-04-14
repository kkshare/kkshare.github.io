---
layout: post
title: "用python快速搭建小型webserver"
description: "用python快速搭建小型webserver"
category: "tech"
tags: [python,快速小型webserver]

---
{% include JB/setup %}

## 创建一次性的、快速的小型web服务

下面是一个使用SimpleXMLRPCServer模块建立一个快速的小的文件读取服务器的例子：

    from SimpleXMLRPCServer import SimpleXMLRPCServer 
    def file_reader(file_name): 

       with open(file_name, 'r') as f: 
            return f.read() 

    server = SimpleXMLRPCServer(('localhost', 8000)) 
    server.register_introspection_functions() 
    server.register_function(file_reader) 
    server.serve_forever()
         
## 客户端：

    import xmlrpclib 
    proxy = xmlrpclib.ServerProxy('http://localhost:8000/') 

    proxy.file_reader('/tmp/secret.txt')

## [web.py](http://webpy.org/)

- 把url映射到class 更面向对象
- 相当的小巧，归属于轻量级的web 框架。但这并不影响web.py 的强大，而且使用起来很简单、很直接。在实际应用上，web.py 更多的是学术上的价值，因为你可以看到更多web 应用的底层
- 是公开的，无论用于什么用途都是没有限制的

## [flask](http://flask.pocoo.org/)

- 映射到def 更面向过程
- 适合做超小型项目

[web.py 0.3 新手指南](http://webpy.org/tutorial3.zh-cn)


