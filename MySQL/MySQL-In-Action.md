#  MySQL 实战技巧

> 最近 MySQL get 到一些新姿势，记录一下，不定期更新中……



###  1. ON DUPLICATE KEY UPDATE

- 作用：若 KEY 不重复，则插入记录；否则更新记录。

- 单条操作，示例代码：

```mysql
INSERT INTO database.table(a, b, c)
VALUES (1,2,3)
ON DUPLICATE KEY 
UPDATE c=c+1;
```

- 批量操作

```mysql
INSERT INTO database.table(id, key, field)
VALUES (v1, v2, v3), (v1, v2, v3), (v1, v2, v3)
ON DUPLICATE KEY UPDATE
id=VALUES(id), key=VALUES(key), field=VALUES(field);
```

> https://www.cnblogs.com/jaxer/p/9676031.html

###  2. 自增主键

- 创建表时修改自增主键，添加 `AUTO_INCREMENT=<Number>`，示例代码：

```mysql
CREATE TABLE database.table(
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=<Number> DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

- 建表后修改自增主键

```mysql
ALTER TABLE <table_name> AUTO_INCREMENT = 2;
```

###  3. 清空表数据

```mysql
TRUNCATE TABLE <table_name>;
```

> https://www.cnblogs.com/jaxer/p/9673523.html





> 更多：https://www.cnblogs.com/jaxer/category/1075873.html