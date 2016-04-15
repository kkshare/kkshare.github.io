---
layout: post
title: "sample code of java"
description: "sample code of java"
category: "tech"
tags: [java,sample code]
tagline: "2016-04-15"

---
{% include JB/setup %}

Map遍历，高效方法

    Map<String,String> map = new HashMap<String,String>;
    Set<Entry<String,String>> set = map.entrySet();      
    Iterator<Entry<String,String>> it = set.iterator();
    while(it.hasNext()){         
        Map.Entry<String, String>  entry = it.next();          
        key = entry.getKey();
        value = entry.getValue();
    }

Map遍历，简洁代码

    Map<String,String> map = new HashMap<String,String>;
    Iterator<String> it = map.keySet().iterator();
    while(it.hasNext()){
        String     key= it.next();
        String     value = map.get(key);
     }

InputStream2Bytes    

    byte [] content = new byte[length];
       int readCount = 0;
       while (readCount < length) {
           readCount += is.read(content, readCount, length - readCount);
       }

深度复制

    String[] arr1 = new String[]{"a","b"};
    String[] arr2 = new String[arr1.length];
    System.arraycopy(arr1, 0, arr2, 0, n);//这是native函数，即底层函数

    String arr[] = Arrays.copyof(arr1,arr1.length);//需要调用arraycopy实现

数组与列表互换

    List<String> list = Arrays.asList(new String[]{"a","b"});
    String arr[] = list.toArray(new String[0]);

判断是否数字

    "123".matches("\\d*");

字符编码转换

    String strVal="xxx";
    strVal = new String(strVal.getBytes("ISO8859_1"), "GBK");

    ISO8859_1 编码是单字节编码，向下兼容ASCII
    ISO8859_1 收录的字符除ASCII收录的字符外，还包括西欧语言
    Latin1是ISO8859_1  的别名，有些环境下写作Latin-1(Mysql默认编码)
    ISO8859_1 为Tomcat默认编码

dateToStr

    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    returnValue = sdf.format(date);

strToDate

    Date date = sdf.parse("2015-07-28 15:48:23");

getTimestamp

    long ts =sdf.parse("2015-07-28 15:48:23").getTime();
    long ts = date.getTime();

timestamp to date

    long ts=2343;//毫秒
    Date date = new Date(ts);

