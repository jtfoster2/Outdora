package com.malecki.outdoora

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// DON'T START USING THIS
@SpringBootApplication
class KotlinApplicationStarter
fun main(args: Array<String>) {
	runApplication<KotlinApplicationStarter>(*args)
}
