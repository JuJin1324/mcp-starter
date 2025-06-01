package com.starter.mcp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendArchitectPromptServiceTest {
    @Autowired
    private BackendArchitectPromptService backendArchitectPromptService;

    @Test
    void getArchitectAdviceTest() {
        String architectAdvice = backendArchitectPromptService.getArchitectAdvice(
                "IntelliJ IDEA Ultimate",
                "새로운 소셜 미디어 플랫폼 백엔드 개발",
                "100만 사용자 동시 접속 처리 및 실시간 알림 기능 제공",
                "Project Phoenix",
                "사용자 간 실시간 채팅 기능 구현"
        );
        System.out.println(architectAdvice);
    }
}