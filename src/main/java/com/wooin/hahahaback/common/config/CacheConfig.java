package com.wooin.hahahaback.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EnableCaching
@Configuration
public class CacheConfig {


    /**
     * Spring Boot 가 기본적으로 RedisCacheManager 를 자동 설정해줘서 RedisCacheConfiguration 없어도 사용 가능
     * Bean 을 새로 선언하면 직접 설정한 RedisCacheConfiguration 이 적용됨
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {

        //직렬화시 클래스 타입을 명시하기 위한 설정
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType(Object.class)
                .build();

        //직렬화, 역직렬화시 LocalDateTime 타입을 위한 fommater를 담은 Module
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSSSSS");
        SimpleModule customDateTimeModule = new SimpleModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL)
                .registerModules(customDateTimeModule);


        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                );
    }

    /**
     * 여러 Redis Cache 에 관한 설정을 하고 싶다면 RedisCacheManagerBuilderCustomizer 를 사용할 수 있음

//      예시
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("cache1",
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .computePrefixWith(cacheName -> "prefix::" + cacheName + "::")
//                                .entryTtl(Duration.ofSeconds(600))
//                                .disableCachingNullValues()
//                                .serializeKeysWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                                )
//                                .serializeValuesWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                                ))
//                .withCacheConfiguration("cache2",
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .entryTtl(Duration.ofHours(2))
//                                .disableCachingNullValues());
//    }
     */
}
