package study.redis

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class LockServiceTest(
    @Autowired
    private val redisTemplate: RedisTemplate<String, Any>,
) : DescribeSpec({
        lateinit var lockService: LockService

        describe("NormalLockService") {
            lockService = NormalLockService(redisTemplate)

            val defaultKey = "normal"
            var key = ""

            afterEach {
                lockService.unlock(key = key)
            }

            context("잠금되어 있지 않으면") {
                key = "$defaultKey-lock"

                it("잠금에 성공한다") {
                    val locked = lockService.lock(key = key, ttl = Duration.ofMillis(200))

                    locked.shouldBeTrue()
                }
            }

            context("잠금되어 있으면") {
                key = "$defaultKey-already-locked"

                beforeEach {
                    lockService.lock(key = key, ttl = Duration.ofMillis(200))
                }

                it("잠금에 실패한다") {
                    val locked = lockService.lock(key = key, ttl = Duration.ofMillis(200))

                    locked.shouldBeFalse()
                }
            }

            context("동시에 잠금을 획득하려 하면") {
                key = "$defaultKey-duplicate-call"

                val threadCount = 10
                val readyLatch = CountDownLatch(threadCount)
                val startLatch = CountDownLatch(1)
                val executor = Executors.newFixedThreadPool(threadCount)

                it("성공한다") {
                    val lockSuccessCounter = AtomicInteger(0)

                    repeat(threadCount) {
                        executor.submit {
                            try {
                                readyLatch.countDown()
                                readyLatch.await()
                                startLatch.await()

                                val locked =
                                    lockService.lock(key = key, ttl = Duration.ofMillis(200))
                                if (locked) {
                                    lockSuccessCounter.incrementAndGet()
                                }
                            } finally {
                                val unlocked = lockService.unlock(key = key)
                                println("Done $it - unlocked: $unlocked")
                            }
                        }
                    }

                    readyLatch.await()

                    startLatch.countDown()

                    executor.shutdown()
                    executor.awaitTermination(1, TimeUnit.SECONDS)

                    lockSuccessCounter.get().shouldBe(1)
                }
            }
        }

        describe("LuaScriptLocService") {
            lockService = LuaScriptLockService(redisTemplate)

            val defaultKey = "lua-script"
            var key = ""

            afterEach {
                lockService.unlock(key = key)
            }

            context("잠금되어 있지 않으면") {
                key = "$defaultKey-lock"

                it("잠금에 성공한다") {
                    val locked = lockService.lock(key = key, ttl = Duration.ofMillis(200))

                    locked.shouldBeTrue()
                }
            }

            context("잠금되어 있으면") {
                key = "$defaultKey-already-locked"

                beforeEach {
                    lockService.lock(key = key, ttl = Duration.ofMillis(200))
                }

                it("잠금에 실패한다") {
                    val locked = lockService.lock(key = key, ttl = Duration.ofMillis(200))

                    locked.shouldBeFalse()
                }
            }

            context("동시에 잠금을 획득하려 하면") {
                key = "$defaultKey-duplicate-call"

                val threadCount = 10
                val readyLatch = CountDownLatch(threadCount)
                val startLatch = CountDownLatch(1)
                val executor = Executors.newFixedThreadPool(threadCount)

                it("성공한다") {
                    val lockSuccessCounter = AtomicInteger(0)

                    repeat(threadCount) {
                        executor.submit {
                            try {
                                readyLatch.countDown()
                                readyLatch.await()
                                startLatch.await()

                                val locked =
                                    lockService.lock(key = key, ttl = Duration.ofMillis(200))
                                if (locked) {
                                    lockSuccessCounter.incrementAndGet()
                                }
                            } catch (e: Exception) {
                                println(e)
                            } finally {
                                val unlocked = lockService.unlock(key = key)
                                println("Done $it - unlocked: $unlocked")
                            }
                        }
                    }

                    readyLatch.await()

                    startLatch.countDown()

                    executor.shutdown()
                    executor.awaitTermination(1, TimeUnit.SECONDS)

                    lockSuccessCounter.get().shouldBe(1)
                }
            }
        }
    })
