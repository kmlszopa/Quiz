package pl.kmlszopa.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {


    @BindView(R.id.player_name)
    protected EditText mName;
    @BindView(R.id.difficulty)
    protected Spinner mDifficulty;
    private UserPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mPrefs = new UserPreferences(this);
        mName.setText(mPrefs.getUsername());
        mDifficulty.setSelection(mPrefs.getLevel());
    }

    @OnClick(R.id.next)
    protected void onNextClick() {
        //  sprawdzic czy jest wpisana nazwa gracza
        String name = mName.getText().toString();
        if (name.trim().isEmpty()) {
            mName.setError("Brak nazwy gracza");
            return;
        }

        // sprawdzenia czy wybrano poziom trudnosci
        int selectedLevel = mDifficulty.getSelectedItemPosition();
        if (selectedLevel == 0) {
            Toast.makeText(this, "Wybierz poziom trudności.", Toast.LENGTH_LONG).show();
            return;
        }
        mPrefs.setUsername(name);
        mPrefs.setLevel(selectedLevel);

        // losowanie puli pytan
        QuestionsDataBase db = new MemoryQuestionsDataBase();
        List<Question> questions = db.getQuestions(selectedLevel);
        Random random = new Random();
        while (questions.size() > 5) {
            questions.remove(random.nextInt(questions.size()));
        }

        Collections.shuffle(questions);


        // Otwarcie nowej aktywnosci
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtra("questions",new ArrayList<>(questions));
        startActivity(questionIntent);

    }
}
