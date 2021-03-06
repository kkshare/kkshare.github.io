---
layout: post
title: "javascript"
description: "javascript"
category: "tech"
tags: [javascript/jquery/ajax]
tagline: "2016-04-15"

---
{% include JB/setup %}

## ajax jquery
[jquery sample](http://www.w3school.com.cn/jquery/)

### jquery语法

    $(selector).action(speed,callback)
    $(this).hide() - 隐藏当前元素
    $("p").hide() - 隐藏所有段落
    $("p.test").hide() - 隐藏所有 class="test" 的段落
    $("#test").hide() - 隐藏所有 id="test" 的元素
    $("p") 选取 <p> 元素。
    $("p.intro") 选取所有 class="intro" 的 <p> 元素。
    $(".intro")      所有 class="intro" 的元素
    $("p#demo") 选取 id="demo" 的第一个 <p> 元素。
    $("ul li:first")      每个 <ul> 的第一个 <li> 元素
    $("div#intro .head")      id="intro" 的 <div> 元素中的所有 class="head" 的元素
    $("#inputid").val()
    $("#inputid").attr('value')

### jQuery 属性选择器

    $("[href]") 选取所有带有 href 属性的元素。
    $("[href='#']") 选取所有带有 href 值等于 "#" 的元素。
    $("[href!='#']") 选取所有带有 href 值不等于 "#" 的元素。
    $("[href$='.jpg']") 选取所有 href 值以 ".jpg" 结尾的元素。

### jQuery CSS 选择器

    $("p").css("background-color","red"); 把所有 p 元素的背景颜色更改为红色

## jQuery HTML 操作

    $(selector).html(content)      改变被选元素的（内部）HTML
    $(selector).append(content)      向被选元素的（内部）HTML 追加内容
    $(selector).prepend(content)      向被选元素的（内部）HTML “预置”（Prepend）内容
    $(selector).after(content)      在被选元素之后添加 HTML
    $(selector).before(content)      在被选元素之前添加 HTML

### ajax

    $(selector).load(url,data,callback)      把远程数据加载到被选的元素中
    $.ajax(options)      把远程数据加载到 XMLHttpRequest 对象中
         htmlobj=$.ajax({url:"/jquery/test1.txt",async:false});
         $("#myDiv").html(htmlobj.responseText);
    $.get(url,data,callback,type)      使用 HTTP GET 来加载远程数据
    $.post(url,data,callback,type)      使用 HTTP POST 来加载远程数据
    $.getJSON(url,data,callback)      使用 HTTP GET 来加载远程 JSON 数据
    $.getScript(url,callback)      加载并执行远程的 JavaScript 文件

### 手动组参数传递

    var parameter = {};
    $("input[type=hidden]", $("form")).each(function(){
       var key = this.name;
       var value = this.value;
       parameter[key] = value;
    });

    $("input[type=text]", $("form")).each(function(){
       var key = this.name;
       var value = this.value;
       parameter[key] = value;
    });

    $.ajax({
       async: false,
       type: "POST",
       url: url+"/action/manager.do",
       data: parameter,
       dataType: "json",
       success: function(resp){
            if(resp.executeSuccess == 0){
            alert("<bean:message key='HC000.SAVED_SUCCESS' />");
            }else{
            alert("<bean:message key='HC000.SAVED_FAIL' />");
       }},
       error: function(msg){
            alert("<bean:message key='HC000.SAVED_FAIL' />");
       }
    });

### js操作JSON

    json.js包更方便处理js: http://www.json.org/json.js
    var str1 = '{ "name": "cxh", "sex": "man" }';//JSON字符串
    var str2 = { "name": "cxh", "sex": "man" };//JSON对象
    var str3 = [ "name": "cxh", "sex": "man" ];//JSON对象

    var obj = eval('(' + str + ')');//由JSON字符串转换为JSON对象，js自带
    var obj = JSON.parse(str);//由JSON字符串转换为JSON对象，js自带
    var obj = str.parseJSON();//由JSON字符串转换为JSON对象，来自json.js

然后，就可以这样读取：

    Alert(obj.name);
    Alert(obj.sex);
    
*特别留心*:  
如果obj本来就是一个JSON对象，那么运用 eval()函数转换后（哪怕是多次转换）还是JSON对象，但是运用 parseJSON()函数处理后会有疑问（抛出语法异常）。

    var last=JSON.stringify(obj); //将JSON对象转化为JSON字符，自带
    var last=obj.toJSONString(); //将JSON对象转化为JSON字符，来自json.js

