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
    private static final String PLAYER_NAME = "x-player-name";
    private static final String NAME_ATR = "name";
    private static final String QUESTION_ATR = "question";
    private static final String PLAY_LOCATION = "play.jsp";
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String name = (String) req.getSession().getAttribute(NAME_ATR);

        final Request request =
                new Request.Builder().url(URL_QUESTION).addHeader(PLAYER_NAME, name).build();
        final Response question = this.okHttpClient.newCall(request).execute();

        req.setAttribute(QUESTION_ATR, question.body().string());
        req.setAttribute(NAME_ATR, name);
        req.getRequestDispatcher(PLAY_LOCATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String username = req.getParameter("user_name");

        final Request request =
                new Request.Builder().url(URL_QUESTION).addHeader(PLAYER_NAME, username).build();
        final Response question = this.okHttpClient.newCall(request).execute();

        req.setAttribute(QUESTION_ATR, question.body().string());
        req.getSession().setAttribute(NAME_ATR, username);
        req.getRequestDispatcher(PLAY_LOCATION).forward(req, resp);
    }
}
