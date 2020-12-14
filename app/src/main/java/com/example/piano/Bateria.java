package com.example.piano;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.LinkedList;

public class Bateria extends Activity {

    private ImageButton[] botonNota = new ImageButton[7];
    private int[] botonesId = {R.id.D, R.id.R, R.id.M, R.id.F, R.id.S, R.id.L, R.id.Si};
    private int[] bateria = {R.raw.bateriad, R.raw.bateriar, R.raw.bateriam, R.raw.bateriaf,
           R.raw.baterias, R.raw.baterial, R.raw.bateriasi};
    private int[] imagenNotas = {R.drawable.tamboruno,R.drawable.tambordos,R.drawable.tambortres, R.drawable.tamborcuatro,
                    R.drawable.tamborcinco,R.drawable.tamborseis};
    private int[] imagenPNotas = {R.drawable.tamborunopres,R.drawable.tambordospres,R.drawable.tambortrespres, R.drawable.tamborcuatropres1,R.drawable.tamborcuatropres2,
                    R.drawable.tamborcincopres,R.drawable.tamborseispres};
    private AudioManager audio;
    private boolean estadoGrabacion = false, estadoReproduccion = false, mostrar = false;

    LinkedList z = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bateria);

        Volumen();
        Sonar();
        Grabar();
        Detener();
        Bucle();
    }

    public void Volumen()
    {
        try
        {
            SeekBar botonVolumen = findViewById(R.id.volumen);
            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            botonVolumen.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            botonVolumen.setProgress(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
            botonVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b)
                {
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Presion(int i)
    {
        if (i == 0)
            botonNota[0].setImageResource(imagenPNotas[i]);
        else if (i == 1)
            botonNota[1].setImageResource(imagenPNotas[i]);
        else if (i == 2)
            botonNota[2].setImageResource(imagenPNotas[i]);
        else if (i == 3)
            botonNota[3].setImageResource(imagenPNotas[5]);
        else if (i == 4)
            botonNota[4].setImageResource(imagenPNotas[3]);
        else if (i == 5)
            botonNota[5].setImageResource(imagenPNotas[4]);
        else if (i == 6)
            botonNota[6].setImageResource(imagenPNotas[6]);
    }
    public void Soltar(int i)
    {
        if (i == 0)
            botonNota[0].setImageResource(imagenNotas[i]);
        else if (i == 1)
            botonNota[1].setImageResource(imagenNotas[i]);
        else if (i == 2)
            botonNota[2].setImageResource(imagenNotas[i]);
        else if (i == 3)
            botonNota[3].setImageResource(imagenNotas[4]);
        else if (i == 4)
            botonNota[4].setImageResource(imagenNotas[3]);
        else if (i == 5)
            botonNota[5].setImageResource(imagenNotas[3]);
        else if (i == 6)
            botonNota[6].setImageResource(imagenNotas[5]);
    }

    public void Grabar()
    {
        Button rojo = findViewById(R.id.grabar);
        rojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                estadoGrabacion = !estadoGrabacion;

                if (estadoGrabacion)
                {
                    z.clear();
                    Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "La grabacion finalizo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Detener()
    {
        Button azul = findViewById(R.id.pausar);
        azul.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (estadoGrabacion)
                {
                    estadoGrabacion = false;
                    Toast.makeText(getApplicationContext(), "La grabacion finalizo", Toast.LENGTH_LONG).show();
                }

                if (estadoReproduccion)
                {
                    Toast.makeText(getApplicationContext(), "Se detuvo el audio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Bucle()
    {
        Button verde = findViewById(R.id.bucle);
        verde.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                estadoReproduccion = !estadoReproduccion;

                Reproducir();
            }
        });
    }

    public void Reproducir()
    {
        if(estadoReproduccion)
        {
            Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_LONG).show();

            for (int i = 0; i < z.size(); i++)
            {
                MediaPlayer mediaplayer = (MediaPlayer) z.get(i);
                final int x = i;

                if(i==0)
                    mediaplayer.start();

                if(i == z.size()-1)
                {
                    mediaplayer.start();
                    break;
                }

                mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        mp = (MediaPlayer) z.get(x + 1);
                        mp.start();
                    }
                });
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se detuvo el audio", Toast.LENGTH_LONG).show();
        }
    }

    public void inicializarPlayer(int n) {
        MediaPlayer mediaplayer = MediaPlayer.create(this, bateria[n]);
        mediaplayer.start();
        if(estadoGrabacion)
            z.add(mediaplayer);
    }

    public void Nota(final int i)
    {
        botonNota[i].setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //boton presionado
                    Presion(i);
                    inicializarPlayer(i);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    //boton liberado
                    Soltar(i);
                }
                return true;
            }
        });
    }

    public void Sonar()
    {
        for (int i = 0; i < 7; i++)
        {
            botonNota[i] = findViewById(botonesId[i]);
        }

        Nota(0);
        Nota(1);
        Nota(2);
        Nota(3);
        Nota(4);
        Nota(5);
        Nota(6);
    }
}
