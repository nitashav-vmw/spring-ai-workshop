package dev.envycodes.workshop.tools.weather;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final ChatClient chatClient;
    private final WeatherService weatherTools;

    public WeatherController(ChatClient.Builder builder, WeatherService weatherTools) {
        this.chatClient = builder.build();
        this.weatherTools = weatherTools;
    }

    @GetMapping("/weather")
    public String getAlerts(@RequestParam String message) {
        return chatClient.prompt()
                .tools(weatherTools)
                .user(message)
                .call()
                .content();
    }
}
