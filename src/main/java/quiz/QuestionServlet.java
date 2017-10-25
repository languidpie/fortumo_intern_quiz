package quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import quiz.listener.QuizContextListener;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Question question = QuizContextListener.getQuestionQueue().nextQuestion();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        QuestionView questionView = new QuestionView();
        questionView.setId(question.getId());
        questionView.setQuestion(question.getQuestion());
        questionView.setCategory(question.getCategory());
        questionView.setDifficulty(question.getDifficulty());

        final String json = gson.toJson(questionView);

        /* Response */
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

        /* Assert user input aka answer */
        //        String answer = req.getParameter("answer");
        //        if (question.getAnswers().contains(answer)) {
        //            resp.setStatus(HttpServletResponse.SC_OK);
        //            resp.getWriter().write("correct");
        //        } else {
        //            resp.setStatus(HttpServletResponse.SC_OK);
        //            resp.getWriter().write("wrong");
        //        }
    }
}
