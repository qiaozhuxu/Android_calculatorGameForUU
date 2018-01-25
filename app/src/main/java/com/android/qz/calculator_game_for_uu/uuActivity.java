package com.android.qz.calculator_game_for_uu;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class uuActivity extends AppCompatActivity {
    private int sum;
    private int rightNum = 0;
    private int answeredCount = 0;
    private boolean timeIsOver = false;
    private Set<Integer> answers = new HashSet<>();

    //Maximum count of questions
    private final static int TOTAL_NUM = 10;
    //Maximum time is 1min
    private final static int TOTAL_TIME = 60000;

    private TextView resultTextView;
    private TextView curScore;
    private GridLayout gridLayout;
    private TextView questionTextView;
    private TextView countDownTextView;
    private CountDownTimer countDownTimer;

    public void summit(View view) {
        //summit happens first
        if (!timeIsOver) {
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
            } else {
                generateNewQuestion();
            }
        } else {
            Toast.makeText(this,"Game is over",Toast.LENGTH_LONG).show();
        }
        //timeIsOver happens before click, this round will not be counted.
    }

    public boolean gameIsOver() {
        if(answeredCount >= TOTAL_NUM || timeIsOver) {
            countDownTimer.cancel();
            return true;
        } else {
            return false;
        }
    }

    public void generateNewQuestion() {
        timeIsOver = false;
        int firstNum = 0;
        int secondNum = 0;
        answers.clear();
        //hide the result from previous
        resultTextView.setVisibility(View.INVISIBLE);

        //set the curscore
        curScore.setText(String.format("%s/%s", rightNum, TOTAL_NUM));

        //set the question

        //generate 2 random number and get the sum from them
        firstNum = (int) (Math.random() * 10 + 1);
        secondNum = (int) (Math.random() * 10 + 1);
        sum = firstNum + secondNum;
        answers.add(sum);

        //setTextView for question
        questionTextView.setText(String.format("%s + %s = ", firstNum, secondNum));

        //set the wrong answers
        for (int i = 0; i < 3; i++) {
            int wrongAnswer = (int) (Math.random() * 20 + 1);
            while (answers.contains(wrongAnswer)) {
                wrongAnswer = (int) (Math.random() * 20 + 1);
            }
            answers.add(wrongAnswer);
        }

        Log.i("answers size", Integer.toString(answers.size()));

        Integer[] ansArray = answers.toArray(new Integer[4]);


        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText(String.valueOf(ansArray[i]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uu);
        resultTextView = findViewById(R.id.resultTextView);
        curScore = findViewById(R.id.curScore);
        questionTextView = (TextView) findViewById(R.id.question);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        countDownTextView = (TextView) findViewById(R.id.countdown);

        generateNewQuestion();

//       countdowntimer start! either timer is over or question number is >=10
        countDownTimer = new CountDownTimer(TOTAL_TIME,1000){

            @Override
            public void onTick(long millisecondLeft) {
                int min = (int) millisecondLeft/60000;
                String minSt = Integer.toString(min);
                int sec = (int) millisecondLeft % 60000/1000;
                String secSt = Integer.toString(sec);
                if (min <= 9) {
                    minSt = "0" + min;
                } else {

                }
                if(sec <= 9) {
                    secSt = "0" + sec;
                }
                countDownTextView.setText(String.format("%s : %s",minSt,secSt));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Game is over",Toast.LENGTH_LONG).show();
                Log.i("game is over","Over");
                Log.i("Time is out!","out");
                timeIsOver = true;

            }
        }.start();
    }
}

