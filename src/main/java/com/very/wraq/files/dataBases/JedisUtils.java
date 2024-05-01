package com.very.wraq.files.dataBases;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
