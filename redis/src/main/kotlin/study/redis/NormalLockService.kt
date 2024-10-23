package study.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class NormalLockService(
    private val redisTemplate: RedisTemplate<String, Any>,
) : LockService {
    companion object {
        private const val DEFAULT_KEY = "normal-lock"
        private const val DEFAULT_VALUE = "locking"
        private val DEFAULT_TTL = Duration.ofSeconds(3)
    }

    override fun lock(
        key: String?,
        ttl: Duration?,
    ): Boolean {
        return redisTemplate.opsForValue()
            .setIfAbsent(key ?: DEFAULT_KEY, DEFAULT_VALUE, ttl ?: DEFAULT_TTL)
            ?: false
    }

    override fun unlock(key: String?): Boolean {
        return redisTemplate.delete(key ?: DEFAULT_KEY)
    }
}
