---
layout: post
title: "hotkey MicroEmacs"
description: ""
category: "memo"
tags: [hotkey]

---
{% include JB/setup %}

http://www.jasspa.com

这个东西诞生在80年代，因为体积不大，互联网又没有出现，所以大家就拷来拷去，改出了
好多个分支：

原始作者Daniel Lawrence 自己的版本（不过最早的作者是Dave Conroy），，最新版本是
09年2月出的5.00，但提供了源代码和Windows版本，似乎用的人不多。网上其它地方可以找
到3.12和4.0版本，这两个版本以前使用比较广泛；uEmacs/PK，作者是Petri H. Kutvonen
，基于Lawrence原版的3.9e分支出来的。最后版本是4.0.15， 各个kernel.org镜像上均可
以下载到源代码[uemacs]；Linus他们在kernel.org上维护的另一个分支（git仓库链接），
基于uEmacs/PK，是在P.K.停止开发后，Linus接手的（这也是为什么kernel.org上有
uemacs/pk的下载连接）；最受欢迎、最有名（似乎功能也最强的）的一个版本是Jasspa，
它在1987年就从原版MicroEmacs 3.8中分支出来，增强了特别多，下面将会详细介绍其特性
；我十年前读书的时候淘到一张C++ User Group的光盘，里面就提供了一个Windows 3.1
GUI的port，似乎改动也不小（要知道除了jasspa，其它版本大多只有终端版本）。刚才在
网上搜了半天，找到说是Pierre Perret 基于Lawrence的MicroEmacs 4.0做的，不过其所在
网址已经打不开了（已经排除了G.F.W的因素）；还有其它一些分支和变体，比如快捷键跟
GNU Emacs更接近的mg（原名MicroGNUEmacs。它甚至有个WinCE版本），克隆vi 的vile，
ersatz emacs，Conroy MicroEmacs等等。前面说的JED跟MicroEmacs也有些渊源。。
windows 版下载： http://www.jasspa.com/downms.html
最新版下载： http://www.jasspa.com/downlatest.html
免安装版下载： http://www.jasspa.com/zeroinst.html

## 必要配置

    set MEUSERPATH = D:\kuaipan\me #设置环境变量
    M-x user-setup 
    ->MS Shift Bindings:check,通过shift标记选择文本
    ->General->Backup
    ->Platform
       ->Color schema:Default White on Black

    M-x buffer-setup #取消Backup模式

    vi ~/.jasspa/hetq.emf
    define-macro previous-buffer
        &neg @# next-buffer
    !emacro

    global-bind-key beginning-of-line       "home"   ;C-a
    global-bind-key end-of-line             "end"    ;C-e
    global-bind-key beginning-of-buffer     "C-home" ;M-<
    global-bind-key end-of-buffer           "C-end"  ;M->
    global-bind-key next-window             "C-tab"  ;C-x o
    global-bind-key previous-window         "C-S-tab";C-x p
    global-bind-key undo                    "C-z"    ;C-x u,C-_
    global-bind-key next-buffer             "f6"     ;C-x x
    -1 global-bind-key next-buffer          "S-f6"   ;previous-buffer
    ml-bind-key tab                         "esc esc";command completion
    global-bind-key indent-increase         "tab"
    global-bind-key indent-decrease         "S-tab"
    ;global-bind-key copy-region         "M-w";esc w, bind fail

    set-variable %cygwin-path "d:/cygwin64"
    ;esc k next-window C-tab #将C-tab绑定到next-window,此命令只是临时有用，关闭窗口重启之后失效
    ;删除配置文件即恢复默认配置（linux: rm  -rf ~/.jasspa/, windows: rm -rf ~/user.emf）

## 技巧

    启动时加参数-c表示加载上一次保存的会话
    me -l <n> <file> #打开文件定位到第n行
    M-x cygwin #打开cygwin窗口， 需要配置 set-variable %cygwin-path "d:/cygwin64"

## 快捷键

    f10 #文件浏览
    S-f10 #ftp文件浏览
    C-s #向前搜索
    C-r #向后搜索
    C-u #重复后面的命令，默认4次
    esc 8 * #输入*8次
    esc ! #执行单个shell命令
    esc \ #执行单个shell命令 区别？
    M-x shell #弹出shell
    esc k #global-bind-key
    esc C-k #global-unbind-key
    f1 #打开菜单

## 光标窗格

    esc g, M-g #goto a line
    C-x 0 #关闭当前窗格
    C-x k #删除当前buffer
    C-x z #放大窗口zoom
    C-x C-z #缩小窗口
    C-PgDn #不失去当前焦点情况下对下一个窗口往下翻页
    C-PgUp #不失去当前焦点情况下对下一个窗口往上翻页

    C-x C-b
    C-x b

    C-up #光标上移5行
    C-down #光标下移5行
    C-left, M-b #光标前移一个单词
    C-right, M-f #光标后移一个单词

    M-up #滚动条上移一行
    M-down #滚动条下移一行
    M-left #滚动条左移一个字符
    M-right #滚动条右移一个单词

    esc [, M-p #前一个段落
    esc ], M-n #后一个段落

## 编辑

    f3 #browse mode
    C-i #插入tab键(如果正常按tab只能插入4个空格)
    M-space #选中标记文本mark set
    esc w #copy
    C-w #cut
    C-y #yank(paste)
    M-y #reyank

## 帮助

    esc ? #帮助手册
    esc z, M-z #快速退出,会保存未保存内容
    C-x C-c #询问用户并退出
    C-h c #查看所有命令
    C-h k ... #查看快捷键简要描述
    C-h f ... #查看命令文档
    C-h a ... #列出相似命令
    C-h b #查看所有绑定的快捷键列表，esc ? -> Key Bindings讲的更详细

## 文件

    几乎同emacs，不一样的如下
    C-x s #search forword; emacs表示保存全部
    C-x C-v #view-file, 只读模式打开文件; emacs表示打开另一个文件取代刚打开的文件
    C-x k #kill-buffer, 删除当前buffer,与C-x 0 区别是后者只关闭窗口，不删除缓冲

    命令区号含义    
    (1) - Executable command line.
    (2) - Editor built in commands.
    (2m) - Editor built in modes.
    (3) - Editor commands implemented as macros.
    (4) - Editor macro language syntax
    (5) - Editor variables
    (8) - Editor specific file formats

    C-x m #修改模式
    M-x buffer-setup #对话框方式修改模式
    M-x major-mode-setup #设置主模式

## buffer modes

    auto – when enabled performs automatic source file line type detection (i.e. UNIX, DOS, Windows)
    autosv – when enabled performs periodic auto-save backup of changes.
    exact – when enabled makes searching and replace case sensitive.
    fence – when enabled performs automatic fence matching i.e. (..) pairs.
    over – when enabled overwrites rather than inserts entered characters, insert key.
    indent – when enabled a new line of text automatically inherits the previous lines left indent.
    magic – when enabled assumes a regular expression search and replace.
    quiet – when enabled disables audio beep on an error.
    tab – when enabled spaces are used instead of literal TAB characters.
    time – when enabled files are automatically time stamped with the edit time.
    undo – when enabled retains undo information.
    view – when enabled the buffer is read only and cannot be modified.
    wrap – when enabled automatic text wrapping is performed at the fill column.

    - ~/*.erf session文件，保存窗口布局，编辑位置，操作 read-session,save-session
    - 缓冲文件（名字如*xxx*的文件）关闭时不会询问用户是否保存

