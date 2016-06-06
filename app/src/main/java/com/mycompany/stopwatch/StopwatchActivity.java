package com.mycompany.stopwatch;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.logging.Handler;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstantState) {
        savedInstantState.putInt("seconds",seconds);
        savedInstantState.putBoolean("running", running);
        savedInstantState.putBoolean("wasRunning",wasRunning);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        wasRunning = running;
//        running = false;
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(wasRunning){
//            running = true;
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        final android.os.Handler handler = new android.os.Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minute = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format("%d:%02d:%02d", hours,minute,secs);

                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }

        });
    }

}
