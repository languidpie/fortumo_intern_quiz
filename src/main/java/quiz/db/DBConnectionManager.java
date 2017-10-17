package quiz.db;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import quiz.Question;
import quiz.QuestionsDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Class for db layer
public class DBConnectionManager implements QuestionsDAO {

    private final String dbURL;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    public Request request;
    private Call call;
    private String name = "";
    private String questions;
    private Map<Integer, Question> allQuestions;
    int count = 0;

    public DBConnectionManager(String dbURL) {
        this.dbURL = dbURL;
    }

    public void load() throws IOException {
        this.request = new Request.Builder().url(this.dbURL).build();
        this.call = this.okHttpClient.newCall(this.request);
        Response response = this.call.execute();

        this.questions = response.body().string();

        if (name.isEmpty()) {
            System.out.println("What is your name?");
            Scanner scanner = new Scanner(System.in);
            name = scanner.nextLine();
        }

        System.out.println("Hello " + name);

        allQuestions = this.findAllQuestions();

        questionGame();
    }

    private void questionGame() {

        if (count < allQuestions.size()) {
            if (showQuestion()) {
                System.out.println("Congratulations! Do you want another question? y/n");
                Scanner scanner = new Scanner(System.in);
                String reply = scanner.nextLine();
                if (reply.equals("y")) {
                    questionGame();
                } else if (reply.equals("n")) {
                    call.cancel();
                } else {
                    System.out.println("Something went wrong. The game will now exit.");
                    call.cancel();
                }
            } else {
                System.out.println("Oops, that was wrong. Do you want to try again? y/n");
                Scanner scanner = new Scanner(System.in);
                String reply = scanner.nextLine();
                if (reply.equals("y")) {
                    questionGame();
                } else if (reply.equals("n")) {
                    call.cancel();
                } else {
                    System.out.println("Something went wrong. The game will now exit.");
                    call.cancel();
                }
            }
        }
    }

    @Override
    public Map<Integer, Question> findAllQuestions() {
        Map<Integer, Question> result = new HashMap<>();

        String[] list = this.questions.split("\n");

        for (String string : list) {
            String[] questionSep = string.split(";");
            if (result.containsKey(Integer.parseInt(questionSep[0]))) {
                System.out.println("Question with this id already exists!");
            } else {
                Question question = new Question();
                question.setQuestion(questionSep[1]);
                question.setCategory(questionSep[2]);
                question.setDifficulty(Integer.parseInt(questionSep[3]));
                if (questionSep.length > 5) {
                    question.addAnswer(questionSep[4]);
                } else {
                    List<String> answers = new ArrayList<>();
                    String[] givenAnswers = Arrays.copyOfRange(questionSep, 4, questionSep.length);
                    for (String answer : givenAnswers) {
                        answers.add(answer);
                    }
                    question.setAnswers(answers);
                }
                int questionId = Integer.parseInt(questionSep[0]);
                result.put(questionId, question);
            }
        }
        return result;
    }

    public Boolean showQuestion() {
        List<Integer> questId = getQuestionId();

        Question question = allQuestions.get(questId.get(count));
        System.out.println("("
                + String.valueOf(question.getDifficulty())
                + ","
                + question.getCategory().toString()
                + ") "
                + question.getQuestion().toString());

        String answer = askUserAnwser();

        count++;
        if (count > questId.size()) {
            count = 0;
        }
        return question.getAnswers().contains(answer);
    }

    private String askUserAnwser() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();

        return s;
    }

    private List<Integer> getQuestionId() {
        List<Integer> questionIds = new ArrayList<>();

        questionIds.addAll(allQuestions.keySet());

        return questionIds;
    }
}
