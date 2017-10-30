package quiz.client;

import okhttp3.OkHttpClient;

public class QuizClient {

    private final OkHttpClient client;

    public QuizClient(OkHttpClient client) {
        this.client = client;
    }
}
