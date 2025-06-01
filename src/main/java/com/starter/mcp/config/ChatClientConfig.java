package com.starter.mcp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/1 */
@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {
  @Bean
  public ChatClient chatClient(ChatClient.Builder builder) {
    return builder.build();
  }
}
