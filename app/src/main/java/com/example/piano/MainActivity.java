package com.example.piano;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

//Agregar animacion cuerdas
//Guitarra electrica
//Arreglar imagen de los tambores
//Agregarlo a git

public class MainActivity extends AppCompatActivity
{
    ImageButton botonPiano,botonGuitarra,botonBateria,botonFlauta;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        botonPiano = findViewById(R.id.piano);
        botonPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Piano.class);
                startActivityForResult(intent, 0);
            }
        });

        botonGuitarra = findViewById(R.id.guitarra);
        botonGuitarra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Guitarra.class);
                startActivityForResult(intent, 0);
            }
        });
        botonBateria = findViewById(R.id.bateria);
        botonBateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Bateria.class);
                startActivityForResult(intent, 0);
            }
        });
        botonFlauta = findViewById(R.id.flauta);
        botonFlauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Flauta.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void irPiano()
    {
        Intent i = new Intent(this,Piano.class);
        startActivity(i);
    }
    public void irGuitarra()
    {
        Intent i = new Intent(this,Guitarra.class);
        startActivity(i);
    }
    public void irBateria()
    {
        Intent i = new Intent(this,Bateria.class);
        startActivity(i);
    }
    public void irFlauta()
    {
        Intent i = new Intent(this,Flauta.class);
        startActivity(i);
    }
}