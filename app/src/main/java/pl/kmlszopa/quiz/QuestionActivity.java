package pl.kmlszopa.quiz;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.RunnableFuture;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {
    @BindView(R.id.question_text)
    protected TextView mTitle;
    @BindView(R.id.answers)
    protected RadioGroup mAnswers;
    @BindViews({R.id.answer_a, R.id.answer_b, R.id.answer_c})
    protected List<RadioButton> mAnswerButtons;
    @BindView(R.id.button_next)
    protected Button mNextButton;

    private List<Question> mQuestions;
    private int mCurrentQuestion = 0;
    private int[] mAnswersArray;

    private boolean mFirstBackClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        mQuestions = (List<Question>) getIntent().getSerializableExtra("questions");
        mAnswersArray = new int[mQuestions.size()];

        refreshQuestionView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAnswersArray[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        outState.putInt("position",mCurrentQuestion);
        outState.putIntArray("answers",mAnswersArray);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentQuestion = savedInstanceState.getInt("position");
        mAnswersArray = savedInstanceState.getIntArray("answers");
        refreshQuestionView();
    }

    @Override
    public void onBackPressed() {
        onBackTapped();
    }

    private void onBackTapped() {
        if (!mFirstBackClicked) {
            mFirstBackClicked = true;
            Toast.makeText(this, "Tapnij ponownie, aby zakończyć", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFirstBackClicked = false;
                }
            }, 2000);
        } else {
            finish();


        }
    }

    private void refreshQuestionView() {
        mAnswers.clearCheck();
        if (mAnswersArray[mCurrentQuestion] > 0) {
            mAnswers.check(mAnswersArray[mCurrentQuestion]);
        }
        Question q1 = mQuestions.get(mCurrentQuestion);
        mTitle.setText(q1.getContent());
        for (int i = 0; i < 3; i++) {
            mAnswerButtons.get(i).setText(q1.getAnswers().get(i));
        }
        mNextButton.setText(mCurrentQuestion < mQuestions.size() - 1 ? "Dalej" : "Zakończ");
    }

    @OnClick(R.id.button_back)
    protected void onBackClick() {
        if (mCurrentQuestion == 0) {
            onBackTapped();
            return;
        }
        mAnswersArray[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();

        mCurrentQuestion--;
        refreshQuestionView();
    }

    @OnClick(R.id.button_next)
    protected void onNextClick() {
        mAnswersArray[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();

        if (mAnswers.getCheckedRadioButtonId() < 0) {
            Toast.makeText(this, "Wybierz odpowiedź.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCurrentQuestion == mQuestions.size() - 1) {
            int correctAnswers = countCorrectAnswers();
            int totalAnswers = mAnswersArray.length;
            displayResults(correctAnswers, totalAnswers);
            return;
        }
        mCurrentQuestion++;
        refreshQuestionView();
    }

    private void displayResults(int correctAnswers, int totalAnswers) {
       QuizResultsDialog.newInstance(correctAnswers,totalAnswers)
               .show(getSupportFragmentManager(),null);
    }


    private int countCorrectAnswers() {
        int sum = 0;

        for (int i = 0; i < mQuestions.size(); i++) {
            Question question = mQuestions.get(i);
            int userAnswerId = mAnswersArray[i];
            int correctAnswerId = mAnswerButtons.get(question.getCorrectAnswer()).getId();
            if (userAnswerId == correctAnswerId) {
                sum++;
            }
        }
        return sum;
    }

}
