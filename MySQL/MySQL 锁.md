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



####  分析表锁定

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





####  加锁&解锁

- 手动给表加锁

```mysql
-- 给表 person 加写锁，表 girl 加读锁
mysql> lock table person write, girl read;
```

- 解锁

```mysql
mysql> unlock tables;
```



###  其他

- MySQL 默认事务隔离级别

```mysql
mysql> show variables like 'tx_isolation';
+---------------+-----------------+
| Variable_name | Value           |
+---------------+-----------------+
| tx_isolation  | REPEATABLE-READ |
+---------------+-----------------+
1 row in set (0.00 sec)
```



##  2. InnoDB 锁

InnoDB 与 MyISAM 的最大不同两点：支持事务（Transaction）；采用了行级锁。

###  事务属性

事务是由一组 SQL 语句组成的逻辑处理单元，具有以下四个属性，简称 ACID 属性。

- 原子性（Atomicity）

要么全都执行，要么全都不执行。

- 一致性（Consistent）

在事务开始和完成时，数据必须保持一致状态。

- 隔离性（Isolation）

数据库系统提供一定的隔离机制，保证事务在不受外部并发操作影响的“独立“环境执行。意味着事务处理过程中对外不可见。

- 持久性（Durable）

事务完成后，它对于数据的修改是永久性的，即使出现系统故障也能保持。



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



###  优化建议

- 尽可能让所有数据检索都通过索引来完成，避免无索引行锁升级为表锁；
- 合理设计索引，尽量缩小锁的范围；
- 尽可能减少检索条件，避免间隙锁；
- 尽量控制事务大小，减少锁定资源量和时间长度；
- 尽可能低级别事务隔离。

> PS : 检索 VARCHAR 类型字段一定要加单引号！！

###  页锁

开销和加锁时间介于表锁和行锁之间；会出现死锁；锁定粒度介于行锁和表锁之间，并发度一般。