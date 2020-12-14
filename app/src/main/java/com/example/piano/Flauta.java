package com.example.piano;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.LinkedList;

public class Flauta extends Activity
{
    private ImageButton[] botonNota = new ImageButton[7];
    private int[] botonesId = {R.id.D,R.id.R,R.id.M,R.id.F,R.id.S,R.id.L,R.id.Si};
    private int[] flauta = {R.raw.flautad, R.raw.flautar, R.raw.flautam, R.raw.flautaf,R.raw.flautas,R.raw.flautal,R.raw.flautasi};
    private AudioManager audio;
    private boolean estadoGrabacion = false, estadoReproduccion = false, mostrar = false;

    LinkedList z = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flauta);

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

    public void inicializarPlayer(int n)
    {
        MediaPlayer mediaplayer = MediaPlayer.create(this, flauta[n]);
        mediaplayer.start();
        if(estadoGrabacion)
            z.add(mediaplayer);
    }

    public void Sonar()
    {
        for (int i = 0; i < 7; i++)
        {
            botonNota[i] = findViewById(botonesId[i]);
        }

        botonNota[0].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (view.getId() == R.id.D)
                {
                    inicializarPlayer(0);
                }
            }
        });
        botonNota[1].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.R)
                {
                    inicializarPlayer(1);
                }
            }
        });
        botonNota[2].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.M)
                {
                    inicializarPlayer(2);
                }
            }
        });
        botonNota[3].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.F)
                {
                    inicializarPlayer(3);
                }
            }
        });
        botonNota[4].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.S)
                {
                    inicializarPlayer(4);
                }
            }
        });
        botonNota[5].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.L)
                {
                    inicializarPlayer(5);
                }
            }
        });
        botonNota[6].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.Si)
                {
                    inicializarPlayer(6);
                }
            }
        });

    }
}
