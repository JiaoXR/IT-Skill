#  Shell 变量

###  定义变量

定义变量时，变量名不加美元符号（$，PHP 语言中变量需要），如：

```bash
your_name="runoob.com"
```

注意，变量名和等号之间不能有空格。同时，变量名的命名须遵循如下规则：

- 命名只能使用英文字母，数字和下划线，首个字符不能以数字开头；
- 中间不能有空格，可以使用下划线（_）；
- 不能使用标点符号；
- 不能使用 bash 里的关键字（可用 help 命令查看保留关键字）。

 除了显式地直接赋值，还可以用语句给变量赋值，如：

 ```shell
for file in `ls /etc`
# 或
for file in $(ls /etc)
 ```

###  使用变量

使用一个定义过的变量，只要在变量名前面加美元符号即可，如：

 ```shell
your_name="qinjx"
echo $your_name
echo ${your_name}
# 变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界
# 推荐使用花括号
 ```

已定义的变量，可以被重新定义，如：

```shell
your_name="tom"
echo $your_name
your_name="alibaba"
echo $your_name
```

###  只读变量

使用 readonly 命令可以将变量定义为只读变量，只读变量的值不能被改变。

 ```shell
#!/bin/bash
myUrl="http://www.google.com"
readonly myUrl
# 再修改时会报错：read-only variable: myUrl
myUrl="http://www.baidu.com"
 ```

###  删除变量

使用 unset 命令可以删除变量。语法：

```shell
unset variable_name
```

变量被删除后不能再次使用。unset 命令不能删除只读变量。例如：

```shell
#!/bin/sh
myUrl="http://www.runoob.com"
unset myUrl
# 无输出
echo $myUrl
```

###  变量类型

运行shell时，会同时存在三种变量：

- 局部变量

局部变量在脚本或命令中定义，仅在当前 shell 实例中有效，其他 shell 启动的程序不能访问局部变量。

- 环境变量

所有的程序，包括 shell 启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候 shell 脚本也可以定义环境变量。

- shell 变量

shell 变量是由 shell 程序设置的特殊变量。shell 变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了 shell 的正常运行。

##  Shell 字符串

字符串是shell编程中最常用最有用的数据类型（除了数字和字符串，也没啥其它类型好用了），字符串可以用单引号，也可以用双引号，也可以不用引号。

###  单引号

```shell
str='this is a string'
```

单引号字符串的限制：

- 单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
- 单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。 

###  双引号

```shell
your_name='runoob'
str="Hello, I know you are \"$your_name\"! \n"
echo $str

# 输出结果：Hello, I know you are "runoob"! 
```

双引号的优点：

- 双引号里可以有变量；
- 双引号里可以出现转义字符。

###  拼接字符串

```shell
your_name="runoob"
```

使用双引号拼接：

```shell
greeting="hello, "$your_name" !"
greeting_1="hello, ${your_name} !"
echo $greeting  $greeting_1

# 输出结果：
# hello, runoob ! hello, runoob !
```

使用单引号拼接：

```shell
greeting_2='hello, '$your_name' !'
greeting_3='hello, ${your_name} !'
echo $greeting_2  $greeting_3

# 输出结果：
# hello, runoob ! hello, ${your_name} !
```

###  获取字符串长度

```shell
string="abcd"
echo ${#string} # 输出 4
```

###  提取子字符串

以下实例从字符串第 **2** 个字符开始截取 **4** 个字符：

 ```shell
string="runoob is a great site"
echo ${string:1:4} # 输出 unoo
 ```

###  查找子字符串

查找字符 **i** 或 **o** 的位置(哪个字母先出现就计算哪个)：

 ```shell
string="runoob is a great site"
echo `expr index "$string" io`  # 输出 4
 ```

> **注意：** 以上脚本中 ` 是反引号，而不是单引号 '。
>
> PS: Mac 系统不识别 `expr` 报错。

##  Shell 数组

bash 支持一维数组（不支持多维数组），并且没有限定数组的大小。

类似于 C 语言，数组元素的下标由 0 开始编号。获取数组中的元素要利用下标，下标可以是整数或算术表达式，其值应大于或等于 0。

 ###  定义数组

在 Shell 中，用括号来表示数组，数组元素用"空格"符号分割开。例如：

```shell
array_name=(value0 value1 value2 value3)

# 或者
array_name=(
value0
value1
value2
value3
)

# 或者还可以单独定义数组的各个分量
array_name[0]=value0
array_name[1]=value1
array_name[n]=valuen
```

可以不使用连续的下标，而且下标的范围没有限制。

 ###  读取数组

读取数组元素值的一般格式是：

 ```shell
${数组名[下标]}

# 例如：
valuen=${array_name[n]}
 ```

使用 `@` 符号可以获取数组中的所有元素，例如：

 ```shell
echo ${array_name[@]}
value0 value1 value2 value3
 ```

###  获取数组的长度

获取数组长度的方法与获取字符串长度的方法相同，例如：

 ```shell
# 取得数组元素的个数
length=${#array_name[@]}
# 或者
length=${#array_name[*]}
# 取得数组单个元素的长度
lengthn=${#array_name[n]}
 ```

##  Shell 注释

- 以 `#` 开头的行就是注释，会被解释器忽略。
- sh 里没有多行注释，只能每一行加一个 `#` 号。

###  多行注释

多行注释还可以使用以下格式：

 ```shell
:<<EOF
注释内容...
注释内容...
注释内容...
EOF
 ```

EOF 也可以使用其他符号。



> 参考链接：http://www.runoob.com/linux/linux-shell-variable.html

