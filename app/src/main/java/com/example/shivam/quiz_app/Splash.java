package com.example.shivam.quiz_app;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView splash_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation animationUtils=AnimationUtils.loadAnimation(Splash.this,R.anim.fade_in);
        splash_img=(ImageView)findViewById(R.id.splash_img);
        splash_img.startAnimation(animationUtils);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,Option_Activity.class));
                finish();
            }
        },1500);
    }

}
