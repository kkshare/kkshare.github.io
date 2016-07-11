title: Swift Deployment Guid
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# [Swift Deployment Guid](http://docs.openstack.org/developer/swift/deployment_guide.html)
<small>2016-06-22 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
## Hardware Considerations
- 不需要也不推荐使用RAID
- proxy services需要比较多的CPU与网络IO
- storage services需要比较多的磁盘IO与网络IO
- Rackspace:proxy部署在业务服务器，所有storage在一台服务器
- proxy是无状态的，需要自己实现负载均衡
## Preparing the Ring
- 推荐每个驱动器至少100个partition，以保证数据的平均分布
- 环数计算：将来支持的最大驱动器数＊100,找到最接近2^n的值
- 推荐3份拷贝，因为是被测试过的
- 推荐至少5个zones,容错性较好

[note]
计算环数：假设集群最多支持5000个驱动器，5000＊100最接近2^19，2^19即为环数  
zone可以按机器、网线、电源、机房、楼宇等区分
[/note]

[slide]
## Preparing the Ring
- building the ring  
```bash
swift-ring-builder <builder_file> create <part_power> <replicas> <min_part_hours>
```
 * <min_part_hours> is the time in hours before a specific partition can be moved in
   succession (24 is a good value for this).
- add devices to the ring
```bash
swift-ring-builder <builder_file> add z<zone>-<ip>:<port>/<device_name>_<meta> <weight>
```
 * <device_name> 比如sdb1
 * <weight> 可以写100.0
- rebalance
```bash
swift-ring-builder <builder_file> rebalance
```

[slide]
## Server Configuration
- /etc/swift/object-server.conf
 * `devices=/srv/node` 设备挂载父目录
 * servers_per_port
 * `disable_fallocate=false`
- /etc/swift/container-server.conf
- /etc/swift/proxy-server.conf
 * bind_ip/bind_port
 * [filter:tempauth] 配置管理员账户:
```bash
user_<account>_<user> = <key> [group] [group] [...] [storage_url]
# 如果带下划线可以用base64编码(不带'='符合)
user64_<account_b64>_<user_b64> = <key> [group] [group] [...] [storage_url]
.reseller_admin = can do anything to any account for this auth
.admin = can do anything within the account
```
[note]
- servers_per_port: This gives complete I/O isolation, drastically reducing the
  impact of slow disks on storage node performance
- disable_fallocate: 对支持fast fail的文件系统有效，如果不支持则关闭
[/note]

[slide]
## Memcached Considerations
- 缓存内容包括认证token，账户和容器的存在信息
- 不缓存对象本身的数据
- 账户信息与容器信息存储在SQLite 
## System Time
- 对服务器很重要(通过时间戳判断对象最新版本)
- 建议用NTP服务
## General Service Tuning
- 单个服务跑一台机器workers=内核数＊2
- 多个服务跑一台机器需要测试，或者workers=内核数

[slide]
## Filesystem Considerations
- 推荐XFS文件系统
 * 性能更佳，通过了完整的稳定性测试,支持扩展属性
 * 设置inode大小很重要，扩展属性存储在此
 * 使用XFS时推荐mount选项
```bash
mount -t xfs -o noatime,nodiratime,nobarrier,logbufs=8 /dev/sda1 /srv/node/sda
```
- 不推荐使用RAID
- 启动器默认挂载在*/srv/node*目录
 * 通过*devices*配置项修改
- /srv/node系统权限 root:root 755
 * 防止rsync将非挂载设备的文件同步到根驱动器

[slide]
# Filesystem Consisderation
- *fallocate* 预分配物理空间的系统调用
 * 对不支持的系统`disable_fallocate=true`
- 不使用updatedb，因会影响性能，edit /etc/updatedb.conf
```bash
PRUNEPATHS="... /tmp ... /var/spool ... /srv/node"
```
[note]
Most current Linux distributions ship with a default installation of updatedb. This tool
runs periodically and updates the file name database that is used by the GNU locate tool
[/note]

[slide]
## General System Tuning
- edit /etc/sysctl.conf
```bash
# disable TIME_WAIT.. wait..
net.ipv4.tcp_tw_recycle=1
net.ipv4.tcp_tw_reuse=1
# disable syn cookies
net.ipv4.tcp_syncookies = 0
# double amount of allowed conntrack
net.ipv4.netfilter.ip_conntrack_max = 262144
```
- 运行`sudo sysctl -p`更新配置
[note]
OS默认让TIME_WAIT端口保持打开60s以确保剩余数据的完整接收，当系统压力大时容易导致端口用尽。
swift已经对网络进行了控制，所以可以关闭TIME_WAIT。
[/note]

[slide]
# Logging Considerations
- *log_facility*选项配置日志目的地
- 推荐syslog-ng
- *custom_log_handlers*配置自定义日志

