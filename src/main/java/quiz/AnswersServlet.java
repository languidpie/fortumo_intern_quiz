package quiz;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswersServlet extends HttpServlet {

    private static Map<Integer, Question> questionMap;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String answer = req.getReader().lines().collect(Collectors.joining());

        try {
            final AnswerView answerView = new Gson().fromJson(answer, AnswerView.class);
            final AnswerAssertion answerAssertion = new AnswerAssertion();
            String assertion = answerAssertion.assertAnswer(questionMap, answerView);
            resp.getWriter().write(assertion);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid input!");
        }
    }

    public static Map<Integer, Question> getQuestionMap() {
        return questionMap;
    }

    public static void setQuestionMap(Map<Integer, Question> questionMap) {
        AnswersServlet.questionMap = questionMap;
    }
}
