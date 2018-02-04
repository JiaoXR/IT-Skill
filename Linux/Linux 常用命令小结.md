#  Linux 常用命令小结

###  基本

- ls

list, 显示当前目录下的文件/目录（不含以 `.` 和 `..` 开头的文件）。

```bash
# 查看当前目录下所有文件（包含以 . 和 .. 开头的文件）
ls -a

# home 目录下文件
ls ~
```

- mkdir

make directory, 新建目录

- cd

change directory, 切换目录。

```bash
# 切换到当前目录
cd .

# 切换到父目录
cd ..

# 切换到 home 目录
cd
cd ~
```

- pwd

print working directory, 打印当前路径

- cp

copy, 拷贝文件。例如：

```bash
# 拷贝 file1 并更名为 file2
% cp file1 file2

# 指定文件拷贝到当前目录
% cp /vol/ee/ee-info/Teaching/Unix/science.txt .
```

- mv

move, 移动/更名文件。例如：

```bash
# 将 file1 更名为 file2
% mv file1 file2

# 指定文件移动到当前目录
% mv science.bak backups/.
```

- rm, rmdir

remove, remove directory, 移除文件/目录。例如：

```bash
# 移除文件
% rm file

# 移除目录
% rmdir directory
```

###  查看文件

- clear

clear screen, 清屏。

- cat

```bash
# 查看文件 science.txt 的内容
% cat science.txt
```

- less

```bash
# 分页查看文件（空格键翻页，q 键退出）
% less science.txt
```

- head

```bash
# 查看 science.txt 内容（前 10 行）
% head science.txt

# 查看 science.txt 前 n 行
% head -n science.txt
```

- tail

```bash
# 查看 science.txt 后 10 行
% tail science.txt

# 查看 science.txt 后 n 行
% tail -n science.txt
```

- grep

根据关键字查找。例如：

```bash
# 在 science.txt 中查找 science (区分大小写)
% grep science science.txt

# 忽略大小写
% grep -i science science.txt

# 查找字符串(左右加 ` 号)
% grep -i ‘spinning top’ science.txt
```

- wc

word count, 统计文件的字符数。例如：

```bash
% wc -w science.txt

# 统计行数
% wc -l science.txt
```

###  重定向

- 重定向输出端

```bash
# 将输出端重定向到 list1 (覆盖之前内容)
% cat > list1

# 将输出端重定向到 list1 (追加到之前内容后面)
% cat >> list1
```

合并两个文件的内容（追加）：

```bash
% cat list1 list2 > biglist
```

- sort

排序，输入 sort，然后输入一些字母或数字

按照字母或数字大小排序，例如：

```bash
# 将 biglist 排序
% sort < biglist

# 将 biglist 排序，并复制到 slist 中
% sort < biglist > slist
```

####  管道(Pipes)

```bash
# 获取当前登入用户
% who
% who > names.txt
% sort < names.txt
% who | sort
% who | wc -l
```

####  通配符

例如：

```bash
# 列出所有以 list 开头的文件或目录
% ls list*

# 列出所有以 list 结尾的文件或目录
% ls *list

# ? 表示只匹配一个字符
% ls ?list
```

####  命名规范

| Good filenames   | Bad filenames    |
| ---------------- | ---------------- |
| project.txt      | project          |
| my_big_program.c | my big program.c |
| fred_dave.doc    | fred & dave.doc  |

####  帮助文档

man command, 例如（以 wc 命令为例）：

```bash
# 多行详细描述
% man wc

# 单行描述
% whatis wc
```

```bash
# 在帮助手册页中匹配关键字
% apropos keyword
```

###  文件系统访问权限

- ls -l

```bash
# l for long listing!
% ls -l
-rw-r--r--@ 1 jaxer  staff   4.0K Feb  3 11:15 file1.gif
```

如图所示：

![file1](/Users/jaxer/GitHub-JiaoXR/IT-Skill/pics/Linux/file1.gif)

说明：

1. 第一个字符为 - 表示文件，为 d 表示目录。
2. 后面 9 个字符三个一组，分别表示 owner、group、everyone 对该文件的权限。
   1. r : read
   2. w : write
   3. x : execute
3. 之后的信息表示文件大小和修改日期。

- ls -lg

可以看到用户组更多的权限信息，例如：

```bash
-rw-r--r--@ 1 staff    4134 Feb  3 11:15 file1.gif
```

####  修改访问权

只有文件所有者才能使用 `chmod` 修改它的权限。有如下选项：

| Symbol | Meaning                        |
| ------ | ------------------------------ |
| u      | user                           |
| g      | group                          |
| o      | other                          |
| a      | all                            |
| r      | read                           |
| w      | write (and delete)             |
| x      | execute (and access directory) |
| +      | add permission                 |
| -      | take away permission           |

例如：

```bash
# 移除 group 和 others 用户对文件 biglist 的读、写和执行权限
% chmod go-rwx biglist

# 添加 group 和 others 用户对文件 biglist 的读、写权限
% chmod a+rw biglist
```

###  进程

PID (process identifier), 进程 id. 查看进程：

```bash
# 显示有终端的进程状态(process status)
% ps

# 例如：
  PID TTY           TIME CMD
 1172 ttys000    0:00.03 /Applications/iTerm.app/Contents/MacOS/iTerm2 --server login -fp jaxer
 1174 ttys000    0:00.25 -zsh
33873 ttys001    0:00.00 sleep 100
```

- 前台执行进程

```bash
# 例如：休眠 10 秒钟
% sleep 10
```

- 后台执行进程

```bash
# 后面的数字表示 PID
% sleep 10 &
[1] 33589
```

- 挂起进程

使用 `Ctrl + Z` 可以将进程挂起，之后使用 `% bg` 将其后台执行。

```bash
# 继续前台执行挂起的进程
% fg %jobnumber
```

- jobs

当一个进程运行、后台或挂起时，它将与一个 job number 保存到一个列表中（上述方括号中的标号）。

- kill

  - kill 前台执行的进程

  例如：

  ```bash
  # 使用 Ctrl + C 命令
  % sleep 100
  ^C
  ```

  - kill 挂起的进程

  ```bash
  % kill %jobnumber
  ```

  - kill 后台进行

  可以先用 `jobs` 查看列表，然后 kill.

- kill

```bash
% kill 33873

# 前者杀不掉时可以使用
% kill -9 33873
```













