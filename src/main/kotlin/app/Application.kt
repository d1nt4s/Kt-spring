package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["app", "core"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
