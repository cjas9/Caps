package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String call = game.qa();
        int positionOfNewLine = call.indexOf("\n");
        Question = call.substring(0, positionOfNewLine);
        storedAnswer = call.substring(positionOfNewLine+1);
        TextView ques = findViewById(R.id.question);
        ques.setText(Question);



    }
    private Game game = new Game();
    private String Question;
    private String storedAnswer;
    private int score = 0;
    private int qNum = 1;
    String log = " ";

    // Controller
    /* public void ask(){
        String call = game.qa();
        int positionOfNewLine = call.indexOf("\n");
        Question = call.substring(0, positionOfNewLine);
        storedAnswer = call.substring(positionOfNewLine+1);
        TextView ques = findViewById(R.id.question);
        ques.setText(Question);
        int num = qNum++;
        TextView number = findViewById(R.id.qNum);
        number.setText("Q# " + num);

        //System.out.println(storedAnswer);
    }*/

    public void onDone(View v){
        EditText ans = findViewById(R.id.answer);
        String userAnswer = ans.getText().toString();

        if (storedAnswer.equalsIgnoreCase(userAnswer)) {
            score++;
        }

            TextView textview = findViewById(R.id.log);
            log = "\n"+ "Q" + qNum + " " + Question + "\n" + "Your Answer: " + userAnswer + "\n" + "Correct Answer: " + storedAnswer + "\n" + log + "\n";
            textview.setText(log);


        if(qNum==10)
        {
            TextView c = findViewById(R.id.qNum);
            c.setText("GAME OVER!");
            Button btn = findViewById(R.id.done);
            btn.setEnabled(false);
            TextView a = findViewById(R.id.question);
            a.setText("");

        }
        else{
            String call = game.qa();
            int positionOfNewLine = call.indexOf("\n");
            Question = call.substring(0, positionOfNewLine);
            storedAnswer = call.substring(positionOfNewLine+1);
            TextView ques = findViewById(R.id.question);
            ques.setText(Question);
            qNum++;
            TextView number = findViewById(R.id.qNum);
            number.setText("Q# " + qNum);

        }

        TextView c = findViewById(R.id.score);
        c.setText("SCORE = " + score);

    }

}
