package com.esep.outdoora

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinApplicationStarter

fun main(args: Array<String>) {
    runApplication<KotlinApplicationStarter>(*args)
}
