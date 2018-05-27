#  MySQL 索引

###  1. 概念

索引：“排好序的快速查找数据结构”。

一般来说索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储在磁盘上。

索引若未特别指明，都是指 B 树（多路搜索树，并不一定是二叉的）结构组织的索引。其中聚集索引，次要索引，复合索引，前缀索引，唯一索引默认都是 B+ 树索引，通常索引。除了 B+ 树这种类型的索引外，还有哈希索引（hash index）等。

###  2. 分类

- 单值索引：即一个索引只包含单个列，一个表可以有多个单值索引。
- 唯一索引：索引列的值必须唯一，但允许有空值。
- 复合索引：一个索引包含多个列。



###  3. 优缺点

####  缺点

- 索引实际上也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是要占空间的。
- 索引会降低更新表的速度。如对表进行 INSERT、UPDATE 和 DELETE。因为更新表时，MySQL 不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段。
- 索引只是提高效率的一个因素，若 MySQL 中有大量数据的表，就需要花时间研究建立最优秀的索引，或优化查询条件。



###  4. 语法

- 创建

```mysql
-- 方式一
CREATE [UNIQUE] INDEX indexName ON mytable(columnName(length));

-- 方式二
ALTER mytable ADD [UNIQUE] INDEX [indexName] ON mytable(columnName(length));
```

示例代码：

```mysql
-- 单值索引
CREATE INDEX idx_user_name ON user(name);

-- 复合索引
CREATE INDEX idx_user_nameEmail ON user(name, email);
```

- 删除

```mysql
DROP INDEX [indexName] ON mytable;
```

- 查看

```mysql
SHOW INDEX FROM mytable;
```

###  5. 创建条件

####  5.1  创建索引条件

- 主键自动建立唯一索引；
- 频繁作为查询条件的字段应该建立索引；
- 查询中与其它表关联的字段，外键关系建立索引；
- 频繁更新的字段不适合创建索引——每次更新记录还需要更新索引。
- WHERE 条件里用不到的字段不创建索引。

####  5.2  哪些情况不建索引

- 表记录太少（一般 300W 左右开始考虑建索引）；

- 经常增删改的表；

- 数据重复且分布平均的表字段，实际效果不大

   举例：假如一个表有 10W 条记录，有个字段 A 只有 T 和 F 两种值，且每个值的分布概率大约为 50%，那么对这种表 A 字段建索引一般不会提高数据库的查询速度。

   索引的选择性是指索引列中不同值的数目与表中记录数的比。

   举例：如果一个表中有 2000 条记录，表索引列有 1980 个不同的值，那么这个索引的选择性就是 1980/2000=0.99，一个索引的选择性越接近于 1，这个索引的效率就越高。





###  6. EXPLAIN 关键字

使用 EXPLAIN 关键字可以模拟优化器执行 SQL 查询语句，从而知道 MySQL 是如何处理你的 SQL 语句的。分析查询语句或是表结构的性能瓶颈。结构如下：

| id | select_type | table | type | possible_keys | key | key_len | ref | rows | Extra |
|||||||||||
||

####  6.1  id

1. id 相同，执行顺序由上至下；
2. 如果是子查询，id 的序号会递增，id 值越大优先级越高，越先被执行；
3. id 相同不同，同时存在。（id 若相同，可以认为是一组，从上往下顺序执行；在所有组中，id 值越大，优先级越高，越先执行）。

####  6.2  select_type

1. SIMPLE：简单的 select 查询，不包含子查询或 union；
2. PRIMARY：查询中若包含任何复杂的子部分，最外层被标记为 PRIMARY；
3. SUBQUERY：在 select 或 where 列表中包含子查询；
4. DERIVED：在 from 列表中包含的子查询被标记为 DERIVED（衍生），MySQL 会递归执行这些子查询，把结果放在临时表里。
5. UNION：若第二个 select 出现在 union 之后，则被标记为 union；若 union 包含在 from 子句的子查询中，外层 select 被标记为 derived；
6. UNION RESULT：从 union 表获取结果的 select。

####  6.3  type

type 显示的访问类型，是较为重要的一个指标，其结果值从最好到最差依次是：

 `system > const > eq_ref > ref > range > index > ALL`

一般来说，要保证查询至少达到 range 级别，最好达到 ref。

- const：表示通过索引一次就找到了，const 用于比较 primary key 或者 unique 索引。
- eq_ref：唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或唯一索引扫描。
- ref：非唯一性索引扫描，返回匹配某个单独值的所有行。
- range：只检索给定范围的行，使用一个索引来选择行。一般是在 where 语句中出现了 between、<、>、in 等的查询。
- index：Full Index Scan，index 与 ALL 的区别为 index 类型只遍历索引树。通常比 ALL 快，因为索引文件通常比数据文件小（也就是说虽然 ALL 和 index 都是读全表，但 index 是从索引中读取的，而 ALL 是从硬盘中读取的）。
- key_len：表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。在不损失精度的情况下，长度越短越好。 key_len 显示的值为索引字段的最大可能长度，并非实际使用长度，即 key_len 是根据表定义计算而得，不是通过表内检索出的。 

