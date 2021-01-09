package com.example.piano;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.LinkedList;

public class Piano extends Activity
{
    private ImageButton[] botonNota = new ImageButton[12];
    private ImageButton[] botonNota2 = new ImageButton[12];
    private ImageButton[] botonNota3 = new ImageButton[12];

    private int[] botonesId = {R.id.D,R.id.Ds,R.id.R,R.id.Rs,R.id.M,R.id.F,R.id.Fs,R.id.S,R.id.Ss,R.id.L,R.id.Ls,R.id.Si};
    private int[] botonesId2 = {R.id.D2,R.id.Ds2,R.id.R2,R.id.Rs2,R.id.M2,R.id.F2,R.id.Fs2,R.id.S2,R.id.Ss2,R.id.L2,R.id.Ls2,R.id.Si2};
    private int[] botonesId3 = {R.id.D3,R.id.Ds3,R.id.R3,R.id.Rs3,R.id.M3,R.id.F3,R.id.Fs3,R.id.S3,R.id.Ss3,R.id.L3,R.id.Ls3,R.id.Si3};

    private int[] piano = {R.raw.pianodo, R.raw.pianodos, R.raw.pianore, R.raw.pianores,R.raw.pianomi, R.raw.pianofa,
            R.raw.pianofas, R.raw.pianoso,R.raw.pianosos, R.raw.pianola, R.raw.pianolas, R.raw.pianossi};
    private int[] guitarra = {R.raw.guitarrad, R.raw.guitarrads, R.raw.guitarrar, R.raw.guitarrars,R.raw.guitarram, R.raw.guitarraf,
            R.raw.guitarrafs, R.raw.guitarras,R.raw.guitarrass, R.raw.guitarral, R.raw.guitarrals, R.raw.guitarrasi};
    private int[] bateria = {R.raw.bateriad, R.raw.bateriads, R.raw.bateriar, R.raw.baterias,R.raw.bateriam, R.raw.bateriaf,
            R.raw.bateriafs, R.raw.baterias,R.raw.bateriass, R.raw.baterial, R.raw.baterials, R.raw.bateriasi};
    private int[] flauta = {R.raw.flautad, R.raw.flautads, R.raw.flautar, R.raw.flautars,R.raw.flautam, R.raw.flautaf,
            R.raw.flautafs, R.raw.flautas,R.raw.flautass, R.raw.flautal, R.raw.flautals, R.raw.flautasi};
    private int[] trompeta = {R.raw.trompetad, R.raw.trompetas, R.raw.trompetar, R.raw.trompetars,R.raw.trompetam, R.raw.trompetaf,
            R.raw.trompetafs, R.raw.trompetas,R.raw.trompetass, R.raw.trompetal, R.raw.trompetals, R.raw.trompetasi};

    private AudioManager audio;
    private String instrumentoElegido = "Piano";
    private String[] valores = {"Piano","Guitarra","Bateria","Flauta","Trompeta"};
    private boolean estadoGrabacion = false, estadoReproduccion = false, mostrar = false, x = false;
    private TextView pantalla;
    MediaPlayer enReproduccion;
    LinkedList z = new LinkedList();

