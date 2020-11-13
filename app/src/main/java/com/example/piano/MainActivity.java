package com.example.piano;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

//Añadir pantalla con nivel de volumen y nota
//Mejorar la reproduccion de las notas grabadas

//Mejorar la UI (agregar botones personalizados)
//Mejorar presentacion (ejecutar finish despues de unos segundos)

public class MainActivity extends AppCompatActivity
{
    ImageButton botonPiano,botonGuitarra,botonBateria,botonFlauta;

    ObjectAnimator animatorAlpha, animatorY;
    AnimatorSet animatorSet;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intro = new Intent(getApplicationContext(), Intro.class);
        startActivity(intro);

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

        Animacion(botonPiano, 40f);
        Animacion(botonGuitarra, 40f);
        Animacion(botonBateria, -10f);
        Animacion(botonFlauta, -10f);

    }

    public void Animacion(ImageButton boton, float desplazamiento)
    {
        animatorY = ObjectAnimator.ofFloat(boton,"y",desplazamiento);
        long dura = 2000;
        animatorY.setDuration(dura);
        animatorAlpha = ObjectAnimator.ofFloat(boton,View.ALPHA,0.0f, 1.0f);
        animatorAlpha.setDuration(dura);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,animatorAlpha);
        animatorSet.start();
    }
}