---
layout: post
title: "hotkey emacs"
description: ""
category: "memo"
tags: [hotkey]

---
{% include JB/setup %}

http://www.gnuemacs.org/

## windows emacs

    http://ftp.gnu.org/pub/gnu/emacs/windows/emacs-24.5-bin-i686-mingw32.zip
    - install: D:\emacs-24.5\bin\addpm.exe
    - run: bin\runemacs.exe
    - C-x c-f ~/.emacs 打开.emacs 添加如下语句
         (load-file "D:/emacs-24.5/.emacs")
    - vi D:/emacs-24.5/.emacs
    (setenv "HOME" "D:/emacs-24.5")
    (setenv "PATH" "D:/emacs-24.5")
    ;;set the default file path
    (setq default-directory "~/")
    (add-to-list 'load-path "~/emacs/site-lisp")
    这种生成.emacs文件的方法简单，方便备份，不会对其他程序产生影响，另外一种方法是写个bat
         path-of-emacs.exe -q -l path-of-your.emacs

## *nix

    emacs -nw
    在cygwin下的emacs还可以，最新版本。
    尝试在MobaXterm安装emacs失败，不能启动

    http://blog.csdn.net/redguardtoo/article/details/7222501/
    http://emacser.com/to-emacs-beginner.htm

## 扩展

    C-x #字符扩展
    M-x #命令名扩展
    M-x fundamental-mode/text-mode #切换编辑模式
    M-x auto-fill-mode #辅模式自动断行开关，默认70字符断行，只能在空白处进行断行
    M-x shell #进入shell mode
    M-! #shell command,直行单个命令
    C-x C-c #退出
    C-z #挂起，暂时离开emacs并返回shell但不杀死emacs,通过fg或%emacs再次返回
    C-u <number> #输入数字参数，如C-u 8 C-f 表示向前移动8个字符
    C-u 8 * #插入8个*
    C-g #终止一条正在执行的命令或取消数字参数与输入到一半的命令

## 光标控制

    C-v     #向前移动一屏
    M-v     #向后移动一屏
    C-l     #重绘屏幕，并将光标所在行置于屏幕的中央
    C-p C-n #前一行 下一行
    C-b C-f #前一个字符 后一个字符
    M-b M-f #前一个单词 后一个单词
    C-a #行首
    C-e #行尾
    M-a #句首，第一次到行首，第二次到段落首
    M-e #句尾，第一次到行尾，第二次到段落尾
    M-< #文档开头
    M-> #文档结尾
    M-g g #跳转到指定行

## 编辑

    C-k #删除光标到行尾间字符(k:kill) #C-u 2 C-k #删除两行
    M-k #删除光标到行首间字符
    C-@ #选择文字
    M-w #复制
    C-w #剪切
    C-y  #召回 M-y 召回再前一次内容
    C-/  #撤销(每组最多20个字符) 或 C-x u 
    C-x h #全选
    C-x f <num> #重新设定行边界
    M-q #将当前段落重新折行 

## 文件

    C-x C-f <filename> #新建或打开文件
    C-x C-v <filename> #读取另一个文件代替刚才读入的文件
    C-x C-s #保存文件 
    C-x C-w #另存为
    C-x s #保存所有文件
    C-x i <filename>#把文件插入到当前光标位置
    C-x C-q #只读模式开关
    M-x recover file #恢复自动保存文件
    M-x customize-variable <Return> make-backup-files <Return> #关掉保存文件时备份源文件特性
    C-x C-b #列出所有缓冲区 C-x 1 离开缓冲区列表
    C-x b <name> #切换缓冲区
    C-s #向前搜索
    C-r #向后搜索
    M-x replace-string #替换字符串，只需要输入repl s<TAB>就行，自动补全 

## 窗格 窗口

    C-x 0 #关掉当前窗格
    C-x 1 #关掉其他窗格 
    C-x 2 #在下方创建窗格
    C-x 3 #在右方创建窗格 
    C-x o #切换窗格(other)
    C-x k #删除缓冲区
    ESC C-v #不用切换窗格滚动另一个窗格中文件
    M-x make-frame #创建新窗口 C-x 5 2
    M-x delete-frame #关闭当前窗口 C-x 5 0

    C-h ? #帮助
    C-h c C-p #查看快捷键C-p的简要说明
    C-h k C-p #查看快捷键C-p的文档 describe key
    C-h f #查看命令的文档 describe function
    C-h v #查看变量的文档
    C-h i #查看阅读手册
    C-h t #查看入门教程tutorial
    C-h m #查看主模式辅模式解释

## 技巧

    - 打开多个shell进程及其对应缓冲区：M-x shell 打开了第一个*shell*缓冲区，
    M-x rename-buff将当前缓冲区重命名后再用M-x shell打开
    - 启动emacs不运行.emacs,然后检查这个文件在什么地方出错
         emacs -q

## LISP

    - 基本元素：函数、变量、原子项
    - 一切函数都有返回值，返回值就是该函数最后一个列表项的值
    - 变量没有类型，可以取任意类型的值
    - 字符
    ?a #字符a
    \e \n \t 分别表示esc newline tab
    ?\C-a 表示C-a,\C-表示控制字符
    - 字符串用双引号引起
    - boolean: t for true,nil for false,non-nil value means true
    - 符号(Symbols):变量或函数的引用

