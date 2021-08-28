package com.example.workshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timeText;
    private TextView questionText;
    private EditText answerTextField;
    private Button submitButton;
    private Button tryAgainButton;
    private TextView scoreText;

    private Question currentQuestion;
    private int score;
    private int time;
    private boolean changeQuestion;
    private int timeChangeQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        questionText = findViewById(R.id.questionText);
        answerTextField = findViewById(R.id.answerTextField);
        submitButton = findViewById(R.id.submitButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        scoreText = findViewById(R.id.scoreText);

        score = 0;
        time = 30;
        timeText.setText("" + time);
        scoreText.setText("Score: " + score);
        tryAgainButton.setVisibility(View.INVISIBLE);
        changeQuestion=false;
        timeChangeQuestion=0;

        generateNewQuestion();

       timeManipulate();


        submitButton.setOnClickListener(
                (view) -> {
                    checkAnswer();
                }
        );

        tryAgainButton.setOnClickListener(
                (view) -> {
                    tryAgainButton.setVisibility(View.INVISIBLE);
                    answerTextField.setEnabled(true);
                    score=0;
                    time=30;
                    timeText.setText("" + time);
                    scoreText.setText("Score: " + score);
                    timeManipulate();
                }
        );

        questionText.setOnTouchListener(
                (v,e)->{
                    switch (e.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            changeQuestion=true;
                            timeChangeQuestion=0;
                            new Thread(
                                    ()->{
                                        while(changeQuestion){
                                            timeChangeQuestion+=500;
                                            if(timeChangeQuestion>=1500){
                                                generateNewQuestion();
                                            }
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException interruptedException) {
                                                interruptedException.printStackTrace();
                                            }
                                        }
                                    }
                            ).start();
                            break;

                        case MotionEvent.ACTION_UP:
                            changeQuestion=false;
                            break;

                    }
                    return true;
                }

        );

    }

    public void checkAnswer() {
        if(!answerTextField.getText().toString().isEmpty()){
            String answerText = answerTextField.getText().toString();
            int answerInt = (int) Integer.parseInt(answerText);

            if (answerInt == currentQuestion.getAnswer()) {
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
                score += 5;
                scoreText.setText("Score: " + score);
            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
                score -= 4;
                scoreText.setText("Score: " + score);
            }
            answerTextField.setText("");
            generateNewQuestion();
        }


    }


    public void generateNewQuestion() {
        currentQuestion = new Question();
        questionText.setText(currentQuestion.getQuestion());

    }

    public void timeManipulate(){
        new Thread(
                () -> {

                    while (time > 0) {
                        time--;

                        runOnUiThread(
                                () -> {
                                    if (time == 0) {
                                        tryAgainButton.setVisibility(View.VISIBLE);
                                        answerTextField.setEnabled(false);
                                    }
                                    timeText.setText("" + time);
                                }
                        );

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }


}