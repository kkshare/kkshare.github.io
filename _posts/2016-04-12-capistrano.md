---
layout: post
title: "capistrano"
description: ""
category: "tech"
tags: [capistrano,cloud]
tagline: "2016-04-15"

---
{% include JB/setup %}

[home page](http://www.capify.org/)

0.  前提  
	SSH;POSIX shell在默认目录;相同密码或SSH无密码登录
1.  ruby 一般系统自带1.8.5
2.  rubygems1.3.5 包管理工具gem安装  
    http://rubyforge.org/frs/download.php/60718/rubygems-1.3.5.tgz  
    ruby setup.rb //初始安装(gem update --system 已经安装过gem之后的更新)
3.  安装  
    gem sources -a http://gems.github.com/  
    gem install capistrano
4.  到项目目录中运行  
    capify .      #生成./Capfile ./config/deploy.rb  
    cap shell -f capfile //默认配置文件capfile/Capfile  
    cap -h  
    cap -T  
    cap -vT  
    
capfile文档:  
http://wiki.capify.org  
https://github.com/capistrano/capistrano/wiki/Documentation-v2.x

参考：D:\preference\source-sample\capfile.sample

