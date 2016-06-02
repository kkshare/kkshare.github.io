---
layout: post
title: "hotkey system"
description: ""
category: memo
tags: [hotkey]
tagline: "2016-04-15"
date: "2016-04-18"

---
{% include JB/setup %}

macbook

    Automator->服务->实用工具->开启应用程序->(没有输入-任何应用程序)
    设置快捷键：系统偏好设置－键盘－快捷键－服务－通用
    ctrl-cmd-b 备忘录
    ctrl-cmd-m 邮件
    ctrl-cmd-e 系统设置
    ctrl-cmd-n safari

    cmd-shift-3 全屏截图
    cmd-shift-4 按space，再单击鼠标  窗口截图
    cmd-shift-4 选择区域，释放鼠标    区域截图

    ctrl-cmd-D 屏幕取词

mou

    cmd-] mou多行代码同时缩进tab

## vmware

vmnet1 & vmnet8 配置文件位于 "/Applications/Vmware Fusion.app/Contents/Library" , 可修改默认地址也，vmnet8 还可以添加映射端口

    cd /Library/Preferences/VMware\ Fusion
    cat networking
    cat vmnet1/dhcpd.conf
    cat vmnet8/dhcpd.conf
    cat vmnet8/nat.conf

修改后重启VMware Funsion网络

    /Applications/Vmware\ Fusion.app/Contents/Library/vmnet-cli -c  
    /Applications/Vmware\ Fusion.app/Contents/Library/vmnet-cli -start  
