package digger.explorer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiggerApplication

fun main(args: Array<String>) {
	runApplication<DiggerApplication>(*args)
}
