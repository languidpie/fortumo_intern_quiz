package quiz;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuizQuestions extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.getWriter().write(getQuestion());
    }

    public String getQuestion() {
        return "{\n"
                + "  \"id\": \"42\",\n"
                + "  \"question\": \"Kes on tubli poiss?\",\n"
                + "  \"category\": \"general\",\n"
                + "  \"difficulty\":1\n"
                + "}";
    }
}
