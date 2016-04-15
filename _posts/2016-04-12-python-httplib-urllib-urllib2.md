---
layout: post
title: "python httplib urllib urllib2"
description: ""
category: "tech"
tags: [python,httplib,urllib,urllib2]
tagline: "2016-04-15"

---
{% include JB/setup %}

    #!/usr/bin/env python   
    # -*- coding: utf-8 -*-   
    import httplib 
    import urllib 
     
      
    def sendhttp(): 
        data = urllib.urlencode({'@number': 12524, '@type': 'issue', '@action': 'show'})    
        headers = {"Content-type": "application/x-www-form-urlencoded", 
                   "Accept": "text/plain"} 
        conn = httplib.HTTPConnection('bugs.python.org') 
        conn.request('POST', '/', data, headers) 
        httpres = conn.getresponse() 
        print httpres.status 
        print httpres.reason 
        print httpres.read() 
            
               
urllib/urllib2 is built on top of httplib. It offers more features than writing to httplib directly.

    import urllib,urllib2
    try:
         resp = urllib2.urlopen('http://python.org/')
         html = resp.read()
    except urllib2.HTTPError, e:#HTTPError是URLError的子类，已经连上了服务器
         print 'The server couldn/'t fulfill the request.'
         print 'Error code: ', e.code
    except urllib2.URLError, e: #未连上服务器
         print 'We failed to reach a server.'
         print e.reason     #它是一个tuple，包含了一个错误号和一个错误信息。
    else:
         #ok

request 方式

    url = 'http://www.someserver.com/cgi-bin/register.cgi'
    headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "text/plain"}
    values = {'name' : 'Michael Foord', 'language' : 'Python' }
    data = urllib.urlencode(values)
    req = urllib2.Request(url, data, headers)
    resp = urllib2.urlopen(req)
    the_page = resp.read()
    resp.geturl()     #返回真实的请求URL,可能跟请求URL不一样
    resp.info     #字典对象,headers等


