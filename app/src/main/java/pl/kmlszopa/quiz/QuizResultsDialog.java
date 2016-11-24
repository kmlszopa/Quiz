package pl.kmlszopa.quiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by kmlsz on 24.11.2016.
 */

public class QuizResultsDialog extends AppCompatDialogFragment {
    public static QuizResultsDialog newInstance(int correctAnswers, int totalAnswers){
     QuizResultsDialog dialog = new QuizResultsDialog();
        Bundle args = new Bundle();

        args.putInt("correct",correctAnswers);
        args.putInt("total",totalAnswers);
        dialog.setArguments(args);

        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setCancelable(false);
        int correctAnswers = getArguments().getInt("correct");
        int totalAnswers = getArguments().getInt("total");
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Wynik quizu")
                .setMessage("Odpowiedziałeś poprawnie na " + correctAnswers + " pytań z " + totalAnswers)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        getActivity().finish();
                    }
                })
                .create();
        return dialog;

    }
}
