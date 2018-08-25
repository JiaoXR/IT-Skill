#  流程控制

在 sh/bash 里，如果 else 分支没有语句执行，就不要写这个 else。

##  if else

###  if

语法：

```bash
if condition
then
    command1 
    command2
    ...
    commandN 
fi
```

写成一行（适用于终端命令提示符）：

```bash
if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi
```

PS: 末尾的 fi 就是 if 倒过来拼写。

###  if else

语法：

```bash
if condition
then
    command1 
    command2
    ...
    commandN
else
    command
fi
```

###  if else-if else

语法：

```bash
if condition1
then
    command1
elif condition2 
then 
    command2
else
    commandN
fi
```

if else 与 test 命令结合使用，示例代码：

```bash
num1=$[2*3]
num2=$[1+5]
if test $[num1] -eq $[num2]
then
    echo '两个数字相等!'
else
    echo '两个数字不相等!'
fi
```

##  for 循环

语法：

```bash
for var in item1 item2 ... itemN
do
    command1
    command2
    ...
    commandN
done
```

写成一行：

```bash
for var in item1 item2 ... itemN; do command1; command2… done;
```

示例代码：

```bash
for loop in 1 2 3 4 5
do
    echo "The value is: $loop"
done
```

输出结果：

> The value is: 1
>
> The value is: 2
>
> The value is: 3
>
> The value is: 4
>
> The value is: 5

##  while 语句

while循环用于不断执行一系列命令，也用于从输入文件中读取数据；命令通常为测试条件。其格式为：

 ```bash
while condition
do
    command
done
 ```

示例代码：

```bash
#!/bin/bash
int=1
while(( $int<=5 ))
do
    echo $int
    let "int++"
done
```

###  无限循环

语法：

```bash
while :
do
    command
done

# 或者

while true
do
    command
done

# 或者

for (( ; ; ))
```

##  until 循环

until 循环执行一系列命令直至条件为 true 时停止。

until 循环与 while 循环在处理方式上刚好相反。一般 while 循环优于 until 循环，但在某些时候—也只是极少数情况下，until 循环更加有用。until 语法格式：

```bash
until condition
do
    command
done
```

condition 一般为条件表达式，如果返回值为 false，则继续执行循环体内的语句，否则跳出循环。

示例代码（输出 0 ~ 9 的数字）：

```bash
#!/bin/bash

a=0

until [ ! $a -lt 10 ]
do
   echo $a
   a=`expr $a + 1`
done
```

##  case

Shell case 语句为多选择语句。可以用 case 语句匹配一个值与一个模式，如果匹配成功，执行相匹配的命令。case 语句格式如下：

```bash
case 值 in
模式1)
    command1
    command2
    ...
    commandN
    ;;
模式2）
    command1
    command2
    ...
    commandN
    ;;
esac
```

case 工作方式如上所示。取值后面必须为单词 in，每一模式必须以右括号结束。取值可以为变量或常数。匹配发现取值符合某一模式后，其间所有命令开始执行直至 `;;`。

取值将检测匹配的每一个模式。一旦模式匹配，则执行完匹配模式相应命令后不再继续其他模式。如果无一匹配模式，使用星号 `*` 捕获该值，再执行后面的命令。

##  跳出循环

在循环过程中，有时候需要在未达到循环结束条件时强制跳出循环，Shell 使用两个命令来实现该功能：break 和 continue。

###  break 命令

break命令允许跳出所有循环（终止执行后面的所有循环）。示例代码：

```bash
#!/bin/bash
while :
do
    echo -n "输入 1 到 5 之间的数字:"
    read aNum
    case $aNum in
        1|2|3|4|5) echo "你输入的数字为 $aNum!"
        ;;
        *) echo "你输入的数字不是 1 到 5 之间的! 游戏结束"
            break
        ;;
    esac
done
```

###  continue

continue命令与break命令类似，只有一点差别，它不会跳出所有循环，仅仅跳出当前循环。示例代码：

```bash
#!/bin/bash
while :
do
    echo -n "输入 1 到 5 之间的数字: "
    read aNum
    case $aNum in
        1|2|3|4|5) echo "你输入的数字为 $aNum!"
        ;;
        *) echo "你输入的数字不是 1 到 5 之间的!"
            continue
            echo "游戏结束"
        ;;
    esac
done
```

###  esac

case 的语法和 C family 语言差别很大，它需要一个 esac（就是 case 反过来）作为结束标记，每个 case 分支用右圆括号，用两个分号表示 break。



> 参考链接：http://www.runoob.com/linux/linux-shell-process-control.html