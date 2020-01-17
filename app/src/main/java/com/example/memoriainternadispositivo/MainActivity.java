package com.example.memoriainternadispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText nombreFichero_et;
    EditText texto_et;
    TextView textoMostrar_tv;
    Switch switchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreFichero_et = findViewById(R.id.editText);
        texto_et = findViewById(R.id.editText2);
        textoMostrar_tv = findViewById(R.id.txt_mostrar);
        switchBtn = findViewById(R.id.switch_btn);

    }

    public void insertar(View view) { //ESCRIBIR
        if (!nombreFichero_et.getText().toString().matches("")) {
            String nombreFichero = nombreFichero_et.getText().toString();
            OutputStreamWriter fichero = null;
            try {
                //escribir
                //Distinguir entre interna y externa
                if (switchBtn.isChecked()) { //externa
                    File file = new File(getExternalFilesDir(null), nombreFichero);
                    fichero = new OutputStreamWriter(new FileOutputStream(file));
                } else { // interna
                    fichero = new OutputStreamWriter(openFileOutput(nombreFichero, this.MODE_APPEND)); //Append para escribir
                }

                if (!texto_et.getText().toString().matches("")) {
                    String textoInsertar = texto_et.getText().toString();
                    try {
                        fichero.write(textoInsertar);
                        fichero.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Introduce un texto a grabar en el fichero", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No se encuentra el fichero insertado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrar(View view) { //LEER
        if (!nombreFichero_et.getText().toString().matches("")) {
            String nombreFichero = nombreFichero_et.getText().toString();
            BufferedReader fichero = null;
            try {
                //Distinguir entre interna y externa
                if (switchBtn.isChecked()) { //externa
                    File file = new File(getExternalFilesDir(null), nombreFichero);
                    fichero = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                } else { // interna
                    fichero = new BufferedReader(new InputStreamReader(openFileInput(nombreFichero)));
                }

                String lectura = fichero.readLine();
                //Lee su contenido y lo mete en un StringBuilder
                StringBuilder sb = new StringBuilder();
                while (lectura != null) {
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
            Toast.makeText(this, "No se encuentra el fichero insertado.", Toast.LENGTH_SHORT).show();
        }
    }


    public void cambiarDireccion(View view) {

        //TODO arregalr esta parte, si el switch está on --> existe un SD Card
        switchBtn = findViewById(R.id.switch_btn);
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            switchBtn.setActivated(false);
            Toast.makeText(this, "Escribiendo en SD...", Toast.LENGTH_SHORT).show();
        } else {
            switchBtn.setActivated(true);
            Toast.makeText(this, "No hay ninguna SD montada.", Toast.LENGTH_SHORT).show();
        }
        if (switchBtn.isChecked() == false) {
            //esta montado o no?
            switchBtn.setText("Interna");
        } else {
            switchBtn.setText("Externa");
        }

    }



    public void removeFile(View view) {
        String nombreFichero = nombreFichero_et.getText().toString();
        if (!nombreFichero.matches("") || !nombreFichero.matches(null)) {

            File file = null;
            if (switchBtn.isChecked()) { // Externo
                file = new File(getExternalFilesDir(null), nombreFichero);
            } else { // Interno
                file = new File("/data/data/com.example.memoriainternadispositivo/files/", nombreFichero);
            }

            //Buscar file ; internal storage
            if (file.exists()) {
                file.delete();
            } else {
                Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
