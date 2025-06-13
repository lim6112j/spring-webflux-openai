package com.ciel.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ChatController {
  private val chatClient: ChatClient

  constructor(chatClient: ChatClient) {
    this.chatClient = chatClient
  }

  @PostMapping("/chat")
  suspend fun chat(@RequestBody message: String): Mono<String> {
    return Mono.just(chatClient.prompt(message).call().content())
  }
}