[Common Lang](http://commons.apache.org/)

    commons-lang3-3.1.jar

字符串空判断

    //isEmpty
    System.out.println(StringUtils.isEmpty(null));      // true
    System.out.println(StringUtils.isEmpty(""));        // true
    System.out.println(StringUtils.isEmpty(" "));       // false
    System.out.println(StringUtils.isEmpty("bob"));     // false
    System.out.println(StringUtils.isEmpty("  bob  ")); // false

    //isBlank
    System.out.println(StringUtils.isBlank(null));      // true
    System.out.println(StringUtils.isBlank(""));        // true
    System.out.println(StringUtils.isBlank(" "));       // true
    System.out.println(StringUtils.isBlank("bob"));     // false
    System.out.println(StringUtils.isBlank("  bob  ")); // false

trim

    System.out.println(StringUtils.trim(null)); // null
    System.out.println(StringUtils.trim("")); // ""
    System.out.println(StringUtils.trim("     ")); // ""
    System.out.println(StringUtils.trim("abc")); // "abc"
    System.out.println(StringUtils.trim("    abc")); // "abc"
    System.out.println(StringUtils.trim("    abc  ")); // "abc"
    System.out.println(StringUtils.trim("    ab c  ")); // "ab c"

strip

    System.out.println(StringUtils.strip(null)); // null
    System.out.println(StringUtils.strip("")); // ""
    System.out.println(StringUtils.strip("   ")); // ""
    System.out.println(StringUtils.strip("abc")); // "abc"
    System.out.println(StringUtils.strip("  abc")); // "abc"
    System.out.println(StringUtils.strip("abc  ")); // "abc"
    System.out.println(StringUtils.strip(" abc ")); // "abc"
    System.out.println(StringUtils.strip(" ab c ")); // "ab c"

    System.out.println(StringUtils.strip("  abcyx", "xyz")); // "  abc"

    System.out.println(StringUtils.stripStart("yxabcxyz  ", "xyz")); // "abcxyz  "
    System.out.println(StringUtils.stripEnd("  xyzabcyx", "xyz")); // "  xyzabc"

字符串连接

    //数组元素拼接
    String[] array = {"aaa", "bbb", "ccc"};
    String result1 = StringUtils.join(array, ",");

    System.out.println(result1);//"aaa,bbb,ccc"

    //集合元素拼接
    List<String> list = new ArrayList<String>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    String result2 = StringUtils.join(list, ",");

    System.out.println(result2);//"aaa,bbb,ccc"

字符串Escape

    System.out.println(StringEscapeUtils.escapeCsv("测试测试哦"));//"测试测试哦"
    System.out.println(StringEscapeUtils.escapeCsv("测试,测试哦"));//"\"测试,测试哦\""
    System.out.println(StringEscapeUtils.escapeCsv("测试\n测试哦"));//"\"测试\n测试哦\""

    System.out.println(StringEscapeUtils.escapeHtml4("测试测试哦"));//"<p>测试测试哦</p>"
    System.out.println(StringEscapeUtils.escapeJava("\"rensaninng\"，欢迎您！"));//"\"rensaninng\"\uFF0C\u6B22\u8FCE\u60A8\uFF01"

    System.out.println(StringEscapeUtils.escapeEcmaScript("测试'测试哦"));//"\u6D4B\u8BD5\'\u6D4B\u8BD5\u54E6"
    System.out.println(StringEscapeUtils.escapeXml("<tt>\"bread\" & \"butter\"</tt>"));//"<tt>"bread" &amp; "butter"</tt>"

随机数

    // 10位英字
    System.out.println(RandomStringUtils.randomAlphabetic(10));
    // 10位英数
    System.out.println(RandomStringUtils.randomAlphanumeric(10));
    // 10位ASCII码
    System.out.println(RandomStringUtils.randomAscii(10));
    // 指定文字10位
    System.out.println(RandomStringUtils.random(10, "abcde"));

数组

    // 追加元素到数组尾部
    int[] array1 = {1, 2};
    array1 = ArrayUtils.add(array1, 3); // => [1, 2, 3]

    System.out.println(array1.length);//3
    System.out.println(array1[2]);//3

    // 删除指定位置的元素
    int[] array2 = {1, 2, 3};
    array2 = ArrayUtils.remove(array2, 2); // => [1, 2]

    System.out.println(array2.length);//2

    // 截取部分元素
    int[] array3 = {1, 2, 3, 4};
    array3 = ArrayUtils.subarray(array3, 1, 3); // => [2, 3]

    System.out.println(array3.length);//2

    // 数组拷贝
    String[] array4 = {"aaa", "bbb", "ccc"};
    String[] copied = (String[]) ArrayUtils.clone(array4); // => {"aaa", "bbb", "ccc"}
             
    System.out.println(copied.length);//3         

    // 判断是否包含某元素
    String[] array5 = {"aaa", "bbb", "ccc", "bbb"};
    boolean result1 = ArrayUtils.contains(array5, "bbb"); // => true         
    System.out.println(result1);//true

    // 判断某元素在数组中出现的位置（从前往后，没有返回-1）
    int result2 = ArrayUtils.indexOf(array5, "bbb"); // => 1         
    System.out.println(result2);//1

    // 判断某元素在数组中出现的位置（从后往前，没有返回-1）
    int result3 = ArrayUtils.lastIndexOf(array5, "bbb"); // => 3
    System.out.println(result3);//3

    // 数组转Map
    Map<Object, Object> map = ArrayUtils.toMap(new String[][]{
         {"key1", "value1"},
         {"key2", "value2"}
    });
    System.out.println(map.get("key1"));//"value1"
    System.out.println(map.get("key2"));//"value2"

    // 判断数组是否为空
    Object[] array61 = new Object[0];
    Object[] array62 = null;
    Object[] array63 = new Object[]{"aaa"};

    System.out.println(ArrayUtils.isEmpty(array61));//true
    System.out.println(ArrayUtils.isEmpty(array62));//true
    System.out.println(ArrayUtils.isNotEmpty(array63));//true

    // 判断数组长度是否相等
    Object[] array71 = new Object[]{"aa", "bb", "cc"};
    Object[] array72 = new Object[]{"dd", "ee", "ff"};

    System.out.println(ArrayUtils.isSameLength(array71, array72));//true

    // 判断数组元素内容是否相等
    Object[] array81 = new Object[]{"aa", "bb", "cc"};
    Object[] array82 = new Object[]{"aa", "bb", "cc"};

    System.out.println(ArrayUtils.isEquals(array81, array82));

    // Integer[] 转化为 int[]
    Integer[] array9 = new Integer[]{1, 2};
    int[] result = ArrayUtils.toPrimitive(array9);

    System.out.println(result.length);//2
    System.out.println(result[0]);//1

    // int[] 转化为 Integer[]
    int[] array10 = new int[]{1, 2};
    Integer[] result10 = ArrayUtils.toObject(array10);

    System.out.println(result.length);//2
    System.out.println(result10[0].intValue());//1
    日期
    // 生成Date对象
    Date date = DateUtils.parseDate("2010/01/01 11:22:33", new String[]{"yyyy/MM/dd HH:mm:ss"});

    // 10天后
    Date tenDaysAfter = DateUtils.addDays(date, 10); // => 2010/01/11 11:22:33
    System.out.println(DateFormatUtils.format(tenDaysAfter, "yyyy/MM/dd HH:mm:ss"));

    // 前一个月
    Date prevMonth = DateUtils.addMonths(date, -1); // => 2009/12/01 11:22:33
    System.out.println(DateFormatUtils.format(prevMonth, "yyyy/MM/dd HH:mm:ss"));

    // 判断是否是同一天
    Date date1 = DateUtils.parseDate("2010/01/01 11:22:33", new String[]{"yyyy/MM/dd HH:mm:ss"});
    Date date2 = DateUtils.parseDate("2010/01/01 22:33:44", new String[]{"yyyy/MM/dd HH:mm:ss"});
    System.out.println(DateUtils.isSameDay(date1, date2));// true

    // 日期格式化
    System.out.println(DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));

## java调用shell脚本

编写脚本加入：#!/bin/sh

权限：chmod a+x test.sh

获取输出信息的：

    Process process = Runtime.getRuntime().exec ("/root/bin/test.sh");
    InputStreamReader ir=newInputStreamReader(process.getInputStream());
    LineNumberReader input = new LineNumberReader (ir);
    String line;
    while ((line = input.readLine ()) != null)
    System.out.println(line);                                                                                                           
    input.close();
    ir.close();
    
