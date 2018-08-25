#  echo 命令

Shell 的 echo 指令用于字符串的输出。命令格式：

```bash
echo string
```

####  1. 显示普通字符串

```bash
echo "It is a test"

# 这里的双引号完全可以省略，以下命令与上面实例效果一致：
echo It is a test
```

####  2. 显示转义字符

```bash
echo "\"It is a test\""

# 输出结果
# "It is a test"

# 双引号可省略，即：
echo \"It is a test\"
```

####  3. 显示变量

read 命令从标准输入中读取一行,并把输入行的每个字段的值指定给 shell 变量

```bash
#!/bin/sh
read name 
echo "$name It is a test"
```

以上代码保存为 test.sh，name 接收标准输入的变量。

####  4. 显示换行

```bash
echo -e "OK! \n" # -e 开启转义
echo "It it a test"

# 输出结果：
# OK!
#
# It it a test
```

####  5. 显示不换行

```bash
#!/bin/sh
echo -e "OK! \c" # -e 开启转义 \c 不换行
echo "It is a test"

# 输出结果：
# OK! It it a test
```

####  6. 显示结果定向到文件

```bash
echo "It is a test" > myfile
```

####  7. 原样输出字符串，不进行转义或取变量(用单引号)

```bash
echo '$name\"'

# 输出结果：
# $name\"
```

####  8. 显示命令执行结果

```bash
echo `date`
# 结果将显示当前日期，例如：
# Wed Aug 22 16:09:18 CST 2018
```

>**注意：** 这里使用的是反引号 `, 而不是单引号 '。



> 参考链接：http://www.runoob.com/linux/linux-shell-echo.html