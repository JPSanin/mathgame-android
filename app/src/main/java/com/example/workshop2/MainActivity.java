package com.example.workshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText= findViewById(R.id.timeText);
        questionText= findViewById(R.id.questionText);
        answerTextField= findViewById(R.id.answerTextField);
        submitButton= findViewById(R.id.submitButton);
        tryAgainButton= findViewById(R.id.tryAgainButton);
        scoreText= findViewById(R.id.scoreText);

        score=0;
        time=30;
        timeText.setText(""+time);
        scoreText.setText("Score: "+ score);

        generateNewQuestion();

        new Thread(
                ()->{
                    while(time>0){
                        time--;

                        runOnUiThread(
                                ()->{
                                    timeText.setText(""+time);
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

        submitButton.setOnClickListener(
                (view) -> {
                    checkAnswer();
                }
        );
    }

    public void checkAnswer(){
    String answerText= answerTextField.getText().toString();
    int answerInt= (int) Integer.parseInt(answerText);

    if(answerInt==currentQuestion.getAnswer()){
        Toast.makeText(this,"Correct",Toast.LENGTH_LONG).show();
        score+=5;
        scoreText.setText("Score: "+ score);
    }else{
        Toast.makeText(this,"Incorrect",Toast.LENGTH_LONG).show();
    }

    generateNewQuestion();

    }

    public void generateNewQuestion(){
        currentQuestion=new Question();
        questionText.setText(currentQuestion.getQuestion());

    }


}