---
layout: post
title: "CLOSE_WAIT与端口用尽"
description: "CLOSE_WAIT与端口用尽"
category: "tech"
tags: [socket,tcp-ip]

---
{% include JB/setup %}

CLOSE_WAIT状态太多说明有异常发生，出现的原因是被动关闭方未关闭socket造成

一、编程解决  
调用accept/read方法是用setSoTimeout()设置超时（默认设置是0，即永远不会超时）

二、系统配置规避

### 设置open files

    /proc/sys/fs/file-max 整个系统可以打开的文件数的限制，由sysctl.conf控制；(大于65536一般不需要修改该值)
    ulimit修改的是当前shell和它的子进程可以打开的文件数的限制，由limits.conf控制；
    ulimit -a     #查看open files（默认是1024)

vi /etc/security/limits.conf

    #所有的用户每个进程可以使用8192个文件描述符
    #<domain>      <type>     <item>         <value>
    *         soft    nofile    8192
    *         hard    nofile    8192
    #domain列表示用户，*表示所有用户，设置的数值与硬件配置有关，别设置太大了

### 使这些限制生效

确定文件/etc/pam.d/login 和/etc/pam.d/sshd包含如下行：

    session required pam_limits.so

然后用户重新登陆一下即可生效。

### 修改tcp_keepalive_*系列参数

若服务器不主动关闭socket，CLOSE_WAIT默认位置7200秒(2小时)

    cat /proc/sys/net/ipv4/tcp_keepalive_* 查看参数值

*暂时生效(重启失效)*

    sysctl -w net.ipv4.tcp_keepalive_time=1200 
    sysctl -w net.ipv4.tcp_keepalive_probes=3
    sysctl -w net.ipv4.tcp_keepalive_intvl=5

NOTE:Linux的内核参数调整的是否合理要注意观察，看业务高峰时候效果如何。

*永久生效*

vi /etc/sysctl.conf
若配置文件中不存在如下信息，则添加：

    net.ipv4.tcp_keepalive_time = 1800
    net.ipv4.tcp_keepalive_probes = 5
    net.ipv4.tcp_keepalive_intvl = 15

重启network之后synsctl.conf才会生效

    /etc/rc.d/init.d/network restart

然后，执行sysctl命令使修改生效，基本上就算完成了。

