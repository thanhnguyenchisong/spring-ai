package org.thanhncs.ai.springai.services;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thanhncs.ai.springai.model.Answer;
import org.thanhncs.ai.springai.model.Question;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Value("classpath:templates/prompt.st")
    private Resource promptResourceTemplate;


    /**
     * Use PromptTemplate from resource
     * @param value
     * @return
     */
    @Override
    public String getAnswerPromptTemplate(String value) {
        PromptTemplate promptTemplate = new PromptTemplate(promptResourceTemplate);
        Prompt prompt = promptTemplate.create(Map.of("value", value));
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    @Value("classpath:templates/prompt-directing-format-response.st")
    private Resource promptResourceTemplateDirectTheResponse;

    public String getAnswerPromptTemplateDirectTheResponse(String value) {
        PromptTemplate promptTemplate = new PromptTemplate(promptResourceTemplateDirectTheResponse);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", value));
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    public String getAnswerPromptTemplateDirectTheResponseWithJson(String value) {
        PromptTemplate promptTemplate = new PromptTemplate(promptResourceTemplateDirectTheResponse);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", value));
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    public Answer getAnswerResponseWithJSONSchema(Question question) {
        BeanOutputParser<Answer> parser = new BeanOutputParser<>(Answer.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(promptResourceTemplateDirectTheResponse);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", question.question(), "format", format));
        ChatResponse response = chatClient.call(prompt);
        System.out.println(response.getResult().getOutput().getContent());
        return parser.parse(response.getResult().getOutput().getContent());
    }
}
