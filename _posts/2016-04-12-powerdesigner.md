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

    Tools->database->edit current DBMS->MYSQL50->Script->Objects
        ->Table->Options 或者
        ->Database->Option
    value末尾或头部添加：

    ENGINE = %s : list = BDB | HEAP | ISAM | InnoDB | MERGE | MRG_MYISAM | MYISAM, default = InnoDB
    DEFAULT CHARACTER SET = %s : list = utf8 | gbk, default = utf8
    COLLATE = %s : list = utf8_bin | utf8_general_ci | gbk_bin | gbk_chinese_ci, default = utf8_bin
    
    第一个：存储引擎,第二个：字符集,第三个：带bin是区分大小写，ci不区分
    点击ok保存，回到工作区，双击某表，在：
    Physicial Options中，可以看到刚刚添加的选项，这样就可以按照自己的方式来操作了

## 创建数据库名

右键工程->properties->Database->New

## display

Tools -> Display Preferences... -> 
    General -> Window Color
    Object View -> Reference -> Constraint name

Tools -> Model Options -> Name Convention -> 右侧display中选择显示name还是code

## other

- 生成sql语句 database --> generate database ....
- 导入sql到pd 反向工程
    file --> reverse engineer -->database....-->MYSQL5.0-->Using script files
- 设置 name不自动等于code
    Tools -> General Options -> Dialog -> 将Name to Code mirroring 上的钩去掉
- 把设计图导出成图片
    选择要导出的对象 -> Edit—>Export Image
- 去掉SQL脚本中的双引号
    Database → Edit Current DBMS → General → Script → Sql → Format → CaseSensitivityUsingQuote设为NO
- 使用MySQL的auto_increment
     打开table properties窗口 → columns → 选中id列 → 打开columns properties窗口 → 勾选identity
     注意：概念模型没有此选项，物理模型才有



