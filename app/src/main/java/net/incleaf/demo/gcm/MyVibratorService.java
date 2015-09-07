package net.incleaf.demo.gcm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

public class MyVibratorService extends Service {
    Vibrator mVib;

    public MyVibratorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mVib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mVib.vibrate(new long[] {10, 20000}, 0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mVib.cancel();
        super.onDestroy();
    }
}
