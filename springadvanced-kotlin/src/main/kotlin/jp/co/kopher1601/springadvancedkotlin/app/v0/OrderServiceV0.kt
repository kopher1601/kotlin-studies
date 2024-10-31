package jp.co.kopher1601.springadvancedkotlin.app.v0

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceV0 @Autowired constructor(
    private val orderRepository: OrderRepositoryV0
) {

    fun orderItem(itemId: String) {
        orderRepository.save(itemId)
    }
}