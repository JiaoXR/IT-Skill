#  Maven 小结

###  1. 简介

Maven 是基于项目对象模型（Project Object Model, POM），可以通过一小段描述信息来管理项目的构建、报告和文档的软件项目管理工具。

安装目录简介：

1. bin 目录是包含 mvn 的运行脚本
2. boot 目录包含一个类加载器的框架，Maven 使用它加载自己的类库
3. conf 配置文件
4. lib 包含 Maven 运行时的依赖类库



###  2. 常用命令

```bash
mvn -v             查看maven版本
mvn compile        编译主程序
mvn test           测试
mvn package        打包

mvn clean          删除 target (保存字节码文件和测试报告)
mvn install        安装 jar 包到本地仓库
```



```bash
mvn -Dtest package
# 打包但不测试。完整命令为：
mvn -D maven.test.skip=true package

# 其他
mvn clean package -Dmaven.test.skip -U
```



###  3. POM 文件简介

####  3.1  构件坐标

```bash
1. groupId : 公司名字 + 项目名
2. artifactId : 项目名 + 模块名
3. varsion : 版本号
```

####  3.2  版本号

```
        第一个 0 表示大版本号
        第二个 0 表示分支版本号
        第三个 1 表示小版本号
        0.0.1
        
        snapshot    快照
        alpha       内部测试
        beta        公测
        Release     稳定
        GA          正式发布
```



```xml
<!--指定编码格式-->
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```



```xml
<!-- 项目名 -->
<name></name>

<!-- 项目地址 -->
<url></url>

<!-- 项目描述 -->
<description></description>

<!-- 开发人员信息 -->
<developers></developers>

<!-- 许可证信息 -->
<licenses></licenses>

<!-- 组织信息 -->
<organization></organization>
```



```xml
<!--依赖的管理，作用主要定义在父模块中，对子模块进行管理-->
<dependencyManagement>
	<dependencies>
	</dependencies>
</dependencyManagement>

<!--插件列表-->
<plugins>
```



###  4. 其他

####  4.1  Maven 生命周期

完整的项目构建过程包括：

清理、编译、测试、打包、集成测试、验证、部署

####  4.2  Maven 依赖范围

1. compile : 默认范围，编译测试运行都有效。
2. provided : 在编译和测试时有效。
3. runtime : 在测试和运行时有效。(典型例子：JDBC驱动的实现。)
4. test : 只在测试是有效。
5. system : 类似provided，与本机系统相关联，可移植性差。
6. import : 导入范围，他只是用在 dependencyManagement 中，表示从其他pom中导入dependecy配置。

####  4.3  依赖冲突

1. 短路优先
2. 先声明的优先

####  4.4  聚合&继承

`modules` 标签，例如：

```xml
<packaging>pom</packaging>
<modules>
    <module>../HoictasStudio-MavenDemo01</module>
    <module>../HoictasStudio-MavenDemo02</module>
    <module>../HoictasStudio-MavenDemo03</module>
</modules>
```