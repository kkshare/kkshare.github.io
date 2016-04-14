---
layout: post
title: "Ehcache vs MemCached"
description: ""
category: "tech"
tags: [java,MemCached]

---
{% include JB/setup %}

| 项目         | Memcache                                         | Ehcache                                |
|--------------|--------------------------------------------------|----------------------------------------|
| 分布式       | 不完全，集群默认不实现                           | 支持                                   |
| 集群         | 可通过客户端实现                                 | 支持（默认是异步同步）                 |
| 持久化       | 可通过第三方应用实现[1]                          | 支持[2]                                |
| 效率         | 高                                               | 高于Memcache                           |
| 容灾         | 可通过客户端实现                                 | 支持                                   |
| 缓存数据方式 | 缓存在memcached server向系统申请的内存中         | 多种方式[3]                            |
| 缓存过期策略 | LRU                                              | LRU(默认)，FIFO，LFU                   |
| 缺点         | 功能不完善，相对于Ehcache效率低                  | 只适用于java体系，只能用java编写客户端 |
| 优点         | 简洁，灵活，所有支持socket的语言都能编写其客户端 | 效率高。功能强大                       |

[1] 如sina的memcachedb，将数据保存到Berkerly DB  
[2] 持久化到本地硬盘，生成一个.data和.index文件。cache初始化时会自动查找这两个文件，将数据放入cache  
[3] 可以缓存在内存（JVM中），也可以缓存在硬盘。通过CacheManager管理cache。多个CacheManager可配置在一个JVM内，CacheManager可管理多个cache  

ehcache是纯java编写的，通信是通过RMI方式，适用于基于java技术的项目。  
memcached服务器端是c编写的，客户端有多个语言的实现，如c，php，python，java（Xmemcached，spymemcached)  
memcached服务器端是使用文本或者二进制通信的。  
memcached的 python客户端没有开源，其他语言的好像都开源了  

http://ehcache.org/

