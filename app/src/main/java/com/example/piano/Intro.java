package com.example.piano;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Intro extends Activity
{

    ImageView logo;
    TextView nombre;
    ObjectAnimator animatorAlpha, animatorY, animatorRotation;
    AnimatorSet animatorSet;
    long duracion = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        logo = findViewById(R.id.logo);
        nombre = findViewById(R.id.nombre);
        nombre.setVisibility(View.INVISIBLE);

        Animacion(logo, 300f);

        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                nombre.setVisibility(View.VISIBLE);
                Animacion(nombre);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                finish();
            }
        }, 4000);
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void Animacion(ImageView logo, float desplazamiento)
    {
        animatorY = ObjectAnimator.ofFloat(logo,"y",desplazamiento).setDuration(duracion);
        animatorAlpha = ObjectAnimator.ofFloat(logo, View.ALPHA,0.0f, 1.0f).setDuration(duracion);
        animatorRotation = ObjectAnimator.ofFloat(logo, "rotation",0,360).setDuration(duracion);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,animatorAlpha,animatorRotation);
        animatorSet.start();
    }

    public void Animacion(TextView nombre)
    {
        animatorAlpha = ObjectAnimator.ofFloat(nombre, View.ALPHA,0.0f, 1.0f);
        animatorAlpha.setDuration(duracion-1000);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorAlpha);
        animatorSet.start();
    }
}