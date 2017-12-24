# MySQL 小结

##  1 初始操作

###  1.1 登录&退出

登录 MySQL：`mysql -u root -p;`

退出 MySQL：

1. `\q;`
2. `quit;`
3. `exit;`

###  1.2 数据库相关操作

查看所有数据库：`SHOW DATABASES;`

创建数据库：`CREATE DATABASES <database_name> [CHARACTER SET utf8]; --可设置编码格式`

修改数据库编码格式：`ALTER DATABASES <table_name> CHARACTER SET = gbk;`

查看数据库创建语句：`SHOW CREATE DATABASES <table_name>;`

删除数据库：`DROP DATABASES <database_name>;`

打开某个数据库：`USE <database_name>;`

##  2 数据类型

###  2.1 整型

| MySQL 数据类型 | 字节数   |
| :--------- | ----- |
| TINYINT    | 1 个字节 |
| SMALLINT   | 2 个字节 |
| MEDIUMINT  | 3 个字节 |
| INT        | 4 个字节 |
| BIGINT     | 8 个字节 |

###  2.2 浮点型

| MySQL 数据类型     | 含义                                |
| -------------- | --------------------------------- |
| FLOAT[(M, D)]  | 单精度浮点数，8 位精度（4 个字节），总共 M 位，D 位小数  |
| DOUBLE[(M, D)] | 双精度浮点数，16 位精度（8 个字节），总共 M 位，D 位小数 |

###  2.3 日期类型

| MySQL 数据类型 | 字节数  |
| ---------- | ---- |
| YEAR       | 1    |
| TIME       | 3    |
| DATE       | 3    |
| DATETIME   | 8    |
| TIMESTAMP  | 4    |

###  2.4 字符型

| MySQL 数据类型                    | 含义                      |
| ----------------------------- | ----------------------- |
| CHAR(M)                       | 固定长度，最多 255 个字符         |
| VARCHAR(M)                    | 固定长度，最多 65535 个字符       |
| TINYTEXT                      | 可变长度，最多 255 个字符         |
| TEXT                          | 可变长度，最多 65535 个字符       |
| MEDIUMTEXT                    | 可变长度，最多 2 的 24 次方-1 个字符 |
| LONGTEXT                      | 可变长度，最多 2 的 32 次方-1 个字符 |
| ENUM('value1', 'value2', ...) | 枚举                      |
| SET('value1', 'value2', ...)  | 集合                      |

##  3. 数据表

查看（可指定）数据库下所有表：`SHOW TABLES [from <database_name>]; --某数据库下的表`

查看表结构：`show columns from <table_name>;` 或者 `desc <table_name>;`

查看建表语句：`show create table <table_name>;`

###  3.1  约束

1. 保证数据的完整性和一致性。
2. 约束分为表级约束和列级约束。
   1. 列级约束：针对一列；既可以在列定义时声明，也可以在列定义后声明。
   2. 表级约束：针对多列。只能在列定义后声明。
3. 约束类型
   1. NOT NULL 非空约束；
   2. PRIMARY KEY 主键约束；
      1. 每张数据表只存在一个主键，主键保证记录的唯一性；
      2. 主键自动为 NOT NULL；
      3. 主键不一定是 AUTO_INCREMENT.
   3. UNIQUE KEY 唯一约束；
      1. 唯一约束可以保证记录的唯一性；
      2. 唯一约束的字段可以为空值（NULL）；
      3. 每张数据表可以存在多个唯一约束。
   4. DEFAULT 默认约束；
      1. 插入记录时, 若没有明确为字段赋值, 则自动赋予默认值。
   5. FOREIGN KEY 外键约束。
      1. 实现一对一或一对多关系；
      2. 父表和子表必须使用相同的存储引擎（只能为 InnoDB），而且禁止使用临时表；
      3. 外键列和参照列必须具有相似的数据类型（数字型符号长度等，字符型长度可以不同）；
      4. 外键列和参照列必须创建索引（若外键列不存在索引，MySQL 将自动为其创建）。

PS: 外键约束还有一些参照操作，例如 `CASCADE, SET NULL, RESTRICT, NO ACTION` 等，需要时再详查。

###  3.2 修改数据表

- 修改字段

添加单列：`ALTER TABLE <table_name> ADD [COLUMN] <col_name1> col_definition1 [FIRST | AFTER <col_name2>];`

删除一列：`ALTER TABLE <table_name> DROP <col_name>;`

删除多列：`ALTER TABLE <table_name> DROP <col_name1>, DROP <col_name2>;`

- 修改约束

添加主键约束：

`ALTER TABLE <table_name> ADD [CONSTRAINT [symbol]] PRIMARY KEY [index_type] (index_col_name,…);`

