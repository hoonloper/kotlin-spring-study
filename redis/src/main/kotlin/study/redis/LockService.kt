package study.redis

import java.time.Duration

interface LockService {
    fun lock(
        key: String?,
        ttl: Duration?,
    ): Boolean

    fun unlock(key: String?): Boolean
}
