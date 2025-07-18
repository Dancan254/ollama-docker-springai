package com.javaguy.ollama;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final OllamaChatModel chatModel;
    private final String msg = """
            List the geographic or cultural origins of names used in Java-related technologies and frameworks, such as 'Java', 'Jakarta EE', and 'Lombok' etc.
            For each name, explain where it comes from, its connection to the technology, and why it was chosen.
            Include both historical and branding considerations if relevant.
            """;
    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    public Map<String, Object> chat(@RequestParam(required = false,
            defaultValue = msg)
                           String message){
        log.info("Chat request received");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String response = chatModel.call(message);
        stopWatch.stop();
        long executionTime = stopWatch.getTotalTimeMillis();
        log.info("Chat response sent in {} ms", executionTime);
        return Map.of(
                "response", response,
                "executionTimeMs", executionTime,
                "executionTimeSeconds", executionTime/1000
        );
    }
}