删除主键约束：

`ALTER TABLE <table_name> DROP PRIMARY KEY;  —因为主键约束只有一个，因此无需加名称`



添加唯一约束：

`ALTER TABLE <table_name> ADD [CONSTRAINT [symbol]] UNIQUE [KEY | INDEX][index_name] [index_type] (index_col_name,…);`

删除唯一约束：

`ALTER TABLE <table_name> DROP {INDEX|KEY} index_name;`



添加外键约束：

`ALTER TABLE <table_name> ADD [CONSTRAINT [symbol]] FOREIGN KEY [index_name][index_type] (index_col_name,…) reference_definition;`

删除外键约束：

`ALTER TABLE <table_name> DROP FOREIGN KEY fk_symbol;`



添加/删除默认约束：

`ALTER TABLE <table_name> ALTER [COLUMN] col_name {SET DEFAULT literal | DROP DEFAULT};`

- 修改列

修改列定义：

`ALTER TABLE <table_name> MODIFY [COLUMN] <col_name> column_definition [FIRST | AFTER col_name];`

修改列名称：

`ALTER TABLE <table_name> CHANGE [COLUMN] <old_col_name> <new_col_name> col_definition [FIRST | AFTER <col_name>];`

PS:  `CHANGE` 既可以修改列定义，又可以修改列名称。 

- 数据表更名

1. `ALTER TABLE <table_name> RENAME [TO|AS] new_table_name;`
2. `RENAME TABLE <table_name> TO new_table_name[, table_name2 TO new_table_name2];`

PS: 建议尽量不要修改数据列和表的名字（可能会影响到索引和存储过程等）。

### 3.3 记录的操作(CRUD)

####  3.3.1 插入记录

语法：`INSERT [INTO] <table_name> [(<col_name1, col_name2...>)] {VALUES|VALUE} (val1, val2…);`

可以为查询结果字段取别名，示例代码：`SELECT id AS userId, username AS uname FROM tb1;`

1. 推荐取别名时使用 AS 关键字。
2. 为主键赋值时，可设置为 NULL 或 DEFAULT.

- 第二种形式（可使用子查询）

语法：`INSERT [INTO] <table_name> SET col_name={expr|DEFAULT},…`

- 第三种形式

语法：`INSERT [INTO] <table_name1(col_name)> SELECT <col_name> FROM <table_name2>;`

####  3.3.2 更新记录（单表更新）

语法：`UPDATE [LOW_PRIORITY][IGNORE] table_reference SET col_name1={expr|DEFAULT} [, col_name2={expr|DEFAULT}]…[WHERE where_condition]`

PS: 省略 WHERE 条件时，所有的记录都将更新。

####  3.3.3 删除记录

语法：`DELETE FROM <table_name> [WHERE where_condition];`

####  3.3.4 GROUP BY

对查询结果进行分组：

语法：`[GROUP BY {col_name|position} [ASC|DESC], …];`

####  3.3.5 HAVING

分组条件：

语法：`[HAVING where_condition];`

示例代码：`SELECT name, age FROM user GROUP BY age HABING age > 20;`

注意：HAVING 中的字段，要么在前面的 SELECT 语句中出现；要么是一个聚合函数（MAX, MIN, AVG, SUM 等，只有一个返回值的函数）。

####  3.3.6 ORDER BY

对查询结果进行排序（默认升序），

语法：`[ORDER BY {col_name|expr|position} [ASC|DESC], …];`

####  3.3.7 LIMIT

限制查询结果的记录条数

语法：`[LIMIT {[offset,] row_count | row_count OFFSET offset}];`

前者为起始位置，后者为记录数量。

##  4. 运算符和函数

###  4.1 字符函数

- CONCAT() : 字符串连接。
- CONCAT_WS() : 使用指定的分隔符进行字符串连接。

示例代码：`SELECT CONCAT_WS('-', 'hello', 'world');`

其中第一个指定的为分隔符，后面的为要连接的字符串。

- FORMAT() : 数字格式化。

示例代码：`SELECT FORMAT(1234.56, 1);`

其中前者为要进行格式化的数字，后者为保留的位数（可为负数）。

- LOWER() : 转小写字母。
- UPPER() : 转大写字母
- LEFT() : 获取左侧字符。

示例代码：`SELECT LEFT(MySQL, 2);`

从左边取两位，结果为 `My` 

- RIGHT() : 获取右侧字符。

- LENGTH() : 获取字符串长度。

- LTRIM() : 删除前面的空格。

- RTRIM() : 删除后面的空格。

