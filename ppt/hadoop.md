title: hadoop与hbase经验总结
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# hadoop与hbase经验总结
<small>2010 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
# hadoop v0.20.0
[slide]
# mapreduce流程(worcount)
1. 每个map任务使用默认的TextInputformat类的LineRecordReader方法按行读取文件，这个读取的行数据就被交给map函数去执行
2. 如果有combine，先对第一步的输出结果就行combine操作。Combine就是个小reduce操作
3. 每个map对自己的输出文件进行patition操作。生成10个文件，假设是r1、r2….r10这10个文件
4. copy文件：reducer1会从100台map机器上取到所有r1文件，reducer2取所有r2的文件...
5. 每个reducer合并（meger）自己取到的文件，reducer1就是合并100个r1文件（实际过程是在上面第4步操作中会边copy边meger，在内存中）。
6. 合并好后进行下sort（排序）操作，再次把不同小文件中的同一个单词聚合在一起。作为提供给reduce操作的数据。
7. 进行reduce操作，对同一个单词的value列表再次进行累加，最终得到某个单词的词频数。
8. Outputformat操作，把reduce结果写到磁盘。

[slide]
# mapreduce流程
- map阶段
 * Inputformat—>map—>(combine)—>partition
- reduce阶段
 * copy&merge—>sort—>reduce—>outputformat
 * copy&merge阶段也称为shuffle阶段

- 总结：reduce有代价，能不用就不用(如果map能搞定)

[slide]
# 经验总结
- 使用LZO
 * 减小数据大小
 * 减少磁盘读写时间
 * 支持分块使hadoop可以并行处理
- 使用ECC内存,关闭swap
 * 一般情况故障率：CPU>MEN>DISK>MAINBORD
 * 但ECC内存的错误率远低于磁盘坏道率
 * 所以如果磁盘不是RAID，则关闭swap分区
- 使用继承，便于将来程序升级
- 完整阅读开发、部署、管理等文档
 * du.reserved(文件系统保留空间),RackAware,HEAPSIZE
- hadoop针对具体项目有很大的优化(流程优化与减少拷贝)

[slide]
# 经验总结
- 使用隐藏文件
 * FileInputSplit/RecordReader读取文件时排除一些文件
 * 隐藏文件作为特殊配置或其它用途

```java
PathFilter filter = new PathFilter()
{
    public boolean accept(Path p) 
    {
        String name = p.getName();
        return !name.startsWith("_") && !name.startsWith(".");
    }
};

FileStatus[] split_dirs = fs.listStatus(new Path(root), filter);
```

- 使用多路归并算法提高效率(N\*logN)

[slide]
# HBase经验总结(0.20.6)
- HBase的写效率还是很高的，但随机读取效率不高。优化措施
 * 启用lzo压缩
 * 增大hbase.regionserver.handler.count数为100
 * 增大hfile.block.cache.size为0.4，提高cache大小
 * 增大hbase.hstore.blockingStoreFiles为15
 * 启用BloomFilter，在HBase0.89中可以设置
 * Put时设置setAutoFlush=false，到一定数目后再flushCommits
- 提高入库效率:LZO；ECC内存，关闭swap
- 有一个BUG需要注意:删除数据后立即插入“相同时间撮”的数据可能导致失败，因为数据major compaction之前都被标记删除

[slide]
#Thanks
