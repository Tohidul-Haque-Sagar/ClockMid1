package com.example.midtesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bstart_timer,bcount_down;//btn_exit;
    TextView text_view1,text_view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bstart_timer=findViewById(R.id.bstart_timer);
        bcount_down=findViewById(R.id.bcount_down);
       // btn_exit=findViewById(R.id.btn_exit);

        bstart_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StopWatch.class);
                startActivity(intent);
            }
        });
        bcount_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,countDown.class);
                startActivity(intent);
            }
        });


    }
}