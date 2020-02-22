package com.pemdas.math.pemdasgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ToggleButton;

public class selectPEMDASActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pemdas);

    }

    public void startPEMDASGame(View view)
    {
        int z = 1;
        ToggleButton p = (ToggleButton)findViewById(R.id.toggleButtonP);
        ToggleButton e = (ToggleButton)findViewById(R.id.toggleButtonE);
        ToggleButton m = (ToggleButton)findViewById(R.id.toggleButtonM);
        ToggleButton d = (ToggleButton)findViewById(R.id.toggleButtonD);
        ToggleButton a = (ToggleButton)findViewById(R.id.toggleButtonA);
        ToggleButton s = (ToggleButton)findViewById(R.id.toggleButtonS);

        Intent startPEMDAS = new Intent(selectPEMDASActivity.this, ActiveGameActivity.class);

        startPEMDAS.putExtra("pBool", p.isChecked());
        startPEMDAS.putExtra("eBool", e.isChecked());
        startPEMDAS.putExtra("mBool", m.isChecked());
        startPEMDAS.putExtra("dBool", d.isChecked());
        startPEMDAS.putExtra("aBool", a.isChecked());
        startPEMDAS.putExtra("sBool", s.isChecked());

        startActivity(startPEMDAS);
    }

}
