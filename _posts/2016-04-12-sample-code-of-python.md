---
layout: post
title: "sample code of python"
description: "sample code of python"
category: tech
tags: [python,sample code]

---
{% include JB/setup %}

## types 

    import types
    if type(dict) == types.DictType or type(d) == types.StringType:
         print 'ok'

## base64 

    import base64
    base64.b64encode('123456')
    base64.decodestring('MTIzNDU2')

    range(0,10,2) #[0, 2, 4, 6, 8]
    range(0,10) #[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

## 命令行参数解析 

    from optparse import OptionParser

    usage = "usage: %prog [options]"     #%prog 会被optparse用当前程序名代替
    parser = OptionParser(usage)
    parser.add_option("-d", "--debug", dest="debug", action="store_true", default=False, help="is print debug message")
    parser.add_option("-f", "--file", dest="filename",type="str", help="input from this file")
    (options, args) = parser.parse_args()
    print options.debug
    print options.filename

    不需要添加 -h/--help, 已经自动添加
    type可以是int,string等

## 拷贝 

    import copy

    x = copy.copy(y)
    w = copy.deepcopy(y)

## Counter 

    from collections import Counter 
    c = Counter('hello world')      #Counter({'l': 3, 'o': 2, ' ': 1, 'e': 1, 'd': 1, 'h': 1, 'r': 1, 'w': 1}) 
    c.most_common(2)     #[('l', 3), ('o', 2)]

## json 

    import json

    >>> data = {"status": "OK", "count": 2, "results": [{"age": 27, "name": "Oz"}, {"age": 29, "name": "Joe"}]}
    >>> print(json.dumps(data))  # No indention
    {"status": "OK", "count": 2, "results": [{"age": 27, "name": "Oz"}, {"age": 29, "name": "Joe"}]}
    >>> print(json.dumps(data, indent=2))

    jobj = json.loads('{"a":"1234"}')
    value = json.dumps(jobj,ensure_ascii=False) #ensure_ascii=False 解决中文问题


