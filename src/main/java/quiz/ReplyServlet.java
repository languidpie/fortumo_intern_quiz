package quiz;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyServlet extends HttpServlet {

    private static final String URL_ANSWER = "https://fortumo-intern-quiz-mlp.herokuapp.com/answer";
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String questionId = req.getParameter("question_id");
        final String answer = req.getParameter("answer");
        final String name = req.getParameter("name");

        final String json = "{\"id\":\"" + questionId + "\",\"answer\":\"" + answer + "\"}";

        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), json);

        final Request request = new Request.Builder().url(URL_ANSWER).addHeader("x-player-name", name).post(requestBody).build();
        final Response response = this.okHttpClient.newCall(request).execute();

        req.setAttribute("reply", response.body().string());
        req.setAttribute("name", name);
        req.getRequestDispatcher("result.jsp").forward(req, resp);
    }
}
