package com.example.u1arlyncotradoejercicio1tema4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ReceptorSMS extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "1000";
    public static final String NOTIFICATION_CHANNEL_NAME = "UNJBG";

    @Override public void onReceive(Context context, Intent intent) {


        Intent i = new Intent(context, Servicio.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);  //context desde la aplicacion donde yo este

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int j = 0; j < pdusObj.length; j++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[j]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("receptorsms", "senderNum: "+ senderNum + "; message: " + message);
                    Toast.makeText(context,"mensaje: "+message,Toast.LENGTH_LONG).show();

                    NotificationCompat.Builder mensaje = new
                            NotificationCompat.Builder(context)
                            .setContentTitle(Html.fromHtml("<b>Mensajes</b>"))
                            .setContentText(message)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(PendingIntent.getActivity(context, 0,
                                    new Intent(context, Servicio.class), 0));
                    NotificationManager notificationManager2 = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel =
                                new NotificationChannel(
                                        NOTIFICATION_CHANNEL_ID,
                                        NOTIFICATION_CHANNEL_NAME,
                                        NotificationManager.IMPORTANCE_LOW);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(R.color.colorAccent);
                        notificationManager2.createNotificationChannel(notificationChannel);
                        mensaje.setChannelId(NOTIFICATION_CHANNEL_ID);
                    }
                    notificationManager2.notify(2, mensaje.build());
                }


            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }


    }
}