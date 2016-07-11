title: Swift Adminitrator Guid
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# [Swift Administrator Guid](http://docs.openstack.org/developer/swift/admin_guide.html)

<small>2016-06-27 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
# Defining Storage Policies
- High level view of steps it takes to configure policies
 * Define your policies in /etc/swift/swift.conf
 * Create the corresponding object rings
 * Communicate the names of the Storage Policies to cluster users
- Configuring more than one storage policy on your development environment is recommended
  but optional
- Policies are implemented at the container level
- Policies are assigned when a container is created.(by optional header)
- Once a container has been assigned a policy, it cannot be changed.
```bash
curl -v -X PUT -H 'X-Auth-Token: <your auth token>' -H 'X-Storage-Policy: gold' http://127.0.0.1:8080/v1/AUTH_test/myCont0
```

[slide]
# Container Reconciler
- What would happen if 2 containers of the same name were created with different Storage
  Policies on either side of a network outage at the same time? 


[note]
# Erasure Code
- 纠删码:将数据分割成不可识别的数据块，使用额外的信息追加到每个数据块中，允许从一些数据块
  的子集就可以复原完整的数据集
- 有内置的数据安全性，因为每个独立的数据块不包含足以泄露原始数据集的信息
- 更适合用于大数据集，特别适合云计算和分布式存储，因为它不用复制数据集就可以跨多个地理位置
  分布数据
- 替代RAID
- PyECLib: External Erasure Code Library
- ref: http://docs.openstack.org/developer/swift/overview_erasure_code.html
[/note]


