---
layout: post
title: "ssl and https"
description: "ssl and https"
category: "tech"
tags: [security,ssl/https]

---
{% include JB/setup %}

[数字证书原理](http://www.cnblogs.com/JeffreySun/archive/2010/06/24/1627247.html)

加密：通过加密算法和公钥对内容(或者说明文)进行加密，得到密文。加密过程需要用到公钥。  
解密：通过解密算法和私钥对密文进行解密，得到明文。解密过程需要用到解密算法和私钥。  
注意,由公钥加密的内容，只能由私钥进行解密，也就是说，由公钥加密的内容，如果不知道私钥，是无法解密的。  

签名：为了防止信息篡改，将发送信息计算hash值，并将hash值加密后(也就是签名)再和信息一起发送。

非对称加密一般流程：  
    收信方生成秘钥对，发送公钥给发信方  
    发信方用公钥加密信息，发送密文  
    收信方用私钥解密  

HTTPS设计原理

- 因为公钥是公开的，仅用私钥加密的数据被监听之后仍然会被解密并泄露
- 所以需要个对称加密算法和密钥来保证后面通信过程内容的安全。
- 但是大家都可以生成公钥、私钥对，客户端无法确认公钥对到底是谁的
- 为解决这个问题使用数字证书，以保证数字证书里的公钥确实是这个证书的所有者(Subject)的，或者证书可以用来确认对方的身份
 
## https

HTTPS其实是有两部分组成：HTTP+SSL/TLS，加密、解密、验证流程：

![](/images/httpsflow.png)

1. 客户端发起HTTPS请求  
用户在浏览器里输入一个https网址，然后连接到server的443端口。
2. 服务端的配置  
采用HTTPS协议的服务器必须要有一套数字证书，可以自己制作，也可以向组织申请。区别就是自己颁发的证书需要客户端验证通过，才可以继续访问，而使用受信任的公司申请的证书则不会弹出提示页面(startssl就是个不错的选择，有1年的免费服务)。这套证书其实就是一对公钥和私钥。
3. 传送证书  
这个证书其实就是公钥，只是包含了很多信息，如证书的颁发机构，过期时间等等。
4. 客户端解析证书  
这部分工作是有客户端的TLS来完成的，首先会验证公钥是否有效，比如颁发机构，过期时间等等，如果发现异常，则会弹出一个警告框，提示证书存在问题。如果证书没有问题，那么就生成一个随即值。然后用证书对该随机值进行加密。

*SSL的位置*  

SSL介于应用层和TCP层之间。应用层数据不再直接传递给传输层，而是传递给SSL层，SSL层对从应用层收到的数据进行加密，并增加自己的SSL头。

    --------------------
           应用层
    --------------------
         SSL或TLS
    --------------------
             TCP
    --------------------
              IP
    --------------------

- 消息完整性：防篡改，基于MD5或SHA的MAC算法来保证消息的完整性，对称加密  
MAC算法是在密钥参与下的数据摘要算法，能将密钥和任意长度的数据转换为固定长度的数据。  
利用非对称加密保证秘钥本身的安全。  
MAC(Message Authentication Code):消息验证码,相当于指纹,用于数据完整性校验  
- 利用PKI保证公钥的真实性

- HTTPS一般使用的加密与HASH算法如下：  

    非对称加密算法：RSA，DSA/DSS
    对称加密算法：AES，RC4，3DES
    HASH算法：MD5，SHA1，SHA256     

OpenSSL是一个开放源代码的SSL协议的产品实现，它采用C语言作为开发语言，具备了跨系统的性能。

## license

Silverlight控件纯客户端注册验证机制

![](/images/license-flow.png)

*优点*  

- 使用私钥签名，公钥验证，能有效防止伪造license文件及分析代码写出注册机（不考虑修改程序逻辑的爆破方式）
- 纯客户端验证不需要跨域访问，不需要控件购买者在服务端部署其他东西

*缺点*  
比直接输入注册码麻烦

#### Flexlm

Flexlm是软件加密方法Flexible License Manager

Flexlm向软件厂商出售相关开发软件，软件厂商把此加密程序集成到自己的软件中。可以锁定机器的硬盘号，网卡号，使用日期。支持加密狗，以保护软件的知识产权，被80%以上的EDA软件公司所采用，是目前最流行的EDA软件加密方法。

证书的内容包括：电子签证机关的信息、公钥用户信息、公钥、权威机构的签字和有效期等等。目前，证书的格式和验证方法普遍遵循X.509 国际标准。