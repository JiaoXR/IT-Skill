#  输入/输出重定向

大多数 UNIX 系统命令从终端接受输入，并将所产生的输出发送回终端。

一个命令通常从一个叫标准输入（默认是终端）的地方读取输入；同样，一个命令通常将其输出写入到标准输出（默认也是终端）。

重定向命令列表如下：

| 命令            | 说明                                             |
| --------------- | ------------------------------------------------ |
| command > file  | 将输出重定向到 file                              |
| command < file  | 将输入重定向到 file                              |
| command >> file | 将输出以追加的方式重定向到 file                  |
| n > file        | 将文件描述符为 n 的文件重定向到 file             |
| n >> file       | 将文件描述符为 n 的文件以追加的方式重定向到 file |
| n >& m          | 将输出文件 m 和 n 合并                           |
| n <& m          | 将输入文件 m 和 n 合并                           |
| << tag          | 将开始标记 tag 和结束标记 tag 之间的内容作为输入 |

> 注意：文件描述符 0 通常是标准输入（STDIN），1 是标准输出（STDOUT），2 是标准错误输出（STDERR）。

###  输出重定向

重定向一般通过在命令间插入特定的符号来实现。这些符号的语法如下所示：

```bash
command1 > file1
```

上面这个命令执行 command1 然后将输出的内容存入 file1。

> 注意：任何 file1 内的已经存在的内容将被新内容覆盖。如果要将新内容添加在文件末尾，请使用 `>>` 操作符。

###  输入重定向

和输出重定向一样，Unix 命令也可以从文件获取输入，语法为：

```bash
command1 < file1
```

这样，本来需要从键盘获取输入的命令会转移到文件读取内容。

### 重定向深入讲解

一般情况下，每个 Unix/Linux 命令运行时都会打开三个文件：

- 标准输入文件(stdin)：stdin 的文件描述符为 0，Unix 程序默认从 stdin 读取数据。
- 标准输出文件(stdout)：stdout 的文件描述符为 1，Unix 程序默认向 stdout 输出数据。
- 标准错误文件(stderr)：stderr 的文件描述符为 2，Unix 程序会向 stderr 流中写入错误信息。

默认情况下，command > file 将 stdout 重定向到 file，command < file 将 stdin 重定向到 file。如果希望 stderr 重定向到 file，可以这样写：

```bash
$ command 2 > file

# 如果希望 stderr 追加到 file 文件末尾，可以这样写（2 表示标准错误文件(stderr)）：

$ command 2 >> file
```

如果希望将 stdout 和 stderr 合并后重定向到 file，可以这样写：

```bash
$ command > file 2>&1

# 或者

$ command >> file 2>&1
```

如果希望对 stdin 和 stdout 都重定向，可以这样写：

```bash
$ command < file1 >file2
```

command 命令将 stdin 重定向到 file1，将 stdout 重定向到 file2。

## Here Document

Here Document 是 Shell 中的一种特殊的重定向方式，用来将输入重定向到一个交互式 Shell 脚本或程序。它的基本的形式如下：

```bash
command << delimiter
    document
delimiter
```

它的作用是将两个 delimiter 之间的内容(document) 作为输入传递给 command。

>注意：
>
>- 结尾的delimiter 一定要顶格写，前面不能有任何字符，后面也不能有任何字符，包括空格和 tab 缩进。
>- 开始的delimiter前后的空格会被忽略掉。

## /dev/null 文件

如果希望执行某个命令，但又不希望在屏幕上显示输出结果，那么可以将输出重定向到 /dev/null：

```bash
$ command > /dev/null
```

/dev/null 是一个特殊的文件，写入到它的内容都会被丢弃；如果尝试从该文件读取内容，那么什么也读不到。但是 /dev/null 文件非常有用，将命令的输出重定向到它，会起到"禁止输出"的效果。

如果希望屏蔽 stdout 和 stderr，可以这样写：

```
$ command > /dev/null 2>&1
```



> 参考链接：http://www.runoob.com/linux/linux-shell-io-redirections.html