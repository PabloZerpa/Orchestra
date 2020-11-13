package com.example.piano;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class Intro extends AppIntro
{
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntro2Fragment.newInstance("Slide 1", "This is the first slide", R.drawable.intro, R.color.Blue));

    }
}