#  Redis 小结



> REmote DIctionary Server(Redis) 是一个由 Salvatore Sanfilippo 写的 key-value 存储系统。
>
> Redis 是一个开源的使用 ANSI C 语言编写、遵守 BSD 协议、支持网络、可基于内存亦可持久化的日志型、Key-Value 数据库，并提供多种语言的 API。
>
> 它通常被称为数据结构服务器，因为值（value）可以是字符串（String），哈希（Map），列表（list），集合（set）和有序集合（sorted set）等类型。



##  1. 安装启动（Mac 环境）

- Homebrew 安装 Redis

```bash
$ brew install redis

==> Downloading https://homebrew.bintray.com/bottles/redis-4.0.8.sierra.bottle.tar.gz
######################################################################## 100.0%
==> Pouring redis-4.0.8.sierra.bottle.tar.gz
==> Caveats
To have launchd start redis now and restart at login:
  brew services start redis
Or, if you don't want/need a background service you can just run:
  redis-server /usr/local/etc/redis.conf
==> Summary
🍺  /usr/local/Cellar/redis/4.0.8: 13 files, 2.8MB
```

- 使用配置文件启动 redis-server

```bash
$ redis-server /usr/local/etc/redis.conf
```

如图：

![server](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Redis/server.png)

> 默认端口号为 6379

- 启动 redis-cli

```bash
$ redis-cli
```

- 测试是否启动成功

```bash
# 成功会返回 PONG
$ redis-cli ping
PONG
```

或者，使用简单命令验证如下：

![cli](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Redis/cli.png)



- 连接远端

```bash
$ redis-cli -h host -p port -a password

# 例如
$ redis-cli -h 127.0.0.1 -p 6379 -a "mypassword"
```

- 退出

```bash
# 断开连接
127.0.0.1:6379> SHUTDOWN

# 退出
not connected> exit
```



##  2. Redis 配置文件

- 查看配置

```bash
# 查看所有配置
127.0.0.1:6379> CONFIG GET *

# 查看 loglevel
127.0.0.1:6379> CONFIG GET loglevel
```

- 默认配置文件目录

```bash
/usr/local/etc/redis.conf
```



##  3. 常用操作

- 清空数据

```bash
# 清空所 DB
127.0.0.1:6379> FLUSHALL

# 清空当前 DB
127.0.0.1:6379> FLUSHDB
```

- 过期时间

```bash
# 例如，给 k1 设置过期时间为 20秒
127.0.0.1:6379> EXPIRE k1 20

# 查看过期时间
127.0.0.1:6379> ttl k1

# 返回值
# n : 剩余时间
# -1 : 永不过期
# -2 : 已过期
```

- 查看数据类型

```bash
# TYPE key : 查看 key 的数据类型
127.0.0.1:6379> TYPE k2
string

# 存入 list 类型数据
127.0.0.1:6379> LPUSH mylist 1 2 3 4 5
(integer) 5
127.0.0.1:6379> LRANGE mylist 0 -1
1) "5"
2) "4"
3) "3"
4) "2"
5) "1"
127.0.0.1:6379> TYPE mylist
list
```



##  4. 数据类型

###  4.1 string 常用操作

- set/get/del/append/strlen
- incr/decr/incrby/decrby, 注意数字才能加减
- getrange/setrange
- setex(set with expire), /setnx(set if not exist)
- mset/mget/msetnx
- getset(先 get 再 set)



###  4.2 list 常用操作

- LPUSH/RPUSH : 插入元素（L : Left, R : Right）


- LRANGE : 查看元素，`LRANG 0 -1 ` 查看全部。


- LPOP/RPOP : 删除元素


- LINDEX : 查看某个索引位置的元素
- LLEN : 查看某个列表的长度



###  4.3 set 常用操作

- SADD m1 m2 ... : 添加元素
- SMEMBERS set01 : 查看所有元素
- SISMEMBER set01 m1 : m1 是否在集合 set01 中
- SCARD set01 : 获取集合中元素个数
- SREM set01 m1 : 删除 set01 中的 m1 元素



###  4.4 hash 常用操作

> 注意存储格式为键值对。

- HSET/HGET/HGETALL : 添加/获取一个 hash/获取所有
- HMSET/HMSET : 添加/获取多个 hash


- HKEYS/HVALS : 获取所有的键/值
- HINCRBY/HINCRBYFLOAT
- HSETNX

