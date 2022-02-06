package cz.jpetrla.cleevio.watchapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WatchAppKotlinApplication

fun main(args: Array<String>) {
	runApplication<WatchAppKotlinApplication>(*args)
}
