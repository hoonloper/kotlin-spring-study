package study.redis.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@TestConfiguration
class RedisTestConfig {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    @Bean
    fun redisson(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = "redis://127.0.0.1:6379" // Redis 서버 주소
        return Redisson.create(config)
    }

    @Bean
    fun redissonConnectionFactory(redisson: RedissonClient): RedisConnectionFactory {
        return RedissonConnectionFactory(redisson)
    }
}
