---
layout: post
title: "hotkey vim"
description: ""
category: "memo"
tags: [hotkey]
tagline: "2016-04-15"

---
{% include JB/setup %}
## 常用命令

    hjkl	#移动光标
    ctrl-f	#上翻页
    ctrl-b	#下翻页
    ctrl-e	#上翻行
    ctrl-y	#下翻行
    50%	#移动到文件中间，请举一反三
    dw	#删除单词，请举一反三

    改变文本命令由一个操作和动作构成：
    operator   [number]   motion
    [number]   motion

    操作符包括:d,y,c
    dw/de/d$	#删除到 下一个单词/单词末尾/行尾
    w/e/$	#移动光标到 下一个单词开始/单词末尾/行尾
    2w/2e/2$	#移动光标到 下两个单词开始/下两个单词末尾/下两行末尾
    d3w/d3e/d3$ 

    dd/2dd	#删除整行 剪切
    u/U	#撤销/撤销整行修改
    ctrl-r	#恢复
    p/r/R	#粘帖/替换单个字符/替换模式

    G/gg/ctrl-g	#行尾/行首/显示文件信息
    ctrl-o/ctrl-i	#跳转到光标前一个停留位置/后一个停留位置

    :X/:set key=	#加密/解密文件

    :s/old/new/g	#当前行中old替换成new
    :%s/old/new/g	#替换整个文件中每个匹配串
    :%s/old/new/gc	#同上+提示是否进行替换
    :1,9s/old/new/g	#替换1-9行中的匹配项

    :!rm t.log	#执行外部shell命令:删除t.log文件
    :w file	#文件保存为file

    v	#字符标记文本
    V	#行标记
    ctrl-v	#块标记
    v motion :w file	#所选内容保存为file
    > 列编辑模式:快标记之后SHIFT+i进入插入模式，两次esc后在每行插入

    :r file	#将文件file的内容提取出来插入到当前位置
    :r !ls	#读取ls输出并插入到当前位置
    :r !date /T	#插入当前时间windows
    :r !date	#插入当前时间unix

    o/O	#光标下方/下方打开新的一行
    A	#行尾开始插入
    v/y/p	#标记/复制/粘贴
    yw/y$	#复制一个单词/复制到行尾
    yy		#复制光标所在行
    2yy/y2y	#复制2行

    %	#跳转到配对括号
    ci’、ci"、ci(、ci[、ci{、ci<	#分别更改这些配对标点符号中的文本内容
    di’、di"、di(、di[、di{、di<	#分别删除这些配对标点符号中的文本内容
    yi’、yi"、yi(、yi[、yi{、yi<	#分别复制这些配对标点符号中的文本内容
    vi’、vi"、vi(、vi[、vi{、vi<	#分别选中这些配对标点符号中的文本内容
    -----------------------------------------------------------------------------
    另外如果把上面的i改成a可以连配对标点一起操作。另外di(相当于dib di{相当于diB
    -----------------------------------------------------------------------------
    "+yi’、"+yi"	#分别复制这些配对标点符号中的文本内容到系统剪贴板
    "+y	#复制到系统剪贴板  
    "+p	#粘贴
    "+gp	#粘贴并且移动光标到粘贴内容后 

    :set ic	#忽略大小写 ignore case
    :set noic	#区分大小写
    :set is	#incsearch
    :set nois	#
    :set hls	#hlsearch 所有结果高亮显示
    :set nohls	#no hleearch
    /str\c		#仅在一次查找中忽略大小写
    :nohlsearch	#移除匹配项高亮

    :h/f1			#帮助
    ctrl-w ctrl-w	#切换窗口
    ctrl-w up/down/left/right	#按方向切换窗口
    :q			#关闭窗口
    :close		#关闭窗口不退出
    :res(ize) num	#显示行数调整为num
    :res(ize) +num	#显示行数增加num行
    :res(ize) -num	#显示行数减少num行
    :vert(ical) res(ize) num	#横向调整
    :h user-manual
    :h vimrc-intro

    :e ~/.vimrc
    :e $VIM/_vimrc
    :e file
    :e!		#刷新文件
    :split file
    :vsplit file
    :set nu	#显示行号

    za 关闭或打开当前折叠
    zM 关闭所有折叠
    zR 打开所有折叠

## nerdtree

    r	#刷新文件列表
    m	#出现增删剪切拷贝操作列表
    :Bookmark <name>	#新增书签，光标在nerdtree窗口有效
    <f5>	#":NERDTreeToggle<cr>"
    <C-f5>	#":NERDTree "
    <f6>	#":TagbarToggle"

    :map	#查看所有映射
    :h key-notation	#查看键盘符号详细说明
    :h mode	#查看模式简称
    :h command-mode	#查看模式含义
    ------------------------------------------------------------------
    insert normal mode: 在插入模式ctrl-o进入，执行完命令后返回插入模式
    insert visual mode: ctrl-o v/V/ctrl-v
    insert select mode: S-arrow
    ------------------------------------------------------------------

    ctrl-z	#最小化窗口，fg还原窗口
    ctrl-a	#数字+1
    ctrl-x	#数字-1
    .	#重复普通模式下最后一次操作
    5.	#重复5次

    :tabnew 		#Creates a new tab
    gt 		#Show next tab
    :tabn	#Show next tab
    gT	#Show previous tab
    :tabp	#Show previous tab
    :tabc	#关闭当前tab
    :tabo	#关闭所有其他tab
    :tabs	#查看所有打开的tab
    :tabfir(st)	#Show first tab
    :tabl	#Show last tab
    :tab ball	#所有buffer显示为 tab
    :new abc.txt 		#Edit abc.txt in new window

    :bn	#下一个 buffer
    :bp	#前一个 buffer
    :b#	#你之前所在的前一个 buffer
    :bd	#删除当前buffer

    m {a-z} 		#Marks current position as {a-z}
    ' {a-z} 		#Move to position {a-z}
    '' 		#Move to previous position

## 寄存器

    CTRL-R {0-9a-z"%#*+:.-=}	#插入模式下插入寄存器内容
    '"'    无名寄存器，包含最近删除或抽出的文本
    '%'    当前文件名
    '#'    轮换文件名
    '*'    剪贴板内容 (X11: 主选择)
    '+'    剪贴板内容
    '/'    最近的搜索模式
    ':'    最近的命令行
    '.'    最近插入的文本
    '-'    最近的行内 (少于一行) 删除
    '='    表达式寄存器

    ctrl-t		#插入模式 indent 
    ctrl-d 	#插入模式 un-indent
    >>  		#普通模式 Indent
    <<  		#普通模式 Un-indent
    ---------------------------------------------------------------------------
    与.结合使用可进行多次indent或un-indent
    ctrl-d/ctrl-t在普通模式下也可以使用
    ctrl-d/<TAB>	#命令补全,先确保vim不是以兼容模式运行
    ---------------------------------------------------------------------------

    YouComplleteMe:A code-completion engine for Vim
    https://github.com/Valloric/YouCompleteMe

## ctrlp

    :cd $VIM/vimfiles
    :Helptags
    :h ctrlp
    ctrl-p	#打开文件搜索窗口  :CtrlP
    ctrl-b	#打开buffer搜索窗口:CtrlPBuffer
    <leader>f	#打开MRU搜索窗口   :CtrlPMRU
    ctrl-j/k	#进行上下选择
    ctrl-x		#在当前窗口水平分屏打开文件
    ctrl-v		#同上, 垂直分屏
    ctrl-t		#在tab中打开
    ag:		#轻量级搜索工具，速度飞快, mac上安装: brew install ag
    https://github.com/ggreer/the_silver_searcher

    vimwiki: 还不如用jekyll+mou+github pages

    Conque-Shell 在vim中运行shell(:sh也可以)
    git clone https://github.com/oplatek/Conque-Shell --depth=1
    rm -rf ~/.vim/bundle/Conque-Shell/.git
    :ConqueTerm bash
    :ConqueTerm python
    :ConqueTerm mysql -h localhost -u root db
    ConqueTermSplit  水平切分
    ConqueTermVSplit 垂直切分
    ConqueTermTab    在新的Tab 中打开
    <F9>    将选中的文本，发送到Conque-Shell的交互程序中
    <F10>   将当前文件所有文本，发送到Conque-Shell的交互程序中
    <F11>   如果当前编辑文件可执行，则打开新的Conque-Shell并运行
    nnoremap    <C-\>b  :ConqueTermSplit bash<CR>  " 水平分割出一个bash
    nnoremap    <C-\>vb :ConqueTermVSplit bash<CR> " 垂直分割出一个bash

    :vim/grep start_stage *     在当前目录下（不包括子目录）搜索
    :vim/grep start_stage **    在当前所有目录（包括子目录）搜索
    :vim/grep /start_stage/ *   在当前目录下搜索正则start_stage
    :vim/grep /start_stage/g ** 一行中若有多个匹配，每个都要单独显示一行
    :vim/grep /start_stage/j ** 只更新quickfix,不跳转到第一个搜索结
    - 文件匹配方式
    *.c         当前目录下的.c文件
    **/*.c      任意目录下的.c文件
    **/*.{h,c}  任意目录下的.c .h文件
    - vimgrep可以跟split或vsplit结合使用，将结果显示到单独的窗口中：
    vsp | vimgrep demo *.c
    - quickfix操作
    :cw查看结果 :cn下一条 :cp上一条 :ccl关闭结果 :colder上次搜索结果 :cnewer下次搜索结果
    - quickfix操作本地
    lopen查看结果 :lcl关闭 :lnext下一条 :lpre上一条 :lolder上次搜索结果 :lnewer下次搜索结果

    :set filetype	#查看filetype变量的值
    :set filetype=vimwiki	#设置当前文件类型

