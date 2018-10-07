#  Servlet

- Servlet 有权限访问所有的 Java API，包括访问企业级数据库的 JDBC API。

###  简介

- Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

- Servlet 架构

![](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Servlet/servlet-arch.jpg)

###  Servlet 任务

- 读取客户端（浏览器）发送的显式的数据
  - 包括网页上的 HTML 表单，或者来自 applet 或自定义的 HTTP 客户端程序的表单
- 读取客户端（浏览器）发送的隐式的 HTTP 请求数据
  - 包括 cookies、媒体类型和浏览器能理解的压缩格式等
- 处理数据并生成结果
  - 这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应
- 发送显式的数据（即文档）到客户端（浏览器）
  - 该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等
- 发送隐式的 HTTP 响应到客户端（浏览器）
  - 这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务

###  Servlet 生命周期

1. Servlet 通过调用 init () 方法进行初始化；

   1. init 方法被设计成只调用一次。它在第一次创建 Servlet 时被调用，在后续每次用户请求时不再调用。

   2. Servlet 创建于用户第一次调用对应于该 Servlet 的 URL 时，也可以指定 Servlet 在服务器第一次启动时被加载。

   3. 当用户调用一个 Servlet 时，就会创建一个 Servlet 实例，每一个用户请求都会产生一个新的线程，适当的时候移交给 doGet 或 doPost 方法。init() 方法简单地创建或加载一些数据，这些数据将被用于 Servlet 的整个生命周期。

   4.  

      ```java
      public void init() throws ServletException {
        // 初始化代码...
      }
      ```

2. Servlet 调用 service() 方法来处理客户端的请求；

   1. service() 方法是执行实际任务的主要方法。Servlet 容器（即 Web 服务器）调用 service() 方法来处理来自客户端（浏览器）的请求，并把格式化的响应写回给客户端。

   2. 每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。

   3.  

      ```java
      public void service(ServletRequest request, 
                          ServletResponse response) 
            throws ServletException, IOException{
      }
      ```

   4. service() 方法由容器调用，service 方法在适当的时候调用 doGet、doPost、doPut、doDelete 等方法。所以只需根据客户端的请求类型重写 doGet() 或 doPost() 即可。

      1. doGet() 方法：GET 请求来自于一个 URL 的正常请求，或者来自于一个未指定 METHOD 的 HTML 表单，它由 doGet() 方法处理。
      2. doPost() 方法：POST 请求来自于一个特别指定了 METHOD 为 POST 的 HTML 表单，它由 doPost() 方法处理。

3. Servlet 通过调用 destroy() 方法终止（结束）；

   1. destroy() 方法只会被调用一次，在 Servlet 生命周期结束时被调用；
   2. destroy() 方法可以让 Servlet 关闭数据库连接、停止后台线程、把 Cookie 列表或点击计数器写入到磁盘，并执行其他类似的清理活动；
   3. 在调用 destroy() 方法之后，servlet 对象被标记为垃圾回收。

4. 最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。

####  架构图

1. 第一个到达服务器的 HTTP 请求被委派到 Servlet 容器；
2. Servlet 容器在调用 service() 方法之前加载 Servlet；
3. 然后 Servlet 容器处理由多个线程产生的多个请求，每个线程执行一个单一的 Servlet 实例的 service() 方法。

![](https://github.com/JiaoXR/IT-Skill/blob/master/pics/Servlet/Servlet-LifeCycle.jpg)

###  Servlet 表单数据

####  GET 方法

- 默认从浏览器向 Web 服务器传递信息的方法，会产生一个很长的字符串，出现在浏览器的地址栏中；
- 若要向服务器传递密码或其他敏感信息，请勿使用 GET 方法；
- GET 方法有大小限制：请求字符串中最多只能有 1024 个字符。

####  POST 方法

- Servlet 使用 doPost() 方法处理这种类型的请求。







