package quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String question;
    private String category;
    private int difficulty;
    private List<String> answers;

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer) {
        if (this.answers == null) {
            this.answers = new ArrayList<>();
        }
        this.answers.add(answer);
    }

    @Override
    public String toString() {
        return "Question{"
                + "id='" + this.id + '\''
                + "question='" + this.question + '\''
                + ", category='" + this.category + '\''
                + ", difficulty=" + this.difficulty
                + ", answers=" + this.answers
                + '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
