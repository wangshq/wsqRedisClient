[TOC]
# cache-client 组件使用说明文档	-v 1.0.0

> 本组件是操作Redis缓存服务的一个java客户端组件，本身是在Redis官方为java提供的jedis客户端的基础上进行的封装。**jedis的版本为2.7.2**

## 1. 为什么要用redis做缓存？（redis的简单介绍）
Redis是一个开源的使用C语言编写的，基于内存的Key-Value类型的高性能的数据库。它的value类型相对比较丰富，包括string(字符串)、list(链表)、set(集合)、zset(sorted set --有序集合)和hash(哈希类型)。并且提供了多种语言的API(包括Java)，使用方便。在部署上，支持主从同步，在3.0的版本之后自带支持集群部署，水平扩展方便。还要很多优点就不一一列举了，想要了解的Redis的可以访问http://redis.io/ ，查看官方文档进行学习。

## 2. Redis如何使用
我们在使用Redis的时候，一般都是通过命令来操作Redis的。

比如常见的**SET**命令，SET key value，将字符串value关联到key上。
```
redis 127.0.0.1:6379> SET key1 "value1"
OK

redis 127.0.0.1:6379> GET key1
"value1"
```
**DEL**命令，DEL key，删除给定的一个或多个key
- 删除一个key：
```
redis> SET name huangz
OK

redis> DEL name
(integer) 1
```
- 删除多个key：
```
redis> SET name "redis"
OK

redis> SET type "key-value store"
OK

redis> SET website "redis.com"
OK

redis> DEL name type website
(integer) 3
```
具体的哪些命令可以通过 [Redis 命令参考](http://doc.redisfans.com/)来查看。

## 3. 组件的简单介绍
本组件其实就是对Jedis的一个封装，在原来的Jedis的基础之上，为了满足便于管理，易维护，使用便利等要求下进行的设计和开发。

### 3.1 给针对不同数据类型的操作分类
  原本的Jedis是把所有的命令都通过Jedis这一个对象来得到，这种方式使得我们在使用和开发维护上都有一定的困难，所有我们把这些命令按照Redis里数据结构来划分了出来，包括有：StringOperation、KeyOperation、ListOperation、SetOperation、SortedSetOperation、HashOperation。
### 3.2 对于key值的处理
在我们往Redis里存入一个key-value一条记录的时候，原本的情况是这个key叫什么在Redis库里就是什么，那么我们要是没有一个统一的管理和区分大家存入的key的话，我们的Redis的库里就会杂乱不一，还极有可能会出现两个不同的服务，会往Redis里放入相同名字的key值，那么后放的会把之前已经存在的key值给覆盖掉。

为了避免这些情况在每个操作里执行方法在传入key的时候，我们会统一的在你这个key的前面加上注册时候的那个key，并用"."号分隔（推荐大家在设置key的名字的时候都用“ . “号分隔）
### 3.3 注册机制
我们在使用Redis的时候，我们需要让不同的服务隔离起来，需要让不同的服务之间不能随意的修改其他服务里的key值。为了满足这种情况，我们加入了注册机制。

我们在使用上面说的这些Operation里面的方法时，必须要调用CacheManagerClient里的registerKey方法来进行注册（如果没有注册就用Operation里的方法会报错），这个方法需要传入三个参数：registerKey 注册的key值，我们在所有的方法前会对所有的key加上这个注册的key值、mode 模式，有只读和读写两种，1是只读，2是读写，在设置只读的模式下在调用Operation里的写方法时会报错、serverName 服务名 ：用于注册时向Redis里写入信息用，这个主要就是为了记录用的，一般来说你在哪个服务或者系统上用的就写这个系统和服务的简称就可以了。
### 3.4 结构目录
如下图所示：
![Alt text](./1472023361208.png)

**本组件主要由这几个部分组成**：
- **CacheManagerClient**：
	对外暴露的客户端，主要包含的功能有注册、获得Redis里的数据类型的操作类。
- **CacheClientDemo**：
	写的一个demo程序。
- **operation**：
这里主要是对Redis里的命令分个类，把关于具体哪个的类型放到了一起。比如吧关于Hash这种数据类型的命令都放在了IHashOperation这里。
- **RedisPool**：
这个类是定义了一个Redis的连接池，还有Redis的一些配置，比如ip，password,timeout等
- **JedisInterceptor**：
这个类是一个动态代理类，代理Jedis类，在使用Operation里的方法之前来判断是否应经注册过和在读模式下不允许进行写操作。
- **RedisLock**：
这个类是用Redis来提供一种分布式锁的实现方案。想要用分布式锁的话可以考虑下。
- **其他**：
剩下的就是一些contans（常量），Util（工具类）

## 4. 使用此组件的方法
1.  **配置**
关于配置，我们目前做的方法是在服务或者系统里的config.properties里进行配置。具体的配置信息如下：

```
#redis配置
#最大分配的对象数
redis.pool.maxActive=300
#最大能够保持idel状态的对象数
redis.pool.maxIdle=200
#当池内没有返回对象时，最大等待时间
redis.pool.maxWait=1000000
#当调用borrow Object方法时，是否进行有效性检查
redis.pool.testOnBorrow=true
redis.ip=172.16.33.14
redis.port=6399
redis.password=
redis.timeout=10000
```

2. **具体使用**
见CacheClientDemo这个类即可
