package com.maniakapps.antar.firebases;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GenerarArchivoActivity extends AppCompatActivity {
    Button EscribirMemoriaInterna, LeerMemoriaInterna, EscribirMemoriaSD, LeerMemoriaSD, LeerApp;
    TextView Mensaje;
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EscribirMemoriaInterna = findViewById(R.id.btnEscribirMemoriaInterna);
        LeerMemoriaInterna = findViewById(R.id.btnLeerMemoriaInterna);
        EscribirMemoriaSD = findViewById(R.id.btnEscribirSD);
        LeerApp = findViewById(R.id.btnLeerApp);
        LeerMemoriaSD = findViewById(R.id.btnLeerSD);

        Mensaje = findViewById(R.id.tvMensaje);



        // Bloque de código que me comprueba si existe SD y si puedo escribir o no
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED))
        {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else
        {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }




        LeerMemoriaInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            openFileInput("meminterna.txt")));

                    String texto = reader.readLine();
                    Mensaje.setText(texto);
                    reader.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde la memoria interna");
                }


            }
        });




        EscribirMemoriaInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    OutputStreamWriter writer=
                            new OutputStreamWriter(
                                    openFileOutput("meminterna.txt", Context.MODE_PRIVATE));

                    writer.write(contenido());
                    writer.close();
                    setear();
                }catch (Exception ex){
                    Log.e("Error", "Error al escribir en el archivo de la memoria interna");
                }

            }
        });

        LeerApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {

                    InputStream reader =
                            getResources().openRawResource(R.raw.fichero);

                    BufferedReader brin =
                            new BufferedReader(new InputStreamReader(reader));

                    String linea = brin.readLine();
                    Mensaje.setText(linea);

                    reader.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde recurso raw");
                }

            }
        });



        LeerMemoriaSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        EscribirMemoriaSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sdDisponible) {

                    try {
                        File ruta_sd = Environment.getExternalStorageDirectory();

                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd.txt");

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                new FileInputStream(f)));

                        String texto = reader.readLine();
                        Mensaje.setText(texto);
                        reader.close();
                    } catch (Exception ex) {
                        Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                    }

                }
            }
        });


        LeerMemoriaSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdAccesoEscritura && sdDisponible){
                    try
                    {
                        File ruta_sd = Environment.getExternalStorageDirectory();

                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd.txt");

                        OutputStreamWriter writer =
                                new OutputStreamWriter(
                                        new FileOutputStream(f));

                        writer.write("Contenido del fichero de la SD");
                        writer.close();
                    }
                    catch (Exception ex)
                    {
                        Log.e("Ficheros", "Error al escribir fichero en la tarjeta SD");
                    }
                }
            }
        });


    }
    public String contenido(){
        SharedPreferences pref =getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String contenido = pref.getString("users", null);
        return contenido;
    }
    public void setear(){
        SharedPreferences sharedPref = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("saldo", "0");
        editor.apply();
        editor.commit();
    }
}
