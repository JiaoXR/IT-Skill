#  Docker 基础小结

三个核心概念：镜像，容器，仓库。

可分别类比为：软件安装包，应用软件，应用市场。（PS : 个人理解）

###  查看

- 查看 docker 版本

`docker --version`

- 查看当前（本地）镜像

`docker images [OPTIONS] [REPOSITORY[:TAG]]`

常用：`docker images`

- 查看当前运行的容器

`docker ps`

查看当前所有运行的容器：`docker ps -a`



###  拉取镜像

> PS: 默认从官方镜像中心。可以在 `daemon.json` 中配置镜像。

`docker pull [OPTIONS] NAME[:TAG]`

拉取镜像流程：

1. 先查询本地是否有符合的镜像；
2. 若有，无操作；
3. 若无，则从远端仓库拉取。



###  运行镜像

- 运行镜像

`docker run [OPTIONS] IMAGE ID[:TAG][COMMAND][ARG…]`



`docker run IMAGE ID` : （前台）运行某个镜像

`docker run -d IMAGE ID` : 后台运行某个镜像

`sudo docker run -dit IMAGE ID` : 后台运行

`docker restart IMAGE ID` : 重启镜像

> PS: 若普通的 `docker run` 命令无法启动镜像，可以尝试 `sudo docker run`;
>
> 可使用 `docker run —help` 查看 run 命令的帮助文档。



运行镜像流程：

1. 先查询本地是否有符合的镜像；
2. 若有，直接运行；
3. 若无，从远端仓库拉取到本地，再运行。

###  进入容器

示例：

`docker exec -it CONTAINER ID bash` : 常用进入容器的参数

`exit` : 退出容器

可使用 `docker exec —help` 查看帮助文档



###  Docker 网络

网络类型：

- Bridge(默认)：独立网络环境；
- Host：使用宿主的网络环境（不会虚拟出单独的网卡和IP）；
- None：无网络。



###  制作自己的镜像

Dockerfile



build 命令：

`docker build .` : 当前目录下编译（注意末尾的点号）

`docker build -t tag` : 给镜像指定 tag

【待补充】

####  推送到远程仓库

两个步骤：

1. 给要传的镜像打 tag，命令：➜  ~ docker tag jpress:tomcat jiaoxr/jpress:tomcat
2. push，命令(以本人为例)：➜  ~ docker push jiaoxr/jpress:tomcat


![rmi](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Docker/push.png)


> 参考：https://stackoverflow.com/questions/41984399/denied-requested-access-to-the-resource-is-denied-docker/41984666#41984666



###  停止&删除

两个与删除相关的命令：

- 删除容器

`rm : remove one or more containers`

- 删除镜像

`rim : remove one or more images`



注意：若要删除镜像，需要先删除容器；若要删除容器，需要先把想要删除的容器停掉。示例：

先用 `docker images` 命令查看所有镜像（红线内为要删除的镜像，镜像 ID 为 208a1ab4af4b）：

![images](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Docker/images.png)

`docker ps -a` 查看所有运行的容器（红线内为对应的容器，容器 ID 为 c36b1eabe494）：

![ps](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Docker/ps.png)

`docker stop c36b1eabe494` 停止容器，`docker rm c36b1eabe494` 删除容器：

![stop](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Docker/stop.png)

停止所有的容器：`docker stop $(docker ps -a -q)`

删除所有的容器：`docker rm $(docker ps -a -q)`

`docker rmi 208a1ab4af4b` 删除镜像：

![rmi](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Docker/rmi.png)

此时再查看镜像，可以看到 ID 为 208a1ab4af4b 的镜像已被删除：

> 参考：<https://www.cnblogs.com/q4486233/p/6482711.html>



一些镜像中心链接：

docker 官方仓库：<https://hub.docker.com/>

网易镜像中心：https://c.163.com/hub#/m/home/

阿里云镜像中心：https://dev.aliyun.com/search.html

