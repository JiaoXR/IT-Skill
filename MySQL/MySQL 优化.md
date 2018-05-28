#  MySQL 优化



##  1. 慢查询日志

- 查看是否开启

```mysql
mysql> show variables like '%slow_query_log%';
```

![slow_query_log](https://github.com/JiaoXR/IT-Skill/blob/master/pics/MySQL/slow_query_log.png)

- 开启命令

```mysql
-- 该命令只对当前数据库有效，MySQL 重启后会失效
mysql> set global slow_query_log=1;
```

- 设置慢查询时间阈值

```mysql
-- 例如，将慢查询时间阈值设置为 3 秒
mysql> set global long_query_time=3;

-- 查看慢查询时间阈值
mysql> show variables like '%long_query_time%';
```

- 查看慢查询日志条数

```mysql
mysql> show global status like '%Slow_queries%’;
```

![Slow_queries](https://github.com/JiaoXR/IT-Skill/blob/master/pics/MySQL/show_slow_queries.png)



###  2. 日志分析工具 mysqldumpslow

####  2.1  mysqldumpslow 常用帮助信息

- s : 表示按照何种方式排序
- c : 访问次数
- l : 锁定时间
- r : 返回记录
- t : 查询时间
- al : 平均锁定时间
- ar : 平均返回记录数
- at : 平均查询时间
- t : 即为返回前面多少条数据
- g : 后面搭配一个正则匹配模式，大小写不敏感

详细信息如下：

```bash
# 注意该命令是在 terminal 执行，而非在 MySQL 内部
$ mysqldumpslow --help
Usage: mysqldumpslow [ OPTS... ] [ LOGS... ]

Parse and summarize the MySQL slow query log. Options are

  --verbose    verbose
  --debug      debug
  --help       write this text to standard output

  -v           verbose
  -d           debug
  -s ORDER     what to sort by (al, at, ar, c, l, r, t), 'at' is default
                al: average lock time
                ar: average rows sent
                at: average query time
                 c: count
                 l: lock time
                 r: rows sent
                 t: query time
  -r           reverse the sort order (largest last instead of first)
  -t NUM       just show the top n queries
  -a           don't abstract all numbers to N and strings to 'S'
  -n NUM       abstract numbers with at least n digits within names
  -g PATTERN   grep: only consider stmts that include this string
  -h HOSTNAME  hostname of db server for *-slow.log filename (can be wildcard),
               default is '*', i.e. match all
  -i NAME      name of server instance (if using mysql.server startup script)
  -l           don't subtract lock time from total time
```

####  2.2  举例说明

日志例子：

```bash
$ cat data/Phoenix-slow.log
/usr/local/mysql/bin/mysqld, Version: 5.7.19 (MySQL Community Server (GPL)). started with:
Tcp port: 3306  Unix socket: /tmp/mysql.sock
Time                 Id Command    Argument
# Time: 2018-05-27T16:21:14.764669Z
# User@Host: root[root] @ localhost []  Id:    35
# Query_time: 4.009312  Lock_time: 0.000000 Rows_sent: 1  Rows_examined: 0
SET timestamp=1527438074;
select sleep(4);
```

慢查询日志分析：

```bash
$ mysqldumpslow -s r -t 10 /usr/local/mysql/data/Phoenix-slow.log

Reading mysql slow query log from /usr/local/mysql/data/Phoenix-slow.log
Count: 1  Time=4.01s (4s)  Lock=0.00s (0s)  Rows=1.0 (1), root[root]@localhost
  select sleep(N)

Died at /usr/local/mysql/bin/mysqldumpslow line 161, <> chunk 1.
```



###  3. Show Profile

####  3.1  查看是否开启

```mysql
-- 默认关闭，默认记录最近 15 条操作记录
mysql> show variables like '%profiling%';
+------------------------+-------+
| Variable_name          | Value |
+------------------------+-------+
| have_profiling         | YES   |
| profiling              | OFF   |
| profiling_history_size | 15    |
+------------------------+-------+
3 rows in set (0.01 sec)
```

####  3.2  开启 profile

```mysql
mysql> set profiling=on;

-- 验证开启成功
mysql> show variables like '%profiling%';
+------------------------+-------+
| Variable_name          | Value |
+------------------------+-------+
| have_profiling         | YES   |
| profiling              | ON    |
| profiling_history_size | 15    |
+------------------------+-------+
3 rows in set (0.01 sec)
```

####  3.3  查看记录结果

```mysql
mysql> show profiles;
+----------+------------+-----------------------------------+
| Query_ID | Duration   | Query                             |
+----------+------------+-----------------------------------+
|        1 | 0.00355100 | show variables like '%profiling%' |
|        2 | 0.00024300 | select * from employee            |
|        3 | 0.00024900 | select * from person              |
|        4 | 0.00037300 | show tables                       |
|        5 | 0.00023100 | select * from girl                |
+----------+------------+-----------------------------------+
5 rows in set, 1 warning (0.00 sec)
```

####  3.4  查看（常用参数）详情（一条查询记录的完整过程）

```mysql
mysql> show profile cpu,block io for query 3;
+----------------------+----------+----------+------------+--------------+---------------+
| Status               | Duration | CPU_user | CPU_system | Block_ops_in | Block_ops_out |
+----------------------+----------+----------+------------+--------------+---------------+
| starting             | 0.000050 | 0.000045 |   0.000005 |            0 |             0 |
| checking permissions | 0.000008 | 0.000005 |   0.000002 |            0 |             0 |
| Opening tables       | 0.000016 | 0.000015 |   0.000001 |            0 |             0 |
| init                 | 0.000017 | 0.000016 |   0.000002 |            0 |             0 |
| System lock          | 0.000008 | 0.000007 |   0.000001 |            0 |             0 |
| optimizing           | 0.000004 | 0.000003 |   0.000001 |            0 |             0 |
| statistics           | 0.000011 | 0.000010 |   0.000001 |            0 |             0 |
| preparing            | 0.000024 | 0.000010 |   0.000014 |            0 |             0 |
| executing            | 0.000003 | 0.000002 |   0.000001 |            0 |             0 |
| Sending data         | 0.000061 | 0.000055 |   0.000006 |            0 |             0 |
| end                  | 0.000004 | 0.000002 |   0.000002 |            0 |             0 |
| query end            | 0.000006 | 0.000005 |   0.000001 |            0 |             0 |
| closing tables       | 0.000008 | 0.000007 |   0.000001 |            0 |             0 |
| freeing items        | 0.000018 | 0.000007 |   0.000011 |            0 |             0 |
| cleaning up          | 0.000011 | 0.000009 |   0.000002 |            0 |             0 |
+----------------------+----------+----------+------------+--------------+---------------+
15 rows in set, 1 warning (0.01 sec)
```

慢 SQL 的罪魁祸首：

- converting HEAP to MyISAM : 查询结果太大，内存不足，搬到磁盘上了。


- Creating tmp table : 创建临时表
  - 拷贝数据到临时表
  - 用完删除
- Copying to tmp table on disk : 把内存中的临时表复制到磁盘，危险！
- locked



###  4. 全局查询日志

> 注意 :no_entry_sign: ：不要在生产环境开启该功能。



- 开启全局日志

```mysql
mysql> set global general_log=1;
```

- 设置格式

```mysql
mysql> set global log_output='TABLE';
```

- 查看查询日志

```mysql
mysql> select * from mysql.general_log;
```

![general_log](https://github.com/JiaoXR/IT-Skill/blob/master/pics/MySQL/general_log.png)

