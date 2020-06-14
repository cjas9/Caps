package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.muddzdev.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {

    private ToneGenerator tg;
    private final Game game = new Game();
    private String Question;
    private String storedAnswer;
    private int Score = 0;
    private int qNum = 1;
    EditText answerView;
    TextView quesView;
    TextView quesNumber;
    private String log = " ";

    private float acelVal;
    private float acelLast;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("");


        this.tg = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;


        String call = game.qa();
        int positionOfNewLine = call.indexOf("\n");
        Question = call.substring(0, positionOfNewLine);
        storedAnswer = call.substring(positionOfNewLine + 1);
        TextView ques = findViewById(R.id.question);
        ques.setText(Question);

    }


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

    public void onDone(View v) {
        answerView = findViewById(R.id.answer);
        String userAnswer = answerView.getText().toString();

        if (storedAnswer.equalsIgnoreCase(userAnswer)) {
            Score++;
            tg.startTone(ToneGenerator.TONE_CDMA_CALLDROP_LITE, 200);

        }

        String hint = game.qa();
        int position_OfNewLine = hint.indexOf("\n");
        String newAnswer = hint.substring(position_OfNewLine + 1);

        Context context = getApplicationContext();
        String text = "The answer is either " + storedAnswer + " or " + newAnswer;
        //int duration = Toast.LENGTH_LONG;

        if (userAnswer.equals("?")) {
            StyleableToast label = StyleableToast.makeText(context, text, Toast.LENGTH_LONG,  R.style.custom_toast);
            label.show();

            answerView.setText("");

        } else {
            TextView logView = findViewById(R.id.log);
            log = "\n" + "Q" + qNum + ": " + Question + "\n" + "Your Answer: " + userAnswer + "\n" + "Correct Answer: " + storedAnswer + "\n" + log + "\n";
            logView.setText(log);
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,  200);
        }

        if (!"?".equals(userAnswer)) {
            String call = game.qa();
            int positionOfNewLine = call.indexOf("\n");
            Question = call.substring(0, positionOfNewLine);
            storedAnswer = call.substring(positionOfNewLine + 1);
            quesView = findViewById(R.id.question);
            quesView.setText(Question);
            qNum++;
            quesNumber = findViewById(R.id.qNum);
            quesNumber.setText("Q# " + qNum);
        }

       /* if(){

        }*/

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText("SCORE =" + " " + Score);


        if (qNum == 10) {
            quesNumber.setText("GAME OVER!");
            Button btn = findViewById(R.id.done);
            btn.setEnabled(false);
            quesNumber = findViewById(R.id.qNum);
            quesView.setText("");
        }

        answerView = findViewById(R.id.answer);
        answerView.setText("");


    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;
             if(shake > 12) {
                 qNum = 1;
                 ((TextView) findViewById(R.id.qNum)).setText("Q# " + qNum);

                 log = "\n" + "Questions Renewed - device shaken" + "\n" + log + "\n";
                 ((TextView) findViewById(R.id.log)).setText(log);

                 /*
                 String call = game.qa();
                 int positionOfNewLine = call.indexOf("\n");
                 Question = call.substring(0, positionOfNewLine);
                 storedAnswer = call.substring(positionOfNewLine + 1);
                 TextView ques = findViewById(R.id.question);
                 ques.setText(Question);
                 newNum++;
                 TextView number = findViewById(R.id.qNum);
                 number.setText("Q# " + newNum);*/
             }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}

    /*@Override
    public void onSensorChanged(SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax*ax + ay*ay + az*az);
        if(a > 10){
            ((TextView) findViewById(R.id.qNum)).setText("Q# 1");
        }*/




