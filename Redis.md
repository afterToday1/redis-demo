Redis

## tips

### 关于学习

​		你见过编程世界的天花板吗？我们现在学习都很急躁，一上来就学大框架，Mybatis、SpringCloud，充其量，学完这些，懂一点东西，增删改查的业务，就是API调用工程师我现在做的事情。有一说一，这些微服务架构的确要掌握，但对个人的发展不能局限于此，这些只是说开发的工具，你只会这些不会根据业务场景改动，没有用的，Mybatis都能自己写SQL语句了，要你这个API调用工程师干嘛？

​		JavaSE、JavaWeb  必须吃透

​		先聊聊学习方式。一般来讲，又两种：

- 上手就用（是什么，怎么用！）  这样的东西，学来根本不值钱

- 基本的理论先学习，然后将知识融汇贯通

  要明白一件事情，学习不是为了面试和工作，仅仅是为了兴趣，兴趣才是最好的老师！

### 关于你的竞争对手

​		大厂不缺人，缺人才，但人才都有自己的想法，不想进大公司。

​		所以说大多数的竞争对手，并不是人才，都是那些图安稳又踏实，成绩中等的老实人，新出的东西一定要研究狠的。

### 关于开发应用技术选型

​		开始开发一个系统时，别一上来就用分布式，一定是从单体开始，需求量大的时候，才做分布式。当然，前提是，你做的系统是易于扩充的。

### 可视化工具客服端

- Redis DeskTop Manager
  - 主流工具
  - https://www.redis.com.cn/clients/
- Another Redis DeskTop Manager
  - 国人开发，界面友好
  - 目前版本还没有对key进行冒号分组
  - https://github.com/qishibo/AnotherRedisDesktopManager

## 1、NoSql概述

### 1.1、数据库发展需求的演变

​		我们现在处于什么时代？2020年，大数据时代。

​		在大数据的场景下，一般的数据库无法进行数据处理了！2006年Hadoop就已经发布了！SSM框架还有用吗...

​		先看看数据库发展历史

> **1、单机MySQL的年代！读写操作都在单机**

​		90年代，一个基本的网站访问量一般不会太大，单个数据库完全足够。

​		那个时候，更多的开发其实就是写静态HTML网页~服务器根本没有太大的压力！

​		思考一下，这种情况下：整个网站的瓶颈是什么？

​		1、数据量如果太大，一个机器就放不下了。

​		2、数据的索引，数据超过300万条，一定要建立索引（B+ tree），但索引建立多了一个机器内存也放不下。

​		3、访问量（读写混合），一个服务器承受不了

​		只要你开始出现以上的三种情况之一，那么你必须要晋级。

