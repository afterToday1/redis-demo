package com.aftertoday;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class TestPing {
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
}
