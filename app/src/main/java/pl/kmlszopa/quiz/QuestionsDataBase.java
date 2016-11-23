package pl.kmlszopa.quiz;

import java.util.List;

/**
 * Created by kmlsz on 23.11.2016.
 */

public interface QuestionsDataBase {
    List<Question> getQuestions(int difficulty);
}
