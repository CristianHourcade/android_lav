package com.example.lavaderocolores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ReferidosCompartirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referidos_compartir);
    }
    public void Volver(View v){
        finish();
        overridePendingTransition(R.anim.entrada, R.anim.salida);
    }
}
