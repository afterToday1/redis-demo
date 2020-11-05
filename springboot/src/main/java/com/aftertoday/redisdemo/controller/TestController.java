package com.aftertoday.redisdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/gettest")
    public void test1(){
        redisTemplate.opsForValue().set("mykey","value1");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }
}
