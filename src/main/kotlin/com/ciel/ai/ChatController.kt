package com.ciel.ai

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ChatController {

    @GetMapping("/chat")
    fun chat(@RequestParam(name = "message", required = false, defaultValue = "Hello") message: String?): Mono<String> {
        return Mono.just("You said: ${message ?: "No message provided"}")
    }
}
