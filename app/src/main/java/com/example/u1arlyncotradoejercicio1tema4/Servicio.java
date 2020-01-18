package com.example.u1arlyncotradoejercicio1tema4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Servicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        startService(new Intent(Servicio.this,
                ServicioEspia.class));
        finish();
    }
}
