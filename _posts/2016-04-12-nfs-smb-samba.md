---
layout: post
title: "nfs smb samba"
description: ""
category: "tech"
tags: [nfs/smb/samba]
tagline: "2016-04-15"

---
{% include JB/setup %}

SMB(Server Messege Block): 实现Windows文件和打印共享的基础协议，后来在*nix下通过samba实现了smb协议

NFS：内核级别实现，通过NFS协议import的分区，可以当成本机的磁盘来使用。效率上比SMB要好，但是仅仅能实现文件读写,没有打印服务。

Win有S4U，也可以支持NFS  
windows平台下的nfs服务端软件haneWinNFS  
准确的说，NFS不是和Samba比，而是CIFS

装了samba的unix主机可以让unix主机和win主机访问,相同于网上邻居,  
装了nfs的unix主机只能让unix主机访问,win主机不能用nfs访问

### NFS服务器

    1、 # rpm –qa | grep nfs 检查软件包NFS是否安装
    2、 配置NFS服务器。用任何文本编辑器配置文件/etc/exports,来确定需要给客户共享的目录。
    3、 启用NFS服务。分两步：首先启用portmap,然后启用NFS服务。

        service portmap start
        service nfs start

    其中portmap的功能是启用远程过程调用，有时启用NFS不能成功，不妨检查一下portmap服务是否启动（# ps aux | grep portmap）。
    4、 在客户端挂接NFS共享出来的目录。(挂接目录 192.168.100.100:/tmp 到本地 /mnt/nfs )

        mount –t nfs 192.168.100.100:/tmp /mnt/nfs

    5、 访问NFS共享资源。
    6、 卸载NFS文件系统。

        umount /mnt/nfs

    7、 其他事项。自动实现对NFS的挂接操作：修改文件/etc/fstab,把挂接项插入进去就可以了
    automounter：自动卸载NFS挂接，避免占用有限的系统和网络资源

### Samba服务器

linux给windows用户提供文件共享的工具Samba

    1、 # rpm –qa | grep smb 检查是否安装samba软件包
    2、 配置文件/etc/samba/smb.conf 对于一般的应用而言，基本上不用修改这个文件。
    3、 添加系统账户。smb的访问必须使用系统账号进行# useradd sery , # passwd sery
    4、 建立Samba用户密码文件。虽然samba的用户是系统用户，但出于安全考虑，samba用户的密码并非创建系统用户时设定的用户密码。为了生成smb所需的密码，应该进行下面的操作：

        cat /etc/passwd | mksmbpasswd.sh > /etc/samba/smbpasswd
        smbpasswd sery //为系统用户设置smb口令
        chown root.root /etc/samba/smbpasswd
        chmod 600 /etc/samba/smbpasswd //不准别的用户访问

    5、 启用Samba服务器。# service smb start
    6、 检查服务是否正常启动。# service smb status 或者 # ps aux | grep smb。
    7、 windows客户端访问 Samba服务器共享目录。在windows环境下，右键点击“网上邻居”图标，然后左击“搜索计算机”
    8、 其他。上述配置的samba服务器，用户的访问共享目录是系统账号的主目录。要想把共享目录设置到其他位置，修改Samba 的配置文件/etc/samba/smb.conf文件即可。

