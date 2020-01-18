package com.example.u1arlyncotradoejercicio1tema4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
import android.content.pm.PackageManager;
import androidx.core.app.NotificationCompat;

import java.net.URLEncoder;

public class ReceptorLlamada extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "1000";
    public static final String NOTIFICATION_CHANNEL_NAME = "UNJBG";

        //on receive se ejecuta apenas llamen
        @Override
        public void onReceive (Context context, Intent intent){

// Sacamos información de la intención
            String estado = "", numero = "";
            Bundle extras = intent.getExtras();



            if (extras != null) {
                estado = extras.getString(TelephonyManager.EXTRA_STATE);
                if (estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    numero = extras.getString(
                            TelephonyManager.EXTRA_INCOMING_NUMBER);
                    String info = "Última llamada recibida de:" + " " + numero;
                    Log.d("ReceptorAnuncio", info + " intent=" + intent);

                    Intent intencionLlamar = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+numero));
                    PendingIntent intencionPendienteLlamar =
                            PendingIntent.getActivity(context,0, intencionLlamar,0);

                    PackageManager packageManager = context.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    try {
                        String url = "https://api.whatsapp.com/send?phone=" + "51"+numero + "&text="
                                + URLEncoder.encode("Buen día, tenia una duda del curso ... ", "UTF-8");
                        i.setPackage("com.whatsapp");
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(packageManager) != null) {
                            context.startActivity(i);
                        }
                        else {
                            Toast.makeText(context, "No tiene Whatsapp porfavor instale la app"
                                    , Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PendingIntent iwhats =
                            PendingIntent.getActivity(context,0, i,0);
// Creamos Notificación
                    NotificationCompat.Builder notificacion = new
                            NotificationCompat.Builder(context)
                            .setContentTitle(Html.fromHtml("<b>Información de llamadas</b>"))
                            .setContentText(info)
                            .addAction(android.R.drawable.ic_menu_call, "llamar", intencionPendienteLlamar)
                            .addAction(android.R.drawable.ic_menu_send, "whatsapp", iwhats)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(PendingIntent.getActivity(context, 0,
                                    new Intent(context, Servicio.class), 0));
                    NotificationManager notificationManager = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel =
                                new NotificationChannel(
                                        NOTIFICATION_CHANNEL_ID,
                                        NOTIFICATION_CHANNEL_NAME,
                                        NotificationManager.IMPORTANCE_LOW);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(R.color.colorAccent);
                        notificationManager.createNotificationChannel(notificationChannel);
                        notificacion.setChannelId(NOTIFICATION_CHANNEL_ID);
                    }
                    notificationManager.notify(1, notificacion.build());


                }
            }
        }
    }