package com.example.midtesting;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class countDown extends AppCompatActivity {

    TextView editText_countdown, store_second;
    EditText second;
    private Button btn_start,btn_reset,btn_pause,btn_back, stop_bell;
    private MyReceiver mReceiver;
    int broadCast_sec;
    int pause = 0, reset = 0;
    private MediaPlayer bell;
    private boolean bell_ringing = false;

    public static final String COUNTDOWN_INTENT = "COUNTDOWN_INTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);


        editText_countdown = findViewById(R.id.editText_countdown);
        btn_start=findViewById(R.id.btn_start);
        btn_reset=findViewById(R.id.btn_reset);
        btn_back=findViewById(R.id.btn_back);
        btn_pause=findViewById(R.id.btn_pause);
        stop_bell=findViewById(R.id.btn_bell_stop);
        second = findViewById(R.id.editText_second);
        store_second = findViewById(R.id.store_second);

        btn_start.setOnClickListener(v -> {
            mReceiver = new MyReceiver();
            Intent service = new Intent(getApplicationContext(), MyService.class);
            if(serviceRunning()) stopService(new Intent(this, MyService.class));
            if(pause == 1){
                pause = 0;
                service.putExtra("second", store_second.getText().toString());
                startService(service);
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .registerReceiver(mReceiver, new IntentFilter(MyService.COUNTDOWN_INTENT));
            }
            else if(reset == 1){
                reset = 0;
                service.putExtra("second", second.getText().toString());
                startService(service);
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .registerReceiver(mReceiver, new IntentFilter(MyService.COUNTDOWN_INTENT));
            }
            else{
                service.putExtra("second", second.getText().toString());
                startService(service);
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .registerReceiver(mReceiver, new IntentFilter(MyService.COUNTDOWN_INTENT));
            }
        });

        stop_bell.setOnClickListener(v -> {
            if(bell_ringing){
                bell_ringing = false;
                bell.stop();
            }
        });
        btn_pause.setOnClickListener(v -> {
            stopService(new Intent(this, MyService.class));
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(mReceiver);
            pause = 1;
        });

        btn_reset.setOnClickListener(v -> {
            stopService(new Intent(this, MyService.class));
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(mReceiver);
            reset = 1;
            store_second.setText("0");
            editText_countdown.setText("0:0:0");
        });

        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    class MyReceiver extends BroadcastReceiver {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            broadCast_sec = Integer.parseInt(intent.getStringExtra(COUNTDOWN_INTENT));
            store_second.setText(String.valueOf(broadCast_sec));
            if(store_second.getText().toString().equals("0")){
                bell = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                bell.setLooping(true);
                bell_ringing = true;
                bell.start();
            }
            Log.d("SECONDR", String.valueOf(broadCast_sec));
            editText_countdown.setText(broadCast_sec/3600+":"+(broadCast_sec%3600)/60+":"+broadCast_sec%60);
        }
    }

    public boolean serviceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(MyService.class.getName().equals(serviceInfo.service.getClassName())){
                return true;
            }
        }
        return false;
    }

}