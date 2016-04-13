---
layout: post
title: "berkeleyDB"
description: ""
category: "tech"
tags: [berkeleyDB,database]

---
{% include JB/setup %}

http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/documentation/index.html

    tar zxfv db-4.x.tgz
    cd db-4.x/build_unix
    env CC=gcc CCC=g++ ../dist/configure 或#vi /dist/configure 在最前面添加:CC gcc
    make
    make install
    echo "/usr/local/BerkeleyDB.5.3/lib" >> /etc/ld.so.conf     #将lib路径加到该文件的最后一行
    ldconfig     #更新系统

工具介绍 http://docs.oracle.com/cd/E17076_04/html/api_reference/C/utilities.html

    db_archive –s #确定哪些是数据库文件，在数据文件目录下
    db_archive #不带任保参数，列出不再需要的日志文件名
    db_archive –l #列出所有日志文件名
    db_checkpoint #用来手工checkpoint的工具
    db_recover #不带任务参数，表示用正常恢复方法恢复数据库
    db_recover –c #用于热恢复用的
    db_reover –t #可以把数据库恢复到指定时间的状态
