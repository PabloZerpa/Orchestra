package com.example.piano;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    ImageButton botonPiano,botonGuitarra,botonBateria,botonFlauta;

    ObjectAnimator animatorX, animatorAlpha, animatorRotation;
    AnimatorSet animatorSet;

    private static long dura = 2000;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        AnimacionPrueba(botonPiano, 50f);
        AnimacionPrueba(botonGuitarra, 450f);
        AnimacionPrueba(botonBateria, 50f);
        AnimacionPrueba(botonFlauta, 450f);

    }

    public void AnimacionPrueba(ImageButton boton, float desplazamiento)
    {
        animatorX = ObjectAnimator.ofFloat(boton,"x",desplazamiento);
        animatorX.setDuration(dura);
        animatorAlpha = ObjectAnimator.ofFloat(boton,View.ALPHA,0.0f, 1.0f);
        animatorAlpha.setDuration(dura);
        animatorRotation = ObjectAnimator.ofFloat(boton,"rotation",0f, 360f);
        animatorRotation.setDuration(dura);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorAlpha,animatorRotation);
        animatorSet.start();
    }
}