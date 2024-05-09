package org.thanhncs.ai.springai.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thanhncs.ai.springai.model.Answer;
import org.thanhncs.ai.springai.model.Question;
import org.thanhncs.ai.springai.services.OpenAIService;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;


    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer ask(Question question) {
        return openAIService.getAnswer(question);
    }
}
