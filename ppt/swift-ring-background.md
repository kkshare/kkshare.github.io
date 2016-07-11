title: Swift: Ring Background
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# Swift: Ring Background
### [building a consistent hashing ring](http://docs.openstack.org/developer/swift/ring_background.html)
<small>2016-06-27 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
# Concepts
- *Consistent Hashing*: Describe a process where data is distributed using a hashing
  algorithm to determine its location.
- *Ring*: The mapping of hashes to locations.

[slide]
# Part 1: Hashing
- hash = data_id % node_count
- node_count = 2
[magic data-transition="vkontext"]
----
```bash
data_id      | 0,1,2,3,4,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 
node2(hash=1)| 
```
========
```bash
data_id      |  ,1,2,3,4,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 0
node2(hash=1)| 
```
========
```bash
data_id      |  , ,2,3,4,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 0
node2(hash=1)| 1
```
========
```bash
data_id      |  , , ,3,4,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 0,2
node2(hash=1)| 1
```
========
```bash
data_id      |  , , , ,4,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 0,2
node2(hash=1)| 1,3
```
========
```bash
data_id      |  , , , , ,5,6,7,8,9
-------------|--------------------
node1(hash=0)| 0,2,4
node2(hash=1)| 1,3
```
========
```bash
data_id      |  , , , , , ,6,7,8,9
-------------|--------------------
node1(hash=0)| 0,2,4
node2(hash=1)| 1,3,5
```
========
```bash
data_id      |  , , , , , , , ,8,9
-------------|--------------------
node1(hash=0)| 0,2,4,6
node2(hash=1)| 1,3,5,7
```
========
```bash
data_id      |  , , , , , , , , , 
-------------|--------------------
node1(hash=0)| 0,2,4,6,8
node2(hash=1)| 1,3,5,7,9
```
[/magic]

[slide]
# Part 1:Add a new node
- hash = data_id % node_count
- node_count = 2 + 1

```bash
node1(hash=0)| 0,2,4,6,8|
node2(hash=1)| 1,3,5,7,9|
------------------------|-------
node1(hash=0)| 0,3,6,9  |新增节点
node2(hash=1)| 1,4,7    |并调整数
node3(hash=2)| 2,5,8    |据之后

可见大部分数据改变了原来位置(60%),当data_id_count=10,000,000
node_count=100时，约99%数据会移动,却只增加了1%的容量
```

[slide]
# Part 2:Virtual Node
[magic data-transition="vkontext"]
![](/img/swiftring1.png)
========
![](/img/swiftring2.png)
========
![](/img/swiftring3.png)
[/magic]

[slide]
# Part 2:Virtual Node
```bash
data_id_count = 10,000,000
node_count = 100
vnode_count = 1000
```
- 1% capcity added
- *0.9%* of existing data moved
- Virtual Node termed *partition*

[slide]
# Part 3:Partition Power
- The number of real nodes <= partitions. {:&.rollIn}
- Change the number of partitions is difficult.
- The easiest way is to make the limit high enough.
- A good rule might be 100 virtual nodes to each real node.
- Keep the partition count a power of two.(位运算更容易)
- Think about the memory.
 * The only structure this affects is the partition to real node mapping.
 * for 6,000,000 partitions, 2 bytes for each real node id. 12MB of memory just
   isn’t that much to use these days.

[slide]
# Part 4:Durability and Availability
- RAID will increase the durability,it does nothing to increase the availability.
- An easy way: three copies.

[slide]
# Part 4:Shuffle and Zone
- Three copies be inaccessible when n0 goes out
![](/img/swiftzone1.png)
- Two copies be accessible when anyone goes out
![](/img/swiftzone2.png)

[slide]
# Part 5:Weight
- The nodes have diffent capacity.
- Set it to 100.0 is ok.

[slide]
# Summary
![](/img/swiftring.png)

## thanks
