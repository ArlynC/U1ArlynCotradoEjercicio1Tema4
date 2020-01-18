package com.example.u1arlyncotradoejercicio1tema4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class ServicioEspia extends Service {
    @Override
    public int onStartCommand(Intent i, int flags, int idArranque) {
       // SystemClock.sleep(25000);

        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}