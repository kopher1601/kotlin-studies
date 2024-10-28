package jp.co.kopher1601.springtxkotlin.apply

import lombok.extern.slf4j.Slf4j
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
class TxBasicTest @Autowired constructor(
    val basicService: BasicService,
) {
    val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun proxyCheck() {
        log.info("aop class = {}", basicService.javaClass)
        assertThat(AopUtils.isAopProxy(basicService)).isTrue()
    }

    @Test
    fun txTest() {
        basicService.tx()
        basicService.nonTx()
    }


    @TestConfiguration
    class TxApplyBasicConfig {
        @Bean
        fun basicService(): BasicService {
            return BasicService()
        }
    }

    open class BasicService {
        val log = LoggerFactory.getLogger(javaClass)

        @Transactional
        open fun tx() {
            log.info("call tx")
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active = {}", txActive)
        }

        open fun nonTx() {
            log.info("call nonTx")
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active = {}", txActive)
        }
    }
}
