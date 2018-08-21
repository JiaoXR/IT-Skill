#  Shell 入门

- Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。
- Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。

###  Shell 脚本

- Shell 脚本（shell script），是一种为 shell 编写的脚本程序。
- 业界所说的 shell 通常都是指 shell 脚本，但读者朋友要知道，shell 和 shell script 是两个不同的概念。

###  Shell 环境

Linux 的 Shell 种类众多，常见的有：

- Bourne Shell（/usr/bin/sh或/bin/sh）
- Bourne Again Shell（/bin/bash）
- C Shell（/usr/bin/csh）
- K Shell（/usr/bin/ksh）
- Shell for Root（/sbin/sh）

由于易用和免费，Bash(Bourne Again Shell) 在日常工作中被广泛使用。同时，Bash 也是大多数 Linux 系统默认的 Shell。

在一般情况下，人们并不区分 Bourne Shell 和 Bourne Again Shell，所以，像 **#!/bin/sh **也可以改为 **#!/bin/bash**。

\#! 告诉系统其后路径所指定的程序即是解释此脚本文件的 Shell 程序。

示例代码：

```shell
#!/bin/bash
echo "Hello World !"
```

###  运行 Shell 脚本

两种方法：

####  1. 作为可执行程序

将上面的代码保存为 test.sh，并 cd 到相应目录：

```bash
chmod +x ./test.sh  #使脚本具有执行权限
./test.sh  #执行脚本
```

>注意，一定要写成 ./test.sh，而不是 **test.sh**，运行其它二进制的程序也一样，直接写 test.sh，linux 系统会去 PATH 里寻找有没有叫 test.sh 的，而只有 /bin, /sbin, /usr/bin，/usr/sbin 等在 PATH 里，你的当前目录通常不在 PATH 里，所以写成 test.sh 是会找不到命令的，要用 ./test.sh 告诉系统说，就在当前目录找。

####  2. 作为解释器参数

这种运行方式是，直接运行解释器，其参数就是 shell 脚本的文件名，如：

```bash
/bin/sh test.sh
/bin/php test.php
```

这种方式运行的脚本，不需要在第一行指定解释器信息，写了也没用。



> 参考链接：http://www.runoob.com/linux/linux-shell.html