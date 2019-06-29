package com.example.galatasaray.askandtalkv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private int SLEEP_TIMER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        LogoLauncer logoLauncer = new LogoLauncer();
        logoLauncer.start();


    }

    private class LogoLauncer extends Thread{
        public void run(){
            try{
                sleep(3000 * SLEEP_TIMER);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(intent);
            SplashScreen.this.finish();
    }
    }
}
