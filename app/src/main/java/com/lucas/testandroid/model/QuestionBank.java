package com.lucas.testandroid.model;

import java.util.Collections;
import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;

        Collections.shuffle(mQuestionList);

    }

    public Question getCurrentQuestionIndex() {

        return mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestionIndex() {
        mQuestionIndex++;
        return getCurrentQuestionIndex();
    }
}
