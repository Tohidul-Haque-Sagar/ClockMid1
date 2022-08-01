package com.example.midtesting;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyService extends Service {
    public static final String COUNTDOWN_INTENT = "COUNTDOWN_INTENT";

    int second;
    final Handler handler = new Handler();
    Runnable myRunnable;
    Intent ui = new Intent(COUNTDOWN_INTENT);

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        second = Integer.parseInt(intent.getStringExtra("second"));
        myRunnable = new Runnable() {
            public void run() {
                boolean running = true;
                second--;
                Log.d("SECOND", String.valueOf(second));
                ui.putExtra(countDown.COUNTDOWN_INTENT, String.valueOf(second+1));

                if(second+2 == 0) {
                    running = false;
                    stopSelf();
                }

                if(running) LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ui);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(myRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(myRunnable);
    }
}