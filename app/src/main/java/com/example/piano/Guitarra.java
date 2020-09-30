package com.example.piano;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Guitarra extends Activity
{

    private Button[] botonNota = new Button[6];
    private int[] botonesId = {R.id.M,R.id.Si,R.id.S,R.id.R,R.id.L,R.id.M2};
    private int[] guitarra = {R.raw.guitarram, R.raw.guitarrasi, R.raw.guitarras, R.raw.guitarrar,R.raw.guitarral,R.raw.guitarram};
    private AudioManager audio;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guitarra);

        Volumen();
        Sonar();
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



    public void inicializarPlayer(int n)
    {
        MediaPlayer mediaplayer = MediaPlayer.create(this, guitarra[n]);
        mediaplayer.start();
    }

    public void Nota(final int i)
    {
        botonNota[i].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    inicializarPlayer(i);
            }
        });
    }

    public void Sonar()
    {
        for (int i = 0; i < 6; i++)
        {
            botonNota[i] = findViewById(botonesId[i]);
        }

        Nota(0);
        Nota(1);
        Nota(2);
        Nota(3);
        Nota(4);
        Nota(5);

    }
}