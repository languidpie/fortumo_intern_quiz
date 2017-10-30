package quiz;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet {

    private static final String URL_QUESTION = "https://fortumo-intern-quiz-mlp.herokuapp.com/question";
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = (String) req.getSession().getAttribute("name");

        final Request request =
                new Request.Builder().url(URL_QUESTION).addHeader("x-player-name", name).build();
        Response question = this.okHttpClient.newCall(request).execute();

        req.setAttribute("question", question.body().string());
        req.setAttribute("name", name);
        req.getRequestDispatcher("play.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String username = req.getParameter("user_name");

        final Request request =
                new Request.Builder().url(URL_QUESTION).addHeader("x-player-name", username).build();
        Response question = this.okHttpClient.newCall(request).execute();

        req.setAttribute("question", question.body().string());
        req.getSession().setAttribute("name", username);
        req.getRequestDispatcher("play.jsp").forward(req, resp);
    }
}
