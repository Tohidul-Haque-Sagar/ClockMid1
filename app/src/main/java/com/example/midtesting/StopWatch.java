package com.example.midtesting;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class StopWatch extends AppCompatActivity {
    private boolean isRunning=false;
    private long pauseOffset=0;
    private long passOffset;
    private Button btn_start_stopwatch,btn_reset_stopwatch,btn_pause_stopwatch,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        Chronometer chronometer=findViewById(R.id.chronometer);
        btn_start_stopwatch=findViewById(R.id.btn_start_stopwatch);
        btn_reset_stopwatch=findViewById(R.id.btn_reset_stopwatch);
        btn_pause_stopwatch=findViewById(R.id.btn_pause_stopwatch);
        btn_back=findViewById(R.id.btn_back);

        btn_start_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning){
                    chronometer.setBase(SystemClock.elapsedRealtime()-passOffset);//timer always start form 0
                    chronometer.start();
                    isRunning=true;
                }
            }
        });
        btn_pause_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning){
                    chronometer.stop();
                    passOffset=SystemClock.elapsedRealtime()-chronometer.getBase();
                    isRunning=false;

                }
            }
        });
        btn_reset_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              chronometer.setBase(SystemClock.elapsedRealtime());
             chronometer.stop();
              pauseOffset=0;
              isRunning=false;
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StopWatch.this,MainActivity.class);
                startActivity(intent);
                if(isRunning){
                    chronometer.stop();
                    passOffset=SystemClock.elapsedRealtime()-chronometer.getBase();
                    isRunning=false;

                }

            }
        });

    }
}