package dev.envycodes.workshop.tools.dayaware;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayAwareController {

    private final ChatClient chatClient;

    public DayAwareController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @GetMapping("/tools")
    public String tools() {
        return chatClient.prompt("What day is tomorrow?")
                .tools(new DateTimeTools())
                .call()
                .content();
    }

}