package com.example.memoriainternadispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText nombreFichero_et;
    EditText texto_et;
    TextView textoMostrar_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreFichero_et = findViewById(R.id.editText);
        texto_et = findViewById(R.id.editText2);
        textoMostrar_tv = findViewById(R.id.txt_mostrar);


    }


    public void insertar(View view) {
        if (!nombreFichero_et.getText().toString().matches("")) {
            String nombreFichero = nombreFichero_et.getText().toString();
            OutputStreamWriter fichero = null;
            try {
                //Append para escribir
                fichero = new OutputStreamWriter(openFileOutput(nombreFichero, this.MODE_APPEND));
                if (!texto_et.getText().toString().matches("")) {
                    String textoInsertar = texto_et.getText().toString();
                    try {
                        fichero.write(textoInsertar);
                        fichero.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Introduce un texto a grabar en el fichero", Toast.LENGTH_SHORT);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(this, "No se encuentra el fichero insertado.", Toast.LENGTH_SHORT);
        }
    }

    public void mostrar(View view) {
        if (!nombreFichero_et.getText().toString().matches("")) {
            String nombreFichero = nombreFichero_et.getText().toString();
            BufferedReader fichero = null;
            try {
                fichero = new BufferedReader(new InputStreamReader(openFileInput(nombreFichero)));
                String lectura = fichero.readLine();

                //Le su contenido y lo mete en un StringBuilder
                StringBuilder sb = new StringBuilder();
                while(lectura != null)
                {
                    sb.append(lectura + "\n");
                    lectura = fichero.readLine();
                }

                //muestra el stringBuilder
                textoMostrar_tv.setText(sb);
                //Cierrra el flujo/fichero
                fichero.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No se encuentra el fichero insertado.", Toast.LENGTH_SHORT);
        }


    }
}
