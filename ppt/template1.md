title: ppt template 1
speaker: kk
url: https://tt8x.github.io
transition: slide3
theme: dark

[slide]

# 演讲主题
## 二级标题
<small>2016-04-26 演讲者：xxx</small>

[主页](https://tt8x.github.io)

[note]
- 这是备注，按键盘“N”显示
- transition: 转场动画，包括
 kontext vkontext circle earthquake cards glue stick move newspaper
 slide slide2 slide3
 horizontal3d horizontal vertical3d zoomin zoomout pulse
- theme: 皮肤，包括 moon color blue green dark light
- usemathjax: yes 启用MathJax渲染公式
[/note]

[slide]

# 对齐
----
- 左对齐 {:&.flexbox.vleft}
- 左对齐 
[note]
- 标题或列表第一条加上：“ {:&.flexbox.vleft}”
- 默认居中对齐
- 注意空格
- "----"为标题与内容的分界线
[/note]

[slide]

## 单条动画
----
- 目前支持的单条动画效果包括
    * moveIn {:&.rollIn}
    * fadeIn
    * bounceIn
    * rollIn
    * zoomIn
[note]
使用方法：列表第一条加上 " {:&.动画类型}"
[/note]

[slide style="background-image:url('/img/bg1.png')"]

## 背景图片

[slide]

[magic data-transition="earthquake"]
## 演示magic标签效果
-----
<div class="columns2">
    <img src="/img/girl.jpg" height="450">
    <img src="/img/girl.jpg" height="450">
</div>
========
## 演示earthquake转场效果
-----
![](/img/girl.jpg)
[/magic]

[slide]

## 表格
----
    | h1 | h2 | h3
----|----|----|---
c11 |c12 |c13 |c14 {:.highlight}
c21 |c22 |c23 |c24

[slide]
# nodePPT快捷键介绍
----
- 输入页码，然后enter
- 使用O键，开启纵览模式，方向键翻页，[enter]进入选中页
- 按下H键，*斜体字*会动
- N键显示备注
- P键使用画笔，B/Y/R/G/M键更换颜色 1～4键更换粗细 C键清空画板
- alt+click 点击地方放大，再做一次会缩小(需要zoom.js)