> **2、Memcahed（缓存）+ MySQL + 垂直拆分（读写分离、主从复制）**
>
> ![img](https://yun1.gree.com/microblog/filesvr/5f9913f784ae00b5d314fd45/%E4%BA%91%E4%B9%8B%E5%AE%B6%E5%9B%BE%E7%89%8720201028144547.png)

​		网站80%的情况都是在读，每次都要去查询数据库的话就十分的麻烦！所以说我们希望减轻数据的压力，我们可以使用缓存来保证效率！

​		发展过程：优化数据结构和索引 --> 文件缓存(IO)-->Memcahed（高速缓存插件）

> **3、分库分表+水平拆分+MySQL集群**
>
> ![img](https://yun1.gree.com/microblog/filesvr/5f9914b084ae00b5d314fd87/%E4%BA%91%E4%B9%8B%E5%AE%B6%E5%9B%BE%E7%89%8720201028145422.png)

​		技术和业务在发展的同时，对人的要求越来越高！

> 数据库的本质，其实就是==读写操作==

**开发者寻求突破的一些方式**：

- 换数据库引擎

  MySQL早些年使用MylSAM引擎

  - MylSAM：表锁，十分影响效率！高并发下就会出现严重的锁问题

  - 现在使用Innodb引擎：行锁

- MySQL在那个年代推出了表分区，使用分库分表解决写的压力！

- 推出MySQL集群，很好满足了那些年的一些需求

>**4、如今的时代**

​		2010-2020十年间，从诺基亚HTC按键手机到Android10，世界的数据爆炸式膨胀。

​		MySQL等关系型数据库就不够用了！==数据量很多，而且变化很快==

​		MySQL有点使用它来存储一些比较大的文件，如博客上的图片，这使得数据库恨到，效率就低了，如果有一种数据库来专门处理这种数据，MySQL压力就变得小了。

​		大数据的IO压力下，表结构几乎没法更大。如，在原有1亿条数据的表中，动态加一列字段。

> 目前一个基本的互联网项目，要保证==金丝雀发布==（灰度发布），平稳的项目迭代。需要的服务器及数据库架构如下。有一说一，光是买这些服务器，成本负担无穷大。有白嫖的用户在，B站根本不赚钱
>
> ![img](https://yun1.gree.com/microblog/filesvr/5f991b8084ae4e56b0479d4e?big)

### 1.2、为什么要使用NoSQL

​		当应用服务器接入第三方平台时，用户会产生一些数据，如用户的个人信息、社交网络、地理位置、用户的日志爆发式增长！这时候关系型数据库的负担达到瓶颈了，实际上应用服务器也不需要持久化存储这些数据。这时候我们就需要使用NoSQL数据库了。NoSQL可以很好处理这些情况

### 1.3、什么是NoSQL

> NoSQL = Not Only SQL（不仅仅是SQL） 泛指非关系型数据库
>
> 关系型数据库：表格， 行 ，列

​		随着web2.0互联网的诞生，传统的关系型数据库很难对付web2.0时代，尤其是超大规模的包并发社区，会暴露出很多难以克服的问题。

> 站长是一个真真正正的职业

​		NoSQL在如今大数据环境下发展得十分迅速，Redis是发展最快的，而且是我们当下必须要掌握的技术。

​		很多数据类型如用户的个人信息，社交网络，地理位置。这些数据类型的存储不需要一个固定的格！不需要多余的操作就可以横向扩展的！`Map<String,Object>`键值对可以存万事万物！

#### 1.3.1 NoSQL特点

- 方便扩展，易于解耦（数据之间没有关系，很好扩展！）

- 大数据量高性能（Redis一秒写8万次，读取11万，NoSQL的缓存记录级，是一种细粒度的缓存，性能高）

- 数据类型是多样型的！（不需要自己设计数据库！随取随用！如果是数据量十分大的表，需要很好的架构天赋）

- 传统的RDBMS和NoSQL

  传统的RDBMS

  - 结构化组织
  - SQL
  - 数据和关系都存在单独的表中 row col
  - 操作操作，数据定义语句
  - 严格的一致性
  - 基础的事务
  - ...

  NoSQL

  - 不仅仅是数据
  - 没有固定的查询语言
  - 键值对存储，列存储，文档存储，图形数据库（社交关系）
  - 最终一致性
  - CAP定理和BASS理论 （异地多活，不down机）
  - 高性能、高可用、高可扩展性
  - ...

  > 大数据时代了解3V，互联网程序需求了解3高
  >
  > 海量Volume，多样Variety，实时Velocity
  >
  > 高并发、高可扩（随时水平拆分，机器不够了，可以随时拓展服务器）、高性能（保证用户体验和性能）

  真正在公司中的实践：NoSQL + RDBMS 一起使用

### 1.4、阿里巴巴架构演进分析

​		一个页面,包含那么多数据，图片，视频，广告，难道都是放在一个数据库里面的吗？

  ![img](https://yun1.gree.com/microblog/filesvr/5f993a4e84ae05be3382fcfe/%E4%BA%91%E4%B9%8B%E5%AE%B6%E5%9B%BE%E7%89%8720201028173451.png)

  架构演变

  ![img](https://yun1.gree.com/microblog/filesvr/5f993a1184ae05be3382fcec/%E4%BA%91%E4%B9%8B%E5%AE%B6%E5%9B%BE%E7%89%8720201028173352.png)

:smile:......阿里的发展史一言难尽。

推荐一篇经典文章《阿里云的这群疯子》，王坚的传奇

https://developer.aliyun.com/article/653511

> 互联网公司开发纲领：敏捷开发、极限编程

==任何一家互联网公司，都不可能只是简简单单让用户能用就好了！==

大量公司做的都是相同的业务：（竞品协议）

随着这样的竟争，业务时越来越完善，然后对于开发者的要求也是越来越高！

如果你未来想当一个架构师：没有什么师加一层解决不了的！

```
# 1、商品的基本信息
	名称、价格、商家信息；
	关系型数据库就可以解决了！MySQL/Oracle（淘宝早年就去IOE了-王坚：阿里云的这群疯子）
	淘宝内部的MySQL 不是大家用的MySQL

# 2、商品的描述、评论（文字比较多）
     文档型数据库中：MongoDB
     
# 3、图片
	分布式文件系统 FastDFS
	- 淘宝自己的  TFC
	- Google的  GFC
	- Hadoop   HDFS
	- 阿里云的   oss云存储
	
# 4、商品的关键字 （搜索）
    - 搜索引擎 solr elasticsearch
    - ISearch: 阿里云大牛-多隆(阿里第一位程序，500一个月)
    
# 5、商品热门的波段信息（秒杀、降价信息）
    - 内存数据库
    - Redis Tair、Memache...
    
# 6、商品的交易，外部的支付接口
    - 三方应用

```

要知道，一个简单的网页，背后的技术一定不是大家想象的那么简单！

大型互联网应用问题：

- 数据类型太多了！
- 数据源繁多，经常重构！
- 数据要改造，大面积改造？

### 1.5、NoSQL的四大分类

#### KV键值对

- 新浪：**Redis**
- 美团：Redis + Tair
- 阿里、百度：Redis + memecache

#### 文档型数据库（bson格式和json一样）

- **MongoDB**(一般必须要掌握)
  - MongoDB是一个基本分布式文件存储的数据库，C++编写，主要用来处理大量的文档
  - MongoDB是一个介于关系型数据库和非关系型数据中中间的产品，是非关系型数据库功能最丰富，最接近关系型数据库的
- ConthDB

#### 列存储数据库

- **HBase**
- 分布式文件系统

#### 图关系（拓扑图）数据库

<img src="https://yun1.gree.com/microblog/filesvr/5f9a2a3784ae8734fbf59abc?big" alt="img" style="zoom: 33%;" />

- 它不是存图形，放的是关系，比如：朋友圈社交网络，广告推荐！
- Neo4j，InfoGrid

![img](https://yun1.gree.com/microblog/filesvr/5f9a2aa984ae8734fbf59beb?big)

---

## 2、Redis入门

### 2.1、概述

> Redis是什么？

​		Redis（==Re==mote ==Di==ctionary ==S==erver），即远程字典服务，C语言编写、支持网络、可基于内存亦可持久化的日志型、Key、Value数据库，并提供多种语言的API，免费开源！当下最热门的NoSQL之一，也称结构化数据库。每秒写8万次，读取11万次。

> Redis能干吗？

- 内存存储、持久化，内存中是断电即失，所以说持久化很重要（rdb、aof）
- 效率高。可以用户高速缓存
- 发布订阅系统
- 地图信息分析
...

> 特性

- 多样的数据类型

- 持久化

- 集群

- 事务

  ...

> 资源

- 官网 https://redis.io/
- 中文网：http://www.redis.cn/
- ==注意==：Windows在Github上下载（停更很久了！最高版本3.2）==Redis推荐都是在Linux服务器上搭建的==

### 2.2、Windows安装

1. 下载安装包：https://github.com/MicrosoftArchive/redis/releases

2. 下载完毕解压到自己的硬盘里就行了。

3. 重点看以下几个文件

   ![img](https://yun1.gree.com/microblog/filesvr/5f9a5e0284ae8734fbf644d1?big)

4. 开启Redis，双击运行服务==redis-server.exe==即可

![img](https://yun1.gree.com/microblog/filesvr/5f9a5ed484ae8734fbf64663?big)

> 默认端口号 6379  PID 为进程ID
>
> 若出现启动闪退现象，为端口占用。一般来讲就是以前启动了Redis，在当前目录下用以下命令关掉Reids
>
> ```cmd
> ## 连接至redis客户端
> $ redis-cli.exe
> > shutdown
> > exit
> 
> ## 开启redis
> $ redis-server.exe
> ```

5. 使用Reids客户端来连接Redis,运行==redis.cli.exe==

![img](https://yun1.gree.com/microblog/filesvr/5f9a601984ae8734fbf648f0?big)

### 2.3、Linux安装

1、下载安装包 `redis-5.0.8.tar.gz`

2、解压Redis安装包

```cmd
tar -zxvf redis-5.0.8.tar.gz
```

![img](https://yun1.gree.com/microblog/filesvr/5f9a648384ae8734fbf65420?big)

解压完成

![img](https://yun1.gree.com/microblog/filesvr/5f9a64cf84ae8734fbf654df?big)

3、进入解压后的文件，可以看到Redis的配置文件

![img](https://yun1.gree.com/microblog/filesvr/5f9a653b84ae8734fbf655a3?big)

4、基本的环境安装，C/C++环境

```cmd
> yum install gcc-c++

## 自动配置Redis环境
> make

> make install
```

5、redis的默认安装路径： `user/local/bin`

![img](https://yun1.gree.com/microblog/filesvr/5f9a667d84ae8734fbf65a0e?big)

6、将redis配置文件，复制到我们当前目录下，保留默认的配置文件，以后每次启动以修改的启动

![img](https://yun1.gree.com/microblog/filesvr/5f9a67db84ae8734fbf65f6b?big)

7、默认不是后台启动的，修改配置文件！

```cmd
vim redis.conf
```

![img](https://yun1.gree.com/microblog/filesvr/5f9a686e84ae8734fbf6608f?big)

```cmd
daemonize yes # 后台运行
```

8、启动redis服务，以kconfig下的配置文件启动

```cmd
redis-server kconfig/redis.conf
```

9、连接Redis

```cmd
redis.cli -p 6379
```

![img](https://yun1.gree.com/microblog/filesvr/5f9a6bc284ae8734fbf66c9f?big)

10、查看Redis进程是否开启

```cmd
> ps -ef|grep redis
```

11、关闭Redis服务

```cmd
> shutdown
> exit
```

12、往后尝试启动Redis集群

### 2.4、性能测试

==redis-benchmark==官方自带的性能测试工具

![img](https://yun1.gree.com/microblog/filesvr/5f9a6d7d84ae8734fbf67106?big)

![img](https://yun1.gree.com/microblog/filesvr/5f9a6e4984ae8734fbf672c6?big)

每秒处理59382.42次请求

### 2.5、基础知识及基本命名

> redis不区分大小写命令

> 查看conf文件，发现==默认16个数据库==，切换命令使用`select`，数库下标以==0开始，最大15==

![img](https://yun1.gree.com/microblog/filesvr/5f9a6f1784ae8734fbf673a3?big)

> ==select 3== # 切换第三个数据库
>
> ==DBSIZE== #查看目前数据库容量
>
> ==keys *== #查看所有的key
>
> ==EXISTS== name # 查看key name 是否存在
>
> ==FLUSHDB== # 清空当前数据库
>
> ==FLUSHALL== # 清空==全部==数据库

![img](https://yun1.gree.com/microblog/filesvr/5f9a6fa084ae8734fbf67473?big)

> 为什么Redis默认端口6379？

​		开发者是某位歌星的粉丝，6379是歌星名字缩写（mysql默认端口3306也是作者女儿的名字）

>Redis是单线程的！

​		Redis是基于内存操作，CPU不是Redis性能瓶颈，Redis的瓶颈是根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了。

> Redis为什么那么快？

​		C语言写的，官方提供的数据为100,000+的QPS，完全不比同样使用key-vale的Memecache差

> Redis为什么单线程还那么快？

​		误区1：高性能的服务器一定是多线程的？

​		误区2：多线程（CPU上下文会切换！）一定比单线程效率高？

​		速度：CPU>内存>硬盘

​		核心：Redis是将所有数据全部放在内存中的，所以说使用单线程操作效率是最高的，多线程（CPU上下文会切换，耗时操作），对应内存系统来说，如果没有上下文切换，效率是最高，多次读写都在同一个CPU下

---

## 3、五大数据类型

![img](https://yun1.gree.com/microblog/filesvr/5f9a612084ae8734fbf64afc?big)

> ==EXPIRE== name ==10==

​		让name 10秒后过期 ，exprre设置过期时间

> ==ttl== name

​		查看name 还能存活多久  ttl查看key剩余时间

> ==type== name 

​		查看当前key的类型

### 3.1、String*（字符串）*

90%的java程序员使用redis只会使用一个String 类型！

> 简单的几行语句
>
> ```cmd
> set key1 helloworld # 设置值
> get key1 helloworld # 获得值
> keys *				# 获得所有的key
> EXISIS key1			# 判断key是否存在
> ttl key1            # 查询剩余时间
> ```

#### 追加字符串==append== 

​		如果key不存在，相当于set一个key

> ==APPEND== key1 "xiaoming"  

#### 获取字符串长度==strlen== 

> ==strlen== key1

#### 自增==incr== 自减==decr==

![img](https://yun1.gree.com/microblog/filesvr/5f9a824684ae05be33858533/Snipaste_2020-10-29_16-53-10.png)![img](https://yun1.gree.com/microblog/filesvr/5f9a824f84ae05be3385853f/Snipaste_2020-10-29_16-53-49.png)

​		在微信公众号中，随处可见浏览量这种数字，难道每次用户浏览都要去MySQL数据库插入一条吗？

当然是在Redis这样做操作，设置初始浏览量views为0

> ==incr== views
>
> ==decr== views
>
> get views

#### 设置步长==incrby==、==decrby==

> ==incrby== views 10  # views+10
>
> ==decrby== view 5    # views-5

#### 截取字符串==getrange== key1 ==start== ==end==

>  ~set key1 "hello world"
> -OK
> ~ ==getrange== key1 0 4
> -"hello"
>
> ~==getrange== key1 0 -1 # -1表示获取全部字符串
>
> -"hello world"

#### 替换==setrange== key1 start str

> ~==setrange== key1 1 "olle1"
>
> -(integer) 11
>
> ~get key1
>
> -"holle1world"

#### ==setex==*(set with expire)*创建+设置过期时间

> ==setex== key3 30 "hello"  # 设置key3 30秒后过期，重复执行该语句可刷新时间
>
> ttl key3   # -2表示不存在  -1表示永久存在

#### ==setnx==*(set if not exists)*  不存在时再创建

抢票系统

>127.0.0.1:6379> set key3 "123"
>OK
>127.0.0.1:6379> set key3 "1234"
>OK
>127.0.0.1:6379> get key3     # ==这里可以看到 单纯的set会覆盖以前的==
>"1234"
>127.0.0.1:6379> setnx mykey "redis"
>(integer) 1
>127.0.0.1:6379> get mykey
>"redis"
>127.0.0.1:6379> setnx mykey "redis123"  # ==返回值为0 创建失败 不会覆盖mykey== 在分布式锁中常使用
>(integer) 0

#### ==mset== k1 v1 [kn vn...] 批量创建

> 127.0.0.1:6379> ==mset== k1 "v1" k2 "v2" k3 "v3"
> OK
> 127.0.0.1:6379> keys *
> 1) "k2"
> 2) "k1"
> 3) "k3"

> ==msetnx==  k1 v1 k2 v2   # 这是一个原子性操作，若k1不存在，k2存在  k1他也不会创建成功 一起成功/失败

#### ==mget== k1 k2 k3  同时获取多个值

> ==mget==  k1 k2 k3

#### ==对象==

```cmd
# 80%的人这样设置对象,使用复杂的json字符串
###########################################
set user:1 {name:zhangsan,age:3}  # 设置一个user:1 对象 值为 json格式的字符串来保存一个对象
127.0.0.1:6379> get user:1
"{name:zhangsan,age:3}"
##########################################

# 进阶用法   使用mset 巧妙设计 user:{id}:{field}
127.0.0.1:6379> mset user:1:name zhangsan user:1:age 3
127.0.0.1:6379> mget user:1:name user:1:age
```

#### ==getset== # 先get再set

```cmd
127.0.0.1:6379> getset db redis  # 如果不存在值，则返回nil
(nil)
127.0.0.1:6379> get db
"redis"
127.0.0.1:6379> getset db mongodb	# 如果存在值，获取原来的值，并设置新的值
"redis"
127.0.0.1:6379> get db
"mongodb"
```

> String类型使用场景：value除了是我们的字符串，还可以是我们的数字
>
> - 计数器
> - 统计多单位的数量 uid:95256449:flowler:0
> - 粉丝数
> - 对象缓存存储！

### 3.2、List*(链式列表)*

​		基本的数据类型，在Redis里，可当作==队列、栈、阻塞队列==

![img](https://yun1.gree.com/microblog/filesvr/5f9b7b7084ae8734fbf76e93?big)

#### 所有的list命名，以==L==开头

#### ==LPUSH==

```cmd
127.0.0.1:6379> lpush list1 one    # 将一个值，或多个值插入列表的头部（往左边）
(integer) 1
127.0.0.1:6379> lpush list1 two
(integer) 2
127.0.0.1:6379> lpush list1 three
(integer) 3
```

#### ==LRANGE==

```cmd
127.0.0.1:6379> lrange list1 0 1  # 通过区间获取具体的值
1) "three"
2) "two"
```

```cmd
127.0.0.1:6379> lpush list1 four free
(integer) 5
127.0.0.1:6379> lrange list1 0 -1     # 获取全部值
1) "free"
2) "four"
3) "three"
4) "two"
5) "one"
```

#### ==RPUSH==  # 从右边插入list

```cmd
127.0.0.1:6379> rpush list1 right
(integer) 6
127.0.0.1:6379> lrange list1 0 -1
1) "free"
2) "four"
3) "three"
4) "two"
5) "one"
6) "right"
```

#### ==LPOP RPOP==

```CMD
127.0.0.1:6379> lpop list1  # 移除第一个值
"free"
127.0.0.1:6379> rpop list1	# 移除最后一个值
"right"
```

#### ==LINDEX==

```cmd
127.0.0.1:6379> lindex list1 0  # 通过下标获得 list 中某个值
"four"
```

#### ==LLEN==

```cmd
127.0.0.1:6379> llen list1    # 返回list长度
(integer) 4
```

#### ==lrem==

```cmd
127.0.0.1:6379> lrem list1 1 one  # 移除找到的第一个one  （remove）
(integer) 1
127.0.0.1:6379> lrange list1 0 -1
1) "four"
2) "three"
3) "two"
```

#### ==ltrim==

```cmd
127.0.0.1:6379> lpush mylist hello1
(integer) 1
127.0.0.1:6379> lpush mylist hello2
(integer) 2
127.0.0.1:6379> lpush mylist hello3
(integer) 3
127.0.0.1:6379> lpush mylist hello4
(integer) 4
127.0.0.1:6379> ltrim mylist 1 2   # 通过下标修剪指定的长度
OK
127.0.0.1:6379> lrange mylist 0 -1
1) "hello3"
2) "hello2"
```

#### ==rpoplpop==

```cmd
127.0.0.1:6379> lpush mylist hello1
(integer) 1
127.0.0.1:6379> lpush mylist hello2
(integer) 2
127.0.0.1:6379> lpush mylist hello3
(integer) 3
127.0.0.1:6379> rpoplpush mylist otherlist # 将mylist右边移除，左添加至otherlist 组合命令
"hello1"
127.0.0.1:6379> lrange mylist 0 -1
1) "hello3"
2) "hello2"
127.0.0.1:6379> lrange otherlist 0 -1
1) "hello1"
```

#### ==lset== list index value

```cmd
127.0.0.1:6379> lset mylist 0 bagan  # 将列表中指定下标的值更新掉
OK
127.0.0.1:6379> lrange mylist 0 -1
1) "bagan"
2) "hello2"

127.0.0.1:6379> lset list 1  bagan   # 没有对应的list会报错
(error) ERR no such key  
127.0.0.1:6379> lset mylist 3 bagan  # 下标也会越界
(error) ERR index out of range
```

#### ==linsert== 

```cmd
127.0.0.1:6379> linsert mylist before "hello2" "hello3"  # 往指定字段前插值
(integer) 3
127.0.0.1:6379> lrange mylist 0 -1
1) "bagan"
2) "hello3"
3) "hello2"
127.0.0.1:6379> linsert mylist after "hello2" "hello1"  # 往指定字段后插值
(integer) 4
127.0.0.1:6379> lrange mylist 0 -1
1) "bagan"
2) "hello3"
3) "hello2"
4) "hello1"
```

> 小结

- 它实际上是一个链表，before Node after , left ,right 都可以插入值
- 如果key不存在，创建新的链表
- 如果key存在，新增内容
- 如果移除了所有值，空链表，也代表不存在！
- 在两边插入或者改动值，效率最高！中间元素，相对来说效率会低一点~

> 应场景

​		消息排队、消息队列（Lpush，Rpop），栈（Lpush  Lpop）

### 3.3、Set*(无序不重复集合)*

> set里的值是不能重复的！

#### 命名开头都是==s==

#### ==sadd==

```cmd
127.0.0.1:6379> sadd myset hello  # add值
(integer) 1
127.0.0.1:6379> sadd myset hello
(integer) 0
127.0.0.1:6379> sadd myset qwert
(integer) 1
```

#### ==smembers==

```cmd
127.0.0.1:6379> smembers myset   # 查看集合
1) "hello"
2) "qwert"
```

#### ==sismember==

```cmd
127.0.0.1:6379> sismember myset hello  # 看值是否存在
(integer) 1
127.0.0.1:6379> sismember myset hell
(integer) 0
```

#### ==scard==

```cmd
127.0.0.1:6379> scard myset  # 获取值的个数
(integer) 2
```

#### ==srem==

```cmd
127.0.0.1:6379> srem myset hello # 移除set中指定元素
(integer) 1
```

#### ==srandmember==

```cmd
127.0.0.1:6379> srandmember myset  # 随机抽取一个数
"qwert"
127.0.0.1:6379> srandmember myset 2  # 随机抽取两个数
1) "asddaw"
2) "qwert"
```

#### ==spop==

```cmd
127.0.0.1:6379> spop myset      # 随机移除一个值
"qwert"
127.0.0.1:6379> smembers myset
1) "asddaw"
```

#### ==smove==

```cmd
127.0.0.1:6379> smove myset myset2 hello  # 向其他set移动值
(integer) 1
```

> 微博，B站，共同关注！（交集）
>
> 数字集合类：
>
> - 差集
> - 交集
> - 并集
>
> 目前有  key1  a b c  key2 b d e

#### ==sdiff==  差集

```cmd
127.0.0.1:6379> sdiff key1 key2
1) "c"
2) "a"
```

#### ==sinsert== 交集    

```cmd
127.0.0.1:6379> sinter key1 key2
1) "b"   # 共同好友
```

#### ==sunion== 并集

```cmd
127.0.0.1:6379> sunion key1 key2
1) "b"
2) "a"
3) "c"
4) "d"
5) "e"
```

微博，A用户将所有人关注的人放在一个set集合中！将他的粉丝也放在一个集合中！

共同关注，共同爱好，二度好友，推荐好友（六度分割理论）

### 3.4、Hash*(散列、哈希)*

​		Map集合，key-map！ 这时候这个值是一个map集合

#### 所有命名以==H==开头

#### ==hset== key field value

```cmd
127.0.0.1:6379> hset myhash field1 hello
(integer) 1
```

#### ==hget==

```cmd
127.0.0.1:6379> hget myhash field1
"hello"
```

#### ==hmset==

```cmd
127.0.0.1:6379> hmset myhash field1 hello1 field2 hello2 field3 hello3  # 同时set多个值
OK
```

#### ==hmget==

```cmd
127.0.0.1:6379> hmget myhash field1 field2
1) "hello1"
2) "hello2"
```

#### ==hgetall==

```cmd
127.0.0.1:6379> hgetall myhash  # 查询所有
1) "field1"
2) "hello1"
3) "field2"
4) "hello2"
5) "field3"
6) "hello3"
```

#### ==hdel==

```cmd
127.0.0.1:6379> hdel myhash field1 # 删除指定的字段
(integer) 1
127.0.0.1:6379> hget myhash field1
(nil)
```

#### ==hlen==

```cmd
127.0.0.1:6379> hlen myhash  # 计算 hash 的大小
(integer) 2
```

#### ==hexists==

```cmd
127.0.0.1:6379> hexists myhash field1  # 判断对应值是否存在
(integer) 0
127.0.0.1:6379> hexists myhash field2
(integer) 1
```

#### ==hkeys、hvals==

```cmd
127.0.0.1:6379> hkeys myhash   # 获取所有的字段
1) "field2"
2) "field3"
127.0.0.1:6379> hvals myhash   # 获取所有的值
1) "hello2"
2) "hello3"
```

#### ==hincrby、hdectby==

```cmd
127.0.0.1:6379> hset myhash num 5 
(integer) 1
127.0.0.1:6379> hincrby myhash num 1  # 对hash的值进行加减操作
(integer) 6
127.0.0.1:6379> hincrby myhash num -1
(integer) 5
```

#### ==hsetnx==

看前面string的

> hash的应用场景  存储变更的数据 如用户信息

```cmd
127.0.0.1:6379> hset user:1 name xiaoming
(integer) 1
127.0.0.1:6379> hget user:1 name
"xiaoming"
```

### 3.5、Zset*(有序集合)*

​		在set的基础上增加了一个值，set k1 v1   zset k1 score1 v1

#### 所有命名以==Z==开头

#### ==zadd==

```cmd
127.0.0.1:6379> zadd myset 1 one  # 增加一个值
(integer) 1
127.0.0.1:6379> zadd myset 2 two 3three  # 增加多个值  一定要加序号
(integer) 2
```

#### ==zrange==

```cmd
127.0.0.1:6379> zrange myset 0 -1
1) "one"
2) "two"
3) "three"
4) "four"
```

```cmd
127.0.0.1:6379> zadd myset 2 fffo   # 会把存在的序号值怼下去
(integer) 1
127.0.0.1:6379> zrange myset 0 -1
1) "one"
2) "fffo"
3) "two"
4) "three"
5) "four"
```

#### ==zrangebyscore==

```cmd
127.0.0.1:6379> zadd salary 2500 xiaohong     # 发工资了 小红2500块
(integer) 1
127.0.0.1:6379> zadd salary 5000 zhangsan
(integer) 1
127.0.0.1:6379> zadd salary 500 lisi
(integer) 1
127.0.0.1:6379> zrangebyscore salary -inf +inf  # 以得分（序号）排序（升序），从负无穷到正无穷
1) "lisi"
2) "xiaogong"
3) "zhangsan"
```

```cmd
127.0.0.1:6379> zrangebyscore salary -inf +inf withscores  # with 排序带上值 
1) "lisi"
2) "500"
3) "zhangsan"
4) "2000"
5) "xiaohong"
6) "2500"
```

​		min和max可以是-inf和+inf，这样一来，你就可以在不知道有序集的最低和最高score值的情况下，使用ZRANGEBYSCORE这类命令。

​		默认情况下，区间的取值使用==闭区间==(小于等于或大于等于)，你也可以通过给参数前增加(符号来使用可选的开区间(小于或大于)。 

​		举个例子： ZRANGEBYSCORE zset (1 5 返回所有符合条件1 < score <= 5的成员；

```cmd
 ZRANGEBYSCORE zset (5 (10 # 返回所有符合条件5 < score < 10 的成员
```

#### ==zrevrangebyscore== 降序排序

```cmd
127.0.0.1:6379> zrevrange salary 0 -1   # zrange 默认从小到大，rev反转
1) "zhangsan"
2) "lisi"
```

#### ==zincrby== :smiley:

​		为有序集key的成员member的score值加上增量increment。如果key中不存在member，就在key中添加一个member，score是increment（就好像它之前的score是0.0）。如果key不存在，就创建一个只含有指定member成员的有序集合。

​		当key不是有序集类型时，返回一个错误。

​		score值必须是字符串表示的整数值或双精度浮点数，并且能接受double精度的浮点数。也有可能给一个负数来减少score的值。

​		返回值:Bulk string reply: member成员的新score值，以字符串形式表示。

​		例子

```cmd
redis> ZADD myzset 1 "one"
(integer) 1
redis> ZADD myzset 2 "two"
(integer) 1
redis> ZINCRBY myzset 2 "one"
"3"
redis> ZRANGE myzset 0 -1 WITHSCORES
1) "two"
2) "2"
3) "one"
4) "3"
redis>
```

#### ==zscore== key member

获取成员在排序设置相关比分

```cmd
redis>zadd myzset 1 one
(integer) 1
redis>zsore myzset one
"1"
```

#### ==zrem==

```cmd
127.0.0.1:6379> zrem salary xiaohong  # 移除
(integer) 1
127.0.0.1:6379> zrange salary 0 -1
1) "lisi"
2) "zhangsan"
```

#### ==zcard==

```cmd
127.0.0.1:6379> zcard salary  # 查询个数
(integer) 2
```

#### ==zcount==

```cmd
127.0.0.1:6379> zadd myset 1 world
(integer) 1
127.0.0.1:6379> zadd myset 2 world2 3 world3
(integer) 2
127.0.0.1:6379> zcount myset 1 3    # 统计 获取指定区间的存在成员数量
(integer) 3
127.0.0.1:6379> zadd myset 0 world0
(integer) 1
```

> 还要许多命令行的API  详情请看官方文档

> 案例思路：set 排序 存储班级成绩表，工资表排序，==排行榜应用==实现

---

## 4、三种特殊数据类型

### 4.1、geospatial*（地理位置）*

​		朋友的定位，附件的人，打车距离计算，城市经度纬度查询？

​		Redis的Geo在==Redis 3.2==版本推出了的！若出现no command 'geoadd'错误 请查看版本

​		这个功能可以推算地理位置的信息，两地之间的距离，方圆几里的人

​		城市经度纬度查询网址：http://www.jsons.cn/lngcode/

#### ==geoadd==

添加地理位置

- 规则：两级无法直接添加，我们一般会下载城市数据，直接通过java程序一次导入

- 参数：key  值（经度、纬度、成员）

> 小科普
>
> - 有效经度 -180度到180度
> - 有效纬度 -85.05112878度到85.05112878  北回归线 23°26′21.448′′N
> - 重要纬线：（N表示北纬）
>   - 北极圈 66°33′38″N  
>   - 北回归线 23°26′21.446″N
>   - 赤道 0°N
>   - 南回归线 23°26′21.446″S
>   - 长度不同，离赤道越远 纬线越短
> - 经线*（古称子午线）*
>   - 以伦敦格林尼治天文台为起点
> - 经纬度1度=60分=3600秒，古巴比伦人发明
> - 纬度1度≈111km 纬度1分≈1.85km 纬度1秒≈30.8km

```cmd
redis> geoadd china:city 116.40 39.30 beijing 121.47 31.23 shanghai
(integer) 2
redis> geoadd china:city 106.50 29.53 chongqing
(integer) 1
```

#### ==geopos==

获取成员坐标

```cmd
redis> geopos china:city beijing
1) 1) "116.39999896287918"
   2) "39.300001176601477"
redis> geopos china:city shanghai chongqing
1) 1) "121.47000163793564"
   2) "31.229999039757836"
2) 1) "106.49999767541885"
   2) "29.529999579006592"
```

#### ==geodist==

计算两个成员之间的距离

```cmd
redis> geodist china:city beijing shanghai
"1008342.6601"
127.0.0.1:6379> geodist china:city beijing shanghai km  # 最后一个参数为距离单位
"1008.3427"
```

![img](https://yun1.gree.com/microblog/filesvr/5f9bede784ae8734fbf86a4a?big)

#### ==georadius==

> 应用场景 显示周围的人

以指定坐标为中心，设定长度为半径

```cmd
redis> georadius china:city 110 30 500 km
1) "chongqing"
redis> georadius china:city 110 30 500 km withdist  # with带上距离
1) 1) "chongqing"
   2) "341.9374"
redis> georadius china:city 110 30 500 km withdist withcoord count 3 #withcoord带上坐标
1) 1) "chongqing"											 # count 3  限制查询3个
   2) "341.9374"
   3) 1) "106.49999767541885"
      2) "29.529999579006592"
```

#### ==georadiusbymember==

找出位于指定范围内的元素，与上面不同的是中心点由给定的位置元素决定

```cmd
redis> georadiusbymember china:city beijing 1010 km
1) "beijing"
2) "shanghai"
```

#### geohash

将二维的经纬度转化为一维的11位字符串 不常用

```cmd
redis> geohash china:city beijing chongqing
1) "wwfz8drghe0"
2) "wm5xzrybty0"
```

#### geo底层实现原理

其实就是Zset，我们可以完全使用Zset的指令来套用

```cmd
redis> zrange china:city 0 -1    # 查看地图中全部的元素
1) "chongqing"
2) "shanghai"
3) "beijing"

redis> zrem china:city beijing  # 移除地图中的元素
(integer) 1
```

### 4.2、Hyperloglog

hyper adj.亢奋的

> 什么是基数？

 A{1,3,5,7,8,9,7}

 B{1,3,5,7,8}

基数（不重复的元素个数）= 5 可以接受误差

> 简介

Redis ==2.8.9==版本更新Hyperloglog数据结构！

Redis Hyperloglog 基数统计算法~

业务场景：**网页的UV**（一个人访问一个网址多次，但是还是算作一个人！）

传统方式，set保存用户的id，然后就可以统计set中的元素数量作为标准判断！

这种方式如果保存大量的用户ID，就会比较麻烦！我们的目的不是为了保存用户ID，目的是为了计数

> Hyperloglog优点

占用内存固定，2^64不同的元素的技术，只需要12KB内存！如果要从内存角度来比较的话Hyperloglog首选

0.81%错误率！统计UV任务，可以忽略不计

#### ==pfadd==

时间复杂度O(1)，将除了第一个参数以为的参数存储到以第一个参数为变量的Hyperloglog结构中

#### ==pfcount==

时间复杂度O(1)，统计Hyperloglog结构的成员内的基数

#### ==pfmerge==

时间复杂度O(n)，将多个Hyperloglog结构合并(merge)为一个Hyperloglog（并集），基数==接近于==所有输入的Hyperloglog的可见集合的并集

```cmd
redis> pfadd mykey a b c d e f g
(integer) 1
redis> pfcount mykey
(integer) 7
redis> pfadd mykey2 i f z x v b n m
(integer) 1
redis> pfcount mykey2
(integer) 8
redis> pfmerge mykey3 mykey mykey2
OK
redis> pfcount mykey3
(integer) 13
redis>
```

> 如果允许容错，那么使用Hyperloglog首选
>
> 如果不允许容错，使用繁琐的set或者自己的数据类型

### 4.3、Bitmaps

起始版本 2.20

> 位存储

统计疫情感染人数：0 1 0 1 0 1

统计用户信息，活跃，不活跃！登录，未登录！打卡，未打开，只有两个状态的，都可以使用bitmaps

Bitmaps位图，数据结构，都是操作二进制位来进行记录，就只有0和1两个状态

365天=365bit  1字节（byte）= 8位(bit)   46字节左右

#### ==setbit==

时间复杂度O(1)，设置或者清空key的value（字符串）在offset处的bit值

```cmd
redis> SETBIT mykey 7 1 
(integer) 0 
redis> SETBIT mykey 7 0 
(integer) 1 
redis> GET mykey 
"\x00"   # 1 为 \x01
redis>
```

#### ==getbit==

查询key 的 offset处的bit值  返回integer

使用bitmap来记录 周一到周日的打卡！

```cmd
redis> setbit sign 0 1
(integer) 0
redis> setbit sign 1 0
(integer) 0
redis> setbit sign 2 0
(integer) 0
redis> setbit sign 3 0
(integer) 0
redis> setbit sign 4 0
(integer) 0
redis> setbit sign 5 0
(integer) 0
redis> setbit sign 6 1
(integer) 0
redis> getbit sign 4
(integer) 0                        # 第5天未打卡
```

#### ==bitcount==

统计操作，统计打开次数，查看是否有全勤

```cmd
redis> bitcount sign
(integer) 2
```

---

## 5、事务:star:

MySQL:ACID原则：一组命名的集合！一个事务中的所有命令都会被序列化，在事务执行过程中，会按照顺序执行

一次性、顺序性、排他性~

```cmd
------ 队列 set set set 执行 ------
```

就是要么同时成功，要么同时失败，原子性！

==Redis事务没有隔离级别的概念==

所有的命令在事务中，并没有直接被执行！只有发起执行命令的时候才会执行！Exec

==Redis单条命令保证原子性，但事务不保证原子性==*（见案例 运行时的异常）*

redis的事务：

- 开启事务（==multi==）
- 命令入队（...）
- 执行事务（==exec==）/放弃事务(==discard==)

锁：Redis还可以实现乐观锁

> 简单测试

```cmd
redis> multi
OK
redis> set k1 v1
QUEUED                # 入队
redis> set k2 v2
QUEUED
redis> get k2
QUEUED
redis> set k3 v3
QUEUED
redis> exec
1) OK
2) OK
3) "v2"
4) OK
```

> 放弃事务 discard

```cmd
redis> multi             # 开启事务
OK
redis> set k1 v1
QUEUED
redis> set k2 v2
QUEUED
redis> set k4 v4
QUEUED
redis> discard          # 取消事务
OK
redis> get k4			# 事务队列中命令都不会被执行
(nil)
```

> 编译型异常（代码有问题！命令有错！），事务中所有的命令都不会被执行~

```cmd
redis> multi
OK
redis> set k1 v1
QUEUED
redis> set k2 v2
QUEUED
redis> getset k3
(error) ERR wrong number of arguments for 'getset' command       # 编译异常
redis> set k4 v4
QUEUED
redis> exec
(error) EXECABORT Transaction discarded because of previous errors.  # 事务执行失败
redis> get k1														# 其他命令未能成功
(nil)
redis>
```

> 运行时异常（1/0），如果事务队列中存在语法性，那么执行命令的时候，其他命令时可以正常执行的，错误命令抛出异常！

```cmd
redis> set k1 "v1"
OK
redis> multi
OK
redis> set k2 v2
QUEUED
redis> set k3 v3
QUEUED
redis> incr k1        					 # k1存的是字符串 非数字类型 对它进行自增操作会报错，
QUEUED									    # 但是在redis事务判定它语 法没问题，继续执行
redis> get k3
QUEUED
redis> exec
1) OK
2) OK
3) (error) ERR value is not an integer or out of range    # 运行异常  其他命令正常执行
4) "v3"
```

---

## 6、监控与乐观锁:smile:

> 监控 Watch

==**悲观锁**==：:sob:

- 很悲观，什么时候都会出问题，无论什么时候都会加锁，性能极其低下！

==**乐观锁**==​：:smile:

- 很乐观，认为什么时候都不会出问题，所有不会上锁，更新数据的时候去判断以下，再此期间是否有人修改这个过程
- 获取version
- 更新的时候比较version（一般为+1操作）

> Redis监控测试

```cmd
redis> flushdb
OK
redis> set money 100       # 我有100块
OK
redis> set out 0		   # 消费0块
OK
redis> watch money         # 监视money对象
OK
redis> multi
OK
redis> decrby money 20     # 花费20块
QUEUED
redis> incrby out 20       # 消费额增加20块
QUEUED
redis> exec
1) (integer) 80
2) (integer) 20		       # 事务正常结束，期间没有任何变动，这个时候就正常实行
```

> 失败操作 开启两个线程

```cmd
# 称该线程为1
##################
redis> keys *
1) "money"
2) "out"
redis> set money 1000        # 线程1插队  执行时间在 线程2 watch money之后 事务执行之前
OK
redis> get money
"1000"
```

```cmd
# 该线程为2
#############
redis> watch money           # 监控
OK
redis> multi
OK
redis> decrby money 10      # 然而money这时候的值已被线程1修改
QUEUED
redis> incrby out 10
QUEUED
redis> exec                 # 执行之前，另外的线程修改了money值事务执行失败
(nil)          
```

> unwatch

![img](https://yun1.gree.com/microblog/filesvr/5f9cd53f84ae8734fbfa055f?big)

> Redis分布锁实现秒杀业务（乐观锁、悲观锁） 
>
> [https://www.cnblogs.com/jasonZh/p/9522772.htm](https://www.cnblogs.com/jasonZh/p/9522772.html)

---

## 7、Jedis

现在开始使用java来操作Redis

> 什么是Jedis 

是Redis 官方推荐的Java连接开发工具！使用java操作Redis中间件

### 7.1、依赖与测试连接

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.2.0</version>
</dependency>
```

随便建个测试类

```cmd
public class TestPing {
    public static void main(String[] args) {
        // 1、new Jedis 对象即可
        Jedis jedis = new Jedis();
        
    }
}
```

> 看一下Jedis源码

![img](https://yun1.gree.com/microblog/filesvr/5f9cd95d84ae8734fbfa0f24?big)

所以最简单的连接，连接本地(Windows环境)

```java
Jedis jedis = new Jedis("127.0.0.1",6379);
```

输出ping

```java
System.out.println(jedis.ping());  // PONG
```

### 7.2、常用API

#### 1、group1

```java
public static void main(String[] args) {
    // 1、new Jedis 对象即可
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    System.out.println(jedis.ping());
    System.out.println("清空数据：" + jedis.flushDB()); 
    System.out.println("判断某个键是否存在" + jedis.exists("username"));
    System.out.println("新增<'username','qqqq'>的键值对：" + jedis.set("username", "qqqq"));
    System.out.println("新增<'password','123'>的键值对：" + jedis.set("password", "123"));
    System.out.println("系统中所有的键如下：");
    Set<String> keys = jedis.keys("*");
    keys.forEach(key -> {
        System.out.println(key);
    });
    System.out.println("删除键password：" + jedis.del("password"));
    System.out.println("判断键password是否存在" + jedis.exists("password"));
    System.out.println("查看键username所存储的值得类型：" + jedis.type("username"));
    System.out.println("随机返回key空间的一个:" + jedis.randomKey());
    System.out.println("重命名key："+ jedis.rename("username", "name"));
    System.out.println("取出改后面的name" + jedis.get("name"));
    System.out.println("按索引查询："+ jedis.select(0));
    System.out.println("删除当前系统16个数据库所有的key!:"+ jedis.flushAll());
    System.out.println("返回当前数据库中key的数目：" + jedis.dbSize());
}
```

#### 2、Stirng

```java
public class TestString {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.flushDB();
        System.out.println("=====增加数据======");
        System.out.println(jedis.set("key1","value1"));
        System.out.println(jedis.set("key2","value2"));
        System.out.println(jedis.set("key3","value3"));

        System.out.println("删除键key2:"+jedis.del("key2"));
        System.out.println("获取键key2:"+jedis.get("key2"));
        System.out.println("修改key1:"+jedis.set("key1", "value1Changed"));
        System.out.println("获取key1的值："+jedis.get("key1"));
        System.out.println("在key3后面加入值："+jedis.append("key3", "End"));
        System.out.println("key3的值："+jedis.get("key3"));
        System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02","key03","value03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03","key04"));
        System.out.println("删除多个键值对："+jedis.del("key01","key02"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));

        jedis.flushDB();
        System.out.println("===========新增键值对防止覆盖原先值==============");
        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.setnx("key2", "value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));

        System.out.println("===========新增键值对并设置有效时间=============");
        System.out.println(jedis.setex("key3", 2, "value3"));
        System.out.println(jedis.get("key3"));
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.get("key3"));

        System.out.println("===========获取原值，更新为新值==========");
        System.out.println(jedis.getSet("key2", "key2GetSet"));
        System.out.println(jedis.get("key2"));

        System.out.println("获得key2的值的字串："+jedis.getrange("key2", 2, 4));
    }
}
```

#### 3、List

```java
public class TestList {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        System.out.println("===========添加一个list===========");
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        jedis.lpush("collections", "HashSet");
        jedis.lpush("collections", "TreeSet");
        jedis.lpush("collections", "TreeMap");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));//-1代表倒数第一个元素，-2代表倒数第二个元素,end为-1表示查询全部
        System.out.println("collections区间0-3的元素："+jedis.lrange("collections",0,3));
        System.out.println("===============================");
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈

        System.out.println("删除指定元素个数："+jedis.lrem("collections", 2, "HashMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("删除下表0-3区间之外的元素："+jedis.ltrim("collections", 0, 3));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（左端）："+jedis.lpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections添加元素，从列表右端，与lpush相对应："+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（右端）："+jedis.rpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("修改collections指定下标1的内容："+jedis.lset("collections", 1, "LinkedArrayList"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("===============================");
        System.out.println("collections的长度："+jedis.llen("collections"));
        System.out.println("获取collections下标为2的元素："+jedis.lindex("collections", 2));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println(jedis.sort("sortedList"));
        System.out.println("sortedList排序后："+jedis.lrange("sortedList", 0, -1));
    }
}
```

#### 4、Hash

```java
public class TestHash {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        Map<String,String> map = new HashMap<String,String>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        //添加名称为hash（key）的hash元素
        jedis.hmset("hash",map);
        //向名称为hash的hash中添加key为key5，value为value5元素
        jedis.hset("hash", "key5", "value5");
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));//return Map<String,String>
        System.out.println("散列hash的所有键为："+jedis.hkeys("hash"));//return Set<String>
        System.out.println("散列hash的所有值为："+jedis.hvals("hash"));//return List<String>
        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 6));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 3));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("删除一个或者多个键值对："+jedis.hdel("hash", "key2"));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("散列hash中键值对的个数："+jedis.hlen("hash"));
        System.out.println("判断hash中是否存在key2："+jedis.hexists("hash","key2"));
        System.out.println("判断hash中是否存在key3："+jedis.hexists("hash","key3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","key3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","key3","key4"));
    }
}
```

#### 5、Set

```java
public class TestSet {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        System.out.println("============向集合中添加元素（不重复）============");
        System.out.println(jedis.sadd("eleSet", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println("查询eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除一个元素e0："+jedis.srem("eleSet", "e0"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除两个元素e7和e6："+jedis.srem("eleSet", "e7","e6"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("eleSet中包含元素的个数："+jedis.scard("eleSet"));
        System.out.println("e3是否在eleSet中："+jedis.sismember("eleSet", "e3"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e1"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e5"));
        System.out.println("==================");
        System.out.println(jedis.sadd("eleSet1", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet2", "e1","e2","e4","e3","e0","e8"));
        System.out.println("将eleSet1中删除e1并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e1"));//移到集合元素
        System.out.println("将eleSet1中删除e2并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e2"));
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet3中的元素："+jedis.smembers("eleSet3"));
        System.out.println("============集合运算=================");
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet2中的元素："+jedis.smembers("eleSet2"));
        System.out.println("eleSet1和eleSet2的交集:"+jedis.sinter("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的并集:"+jedis.sunion("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的差集:"+jedis.sdiff("eleSet1","eleSet2"));//eleSet1中有，eleSet2中没有
        jedis.sinterstore("eleSet4","eleSet1","eleSet2");//求交集并将交集保存到dstkey的集合
        System.out.println("eleSet4中的元素："+jedis.smembers("eleSet4"));
    }
}
```

### 7.3、再次了解事务

```cmd
public class TestTX {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "kkkk");

        // 开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();

        try {
            multi.set("user1", result);
            multi.set("user2", result);

            multi.exec();
        }catch (Exception e){
            e.printStackTrace();
            multi.discard(); // 放弃事务 回滚
        }finally {
        	System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();   // 关闭连接
        }

    }
}
```

---

## 8、SpringBoot简单集成Redis

SpringBoot操作数据：spring-data jpa jdbc mongodb redis

SpringData也是和Spring Boot齐名的项目

本次spring boot版本 2.2.5

### 8.1、pom依赖与yml配置

```xml
<!--redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

```yml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
```

> 说明：在SpringBoot2.X之后，原来使用的jedis被替换为了lettuce

点开坐标，查看底层的东西

![img](https://yun1.gree.com/microblog/filesvr/5f9d0ac884ae8734fbfa57f8?big)

> ==发现依赖里面没有Jedis了！==

![img](https://yun1.gree.com/microblog/filesvr/5f9d0b3784ae8734fbfa5887?big)

>**==jedis==**：采用的直连，多个线程操作的话，是不安全的，如果想要避免不安全性，使用jedis pool连接池 更像BIO模式
>
>**==lettuce==**：采用netty，实例可以在多个线程中共享，不存在线程不安全的情况，可以减少线程的数量了，（不用开连接池），更像NIO模式
>
>![img](https://yun1.gree.com/microblog/filesvr/5f9d0cb684ae8734fbfa5bae?big)

### 8.2、自动装配源码

SpringBoot所有的配置类，都有一个自动配置类 RedisAutoConfiguration

自动配置类都会绑定一个 properties 配置文件  RedisProperties

> @ConditionalOnMissingBean(name = "redisTemplate")  当这个bean不存在的时候，这个类就生效
>
> 也就是我们可以自己写一个Template 去替换掉自带的bean

![img](https://yun1.gree.com/microblog/filesvr/5f9d0e6484ae8734fbfa6389?big)

```java
@Bean
@ConditionalOnMissingBean(name = "redisTemplate")// 根据自己业务场景，自定义一个template
public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    throws UnknownHostException {
    // 默认的RedisTemplate没有过多的设置，redis对象都是需要序列化的！
    // 两个泛型都是Object， Object的类型，我们后使用需要强制转换<String,Object>
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}

@Bean
@ConditionalOnMissingBean  // 由于String 是redis中最常用的类型，所以说单独提出来一个bean!
public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
    throws UnknownHostException {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}
```

### 8.3、配置yml连接测试类

```java
@SpringBootTest
class Test1 {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void contextLoad(){
        // redisTemplate
        // opsForValue  操作字符串 类似String  api和cmd指令一致
        // opsForList   操作List 类似list
        // opsForHash
        // opsForGeo
        // opsForSet

        // 除了进本的操作，我们常用的方法都可以直接通过redisTemplate操作，比如事务。
        // 获取redis的连接对象
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();

        redisTemplate.opsForValue().set("mykey","value1");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }

}
```

### 8.4、序列化配置源码

![img](https://yun1.gree.com/microblog/filesvr/5f9d142f84ae8734fbfa756c?big)

![img](https://yun1.gree.com/microblog/filesvr/5f9d143a84ae8734fbfa7584?big)

> 自定义bean

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
```

### 8.5、自定义工具类

见代码。。

---

## 9、Reids.conf详解

> 单位  配置文件对unit单位大小写不敏感

![img](https://yun1.gree.com/microblog/filesvr/5f9fa26e84ae8734fbfc36c9?big)

> 包含  可添加其他配置

![img](https://yun1.gree.com/microblog/filesvr/5f9fa2bb84ae8734fbfc37ab?big)

> 网络

```conf
bind 127.0.0.1   # 绑定的ip
protected-mode yes  # 保护模式
port 6379           # 端口配置
```

> 通用  GENERAL

```cmd
# NOT SUPPORTED ON WINDOWS daemonize no  这一条window不支持
daemonize yes                    # 默认是no  设为yes可后台运行，以守护进程运行

pidfile /var/run/redis.pid   # 如果以后台的方式运行，我们需要指定一个pid文件

# 日志
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)  生产环境
# warning (only very important / critical messages are logged)
loglevel notice
logfile ""  # 日志文件名
databases 16  # 数据库的数量
always-show-logo yes  # 是否总是显示logo
```

> 快照  SNAPSHOTTING

持久化，在规定的时间内，执行了多少次操作，则会持久化.rdb .aof 文件

redis是内存数据库，如果没有持久化，那么数据断电及失

```cmd
# 如果900s内，至少有一个1 key进行修改，我们即进行持久化操作
save 900 1
# 如果300s内，至少有十个10 key进行修改，我们即进行持久化操作
save 300 10
# 如果1000s内，至少有六十个50 key进行修改，我们即进行持久化操作
save 60 10000

stop-writes-on-bgsave-error yes  # 如果持久化出错，是否还需要继续操作

rdbcompression yes  # 是否压缩.rdb文件（持久化文件）
rdbchecksum yes  # 保存rdb文件的时候，进行错误的检查校验！
dbfilename dump.rdb  # rdb文件命名为dump.rdb
dir ./              # 保存到当前目录下
```

> REPLICATION  复制  与主从复制相关

> SECURITY  安全相关

```cmd
#requirepass 123456  # 默认是空的，不需要密码  但一般用config set requirepass来设置密码
redis>config set requirepass "123456"
OK
redis>config get requirepass
(error)NOAUTH Authentication required.
redis>ping
(error)NOAUTH Authentication required.
redis>auth 123456                         # 使用密码进行登录
OK
redis>config get requirepass
1)"requirepass"
2)"123456"
```

> LIMITS 限制

```cmd
maxmemory 10000 # 最大10000个能连接上redis的客户端
maxmemory <bytes>  # redis 配置最大的内存容量
maxmemory-policy noeviction  # 内存到达上限之后的处理淘汰策略
					# 移除一些key
					# 随机删除
					# 永不过期，返回错误
noeviction          #当内存使用达到阈值的时候，所有引起申请内存的命令会报错。
allkeys-lru         #在主键空间中，优先移除最近未使用的key。
volatile-lru        #在设置了过期时间的键空间中，优先移除最近未使用的key。
allkeys-random      #在主键空间中，随机移除某个key。
volatile-random     #在设置了过期时间的键空间中，随机移除某个key。
volatile-ttl        #在设置了过期时间的键空间中，具有更早过期时间的key优先移除。
```

> APPEND ONLY MODE  模式 aof配置

```cmd
appendonly no   # 默认是不开启aof模式的，默认是使用rdb方式持久化的，在大部分情况下，rdb完全够用
appendfilename "appendonly.aof"  # 持久化aof文件名字

# appendfsync always         # 每次修改都会sync  消耗性能
appendfsync everysec         # 每秒执行一次sync 可能会丢失这1s的数据！
# appendfsync no		     # 不执行sync 这个时候操作系统自己同步数据，速度最快！
```

具体配置详见持久化配置

---

## 10、Redis持久化 :star2:

​		Redis是内存数据库，如果不降内存中的数据库状态保存到磁盘，那么一旦服务器进程退出，服务器中的数据库状态也会消失。所以Redis提供了持久化功能！目前有两个持久化方式 ***RDB***与***AOF***，默认RDB。

> 如果你只希望你的数据在服务器运行的时候存在，也可以不做任何持久化

### 10.1、RDB*(Redis DataBase)*

> 什么是RDB

![img](https://yun1.gree.com/microblog/filesvr/5f9fb54a84ae8734fbfc68da?big)

​		RDB是Redis用来进行持久化的一种方式，在指定的时间间隔内是把当前内存中的数据集快照写入磁盘，也就是 Snapshot 快照（数据库中所有键值对数据）。恢复时是将快照文件直接读到内存里。

​		实际操作过程是fork一个子进程来进行持久化，会先将数据集写入临时文件，写入持久化过程成功后，再替换之前持久化好的文件，用二进制压缩存储。整个过程，主进程不进行任何IO操作，这就确保了极高的性能。

- 缺点：
  
  - 如果你想保证数据的高可用性，即最大限度的避免数据丢失，那么RDB将不是一个很好的选择。因为系统一旦在定时持久化之前出现宕机现象，此前没有来得及写入磁盘的数据都将丢失。
  
  - 由于RDB是通过fork子进程来协助完成数据持久化工作的，因此，如果当数据集较大时，可能会导致整个服务器停止服务几百毫秒，甚至是1秒钟。
  
- 优势：

  - 整个Redis数据库只包含一个文件 ==dump.rdb==，数据很好备份，很好压缩，灾难恢复不难
  - 性能最大化
  - 相比AOF，数据集很大的时候，启动效率更高

> 触发规则

- save的规则满足
- 执行flushall命名
- 退出redis

备份就会自带生成一个dump.rdb文件

> 如果恢复rdb文件？

- 只需把rdb文件放在redis启动目录下，redis自动检查恢复

- 查看需要存放的位置

  ```cmd
  redis>config get dir
  1)"dir"
  2)"/user/local/bin"  # 如果在这个目录下存放 dump.rdb文件，启动会自动恢复其中的数据
  ```

  

### 10.2、AOF*(Append Only File)*

将我们的所有命令（日志）都记录下来，history，恢复的时候就把这个文件全部执行一遍

> 是什么

![img](https://yun1.gree.com/microblog/filesvr/5f9fba5384ae8734fbfc73b2?big)

​		以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件内容将写指令从前到后全部执行一遍恢复工作

==AOF保存的是 appendonly.aof文件==

默认是不开启的，需要手动配置开启，重启redis生效

```cmd
appendonly yes
```

如果这个aof文件有错位，这时候redis是启动不起来的，我们需要修复这个aof文件。

```cmd
redis-check-aof --fix appendonly.aof
```

![img](https://yun1.gree.com/microblog/filesvr/5f9fc41384ae8734fbfc8f45?big)

> 如果aof文件大于64m，太大了，会fork一个新的进程来将我们的文件进行重写

- 优点：

  - 每次修改都同步，文件的完整性更好

  - 同步方式：

    ```cmd
    # appendfsync always         # 每次修改都会sync  消耗性能
    appendfsync everysec         # 每秒执行一次sync 可能会丢失这1s的数据！
    # appendfsync no		     # 不执行sync 这个时候操作系统自己同步数据，速度最快！
    ```

- 缺点：

  - 数据文件 aof远大于rdb，修复速度比rdb慢！
  - aof启动慢，运行效率也比rdb慢

---

## 11、Redis发布订阅*(pub/sub)*

Redis发布订阅（pub/sub）是一种==消息通信模式：==发送者(pub)发送消息，订阅者(sub)接受消息。微信、微博、关注系统。

Redis客户端可以订阅任意数量的频道。

订阅/发布消息图：

三个组成：

- 消息发送者
- 频道
- 消息订阅者

> 以下资料来自菜鸟教程

![img](https://yun1.gree.com/microblog/filesvr/5f9fc62084ae8734fbfc9562?big)

下图展示了频道channel1，以及订阅这个频道的三个客户端---client2、client5和client1之间的关系：

![img](https://yun1.gree.com/microblog/filesvr/5f9fc6d684ae8734fbfc97ca?big)

当有新消息通过PUBLISH命令发送给频道channel1时，这个消息就会被发送给订阅它的三个客户端：

![img](https://yun1.gree.com/microblog/filesvr/5f9fc72484ae8734fbfc98a2?big)

> 命令

这些命名被广泛用于构建即时通信应用，比如网络聊天室(chatroom)和实时广播、实时提醒等。

![img](https://yun1.gree.com/microblog/filesvr/5f9fc77784ae8734fbfc99b2?big)

> 命令

***先打开一个客户端，好比订阅者***

```cmd
redis> subscribe aftertoday                # 订阅 aftertoday的频道
Reading messages... (press Ctrl-C to quit)    # 正在等待消息发布
1) "subscribe"
2) "aftertoday"
3) (integer) 1

```

***新开一个客户端作为发布者发布消息***

```cmd
$>redis-cli -p 6379
redis> ping
PONG
redis> publish aftertoday "hello goodnight"      # 发布者向频道发送消息
(integer) 1
redis> publish aftertoday "hello 22222"
(integer) 1
```

***发送者每发送一条，订阅者马上收到一条***

```cmd
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "aftertoday"
3) (integer) 1
1) "message"
2) "aftertoday"
3) "hello goodnight"
1) "message"
2) "aftertoday"
3) "hello 22222"
```

> 原理

Redis是使用C语言实现的，分析pubsub.c文件，可了解发布和订阅底层实现。关键字：==字典、链表==

相关blog:

https://my.oschina.net/u/779531/blog/904622

## 12、Redis主从复制

### 12.1、概念

​		主从复制，是指将一台Redis服务器的数据，复制到其他Redis服务器。前者称为主节点（==master/leader==），后者称为从节点（==slave/follower==)；数据的复制时单向的，只能由主节点到从节点。Master以写为主，Slave以读为主。

​		默认情况下，每台Redis服务器都是主节点；且一个主节点可以有多个从节点（或没有从节点），但一个从节点只能有一个主节点。

​		主从复制的作用包括：

		- 故障恢复：做数据的热备，作为后备数据库，主数据库服务器故障后，可切换到从数据库继续工作，避免数据丢失。
		- 架构扩展：业务量越来越大，I/O访问频率过高，单机无法满足，此时做多库的存储，降低磁盘I/O访问的频率，提高单个机器的I/O性能。
		- 负载均衡、读写分离：80%的情况都是在进行读操作，读写分离使数据库能支撑更大的并发，不会造成前台锁，保证了前台速度。

![img](https://yun1.gree.com/microblog/filesvr/5f9fcf0084ae8734fbfcae89?big)

只使用一台Redis的情况万万不能宕机,以下情况常在电商平台出现：

- 单个Redis服务器会发生单点故障，一台服务器需要处理的请求负载压力大，哪怕redis每秒读11万次。
- 容量也许不够，==单台Redis最大使用内存不应该超过20G==

### 12.2、环境配置

==只配置从库，不用配置主库！==

```cmd
redis> info replication         # 查看当前库主从复制关系
# Replication
role:master               # 角色
connected_slaves:0        # 连接的从机  目前为0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
redis>
```

一台电脑的话，复制修改conf，用端口多开几个客户端，必须修改点如下，如设置端口6378

```cmd
pidfile /var/run/redis6378.pid            # 修改进程ID
logfile "6378.log"                        # 修改日志
port 6378                                 # 修改端口
dbfilename dump6378.rdb                   # 修改rdb持久化
```

```cmd
redis-server redis6377.windows.conf  # 以指定的conf启动
```

```cmd
$>redis-cli -p 6378
127.0.0.1:6378> ping
PONG
127.0.0.1:6378>
```

启动3个服务

### 12.3、一主二从

***slave***           n. 奴隶；从动装置   vi. 苦干；拼命工作

一主（6379）二从（6377、6378）

> slaveof 认老大

```cmd
127.0.0.1:6378> slaveof 127.0.0.1 6379
OK
```

```cmd
127.0.0.1:6378> info replication
# Replication
role:slave 
master_host:127.0.0.1
master_port:6379           # 信息查看 已认老大  主机信息
```

```cmd
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=6378,state=online,offset=253,lag=1          # 6378小第
slave1:ip=127.0.0.1,port=6377,state=online,offset=253,lag=1          # 6377小弟
master_repl_offset:267
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:266
```

> 以上的配置全是采用命令行配置，这样是暂时的。==真实的配置应该在配置文件中配置==，这样的话是永久的！

> 细节

主机可以写，从机不能写只能读！主机中的所有信息和数据，都会自动被从机保存！

若从机进行写操作：

```cmd
127.0.0.1:6377> keys *
1) "mykeyt2"
2) "mykey"
127.0.0.1:6377> get mykey
"\"value1\""
127.0.0.1:6377> set k1 v1
(error) READONLY You can't write against a read only slave.
127.0.0.1:6377>
```

> 测试：==主机断开连接，从机依旧连接到主机的，有读操作，但是没有写操作==，这个时候，主机如果回来了，从机依旧可以直接获取主机写入的信息！

> 复制原理

slave启动成功连接到master后会发送一个sync命令

Master接到命令，启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进行执行完毕之后，master将传送整个数据文件到slave，并完成一次完全同步。

==全量复制==：slave服务在接受到数据库文件数据后，将其存盘并加载到内存中。

==增量复制==：Mater继续将新的所有收集到的修改命令依次传给slave，完成同步

但是只要是重新连接到master，一次完全同步（全量复制）将自动执行。数据一定可以在从机中看到！

> 层层链路

上一个M链接下一个S！

![img](https://yun1.gree.com/microblog/filesvr/5f9fdc8884ae8734fbfccb58?big)

> 如果没有老大了，这时候S可以冲出来自己当回M，手动操作

==谋朝篡位==`slaveof no one`

```cmd
127.0.0.1:6377> slaveof no one
OK
127.0.0.1:6377> info replication
# Replication
role:master                             # 回到自己的master，其他从机就可以手动连接这个主机
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

如果这个时候原理的老大恢复了，那就重新连接

### 12.4、哨兵模式

（从库自动选举老大的模式）

> 提出

主从切换技术的方法是：当主服务器宕机后，需要手动把一台服务器切换为主服务器，这需要人工干预，费时费力。需要一个谋朝篡位自动版。

> 这一块的配置相当麻烦，且不同场景配置不一，日后缓些了解

https://www.jianshu.com/p/06ab9daf921d

---

## 13、Redis缓存穿透和雪崩

先不详细探究底层源码。

​		Redis缓存的使用，极大的提升了应用程序的性能和效率，特别师数据查询方面。但同时，它也带来了一些问题。其中，求要害的问题，就是数据的一致性问题，从严格意义上讲，这个问题无解。如果对数据的一致性要求很高，那么就不能使用缓存。

​		另外的一些典型问题就是，缓存穿透、缓存雪崩和缓存击穿。目前。业界都有比较流行的解决方案。

​		==这三个问题，即服务的高可用问题！==

### 13.1、缓存穿透（查不到）

> 概念

​		用户想要查询一个数据，发现redis内存数据库没有，也就是缓存没有命中，于是向持久层数据库查询。发现也没有，于是本次查询失败。当用户很多的时候，缓存都没有命中，预设都去请求了持久层数据库。这会给持久层数据库造成很大的压力，这时候就相当于出现了缓存穿透。

> 解决方案

#### 布隆过滤器（bloomfilter）

​		布隆过滤器师一种数据结构，对所有可能查询的参数以hash形式存储，再控制层先进行校验，不符合则丢弃，从而避免了对底层存储系统的查询压力；

![img](https://yun1.gree.com/microblog/filesvr/5fa02b4a84ae8734fbfd244d?original)

#### 缓存空对象

​		当存储层不命中后，即使返回的空对象也将其缓存起来，同时会设置一个过期时间，之后再访问这个数据将会从缓存中获取，保护了后端数据源；

![img](https://yun1.gree.com/microblog/filesvr/5fa02be884ae8734fbfd246d?original)

​		但是这种方法会存在两个问题：

1. 如果空值能够被缓存起来，这意味着缓存需要更多的空间存储更多的键，因为这当中可能会有很多的空值的键；
2. 即使对控制设置了过期时间，还是会存在缓存层和存储层的数据会有一段时间窗口的不一致，这对于需要保持一致性的业务会有影响。

### 13.2、缓存击穿（查得太多）

​		微博服务器在出现热点新闻的时候会经常宕机。

> 概述

​		这里需要注意和缓存击穿的区别，缓存击穿是指一个key非常热点没在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间（那怕0.1s），持续的大并发就穿破缓存，直接请求数据库，就像在屏幕上凿开了一个洞。

​		当某个key在过期的瞬间，有大量的请求并发访问，这类数据一般都是热点数据，由于缓存过期，会同时访问数据库来查询最新数据，并且回写缓存，会导致数据库瞬间压力过大。

> 解决方法

#### 设置热点数据永不过期

​		从缓存层面来看，没有设置过期时间，所以不会出现热点key过期后产生的问题。

#### 加互斥锁

​		分布式锁：使用分布式锁，保证对于每个key同时只有一个线程其查询后端服务，其他线程没有获得分布式锁的权限，隐藏只需要等待即可。这种方式将高并发的压力转移到了分布式锁，隐藏对分布式锁的考验很大。

![img](https://yun1.gree.com/microblog/filesvr/5fa02fcb84ae8734fbfd24e2?original)

### 13.3、缓存雪崩

> 概念

​		缓存雪崩，是指在某一个时间段，缓存集中过期失效。Redis宕机

​		==击穿是访问一个key，雪崩是redis的数据大批量消失==

​		==击穿是点，雪崩是面；击穿是露了，雪崩是碎了==

​		产生雪崩的原因之一，比如在写本文的时候，马上就要到双十一了，很快就会迎来一波抢购，这波商品时间比较集中的放入了缓存（不可能放数据库里面），假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波峰。预设所以的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会挂掉的情况。

​		![img](https://yun1.gree.com/microblog/filesvr/5fa030b484ae8734fbfd2512?original)

​		其实集中过期，倒不是非常致命，比较致命的缓存雪崩，是缓存服务器某个节点宕机或断网。因为自然形成的缓存雪崩，一定实在某个时间段集中创建缓存，这个时候，数据库也是可以顶住压力的。无非就是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力是不可预知的，很有可能瞬间就把数据库压垮。

​		双十一：停掉一些服务（服务降级），保证主要的服务可用（如无法当天退款）

> 解决方案

#### redis高可用

​		即是redis有可能挂掉，那就多设置几条redis，搭建主从复制的集群（异地多活）

#### 限流降级

​		在缓存失效后，通过加锁或者队列来控制读书库库写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待。（服务降级）

#### 数据预热

​		数据预热的含义就是在正式部属之前，我先把可能的数据预先访问一遍，这样部分可能大量访问的数据就会加载到缓存中。在即将发生大并发访问签出发加载缓存不同的key，设置不同的过期时间，让缓存失效的时间点尽量均匀。