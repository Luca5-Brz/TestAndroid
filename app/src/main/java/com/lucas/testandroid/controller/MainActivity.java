package com.lucas.testandroid.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import com.lucas.testandroid.R;
import com.lucas.testandroid.model.User;


public class MainActivity extends AppCompatActivity {

    private TextView mBienvenueTextView;
    private EditText mPrenomEditText;
    private Button mPlayButton;

    //private User mUser;

    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBienvenueTextView = findViewById(R.id.main_textview_bienvenue);
        mPrenomEditText = findViewById(R.id.main_edittext_prenom);
        mPlayButton = findViewById(R.id.main_button_play);

        mPlayButton.setEnabled(false);

        //String mPrenom = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME,null);

        mPrenomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mPlayButton.setEnabled(!s.toString().isEmpty());

            }
        });

        //noinspection Convert2Lambda
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mPrenomEditText.getText().toString())
                        .apply();


                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                //startActivity(gameActivity);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);

                //mUser.setFirstName(mPrenomEditText.getText().toString());

            }

        });

        greetUser();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK==resultCode && data != null){

            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);

            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();

            greetUser();
        }

    }

    private void greetUser(){
        String prenom = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, -1);

        if(prenom != null){
            if(score != -1){
                mBienvenueTextView.setText(getString(R.string.welcome_back_with_score, prenom, score));
            }else{
                mBienvenueTextView.setText(getString(R.string.welcome_back, prenom));
            }

            mPrenomEditText.setText(prenom);
        }

    }
}