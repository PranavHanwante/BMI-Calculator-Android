package com.example.pranav.bmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView ivRun;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivRun = findViewById(R.id.ivRun);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.a1);
        ivRun.startAnimation(animation);



        new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    Thread.sleep(5000);
                    Intent i = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }


            }
        }).start();


    }
}