####  6.4  refer

显示索引的哪一列被使用了，如果可能的话，是一个常数。

####  6.5  rows

根据表统计信息及索引选用情况，大致估算出找到所需的记录所需读取的行数。

####  6.6  Extra

1. Using filesort(💀)：说明 MySQL 会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。MySQL 中无法利用索引完成的排序操作称为“文件排序”。
2. Using temporary(💀)：使用了临时表保存中间结果，MySQL 在对查询结果排序时使用临时表。常见于排序 order by 和分组查询 group by。
3. Using index(😀)：表示相应的 select 操作中使用了覆盖索引（Covering Index），避免访问了表的数据行，效率不错！如果同时出现 Using where，表明索引被用来执行索引键值的查找；如果没有同时出现 Using where，表明索引用来读取数据而非执行查找操作。
   1. 覆盖索引：理解方式一：就是 select 的数据列只用从索引中就能够取得，不必读取数据行，MySQL 可以利用索引返回 select 列表中的字段，而不必根据索引再次读取数据文件，即查询列要被所建的索引覆盖。
   2. 注意：如果要使用覆盖索引，一定要注意 select 列表中只取出需要的列，不可 select *。
4. Using where：表明使用了 where 过滤；
5. Using join buffer：使用了连接缓存；
6. impossible where：where 子句的值总是 false；




###  7. 排序使用索引

####  7.1  order by 关键字优化

KEY a_b_c(a,b,c)

- order by 能使用最左前缀
  - ORDER BY a
  - ORDER BY a, b
  - ORDER BY a, b, c
  - ORDER BY a DESC, b DESC, c DESC
- 若 where 语句使用索引的最左前缀定义为常量，则 order by 能使用索引
  - WHERE a=const ORDER BY b, c
  - WHERE a=const AND b=const ORDER BY c
  - WHERE a=const AND b>const ORDER BY b, c
- 不能使用索引进行排序
  - ORDER BY a ASC, b DESC, c DESC  （排序不一致）
  - WHERE g=const ORDER BY b, c  （丢失 a）
  - WHERE a=const ORDER BY c  （丢失 b）
  - WHERE a=const ORDER BY a, d  （d 不是索引的一部分）
  - WHERE a IN(...) ORDER BY b, c  （多个条件也是范围查询）

####  7.2  group by 关键字优化

1. group by 实质是先排序后进行优化，遵照索引的最佳左前缀原则
2. 无法使用索引列时，增大 `max_length_for_sort_data` 参数的设置 + 增大 `sort_buffer_size` 参数的设置
3. where 高于 having，能写在 where 限定的条件，就不要去 having 限定



###  7. 索引小结

####  7.1  小结  

1. 全值匹配我最爱（即，需要的搜索条件刚好都在索引中）
2. 最佳左前缀法则：如果索引多列，要遵守最佳左前缀法则。（“带头大哥不能死，中间兄弟不能断”，指的是查询从索引的最左前列开始，并且不跳过索引的中间列）


3. 不在索引列上做任何操作（计算、函数、（自动或手动）类型转换等），会导致索引失效而转向全表
4. 存储引擎不能使用索引中范围条件（`>、<、in、between...and` 等），否则右边的列索引会失效
5. 尽量使用覆盖索引（只访问索引的查询（索引列和查询列一致）），减少 `select *`
6. MySQL 在使用不等于（!= 或 <>）时无法使用索引，会导致全表扫描
7. `is null, is not null` 也无法使用索引
8. LIKE 以通配符开头（`%abc...` 或 `%abc..%`）时，索引会失效；
9. 字符串不加单引号索引失效；
10. 少用 OR，用它来连接时会索引失效。



####  7.2  例子

假设索引为 `index(a,b,c)`

|                  WHERE 语句                   |          索引是否被用到          |
| :-------------------------------------------: | :------------------------------: |
|                   WHERE a=3                   |            Y, 用到 a             |
|               WHERE a=3 AND b=5               |           Y, 用到 a, b           |
|           WHERE a=3 AND b=5 AND c=4           |         Y, 用到 a, b, c          |
| WHERE b=3 或 WHERE b=3 AND c=4 或者 WHERE c=4 |                N                 |
|               WHERE a=3 AND c=5               | Y, 用到 a, c 不可用，因为 b 中断 |
|           WHERE a=3 AND b>4 AND c=5           | Y, 用到 a 和 b, c 在范围后，中断 |
|      WHERE a=3 AND b LIKE 'kk%' AND c=4       |         Y, 用到 a, b, c          |
|      WHERE a=3 AND b LIKE '%kk' AND c=4       |            Y, 用到 a             |
|      WHERE a=3 AND b LIKE '%kk%' AND c=4      |            Y, 用到 a             |
|     WHERE a=3 AND b LIKE 'k%kk%' AND c=4      |         Y, 用到 a, b, c          |

