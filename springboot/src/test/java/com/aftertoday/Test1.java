package com.aftertoday;

import com.aftertoday.redisdemo.RedisDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RedisDemoApplication.class)
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
