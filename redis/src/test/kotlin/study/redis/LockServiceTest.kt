package study.redis

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class LockServiceTest(
    @Autowired
    private val normalLockService: LockService,
) : DescribeSpec({
        describe("NormalLockService") {
            context("잠금되어 있지 않으면") {
                val key = "normal-lock"

                it("잠금에 성공한다") {
                    val locked = normalLockService.lock(key = key, ttl = Duration.ofMillis(10))

                    locked.shouldBeTrue()
                }
            }

            context("잠금되어 있으면") {
                val key = "already-locked"

                beforeEach {
                    normalLockService.lock(key = key, ttl = Duration.ofMillis(10))
                }

                it("잠금에 실패한다") {
                    val locked = normalLockService.lock(key = key, ttl = Duration.ofMillis(10))

                    locked.shouldBeFalse()
                }
            }

            context("동시에 잠금을 획득하려 하면") {
                val key = "duplicate-call"

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
                                    normalLockService.lock(key = key, ttl = Duration.ofMillis(100))
                                if (locked) {
                                    lockSuccessCounter.incrementAndGet()
                                }
                            } finally {
                                println("Done $it")
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
