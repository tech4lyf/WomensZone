package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainWinActivity extends AppCompatActivity {

    CardView cvWZ,cvLB,cvCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_win);

        cvWZ=(CardView) findViewById(R.id.cardWz);
        cvLB=(CardView) findViewById(R.id.cardLb);
        cvCM=(CardView) findViewById(R.id.cardCm);


        cvWZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent actWz = new Intent(MainWinActivity.this, WomensZoneActivity.class);
                startActivity(actWz);
            }
        });

        cvLB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actWz=new Intent(MainWinActivity.this,StaytunedActivity.class);
                startActivity(actWz);
            }
        });

        cvCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actWz=new Intent(MainWinActivity.this,StaytunedActivity.class);
                startActivity(actWz);
            }
        });


    }
}