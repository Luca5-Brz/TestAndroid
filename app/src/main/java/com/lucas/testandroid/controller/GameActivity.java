package com.lucas.testandroid.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.lucas.testandroid.R;
import com.lucas.testandroid.model.Question;
import com.lucas.testandroid.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;
    private final QuestionBank mQuestionBank = generateQuestions();
    //gestion du nombre de question
    private int mRemainingQuestionCount;
    //gestion du score
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        mAnswer1Button = findViewById(R.id.game_activity_button_1);
        mAnswer2Button = findViewById(R.id.game_activity_button_2);
        mAnswer3Button = findViewById(R.id.game_activity_button_3);
        mAnswer4Button = findViewById(R.id.game_activity_button_4);


        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);

        displayQuestion(mQuestionBank.getCurrentQuestionIndex());

        mEnableTouchEvents=true;

        mRemainingQuestionCount = 4;
        mScore=0;

    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Qui est le cr??ateur d'Android ?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Warton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "Quand ?? atteri le premier home sur la lune ?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "Quel est le num??ro de maison des Simpsons ?",
                Arrays.asList(
                    "42",
                    "101",
                    "666",
                    "742"
                ),
                3
        );

        Question question4 = new Question(
                "Qui ?? cr??e Microsoft ?",
                Arrays.asList(
                        "Bill Gates",
                        "Steve Jobs",
                        "Mark Zuckerberg",
                        "Elon Musk"
                ),
                0
        );

        Question question5 = new Question(
                "Qui a peint la Mona Lisa ?",
                Arrays.asList(
                        "Michelangelo",
                        "Leonardo Da Vinci",
                        "Raphael",
                        "Carravagio"
                ),
                1
        );

        Question question6 = new Question(
                "Quelle est l'extension belge pour le web ?",
                Arrays.asList(
                        ".bg",
                        ".bm",
                        ".be",
                        ".bl"
                ),
                2
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5, question6));
    }

    private void displayQuestion(final Question question){

        mQuestionTextView.setText(question.getQuestion());
        mAnswer1Button.setText(question.getChoiceList().get(0));
        mAnswer2Button.setText(question.getChoiceList().get(1));
        mAnswer3Button.setText(question.getChoiceList().get(2));
        mAnswer4Button.setText(question.getChoiceList().get(3));

    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mAnswer1Button){
            index=0;
        }
        else if (v == mAnswer2Button){
            index=1;
        }
        else if (v==mAnswer3Button){
            index=2;
        }
        else if (v==mAnswer4Button){
            index=3;
        }
        else{
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        // V??rification de la bonne r??ponse
        if(index == mQuestionBank.getCurrentQuestionIndex().getAnswerIndex())
        {
            Toast.makeText(this, "Correct !",Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else{
            Toast.makeText(this,"Incorrect !", Toast.LENGTH_SHORT).show();
        }


        mEnableTouchEvents=false;

        //Suite du jeu ou pas
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount --;

                if(mRemainingQuestionCount > 0){

                    displayQuestion(mQuestionBank.getNextQuestionIndex());
                }
                else{
                    endGame();
                }

                mEnableTouchEvents=true;
            }
        },2000);

    }

    public void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //noinspection Convert2Lambda
        builder.setTitle("Bien jou?? !")
                .setMessage("Votre score est : " + mScore)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                })
                .create()
                .show();

    }
}