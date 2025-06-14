package com.ciel.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ChatController(private val builder: ChatClient.Builder) {
    private var chatClient: ChatClient = builder.build()
    @GetMapping("/chat")
    fun chat(
            @RequestParam(name = "message", required = false, defaultValue = "Hello")
            message: String?
    ): Mono<String> {
        return Mono.defer {
            val promptMono = Mono.just(chatClient.prompt(message ?: "Hello"))
            promptMono.flatMap { prompt ->
                Mono.fromFuture(prompt.callAsync())
                    .map { response ->
                        "You said: ${message ?: "No message provided"}\nAI response: ${response.content()}"
                    }
            }
        }
    }
}
