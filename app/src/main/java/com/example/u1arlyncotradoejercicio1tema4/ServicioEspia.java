package com.example.u1arlyncotradoejercicio1tema4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import static com.example.u1arlyncotradoejercicio1tema4.ReceptorLlamada.NOTIFICATION_CHANNEL_ID;

public class ServicioEspia extends Service {


    private static final int ID_NOTIFICACION_CREAR = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "1000";
    public static final String NOTIFICATION_CHANNEL_NAME = "UNJBG";


    @Override //notificacion
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        Intent intencionLlamar = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:555123456"));
        PendingIntent intencionPendienteLlamar =
                PendingIntent.getActivity(this,0, intencionLlamar,0);

        NotificationCompat.Builder notific = new NotificationCompat.Builder(this);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //si es mayor a android oreo=8
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            NOTIFICATION_CHANNEL_ID,
                            NOTIFICATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_LOW);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorAccent);
            notificationManager.createNotificationChannel(notificationChannel);
            notific.setChannelId(NOTIFICATION_CHANNEL_ID);

        }
        return START_STICKY;
    }
    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}
