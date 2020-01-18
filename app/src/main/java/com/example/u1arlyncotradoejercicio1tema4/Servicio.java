package com.example.u1arlyncotradoejercicio1tema4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Servicio extends Activity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //enciende el celular aunque este en pantalla de bloqueo
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_servicio);




        if ((ContextCompat.checkSelfPermission(Servicio.this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED)){
            arranque();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            if (ContextCompat.checkSelfPermission(Servicio.this,
                                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                                permisolocalizacion();
                            } else {
                                solicitarPermiso(Manifest.permission.CALL_PHONE,
                                        "Sin el permiso" + " de estado de telefono", 0);
                            }
                        }
                    }, 2000L);
        }
    }


    void permisolocalizacion(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            arranque();
        } else {
            solicitarPermiso(Manifest.permission.READ_CALL_LOG,
                    "Sin el permiso" + " de registro de llamadas", 1);
        }
    }


    public void solicitarPermiso(final String permiso, String justificacion, final int codigo) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permiso)) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Solicitud de permiso");
            dialogo1.setMessage(justificacion);
            dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    ActivityCompat.requestPermissions(Servicio.this, new String[]{permiso}, codigo);
                }
            });
            dialogo1.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permiso}, codigo);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisolocalizacion();
            } else {
                solicitarPermiso(Manifest.permission.READ_PHONE_STATE,
                        "Sin el permiso" + " de telefono no podemos realizar llamadas.", 0);
            }
        }
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                arranque();
            } else {
                solicitarPermiso(Manifest.permission.READ_CALL_LOG,
                        "Sin el permiso" + " de ubicacion no podremos localizarte", 1);
            }
        }
    }
    void arranque() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
    }
}