- TRIM() : 删除前面和后面的空格。

  - 在前面删除指定的字符（`LEADING` 关键字）

  示例代码：`SELECT TRIM(LEADING '?' FROM '??MySQL???');`

  前面的 `?` 将被删除。

  - 在后面删除指定的字符（`TRAILING` 关键字）

  示例代码：`SELECT TRIM(TRAILING '?' FROM '??MySQL???');`

  - 删除前后指定的字符（`BOTH` 关键字）

  PS: 不能删除字符串中间的字符，若要这么做，使用 `REPLACE()` 函数。


- SUBSTRING() :  字符串截取。

示例代码：`SELECT SUBSTRING('MySQL, 1, 2');`

从第 1 位开始，截取 2 位。

- [NOT] LIKE : 模糊匹配。

通配符 `%` 表示任意一个或多个字符；`_(下划线)`  表示任意一个字符。

值得注意的是，若要匹配的字符串中包含 `%`, 则可用如下方法匹配：

`SELECT * FROM <table_name> WHERE <col_name> LIKE '%1%%' ESCAPE '1';`

这里 `1` 之后的 `%` 不再认为是通配符（可以自己指定其他的）。

- REPLACE() : 字符串替换。

###  4.2 数值运算符与函数

- CEIL() : 进一法取整数。
- FLOOR() : 去尾法取整数。
- DIV : 整数除法。
- MOD : 取余数（取模）。
- POWER() : 幂运算。
- ROUND() : 四舍五入。
- TRUNCATE() : 数字截取。

###  4.3 比较运算符与函数

- [NOT] BETWEEN…AND… : [不]在范围之内。
- [NOT] IN() : [不]在列出值范围内。
- IS [NOT] NULL : [不]为空。

###  4.4 日期时间函数

- NOW() : 当前日期和时间。
- CURDATE() : 当前日期。
- CURTIME() : 当前时间。
- DATE_ADD() : 日期变化（可增减）。
- DATEDIFF() : 日期差值。
- DATE_FORMAT() : 日期格式化。

把日期转为指定格式，示例代码：`SELECT DATE_FORMAT(NOW(), '%Y%m%d %H:%m:%s’);`

###  4.5 信息函数

- CONNECT_ID() : 连接 ID。
- DATABASE() : 当前数据库。
- LAST_INSERT_ID() : 最后插入记录的 ID 号。

PS: 写入多条记录时，得到写入第一条记录的 ID。

- USER() : 当前用户。
- VERSION() : 版本信息。

###  4.6 聚合函数

- AVG() : 平均值。
- COUNT() : 计数。
- MAX() : 最大值。
- MIN() : 最小值。
- SUM() : 求和。

###  4.7 加密函数

- MD5() : 信息摘要算法（可用作用户登录密码）。
- PASSWORD() : 密码算法（一般用于数据库的登录密码）。

##  5. 自定义函数

###  5.1 概念

用户自定义函数（user-defined function, UDF）是对 MySQL 函数的扩展，其用法与内置函数相同。

自定义函数的两个条件：

1. 参数（可为空）；
2. 返回值（可为任意类型）。

###  5.2 语法

- 创建自定义函数

```mysql
CREATE FUNCTION function_name
RETURNS
{STRING|INTEGER|REAL|DECIMAL}
routine_body
```

函数体：

1. 由合法的 SQL 语句构成；
2. 可以是简单的 SELECT 或 INSERT 语句；
3. 若为复合结构，则需使用 `BEGIN...END` 语句；
4. 复合结构可包含声明、循环、控制结构。

- 删除函数

语法：`DROP FUNCTION [IF EXISTS] function_name;`

### 5.3 示例代码

定义无参数函数：

```mysql
CREATE FUNCTION my_time() RETURNS VARCHAR(30)
RETURN DATE_FORMAT(NOW(), '%Y年%m月%d日 %H时:%m分:%s秒');
```

定义带参数函数：

```mysql
CREATE FUNCTION f2(num1 SMALLINT UNSIGNED,num2 SMALLINT UNSIGNED)
RETURNS FLOAT(10,2) UNSIGNED
RETURN (num1+num2)/2;
```

##  6  存储过程

###  6.1 概念

存储过程是 SQL 语句和控制语句的预编译集合，以一个名称存储，并作为一个单元处理。

优点：

1. 增强 SQL 语句的功能和灵活性；
2. 实现较快的执行速度；
3. 减少网络流量。

###  6.2 语法&示例代码

####  6.2.1 创建存储过程

- 语法

```mysql
CREATE
[DEFINER = {user|CURRENT_USER}]
PROCEDURE sp_name ([proc_parameter[, ...]])
[characteristic ...] routine_body

proc_parameter:
[IN|OUT|INOUT] param_name type
```

参数：

