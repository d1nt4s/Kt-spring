package app.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/api/hello")
    fun hello() = mapOf("ok" to true, "ts" to System.currentTimeMillis())
}
