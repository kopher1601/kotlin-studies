package jp.kopher.springbootactuator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootActuatorApplication

fun main(args: Array<String>) {
    runApplication<SpringBootActuatorApplication>(*args)
}
