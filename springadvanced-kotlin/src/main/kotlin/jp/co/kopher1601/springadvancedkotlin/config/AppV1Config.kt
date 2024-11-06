package jp.co.kopher1601.springadvancedkotlin.config

import jp.co.kopher1601.springadvancedkotlin.app.v1.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppV1Config {

    @Bean
    fun orderControllerV1(): OrderControllerV1 {
        return OrderControllerV1Impl(orderServiceV1())
    }

    @Bean
    fun orderServiceV1(): OrderServiceV1 {
        return OrderServiceV1Impl(orderRepositoryV1())
    }

    @Bean
    fun orderRepositoryV1(): OrderRepositoryV1 {
        return OrderRepositoryV1Impl()
    }
}