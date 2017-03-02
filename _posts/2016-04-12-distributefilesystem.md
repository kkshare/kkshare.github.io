---
layout: post
title: "分布式文件系统"
description: ""
category: tech
tags: [hdfs,mfs,hadoop]
date: "2017-03-02"

---
{% include JB/setup %}

# MFS vs. HDFS

## 相同之处：

1. 这两个文件系统都是类似GoogleFS的实现方式，即一个MasterServer和多个ChunkServer构成的存储集群；
2. 这两个文件系统都存在MasterServer的单点问题;
3. 这两个文件系统追加写模式，也就是说，两者都更加适合“一次写多次读”的模式，如果涉及到数据的修改，那么这个问题就相对比较麻烦了；
4. 由于海量元数据的因素，对待海量小文件都相对比较乏力；
5. 两者都支持在线扩容。

## 差异：

1. HDFS由Java实现，MooseFS由C++实现；
2. HDFS不符合posix语义，MooseFS是完全符合posix语义的，原因在于MooseFS是通过Fuse来通过客户端接口的（Fuse目前已经是标准内核的一部分了），现有使用本地文件系统的程序可以直接平滑迁移到MooseFS上，无需任何修改，但是MooseFS也付出了相应地开销：使用HDFS编写程序时直接使用库（如libhdfs）就可以跟Master或者ChunkServer通信，请求传输更为高效，而MooseFS需要通过标准posix接口将请求发送到内核，再通过Fuse将请求截获发送到用户态，然后才能和Master或者ChunkServer通信；
3. MooseFS提供了快照功能，HDFS目前还没有看到这个方面的实际开发行动；
4. MooseFS针对小文件和随机I/O进行了一些优化；

说到主流这个问题，HDFS具有压倒性的优势：Facebook、Yahoo、阿里、腾讯、百度等等都是使用者，社区也更为活跃，谁让Apache Software Foundation是开源一姐呢？！但是题主也不用灰心，MooseFS的粉丝也不少 Who is using MooseFS，其中当属豆瓣是大哥，1.5PB的数据也证明了MooseFS还是有两把刷子的。
