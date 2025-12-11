package dev.envycodes.workshop.async;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AsyncChatController {

    private final ChatClient chatClient;
    private final SimpleLoggerAdvisor aLoggerAdvisor;

    public AsyncChatController(ChatClient.Builder builder, SimpleLoggerAdvisor aLoggerAdvisor) {
        this.chatClient = builder.build();
        this.aLoggerAdvisor = aLoggerAdvisor;
    }

    @GetMapping("/stream")
    public Flux<String> stream() {
        return chatClient.prompt()
                .user("I am visiting Charleston, SC can you give me 10 places I must visit")
                .advisors(aLoggerAdvisor)
                .stream()
                .content();
    }

}
