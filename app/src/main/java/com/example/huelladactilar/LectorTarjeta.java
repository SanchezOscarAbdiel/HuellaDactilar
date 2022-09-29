package com.example.huelladactilar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class LectorTarjeta extends AppCompatActivity {

    int REQUEST_ENABLE_BT;
    float Transparencia = 0.75F;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ImageView CuadroVerde;
    Button BAprobado;

    //==============ON CREATE==============//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lector_tarjeta);

        //INICIALIZA VISUALES
            CuadroVerde = (ImageView) findViewById(R.id.ivVerdeAprobado);
            BAprobado = (Button) findViewById(R.id.Bverde);

        //LLAMADA A METODOS
            PrendeBT();

    }

    //==============METODOS==============//
    public void PrendeBT() {
        //INSTANCIA EL BLUETOOTH
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices()); //Encuentra las direcciones mac de los dispositivos
        if (btAdapter == null) { /* Device doesn't support Bluetooth*/ }

        //PREGUNTA DE BT ENCENDIDO
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT); //PARECE QUE DA ERROR, PERO NO
        }

        BluetoothDevice hc05 = btAdapter.getRemoteDevice("DIRECCION MAC");
        System.out.println(hc05.getName());

        //SOCKET PARA LA COMUNICACION
        BluetoothSocket btSocket = null;

        int cont = 0;
        do {
            try {
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                //Conexion a la placa "Servidor"
                btSocket.connect();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }

            cont++;
        }while (!btSocket.isConnected() && cont <3);

        //==============MANEJO DE DATOS
        try { //Enviar datos
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(48);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try { //Recibir datos
            InputStream inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());

            for (int i = 0; i < 26; i++){
                byte b = (byte) inputStream.read();
                System.out.println((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



            //Cerrar conexion
            try {
                btSocket.close();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }




    }

//------------------------------------
    public void BotonAprobado(View view){
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein); //CARGA EL TIPO DE ANIMACION
        //PROCESO DE ANIMACION
        animFadeIn.reset();
        CuadroVerde.clearAnimation();
        CuadroVerde.startAnimation(animFadeIn);
        //AL TERMINAR LA ANIMACION, SE HACE VISIBLE EL CUADRO VERDE
        CuadroVerde.setAlpha(Transparencia);
        CuadroVerde.setVisibility(View.VISIBLE);
    }

}