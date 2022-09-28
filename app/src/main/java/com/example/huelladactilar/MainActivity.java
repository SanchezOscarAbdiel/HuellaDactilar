package com.example.huelladactilar;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int REQUEST_ENABLE_BT;
    float Transparencia = 0.75F;

    ImageView CuadroVerde;
    Button BAprobado;

    //==============ON CREATE==============//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CuadroVerde = (ImageView) findViewById(R.id.ivVerdeAprobado);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        BAprobado = (Button) findViewById(R.id.Bverde);

        BAprobado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animFadeIn.reset();
                CuadroVerde.clearAnimation();
                CuadroVerde.startAnimation(animFadeIn);
                CuadroVerde.setAlpha(Transparencia);
                CuadroVerde.setVisibility(View.VISIBLE);

            }
        });

        PrendeBT();

    }

//==============METODOS==============//
public void PrendeBT(){
    //INSTANCIA EL BLUETOOTH
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null) { /* Device doesn't support Bluetooth*/ }

    //PREGUNTA DE BT ENCENDIDO
    if (!bluetoothAdapter.isEnabled()) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT); //PARECE QUE DA ERROR, PERO NO
    }
}

}