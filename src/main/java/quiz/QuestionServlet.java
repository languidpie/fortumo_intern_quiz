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
        super.doGet(req, resp);
        final Question question = QuizContextListener.getQuestionQueue().nextQuestion();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Question.class, new QuestionSerializer());
        gsonBuilder.setPrettyPrinting();
        final Gson gson = gsonBuilder.create();

        final String json = gson.toJson(question);

        /* Response */
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

        /* Assert user input aka answer */
        String answer = req.getParameter("answer");
        if (question.getAnswers().contains(answer)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("correct");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("wrong");
        }
    }
}
