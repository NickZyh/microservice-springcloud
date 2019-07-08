package com.springcloud.microservicebaseframework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Zyh 2019-7-7 12:08
 * @Description redis连接配置类
 * @Note TODO
 */
@Configuration
/**
 * @PropertySource这个注解表示引入外部的配置文件,使用后该配置文件就能够和application.yml一样进行操作
 */
@PropertySource("classpath:redis.properties")
/**
 * 日志记录类
 */
@Slf4j
public class RedisConfig {
 
    @Value("${spring.redis.host}")
    private String host;
 
    @Value("${spring.redis.port}")
    private int port;
 
    @Value("${spring.redis.timeout}")
    private int timeout;
 
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
 
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;
 
    @Value("${spring.redis.password}")
    private String password;
 
    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;
 
    @Bean
    public JedisPool redisPoolFactory() {
        log.info("redis connect start.redis-address：" + host + ":" + port);

        //连接池配置类
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 阻塞最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时,默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        jedisPoolConfig.setTestOnBorrow();

        // 构建连接池
        JedisPool jedisPool;
        try {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        } catch (Exception e) {
            log.error("redis connect failed:" + e.getMessage());
            throw e;
        }
        return jedisPool;
    }
 
}