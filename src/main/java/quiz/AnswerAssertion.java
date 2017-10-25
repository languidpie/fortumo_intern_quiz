package quiz;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class AnswerAssertion {

    public void assertAnswer(Map<Integer, Question> questionMap, AnswerView answerView,
            HttpServletResponse resp) throws IOException
    {
        final Question question = questionMap.get(answerView.getId());
        if (question.getAnswers().contains(answerView.getAnswer())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("correct");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("wrong");
        }
    }
}
