package quiz;

import java.io.IOException;
import java.util.Map;

public class AnswerAssertion {

    public String assertAnswer(Map<Integer, Question> questionMap, AnswerView answerView) throws IOException {
        final Question question = questionMap.get(answerView.getId());
        if (question.getAnswers().contains(answerView.getAnswer())) {
            return "correct";
        } else {
            return "wrong";
        }
    }
}