1. IN : 表示该参数的值必须在调用存储过程时指定（输入参数）；
2. OUT : 表示该参数的值可以被存储过程改变，且可以返回（返回值）；
3. INOUT : 表示该参数在调用时指定，且可以被改变和返回（既可以做输入，又可以做返回值）。

存储体与函数体相似。

- 示例代码

```mysql
CREATE PROCEDURE pro_name(IN param1 INT UNSIGNED, OUT param2 INT UNSIGNED)
BEGIN
DELETE FROM <table_name> WHERE id = param1;
SELECT COUNT(col_name) FROM <table_name> INTO param2; 
END
```

注意：参数名与表中的字段名要区分开，不要有歧义。

PS: 创建存储过程时，一般会先修改 MySQL 的结束标志符。例如：

```mysql
delimiter // : -- 将 MySQL 的结束标志改为 //（可自定义）
```

####  6.2.2 调用存储过程

- 语法

```mysql
CALL sp_name([parameter[, ...]])
CALL sp_name[()]
```

PS: 若存储过程无参数，则其后的 `()` 可以省略。

- 示例代码

```mysql
CALL pro_name(param1, @num);
```

其中，@ 声明的变量为用户变量（类似全局变量），可用于接收存储过程的返回值，之后用 `SELECT` 查看。

####  6.2.3 删除存储过程

语法：

```mysql
DROP PROCEDURE [IF EXISTS] sp_name;
```

###  6.3 存储过程与自定义函数的区别

- 存储过程实现的功能略复杂，而函数的针对性更强；
- 存储过程可以返回多个值，而函数只能有一个返回值；
- 存储过程一般独立执行，而函数可以作为其他 SQL 语句的组成部分。

常用的操作可封装为存储过程（执行效率较高）。

##  7 存储引擎

###  7.1 概念

MySQL 可以将数据以不同的技术存储在文件（内存）中，这种技术称为存储引擎。

即，存储引擎是一种存储和查询数据的技术。

MySQL 支持的存储引擎：MyISAM, InnoDB, Memory, CSV, Archive.

###  7.2 其他相关概念

- 并发控制

当多个连接对记录进行操作时，保证数据的一致性和完整性。

- 锁

  - 共享锁（读锁）

  在同一时间段内，多个用户可以读取同一个资源，读取过程中数据不会发生任何变化。

  - 排他锁（写锁）

  在任何时候，只能有一个用户写入资源，在此期间会阻塞其他的读锁或写锁操作。

  - 锁粒度

    - 表锁

    是一种开销最小的锁策略。

    - 行锁

    是一种开销最大的锁策略。行锁可能有多个（表中有多少记录，就可能有多少个行锁）。

- 事务

  保证数据库的完整性。特性：

  - 原子性（Atomicity）
  - 一致性（Consistency）
  - 隔离性（Isolation）
  - 持久性（Durability）

- 索引

对数据表中一列或多列的值进行排序的一种结构。

索引的类型有很多，比如普通索引、唯一索引、全文索引、btree 索引、hash 索引……

BlackHole：黑洞索引，写入的数据都会消失，一般用作数据复制的中继。

###  7.3 几种存储引擎的特点

集中存储引擎的特点如下：

| 特点\存储引擎 | MyISAM | InnoDB | Memory | Archive |
| ------- | ------ | ------ | ------ | ------- |
| 存储限制    | 256TB  | 64TB   | 有      | 无       |
| 事务安全    | -      | 支持     | -      | -       |
| 索引支持    | 支持     | 支持     | 支持     |         |
| 锁粒度     | 表锁     | 行锁     | 表锁     | 行锁      |
| 数据压缩    | 支持     | -      | -      | 支持      |
| 外键支持    | -      | 支持     | -      | -       |

MyISAM : 适用于事务处理不多的情况。

InnoDB : 适用于事务处理较多，需要外键支持的情况。

### 7.4 修改存储引擎

1. 修改 MySQL 配置文件

`default-storage-engine = engine`

2. 通过创建数据表命令实现

```mysql
CREATE TABLE <table_name> (
...
) ENGINE = <engine_name>;
```

3. 通过修改数据表命令修改

```mysql
ALTER TABLE <table_name> ENGINE [=] engine_name;
```

##  8 其他

###  8.1 规范

- 关键字与函数名称全部大写；
- 数据库名称、表名称、字段名称全小写；
- SQL语句必须以分号结尾。

###  8.2 其他命令

delimiter // : 将 MySQL 的结束标志改为 //（可自定义）

查看数据表的索引：`show indexes from <table>[\G]`; —后面加 `’\G’` 表示查询结果以网格形式显示

查看警告信息：`show warnings;`



>慕课网视频链接：<https://www.imooc.com/learn/122>