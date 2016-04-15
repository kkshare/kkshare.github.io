---
layout: post
title: "powerdesigner"
description: "powerdesigner"
category: "tech"
tags: [powerdesigner,实用工具与软件]
tagline: "2016-04-15"

---
{% include JB/setup %}

## 添加DEFAULT CHARACTER SET/COLLATE

    工具栏->database->edit current DBMS
    选中：MYSQL50::Script\Objects\Table\Options 或者 Script\Object\Database\Option
    value末尾或头部添加：
    ENGINE = %s : list = BDB | HEAP | ISAM | InnoDB | MERGE | MRG_MYISAM | MYISAM, default = InnoDB
    DEFAULT CHARACTER SET = %s : list = utf8 | gbk, default = utf8
    COLLATE = %s : list = utf8_bin | utf8_general_ci | gbk_bin | gbk_chinese_ci, default = utf8_bin
    第一个：存储引擎
    第二个：字符集
    第三个：带bin是区分大小写，ci不区分
    点击ok保存，回到工作区，双击某表，在：
    Physicial Options中，可以看到刚刚添加的选项，这样就可以按照自己的方式来操作了

## 创建数据库名

右键工程->properties->Database->New
