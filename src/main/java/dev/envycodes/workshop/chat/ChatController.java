
package dev.envycodes.workshop.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    /**
     * A basic example of how to use the chat client to pass a message and call the LLM
     * @param message the user message to send to the LLM
     * @return the LLM response content
     */
    @GetMapping("/")
    public String joke(@RequestParam(value = "message", defaultValue = "Tell me a dad joke about Dogs") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content(); // short for getResult().getOutput().getContent();
    }

    /**
     * Take in a topic as a request parameter and use that param in the user message
     * @param topic the subject about which a joke is requested
     * @return a joke about the provided topic
     */
    @GetMapping("/jokes-by-topic")
    public String jokesByTopic(@RequestParam String topic) {
        return chatClient.prompt()
                .user(u -> u.text("Tell me a joke about {{topic}}").param("topic", topic))
                .call()
                .content();
    }

    /**
     * Take in a weather condition as a request parameter and use that param in the user message.
     * @param weather the weather condition (e.g., sunny, rainy) for which a joke is requested
     * @return a joke about the provided weather condition
     */
    @GetMapping("/jokes-by-weather")
    public String jokesByWeather(@RequestParam String weather) {
        // Simple validation – avoid empty weather values
        if (weather == null || weather.isBlank()) {
            return "Please provide a non‑empty weather parameter.";
        }
        return chatClient.prompt()
                .user(u -> u.text("Tell me a joke about {{weather}}").param("weather", weather))
                .call()
                .content();
    }

    /**
     * What if you didn't want to get a String back, and you wanted the whole response?
     * @param message the user message to send to the LLM
     * @return the full {@link ChatResponse} object
     */
    @GetMapping("jokes-with-response")
    public ChatResponse jokeWithResponse(@RequestParam(value = "message", defaultValue = "Tell me a dad joke about computers") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }

}
