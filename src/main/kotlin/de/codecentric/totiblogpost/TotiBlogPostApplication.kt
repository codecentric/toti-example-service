package de.codecentric.totiblogpost

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TotiBlogPostApplication

fun main(args: Array<String>) {
	runApplication<TotiBlogPostApplication>(*args)
}
