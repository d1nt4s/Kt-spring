package core.cli

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun coroutinesDemo() = runBlocking {
    val jobs = (1..3).map { i ->
        launch {
            delay(100L * i)
            println("Job #$i done")
        }
    }
    jobs.forEach { it.join() }
}
