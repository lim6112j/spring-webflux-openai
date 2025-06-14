package com.ciel.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class ChatController(private val builder: ChatClient.Builder) {
    private var chatClient: ChatClient = builder.build()
    @GetMapping("/chat")
    fun chat(
            @RequestParam(name = "message", required = false, defaultValue = "Hello")
            message: String?
    ): Mono<String> {
        return Mono.fromCallable { chatClient.prompt().user(message ?: "Hello").call() }
                .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                .map { response ->
                    "You said: ${message ?: "No message provided"} \n AI response: ${response.content()}"
                }
    }
    @GetMapping("/stream")
    fun stream(
            @RequestParam(name = "message", required = false, defaultValue = "Hello")
            message: String?
    ): Flux<String> {
        return chatClient
                .prompt()
                .user(message ?: "Hello")
                .stream()
                .content()
                .onErrorReturn("An error occurred while processing your request.")
    }
}
