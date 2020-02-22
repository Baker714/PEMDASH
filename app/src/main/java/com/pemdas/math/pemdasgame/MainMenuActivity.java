package com.pemdas.math.pemdasgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void displayHelloWorld(View view)
    {
        //System.out.println("Hello World");
        findViewById(R.id.helloWorld).setVisibility(View.VISIBLE);
    }

    public void startSettingsActivity(View view)
    {
        Intent settingPEMDAS = new Intent(MainMenuActivity.this, selectPEMDASActivity.class);
        startActivity(settingPEMDAS);
    }

    public void openHowToPlayActivity(View view)
    {
        Intent openHowToPlay = new Intent(MainMenuActivity.this, HowToPlayActivity.class);
        startActivity(openHowToPlay);
    }

    public void openOptionsActivity(View view)
    {
        Intent openOptions = new Intent(MainMenuActivity.this, OptionsMenuActivity.class);
        startActivity(openOptions);
    }
}
