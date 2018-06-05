#  MySQL 锁

##  1. MyISAM 锁

MyISAM 在执行查询语句（SELECT）前，会自动给涉及的所有表加读锁；在执行增删改操作前，会自动给涉及的表加写锁。

MySQL 的表级锁有两种模式：

- 表共享读锁（Table Read Lock）
- 表独占写锁（Table Write Lock）

对 MyISAM 表进行操作，会有以下情况：

1. 对 MyISAM 表的读操作（加读锁），不会阻塞其他进程对同一表的读操作，但会阻塞对同一表的写操作。只有当读锁释放后，才会执行其他进程的写操作。
2. 对 MyISAM 表的写操作（加写锁），会阻塞其他进程对同一表的读和写操作；只有当写锁释放后，才会执行其他进程的读写操作。

> 即：读锁会阻塞写操作，不阻塞读操作；写锁会阻塞读和写操作。

###  1.1  分析表锁定

语句如下：

```mysql
-- SQL 语句
mysql> show status like 'table%';

+----------------------------+-------+
| Variable_name              | Value |
+----------------------------+-------+
| Table_locks_immediate      | 137   |
| Table_locks_waited         | 0     |
| Table_open_cache_hits      | 20    |
| Table_open_cache_misses    | 6     |
| Table_open_cache_overflows | 0     |
+----------------------------+-------+
5 rows in set (0.00 sec)
```

- Table_locks_immediate

产生表级锁定的次数，表示可以立即获取锁的次数，每次立即获取锁，值加 1.

- Table_locks_waited

出现表级锁定争用而发生等待的次数（不能立即获取锁的次数，每等待一次值加 1），此值高则说明存在较严重的表级锁争用情况。

> MyISAM 的读写锁调度是写优先，这也是 MyISAM 不适合做写为主表的引擎。
>
> 因为写锁后，其他线程不能做任何操作，大量的更新会使查询很难得到锁，从而造成永远阻塞。 

查看哪些表被锁：

```mysql
mysql> show open tables;
```

如图所示：

![show_open_tables](https://github.com/JiaoXR/IT-Skill/blob/master/pics/MySQL/show_open_tables.png)

其中 `In_use` 为 1 表示表被锁定。

###  1.2  加锁&解锁

- 手动给表加锁

```mysql
-- 给表 person 加写锁，表 girl 加读锁
mysql> lock table person write, girl read;
```

- 解锁

```mysql
mysql> unlock tables;
```

##  2. InnoDB 锁

> InnoDB 与 MyISAM 的最大不同两点：支持事务（Transaction）；采用了行级锁。

###  2.1  事务

####  2.1.1  事务属性

事务是由一组 SQL 语句组成的逻辑处理单元，具有以下四个属性，简称 ACID 属性。

- 原子性（Atomicity）

要么全都执行，要么全都不执行。

- 一致性（Consistent）

在事务开始和完成时，数据必须保持一致状态。

- 隔离性（Isolation）

数据库系统提供一定的隔离机制，保证事务在不受外部并发操作影响的“独立“环境执行。意味着事务处理过程中对外不可见。

- 持久性（Durable）

事务完成后，它对于数据的修改是永久性的，即使出现系统故障也能保持。

####  2.1.2  事务的并发

- 脏读

事务 A 读取了事务 B 更新的数据，然后 B 回滚操作，那么 A 读取到的数据是脏数据。

- 不可重复读

事务 A 多次读取同一数据，事务 B 在事务 A 多次读取的过程中，对数据作了更新并提交，导致事务 A 多次读取同一数据时，结果不一致。

- 幻读

系统管理员 A 将数据库中所有学生的成绩从具体分数改为 ABCDE 等级，但是系统管理员 B 就在这个时候插入了一条具体分数的记录，当系统管理员 A 改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。

> 参考：https://www.cnblogs.com/huanongying/p/7021555.html

####  2.1.3  隔离级别

| 事务隔离级别                 | 脏读 | 不可重复读 | 幻读 |
| ---------------------------- | ---- | ---------- | ---- |
| 读未提交（read-uncommitted） | 是   | 是         | 是   |
| 不可重复读（read-committed） | 否   | 是         | 是   |
| 可重复读（repeatable-read）  | 否   | 否         | 是   |
| 串行化（serializable）       | 否   | 否         | 否   |

MySQL 默认的事务隔离级别为 `repeatable-read`：

```mysql
mysql> show variables like 'tx_isolation';
+---------------+-----------------+
| Variable_name | Value           |
+---------------+-----------------+
| tx_isolation  | REPEATABLE-READ |
+---------------+-----------------+
1 row in set (0.00 sec)
```

###  2.2  查看锁的状态

```mysql
mysql> show status like 'innodb_row_lock%';
+-------------------------------+-------+
| Variable_name                 | Value |
+-------------------------------+-------+
| Innodb_row_lock_current_waits | 0     |
| Innodb_row_lock_time          | 0     |
| Innodb_row_lock_time_avg      | 0     |
| Innodb_row_lock_time_max      | 0     |
| Innodb_row_lock_waits         | 0     |
+-------------------------------+-------+
5 rows in set (0.00 sec)
```

- Innodb_row_lock_current_waits : 当前正在等待锁定的数量；
- Innodb_row_lock_time (重要) : 从系统启动到现在锁定总时间长度；
- Innodb_row_lock_time_avg (重要) : 每次等待所花平均时间；
- Innodb_row_lock_time_max : 从系统启动到现在等待最长的一次花费的时间；
- Innodb_row_lock_waits (重要) : 系统启动后现在总共等待的次数。

###  2.3  优化建议

- 尽可能让所有数据检索都通过索引来完成，避免无索引行锁升级为表锁；
- 合理设计索引，尽量缩小锁的范围；
- 尽可能减少检索条件，避免间隙锁；
- 尽量控制事务大小，减少锁定资源量和时间长度；
- 尽可能低级别事务隔离。

> PS : 检索 VARCHAR 类型字段一定要加单引号！！

###  2.4  间隙锁

当使用范围条件检索数据，并请求共享或排它锁时，InnoDB 会给符合条件的已有数据记录的索引项加锁。

对于键值在条件范围内，但并不存在的记录（可能被物理删除），叫做“间隙（GAP）”，InnoDB 也会对这个“间隙”加锁，这种机制称为间隙锁（Next-Key锁）。

- 危害

间隙锁有一个比较致命的弱点：即当锁定一个键值范围后，即使某些不存在的键值也会被锁定，从而造成无法插入锁定键值范围内的任何数据。在某些场景下可能会对性能造成很大的危害。

### 2.5  比较

InnoDB 存储引擎由于实现了行级锁定，虽然在锁定机制的实现方面所带来的性能损耗可能比表级锁定会更高一些，但在整体并发处理能力方面要远远优于 MyISAM 的表级锁定。当系统并发量较高的时候，InnoDB 的整体性能相较于 MyISAM 优势比较明显。

但 InnoDB 的行级锁定有其脆弱的一面，当使用不当的时候，可能会使 InnoDB 的整体性能不如 MyISAM。

###  2.6  其他

- 页锁

开销和加锁时间介于表锁和行锁之间；会出现死锁；锁定粒度介于行锁和表锁之间，并发度一般。

- 锁定一行

```mysql
-- 锁定某一行后，其他的操作会被阻塞，直到锁定行的会话提交(commit)
SELECT xxx FROM tb_name WHERE ... FOR UPDATE
```

示例代码：

```mysql
mysql> begin;
mysql> select * from employee where id = 8 for update;
mysql> commit;
```