    //--------------------Imagenes con las notas------------------------------------
    private int[] imagenConNotas = {R.drawable.ndo,R.drawable.ndos,R.drawable.nre,R.drawable.nres,R.drawable.nmi,R.drawable.nfa,R.drawable.nfas,R.drawable.nsol,
                                    R.drawable.nss,R.drawable.nla,R.drawable.nlas,R.drawable.nsi};
    //--------------------Imagenes presionadas con las notas------------------------------------
    private int[] imagenPConNotas = {R.drawable.ndop,R.drawable.ndos,R.drawable.nrep,R.drawable.nres,R.drawable.nmip,R.drawable.nfap,R.drawable.nfas,R.drawable.nsolp,
            R.drawable.nss,R.drawable.nlap,R.drawable.nlas,R.drawable.nsip};
    //--------------------Imagenes sin las notas------------------------------------
    private int[] imagenSinNotas = {R.drawable.ni,R.drawable.na,R.drawable.nm,R.drawable.na,R.drawable.nd,R.drawable.ni,R.drawable.na,R.drawable.nm,R.drawable.na,
                                    R.drawable.nm,R.drawable.na,R.drawable.nd};
    //--------------------Imagenes presionadas sin las notas------------------------------------
    private int[] imagenPSinNotas = {R.drawable.nip,R.drawable.nap,R.drawable.nmp,R.drawable.nap,R.drawable.ndp,R.drawable.nip,R.drawable.nap,R.drawable.nmp,R.drawable.nap,
                                     R.drawable.nmp,R.drawable.nap,R.drawable.ndp};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piano);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat .requestPermissions(Piano.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        instrumento();
        volumen();
        sonar();
        mostrar();
        grabar();
        detener();
        bucle();
    }

    //-----------------------------Funcion para variar el volumen--------------------------------
    public void volumen()
    {
        pantalla = findViewById(R.id.pantalla);
        try
        {
            final SeekBar botonVolumen = findViewById(R.id.volumen);
            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            botonVolumen.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            botonVolumen.setProgress(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
            botonVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    pantalla.setText("" + progress);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        final SeekBar botonVolumen = findViewById(R.id.volumen);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            int index = botonVolumen.getProgress();
            botonVolumen.setProgress(index + 1);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            int index = botonVolumen.getProgress();
            botonVolumen.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //-----------------------------Funcion para elegir instrumento------------------------------------
    public void instrumento()
    {
        pantalla = findViewById(R.id.pantalla);
        Spinner instrumento = findViewById(R.id.instrumentos);
        instrumento.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valores));
        instrumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                pantalla.setText((String) adapterView.getItemAtPosition(position));
                instrumentoElegido = (String) adapterView.getItemAtPosition(position);
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //----------------------------------Funcion para cambiar color a la tecla presionada-----------------------------
    public void presion(int i, int n)
    {   // !mostrar == false
        if (!mostrar)
        {
            if(n == 1)
            {
                if (i == 0)
                    botonNota[0].setImageResource(imagenPSinNotas[i]);
                else if (i == 2)
                    botonNota[2].setImageResource(imagenPSinNotas[i]);
                else if (i == 4)
                    botonNota[4].setImageResource(imagenPSinNotas[i]);
                else if (i == 5)
                    botonNota[5].setImageResource(imagenPSinNotas[i]);
                else if (i == 7)
                    botonNota[7].setImageResource(imagenPSinNotas[i]);
                else if (i == 9)
                    botonNota[9].setImageResource(imagenPSinNotas[i]);
                else if (i == 11)
                    botonNota[11].setImageResource(imagenPSinNotas[i]);
            }
            else if(n == 2)
            {
                if (i == 0)
                    botonNota2[0].setImageResource(imagenPSinNotas[i]);
                else if (i == 2)
                    botonNota2[2].setImageResource(imagenPSinNotas[i]);
                else if (i == 4)
                    botonNota2[4].setImageResource(imagenPSinNotas[i]);
                else if (i == 5)
                    botonNota2[5].setImageResource(imagenPSinNotas[i]);
                else if (i == 7)
                    botonNota2[7].setImageResource(imagenPSinNotas[i]);
                else if (i == 9)
                    botonNota2[9].setImageResource(imagenPSinNotas[i]);
                else if (i == 11)
                    botonNota2[11].setImageResource(imagenPSinNotas[i]);
            }
            else if(n == 3)
            {
                if (i == 0)
                    botonNota3[0].setImageResource(imagenPSinNotas[i]);
                else if (i == 2)
                    botonNota3[2].setImageResource(imagenPSinNotas[i]);
                else if (i == 4)
                    botonNota3[4].setImageResource(imagenPSinNotas[i]);
                else if (i == 5)
                    botonNota3[5].setImageResource(imagenPSinNotas[i]);
                else if (i == 7)
                    botonNota3[7].setImageResource(imagenPSinNotas[i]);
                else if (i == 9)
                    botonNota3[9].setImageResource(imagenPSinNotas[i]);
                else if (i == 11)
                    botonNota3[11].setImageResource(imagenPSinNotas[i]);
            }
        }
        else {
            if(n == 1)
            {
                if (i == 0)
                    botonNota[0].setImageResource(imagenPConNotas[i]);
                else if (i == 2)
                    botonNota[2].setImageResource(imagenPConNotas[i]);
                else if (i == 4)
                    botonNota[4].setImageResource(imagenPConNotas[i]);
                else if (i == 5)
                    botonNota[5].setImageResource(imagenPConNotas[i]);
                else if (i == 7)
                    botonNota[7].setImageResource(imagenPConNotas[i]);
                else if (i == 9)
                    botonNota[9].setImageResource(imagenPConNotas[i]);
                else if (i == 11)
                    botonNota[11].setImageResource(imagenPConNotas[i]);
            }
            else if(n == 2)
            {
                if (i == 0)
                    botonNota2[0].setImageResource(imagenPConNotas[i]);
                else if (i == 2)
                    botonNota2[2].setImageResource(imagenPConNotas[i]);
                else if (i == 4)
                    botonNota2[4].setImageResource(imagenPConNotas[i]);
                else if (i == 5)
                    botonNota2[5].setImageResource(imagenPConNotas[i]);
                else if (i == 7)
                    botonNota2[7].setImageResource(imagenPConNotas[i]);
                else if (i == 9)
                    botonNota2[9].setImageResource(imagenPConNotas[i]);
                else if (i == 11)
                    botonNota2[11].setImageResource(imagenPConNotas[i]);
            }
            else if(n == 3)
            {
                if (i == 0)
                    botonNota3[0].setImageResource(imagenPConNotas[i]);
                else if (i == 2)
                    botonNota3[2].setImageResource(imagenPConNotas[i]);
                else if (i == 4)
                    botonNota3[4].setImageResource(imagenPConNotas[i]);
                else if (i == 5)
                    botonNota3[5].setImageResource(imagenPConNotas[i]);
                else if (i == 7)
                    botonNota3[7].setImageResource(imagenPConNotas[i]);
                else if (i == 9)
                    botonNota3[9].setImageResource(imagenPConNotas[i]);
                else if (i == 11)
                    botonNota3[11].setImageResource(imagenPConNotas[i]);
            }
        }
    }

    //----------------------------------Funcion para restablecer color a la tecla soltada-----------------------------
    public void soltar(int i, int n)
    {
        if (!mostrar)
        {
            if(n == 1)
            {
                if (i == 0)
                    botonNota[0].setImageResource(imagenSinNotas[i]);
                else if (i == 2)
                    botonNota[2].setImageResource(imagenSinNotas[i]);
                else if (i == 4)
                    botonNota[4].setImageResource(imagenSinNotas[i]);
                else if (i == 5)
                    botonNota[5].setImageResource(imagenSinNotas[i]);
                else if (i == 7)
                    botonNota[7].setImageResource(imagenSinNotas[i]);
                else if (i == 9)
                    botonNota[9].setImageResource(imagenSinNotas[i]);
                else if (i == 11)
                    botonNota[11].setImageResource(imagenSinNotas[i]);
            }
            else if(n == 2)
            {
                if (i == 0)
                    botonNota2[0].setImageResource(imagenSinNotas[i]);
                else if (i == 2)
                    botonNota2[2].setImageResource(imagenSinNotas[i]);
                else if (i == 4)
                    botonNota2[4].setImageResource(imagenSinNotas[i]);
                else if (i == 5)
                    botonNota2[5].setImageResource(imagenSinNotas[i]);
                else if (i == 7)
                    botonNota2[7].setImageResource(imagenSinNotas[i]);
                else if (i == 9)
                    botonNota2[9].setImageResource(imagenSinNotas[i]);
                else if (i == 11)
                    botonNota2[11].setImageResource(imagenSinNotas[i]);
            }
            else if(n == 3)
            {
                if (i == 0)
                    botonNota3[0].setImageResource(imagenSinNotas[i]);
                else if (i == 2)
                    botonNota3[2].setImageResource(imagenSinNotas[i]);
                else if (i == 4)
                    botonNota3[4].setImageResource(imagenSinNotas[i]);
                else if (i == 5)
                    botonNota3[5].setImageResource(imagenSinNotas[i]);
                else if (i == 7)
                    botonNota3[7].setImageResource(imagenSinNotas[i]);
                else if (i == 9)
                    botonNota3[9].setImageResource(imagenSinNotas[i]);
                else if (i == 11)
                    botonNota3[11].setImageResource(imagenSinNotas[i]);
            }
        }
        else
        {
            if(n == 1)
            {
                if (i == 0)
                    botonNota[0].setImageResource(imagenConNotas[i]);
                else if (i == 2)
                    botonNota[2].setImageResource(imagenConNotas[i]);
                else if (i == 4)
                    botonNota[4].setImageResource(imagenConNotas[i]);
                else if (i == 5)
                    botonNota[5].setImageResource(imagenConNotas[i]);
                else if (i == 7)
                    botonNota[7].setImageResource(imagenConNotas[i]);
                else if (i == 9)
                    botonNota[9].setImageResource(imagenConNotas[i]);
                else if (i == 11)
                    botonNota[11].setImageResource(imagenConNotas[i]);
            }
            else if(n == 2)
            {
                if (i == 0)
                    botonNota2[0].setImageResource(imagenConNotas[i]);
                else if (i == 2)
                    botonNota2[2].setImageResource(imagenConNotas[i]);
                else if (i == 4)
                    botonNota2[4].setImageResource(imagenConNotas[i]);
                else if (i == 5)
                    botonNota2[5].setImageResource(imagenConNotas[i]);
                else if (i == 7)
                    botonNota2[7].setImageResource(imagenConNotas[i]);
                else if (i == 9)
                    botonNota2[9].setImageResource(imagenConNotas[i]);
                else if (i == 11)
                    botonNota2[11].setImageResource(imagenConNotas[i]);
            }
            else if(n == 3)
            {
                if (i == 0)
                    botonNota3[0].setImageResource(imagenConNotas[i]);
                else if (i == 2)
                    botonNota3[2].setImageResource(imagenConNotas[i]);
                else if (i == 4)
                    botonNota3[4].setImageResource(imagenConNotas[i]);
                else if (i == 5)
                    botonNota3[5].setImageResource(imagenConNotas[i]);
                else if (i == 7)
                    botonNota3[7].setImageResource(imagenConNotas[i]);
                else if (i == 9)
                    botonNota3[9].setImageResource(imagenConNotas[i]);
                else if (i == 11)
                    botonNota3[11].setImageResource(imagenConNotas[i]);
            }
        }

    }

    //----------------------------------Funcion para mostrar las notas en las teclas---------------------------
    public void mostrar()
    {
        Button amarillo = findViewById(R.id.mostrar);
        amarillo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!mostrar)
                {
                    for (int i = 0; i < 12; i++)
                    {
                        botonNota[i].setImageResource(imagenConNotas[i]);
                        botonNota2[i].setImageResource(imagenConNotas[i]);
                        botonNota3[i].setImageResource(imagenConNotas[i]);
                    }
                    mostrar = true;
                }
                else
                {
                    for (int i = 0; i < 12; i++)
                    {
                        botonNota[i].setImageResource(imagenSinNotas[i]);
                        botonNota2[i].setImageResource(imagenSinNotas[i]);
                        botonNota3[i].setImageResource(imagenSinNotas[i]);
                    }
                    mostrar = false;
                }
            }
        });
    }

    //-----------------------------Funcion para sonar la nota-------------------------------------
    public void inicializarPlayer(int n, String i)
    {
        switch (i)
        {
            case "Piano":
                MediaPlayer mediaplayer = MediaPlayer.create(this, piano[n]);
                mediaplayer.start();
                if(estadoGrabacion)
                    z.add(mediaplayer);
                break;
            case "Guitarra":
                mediaplayer = MediaPlayer.create(this, guitarra[n]);
                mediaplayer.start();
                if(estadoGrabacion)
                    z.add(mediaplayer);
                break;
            case "Bateria":
                mediaplayer = MediaPlayer.create(this, bateria[n]);
                mediaplayer.start();
                if(estadoGrabacion)
                    z.add(mediaplayer);
                break;
            case "Flauta":
                mediaplayer = MediaPlayer.create(this, flauta[n]);
                mediaplayer.start();
                if(estadoGrabacion)
                    z.add(mediaplayer);
                break;
            case "Trompeta":
                mediaplayer = MediaPlayer.create(this, trompeta[n]);
                mediaplayer.start();
                if(estadoGrabacion)
                    z.add(mediaplayer);
                break;
        }
    }

    public void grabar()
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

    public void detener()
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

    public void bucle()
    {
        Button verde = findViewById(R.id.bucle);
        verde.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                estadoReproduccion = !estadoReproduccion;

                if(estadoReproduccion)
                {
                    Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_LONG).show();

                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            while(estadoReproduccion && !x) {
                                reproducir();
                            }
                        }
                    }).start();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Se detuvo el audio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void reproducir()
    {
        for (int i = 0; i < z.size(); i++)
        {
            enReproduccion = (MediaPlayer) z.get(i);
            final int current = i;

            if (i == 0)
                enReproduccion.start();

            enReproduccion.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    if (current < z.size() - 1)
                    {
                        mp = (MediaPlayer) z.get(current + 1);
                        mp.start();
                    }
                }
            });
            x = i < z.size();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void nota(final int i)
    {
        pantalla = findViewById(R.id.pantalla);

        botonNota[i].setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //boton presionado
                    if(i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                    {
                        presion(i,1);
                    }
                    inicializarPlayer(i,instrumentoElegido);
                    pantalla.setText(getName(i));
                } else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    //boton liberado
                    if(i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                        soltar(i,1);
                }
                return true;
            }
        });
        botonNota2[i].setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //boton presionado
                    if (i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                    {
                        presion(i,2);
                    }
                    inicializarPlayer(i,instrumentoElegido);
                    pantalla.setText(getName(i));
                } else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    //boton liberado
                    if(i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                        soltar(i,2);
                }
                return true;
            }
        });
        botonNota3[i].setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //boton presionado
                    if(i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                    {
                        presion(i,3);
                    }
                    inicializarPlayer(i,instrumentoElegido);
                    pantalla.setText(getName(i));
                } else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    //boton liberado
                    if(i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 11)
                        soltar(i,3);
                }
                return true;
            }
        });
    }

    public String getName(int i)
    {
        if (i==0)
            return "Do";
        else if (i==1)
            return "Do#";
        else if (i==2)
            return "Re";
        else if (i==3)
            return "Re#";
        else if (i==4)
            return "Mi";
        else if (i==5)
            return "Fa";
        else if (i==6)
            return "Fa#";
        else if (i==7)
            return "Sol";
        else if (i==8)
            return "Sol#";
        else if (i==9)
            return "La";
        else if (i==10)
            return "La#";
        else
            return "Si";
    }

    public void sonar()
    {
        for(int i=0; i<12; i++)
        {
            botonNota[i] = findViewById(botonesId[i]);
            botonNota2[i] = findViewById(botonesId2[i]);
            botonNota3[i] = findViewById(botonesId3[i]);
        }

        for(int i=0; i<12; i++)
        {
            nota(i);
        }
    }
}