package com.study.java.config;

import com.study.java.dto.User;
import com.study.java.service.BookingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    BookingService bookingService(){
        return new BookingService();
    }

    // i need to craete connection factoruy

    private RedisConnectionFactory getConnectionfactory(){

        RedisStandaloneConfiguration configuration= new RedisStandaloneConfiguration("localhost",6379);
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build();

        JedisConnectionFactory factory = new JedisConnectionFactory(configuration,jedisClientConfiguration);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedisTemplate<String, List<User>> redisTemplate(){
        RedisTemplate<String, List<User>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionfactory());
        setSerializer(redisTemplate);
        return redisTemplate;
    }

    private <K,V> void setSerializer(RedisTemplate<K,V> redisTemplate) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
    }
}
