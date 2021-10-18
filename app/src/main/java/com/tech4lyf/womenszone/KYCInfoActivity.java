package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

public class KYCInfoActivity extends AppCompatActivity {

    Button btnCon;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycinfo);

        SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);

        phone = sh.getString("phone", "");
//        Toast.makeText(getApplicationContext(), "Phone:"+phone, Toast.LENGTH_SHORT).show();

//        new GetData().execute();


        btnCon = (Button) findViewById(R.id.btn_con);
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actSelfie = new Intent(KYCInfoActivity.this, UploadSelfieActivity.class);
                startActivity(actSelfie);
            }
        });
    }
}