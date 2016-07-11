title: Swift Concepts
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# Swift Concepts
## [swift documentation](http://docs.openstack.org/developer/swift/index.html)
<small>2016-06-27 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
# swift 是什么？
- 开源，Apache 2.0 License, 商业友好
- 创建可扩展的、冗余的、对象存储（引擎） 
- 存储 PB 级可用数据。但它并不是文件系统，实时的数据存储系统
- 看起来更像是一个长期的存储系统
 * 比如：虚拟机镜像，图片存储，邮件存储，文档的备份
- 没有“单点”或者主控结点,具有更强的扩展性、冗余和持久性

[slide]
# swift 能做什么?
- 长于存储非结构化数据，大、小文件性能据说都很好
 * adrian otto 说测试过10亿个 1byte 数据
- Swift 作为对象存储已经很成熟，连 CloudStack 也支持它

# swift 不能做什么?
- Not a Filesystem
 * 使用REST API 而不是open(),read(),write(),seek()等。
- No File Locking ：干脆不支持“文件锁”。
 * 在 swift 中，“锁”的概念是没有必要的。
- No Directory Hierarchies
- Not a Database

[slide]
# Container
- You can use the container to control access to objects by using an access control
  list (ACL). You cannot store an ACL with individual objects.
- Configure and control object versioning, at the container level.
- You can bulk-delete up to 10,000 containers in a single request.

[slide]
# Object
- Store an unlimited number of objects. Each object can be as large as 5 GB, which is the
  default. You can configure the maximum object size.
- Use cross-origin resource sharing to manage object security.
- Compress files using content-encoding metadata.
- Schedule objects for deletion.
- Bulk-delete up to 10,000 objects in a single request.
- Auto-extract archive files.
- Generate a URL that provides time-limited GET access to an object.
- Upload objects directly to the Object Storage system from a browser by using form POST
  middleware

[slide]
# Object
- url format:`/v1/{account}/{container}/{object}`
- marker/end\_marker用法
```bash
# objects=[a, b, c, d, e]
/v1/{account}/{container}/?marker=a&end_marker=d
b
c
/v1/{account}/{container}/?marker=d&end_marker=a&reverse=on
c
b
``` 

[slide]
# Concepts
- Ring
 * 用于记录存储对象与物理位置间的映射关系
 * 信息包括 Zone、Device、Partition、Replica
 * 每个存储策略对应一个ObjectRing
- Updater
 * 对象无法更新时会被系列化到本地文件系统中进行排队
 * 服务恢复后会进行异步更新
- For Erasure Code type policies, the Proxy Server is also responsible for encoding and
  decoding object data.
- A large number of failures are also handled in the Proxy Server.

[note]
对象无法更新可能是高负载。
[/note]

[slide]
# Concepts
- Storage Policies
 * Each Storage Policy has an independent object ring.
 * Each container can be assigned a specific storage policy when it is created.
- Object Server
 * ext3 have xattrs turned off by default.
 * object name + operation's timestamp
 * A 0 byte file ending with ”.ts”, which stands for tombstone
 * Tombstone ensures that deleted files are replicated correctly and older versions don’t
   magically reappear due to failure scenarios.

[slide]
# Concepts
- Container Server
 * It doesn’t know where those object’s are, just what objects are in a specific
   container. 
- Account Reaper
 * Removes data from deleted accounts in the background.
- TempAuth allows account-level ACLs.
 * is written as wsgi middleware, so implementing your own auth is as easy as writing new
   wsgi middleware
- swift-object-expirer: offers scheduled deletion of objects.

[slide]
# Concepts
- Backing Store Schemes
 * Single Tenant
 * Multi Tenant
 * Service Prefix Account:user-token + service-token

