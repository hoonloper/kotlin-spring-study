package study.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class LuaScriptLockService(
    private val redisTemplate: RedisTemplate<String, Any>,
) : LockService {
    companion object {
        private const val DEFAULT_KEY = "lua-script-lock"
        private const val DEFAULT_VALUE = "locking"
        private val DEFAULT_TTL = Duration.ofSeconds(3)

        private val ACQUIRE_LOCK_SCRIPT =
            """ 
            if redis.call("SET", KEYS[1], ARGV[1], "NX", "EX", ARGV[2]) then
                return 1
            else
                return 0
            end
            """.trimIndent()
        private val RELEASE_LOCK_SCRIPT =
            """
            if redis.call("GET", KEYS[1]) == ARGV[1] then
                return redis.call("DEL", KEYS[1])
            else
                return 0
            end
            """.trimIndent()
    }

    override fun lock(
        key: String?,
        ttl: Duration?,
    ): Boolean {
        return redisTemplate.execute(
            DefaultRedisScript(ACQUIRE_LOCK_SCRIPT, Boolean::class.java),
            listOf(key ?: DEFAULT_KEY),
            DEFAULT_VALUE,
            (ttl ?: DEFAULT_TTL).toMillis().toString(),
        )
    }

    override fun unlock(key: String?): Boolean {
        return redisTemplate.execute(
            DefaultRedisScript(RELEASE_LOCK_SCRIPT, Boolean::class.java),
            listOf(key ?: DEFAULT_KEY),
            DEFAULT_VALUE,
        )
    }
}
