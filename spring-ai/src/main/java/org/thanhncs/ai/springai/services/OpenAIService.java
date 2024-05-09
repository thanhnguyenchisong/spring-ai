package org.thanhncs.ai.springai.services;

import org.thanhncs.ai.springai.model.Answer;
import org.thanhncs.ai.springai.model.Question;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    String getAnswerPromptTemplate(String question);
}
