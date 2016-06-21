title: blog之路：github+jekyll+nodeppt
speaker: kk
url: https://kkshare.github.io
transition: slide3
theme: dark

[slide]

# blog之路：github+jekyll+nodeppt
<small>2016-06-21 何通庆</small>

[主页](https://kkshare.github.io)

[slide]
# 为何要写博客
- 重新认识自己 {:&.rollIn}
- 提高思维逻辑与表达能力
- 让事情容易开始，开始了也很难停下来(万事开头难？)
 * 让你有大段不被打扰的时间思考，想事情想得更清楚
 * 锻炼总结归纳能力，提纲挈领能力
 * 提纲与框架是很好的开始，大脑喜欢(让事情变得简单)
 * 记录下来，大脑喜欢(大脑认为已经完成，不会有负担)
 * 因为有记录，中断了也没有关系，可以随时、随地继续
 * 每一个小步都是向前(方向太重要了)
- 事情的开始变成:给项目起一个名字，新建一个文档
- 博客不是唯一方式，请找到适合自己的

[note]
事情可以指一篇文章、一个项目、人生的一个规划
[/note]

[slide]
# 本博客特点
- git+github+jekyll {:&.rollIn}
 * git版本管理
 * github免费托管
 * jekyll自动生成博客，基于文本的markdown语法
 * 也可以自建服务器
- ppt
 * 更要注重逻辑与严谨
 * 提纲挈领功能更强，变成真正自己的知识
 * 注重*美*与*简单*(*美*是态度、细节)

[slide]
# jekyll简介
- 简单的免费的Blog生成工具
- 只生成静态页面，无需数据库支持
- 可以免费部署在Github上，而且可以绑定自己的域名
- 结合第三方服务支持评论
- markdown语法
- 文件介绍
 * \_config.yml:配置文件
 * \_includes:存放可以重复利用的文件
 * \_layouts:存放的是模板文件
 * \_posts:文章内容(yyyy-mm-dd-title.MARKUP格式文件名)
 * \_site:最终生成文件

[slide]
# github:账号申请
- 到[github](https://github.com)申请账号，以myblog为例
- 创建仓库myblog.github.io
 * 仓库名字必须是<username>.github.io，否则必须创建gh-pages分支，  
 这样才能通过 http://myblog.github.io 访问博客。  
 可以进入仓库->settings，修改仓库名字
- 克隆远程仓库
```bash
git clone https://github.com/myblog/myblog.github.io.git myblog.repo
```
[slide]
# jekyll:安装
- 安装
```bash
gem install jekyll
# 如果出现 Errno::ECONNRESET: Connection reset by peer - SSL_connect
# 是因为GFW原因，用下面方法解决：
gem sources --remove https://rubygems.org/
gem sources -a https://ruby.taobao.org/
gem sources -l # 请确保只有 ruby.taobao.org
```
- 克隆jekyll-bootstrap
```bash
git clone https://github.com/plusjade/jekyll-bootstrap.git jekyll-bootstrap
cd jekyll-bootstrap;rm -rf .git;cd -
cp -rf jekyll-bootstrap myblog.io
cd myblog.io
jekyll serve #启动服务，使可以本地预览
```
- 测试: http://127.0.0.1:4000/ 查看示例页面

[slide]
# jekyll:常见问题与文章模版
- github 页面显示不全问题  
safari->开发->显示错误控制台，查看哪些域名不能解析，  
然后从[这里](http://ipaddress.com)查找对应域名，写入/etc/hosts
```bash
199.27.74.133 assets-cdn.gihub.com
172.217.16.170 ajax.googleapis.com
```
- 文章模版

```bash
---
layout: post
title: "java"
other-name: other-value
---
<content of markdown>
```

[slide]
# jekyll:生成文章模版
- 生成post(支持comment,date,category,tags)
```bash
rake post title="Hello World"
```
- 生成page(不支持comment,date,category,tags)
```bash
rake page name="about.md"
rake page name="pages/about.md" # 可以指定目录
```
- 我的常用命令
```bash
rake post category='tech' title=xx  #技术文章
rake post category='diary' title=xx #随笔
rake post category='memo' title=xx  #备忘录
rake post category='[tech,memo]' tags='[a,b]' title=xx #备忘录
```
- rake调用的函数在Rakefile中定义，可以自定义命令，比如我对post文件名固定格式很有意见，所以
  我修改了`rake post`的行为

[note]
修改后的Front Matter:
```
---
layout: post
title: "java"
category: [tech,digest,java]
date: 发布日期(对文件名中日期进行重写)
tags: [java,sample code]
---
```
[/note]

[slide]
# jekyll-bootstrap源码备忘录
- 配置
```bash
_include/JB/setup 初始化BASE_PATH/HOME_PATH/ASSERT_PATH变量
_layout/ 配置themes风格，真正的css在_include/themes/THEME-NAME/ 定义
_include/themes/THEME-NAME/default 定义topbar/footer
_config.yml: ASSET_PATH 与图片、js路径相关
```
- 显示某个group中的页面(如navigation, 去掉%与{}之间空格)
```bash
{ % assign pages_list = site.pages % }
{ % assign group = 'navigation' % }
{ % include JB/pages_list % }
```
- 安装主题与切换主题
```bash
# https://github.com/jekllbootstrap 下面有几个主题
rake theme:install git="https://github.com/jekyllbootstrap/theme-the-program.git"
rake theme:install git="https://github.com/jekyllbootstrap/theme-twitter.git"
rake theme:switch name="the-program" #bootstrap-3 the-program twitter
# 另外需修改 _include/JB/setup 中 assets/themes/THEME-NAME
```

[slide]
# nodeppt
- 支持markdown语法
- jekyll不能将nodeppt语法，对源码做以下修改使其识别
 * 添加Front Matter
 * 生成静态页面扩展名从htm改成html
 * 具体参考[nodeppt](https://kkshare.github.io/memo/tech/2016-04-24-nodeppt)

[slide]
# 参考资料
- 本人[blog](https://github.com/kkshare/kkshare.github.io)
- Jekyll文档:[http://jekyllrb.com](http://jekyllrb.com)
- Jekyll创始人的[示例库](https://github.com/mojombo/tpw)
- 其他用Jekyll搭建的[blog](https://github.com/jekyll/jekyll/wiki/Sites)  
 * end of ppt,thanks! {:&.rollIn}