示例代码：

```bash
# 添加一个键值对
127.0.0.1:6379> HSET user id 12

# 添加/获取多个 hash
127.0.0.1:6379> HMSET user id 1 name Jack age 24
127.0.0.1:6379> HMGET user id name age

# 获取全部(所有的键值对)
127.0.0.1:6379> HGETALL user

# 获取所有的键或值
127.0.0.1:6379> HKEYS user
127.0.0.1:6379> HVALS user
```



###  4.5 ZSet 常用操作

zset（sorted set, 有序集合）

- ZADD/ZRANGE

```bash
127.0.0.1:6379> ZADD zset01 60 va 70 v2 80 v3

127.0.0.1:6379> ZRANGE zset01 0 -1
1) "va"
2) "v2"
3) "v3"

127.0.0.1:6379> ZRANGE zset01 0 -1 withscores
1) "va"
2) "60"
3) "v2"
4) "70"
5) "v3"
6) "80"
```



##  5. 持久化

###  5.1 RDB

RDB : Redis DataBase

- 获取备份文件目录

```bash
127.0.0.1:6379> CONFIG GET dir
1) "dir"
2) "/usr/local/var/db/redis"
```

Mac 默认目录：`/usr/local/var/db/redis/dump.rdb`

> 注意：执行 FLUSHALL 命令会产出备份，但数据为空！



###  5.2 AOF

AOF : Append Only File

> 注意：
>
> 若使用 Redis 持久化，推荐同时使用 RDB 和 AOF。
>
> 若二者同时使用，当 Redis 重启时，会优先载入 AOF 文件来恢复原始的数据。



> 性能建议：
>
> RDB 文件只用作后备用途，建议只在 Slave 上持久化 RDB 文件，且 15 分钟备份一次就够了，即只保留 save 900 1 这条规则。



##  6. 事务

命令：

```bash
# 开启事务(返回 OK，接下来再执行的操作会添加到队列)
127.0.0.1:6379> MULTI

# 执行队列中的操作
127.0.0.1:6379> EXEC

# 取消事务
127.0.0.1:6379> DISCARD

# 监视某个 key & 取消监视所有 key
127.0.0.1:6379> WATCH
127.0.0.1:6379> UNWATCH
```

- 正常执行
- 放弃事务
- 全体连坐

对于多个操作，一旦有一个报错，其他全部不执行。

> 注意：这里的报错指的是添加到队列时报错，并未添加到队列中。

例如：

```bash
(error) ERR wrong number of arguments for 'getset' command
```

- 冤头债主

对于多个操作，若有执行报错，其他操作正常执行。

> 注意：这里的错误指的是逻辑错误，该操作会添加到队列中。执行时报错。

- watch 监控



###  悲观锁&乐观锁

- 悲观锁

悲观锁（Pessimistic Lock），每次拿数据的时候都会上锁，这样别人拿数据的时候就会 block，直到它拿到锁。

- 乐观锁

乐观锁（Optimistic Lock），每次拿数据的时候不会上锁，但在更新数据时会判断在此期间是否有人更新数据，可以使用版本号等机制。适用于多读的应用类型。

乐观锁策略：提交版本必须大于记录当前版本才能执行更新。



##  7. 主从复制

主从（Master/Slaver）复制，读写分离。只有主机可以写，从机只能读。

查看主从信息：

```bash
127.0.0.1:6379> INFO replication
# Replication
role:master
connected_slaves:0
master_replid:1a2f337aab99e57d4520a51240b4487fefe89b36
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

####  配置主从关系

```bash
# 主库改为从库（会备份主库所有数据）
127.0.0.1:6379> SLAVEOF 127.0.0.1 6379

# 从库改为主库
127.0.0.1:6379> SLAVEOF no one
```



> 注意：
>
> 主库断开后再启动，从库会自动备份；从库断开后再启动，角色切换为主库。
>
> 从库每次与 master 断开后，都需要重新连接，除非在 redis.conf 中配置。

####  复制原理

首次：全量复制；

之后：增量复制。



###  哨兵（sentinel）模式

反客为主的自动版。能够后台监控主库是否故障，如果主库故障，则根据投票数自动将从库转为主库。

缺点：复制延迟。





> 参考：
>
> http://www.runoob.com/redis/redis-tutorial.html
