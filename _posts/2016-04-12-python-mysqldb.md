---
layout: post
title: "python mysqldb"
description: ""
category: "tech"
tags: [python,mysqldb]
tagline: "2016-04-15"

---
{% include JB/setup %}

## 安装

    yum install mysql-devel
    wget http://pypi.python.org/packages/2.7/s/setuptools/setuptools-0.6c11-py2.7.egg#md5=fe1f997bc722265116870bc7919059ea
    sh setuptools-0.6c11-py2.7.egg
    wget http://jaist.dl.sourceforge.net/project/mysql-python/mysql-python/1.2.3/MySQL-python-1.2.3.tar.gz
    cd MySQL-python-xxx
    python setup.py build
    python setup.py install

## mysqldb

    import os, sys 
    import MySQLdb 
    try: 
       conn=MySQLdb.connect(host=’localhost’,user=’root’,passwd=’’,db=’address’ ,charset='utf8')
       conn.autocommit(True)
    except Exception,e: 
       print e 
       sys.exit() 
    cursor=conn.cursor() 

### 插入多条

    sql=’insert into address(name, address) values(%s, %s)’ 
    param= [(“zhangsan”,”haidian”),(“lisi”,”haidian”)]
    cursor.executemany(sql,param) 

### 插入一条

    sql = "insert into user(name,created) values(%s,%s)"  
    param = ("aaa",int(time.time()))   #元组
    n = cursor.execute(sql,param)

### 更新   

    sql = "update user set name=%s where id=3"  
    param = ("bbb")   
    n = cursor.execute(sql,param)
     
    sql=”select * from address” 
    cursor.execute(sql) 
    data=cursor.fetchall()     #fetchone()
    if data :
       for x in data: 
            print x[0],x[1] 
    cursor.close() 
    conn.close()

*注意*1.插入很多条数据推荐用executemanyl;2.对应的%s没有引号;3.values是一个包含多个元组的数组
     
