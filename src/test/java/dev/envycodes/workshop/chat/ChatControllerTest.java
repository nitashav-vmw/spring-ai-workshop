package dev.envycodes.workshop.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.Prompt;
import org.springframework.ai.chat.client.PromptResponse;
import org.springframework.ai.chat.model.ChatResponse;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatClient chatClient;

    @Mock
    private Prompt mockPrompt;

    @Mock
    private PromptResponse mockPromptResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Stub the fluent API of ChatClient
        when(chatClient.prompt()).thenReturn(mockPrompt);
        when(mockPrompt.user(any())).thenReturn(mockPrompt);
        when(mockPrompt.user(any(java.util.function.Consumer.class))).thenReturn(mockPrompt);
        when(mockPrompt.call()).thenReturn(mockPromptResponse);
        when(mockPromptResponse.content()).thenReturn("It's raining cats and dogs!" );
    }

    @Test
    void testJokesByWeatherHappyPath() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jokes-by-weather")
                .param("weather", "rain"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("It's raining cats and dogs!"));
    }

    @Test
    void testJokesByWeatherValidation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jokes-by-weather")
                .param("weather", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The 'weather' request parameter must not be empty."));
    }
}
