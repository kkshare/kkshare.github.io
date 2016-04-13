---
layout: post
title: "python xml ElementTree"
description: ""
category: "tech"
tags: [python,xml,ElementTree]

---
{% include JB/setup %}

## ElementTree

    import xml.etree.ElementTree as etree
    #import xml.etree.cElementTree as etree

    Element属性:tag,attrib,text,tail     #<tag attrib1=1>text</tag>tail
    ＃从字符串读取数据
    root = etree.fromstring(country_data_as_string)     #root是Element。

    for child in root:     #遍历root下的子节点  

    etree.iselement(element)     #检查是否是一个element对象。
    etree.SubElement(parent, tag, attrib={}, **extra)     #子元素工厂，创建一个Element实例并追加到已知的节点。

    etree.tostring(element, encoding="us-ascii", method="xml")
    生成一个字符串来表示表示xml的element，包括所有子元素。element是Element实例，method为"xml","html","text"。返回包含了xml数据的字符串。

## etree.Element遍历操作

    Element.iter(tag=None)：遍历该Element所有后代，也可以指定tag进行遍历寻找。
    Element.findall(path)：查找当前元素下tag或path能够匹配的直系节点。
    Element.find(path)：查找当前元素下tag或path能够匹配的首个直系节点。
    Element.findtext(path, default=None, namespaces=None)
    Element.text: 获取当前元素的text值。
    Element.get(key, default=None)：获取元素指定key对应的属性值，如果没有该属性，则返回default值。
    root.find('item/name').text #获取root子元素item中name的属性值

### 针对属性的操作

    clear()：清空元素的后代、属性、text和tail也设置为None。
    get(key, default=None)：获取key对应的属性值，如该属性不存在则返回default值。
    items()：根据属性字典返回一个列表，列表元素为(key, value）。
    keys()：返回包含所有元素属性键的列表。
    set(key, value)：设置新的属性键与值。

### 针对后代的操作

    append(subelement)：添加直系子元素。
    extend(subelements)：增加一串元素对象作为子元素。＃python2.7新特性
    find(match)：寻找第一个匹配子元素，匹配对象可以为tag或path。
    findall(match)：寻找所有匹配子元素，匹配对象可以为tag或path。
    findtext(match)：寻找第一个匹配子元素，返回其text值。匹配对象可以为tag或path。
    insert(index, element)：在指定位置插入子元素。
    iter(tag=None)：生成遍历当前元素所有后代或者给定tag的后代的迭代器。＃python2.7新特性
    iterfind(match)：根据tag或path查找所有的后代。
    itertext()：遍历所有后代并返回text值。
    remove(subelement)：删除子元素。

## 插入儿子结点

方法一：

    item = etree.Element("item", {'sid' : '1713', 'name' : 'he'})
    root.append(item)      #无返回

方法二：
    
    etree.SubElement(root,'item',{'sid':'1713','name':'ityouhui'}) #返回Element

    http://effbot.org/zone/element-index.htm

## CDATA的支持

    def CDATA(text=None):
         e = etree.Element('![CDATA[')
         e.text = text
         return e
             
    _original_serialize_xml = etree._serialize_xml
    def _serialize_xml(write, elem, encoding, *args):
         if elem.tag == '![CDATA[':
              write("<![CDATA[%s]]" % elem.text.encode(encoding))
              return
         return _original_serialize_xml(write, elem, encoding, *args)
    etree._serialize_xml = etree._serialize['xml'] = _serialize_xml

    e = CDATA('<file>hah</file>')
    print etree.tostring(e)

## Get pretty look

    def indent(elem, level=0):
            i = "\n" + level*"  "
            if len(elem):
                    if not elem.text or not elem.text.strip():
                            elem.text = i + "  "
                    for e in elem:
                            indent(e, level+1)
                    if not e.tail or not e.tail.strip():
                            e.tail = i
            if level and (not elem.tail or not elem.tail.strip()):
                    elem.tail = i
            return elem
