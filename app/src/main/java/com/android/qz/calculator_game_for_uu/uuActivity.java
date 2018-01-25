package com.android.qz.calculator_game_for_uu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class uuActivity extends AppCompatActivity {
    private int firstNum = 0;
    private int secondNum = 3;
    private int sum = 0;
    private int buttonTag = 0;
    private int rightNum = 0;
    private int answeredCount = 0;
    private final static int TOTAL_NUM = 1;
    private TextView resultTextView;
    private TextView curScore;
    private int[] solutionArray;

    private TextView questionTextView;

    public void summit(View view) {
        int id = view.getId();
        Button buttonClicked = findViewById(id);
        int result = Integer.parseInt(buttonClicked.getText().toString());
        if(result == sum) {
            resultTextView = findViewById(R.id.resultTextView);
            resultTextView.setVisibility(View.VISIBLE);
            rightNum ++;
        }
        answeredCount++;
        if (gameIsOver()) {
            Toast.makeText(this,"Game is over",Toast.LENGTH_LONG).show();
            Log.i("game is over","Over");
        } else {
            generateNewQuestion();
        }
    }
    public boolean gameIsOver() {
        if(answeredCount >= TOTAL_NUM) {
            return true;
        } else {
            return false;
        }
    }

    public void generateNewQuestion(){
        //hide the result from previous
        resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.INVISIBLE);

        //set the curscore
        curScore = findViewById(R.id.curScore);
        curScore.setText(String.format("%s/%s", rightNum, TOTAL_NUM));

        //set the question

        //generate 2 random number and get the sum from them
        firstNum = (int) (Math.random()*10 + 1);
        firstNum = (int) (Math.random()*10 + 1);
        sum = firstNum + secondNum;

        //setTextView for question
        questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText(String.format("%s + %s = ", firstNum, secondNum));

        //setButtonText
        //random button will show the right answer
        buttonTag = (int) (Math.random()*4 + 1);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        HashSet<Integer> wrongAns = new HashSet<>();

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            Button button = (Button) gridLayout.getChildAt(i);

            if (Integer.parseInt(button.getTag().toString()) == buttonTag) {
                button.setText(String.valueOf(sum));
            } else {
                int wrongAnswer = (int) (Math.random()*20 + 1);
                while(wrongAns.contains(wrongAnswer)) {
                    wrongAnswer = (int) (Math.random()*20 + 1);
                }
                button.setText(String.valueOf(solutionArray[i]));
                wrongAns.add(wrongAnswer);
                }
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uu);

        //set up the wrong answers
        solutionArray = new int[20];
        for(int i = 0; i < 20; i++) {
            solutionArray[i] = i + 1;
        }

        generateNewQuestion();

//       countdowntimer start! either timer is over or question number is >=10
    }
}

