package pl.kmlszopa.quiz;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kmlsz on 23.11.2016.
 */

public class Question implements Serializable{
    private String content;
    private List<String> answers;
    private int correctAnswer;
    private int difficulty;

    public Question(String content, List<String> answers, int correctAnswer, int difficulty) {
        this.content = content;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
