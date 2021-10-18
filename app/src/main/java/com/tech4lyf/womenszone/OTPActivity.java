package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;


import java.util.HashMap;

public class OTPActivity extends AppCompatActivity {

    private Button btnverify;
    private EditText otp1,otp2,otp3,otp4;
    private TextView tvre,tvnum;
    private String otp="",isNew="";
    String phone;




    Intent navActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        getSupportActionBar().hide();
        btnverify = findViewById(R.id.btn_verify);
        tvre = findViewById(R.id.tv_re);
        otp1 = findViewById(R.id.otp_1);
        otp2 = findViewById(R.id.otp_2);
        otp3 = findViewById(R.id.otp_3);
        otp4 = findViewById(R.id.otp_4);
        tvnum = findViewById(R.id.tv_num);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            phone=intent.getStringExtra("phone");
            otp=intent.getStringExtra("otp");
            isNew=intent.getStringExtra("isNew");

        }

        if(isNew.equals("true"))
        {
            navActivity=new Intent(OTPActivity.this, RegisterActivity.class);
        }
        else {
            navActivity=new Intent(OTPActivity.this, MainWinActivity.class);
        }

        tvnum.setText("To number : "+phone);
        String t = "Can't get resend ?";
        SpannableString ss = new SpannableString(t);

        ForegroundColorSpan fb = new ForegroundColorSpan(getResources().getColor(R.color.black));
        ss.setSpan(fb,11,18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvre.setText(ss);

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(otp1.getText().toString().length()==1)
                {
                    otp2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(otp2.getText().toString().length()==1)
                {
                    otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(otp3.getText().toString().length()==1)
                {
                    otp4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp1.length() == 0){
                    Toast.makeText(OTPActivity.this, "Check the first digit", Toast.LENGTH_SHORT).show();
                }else if(otp2.length() == 0){
                    Toast.makeText(OTPActivity.this, "Check the second digit", Toast.LENGTH_SHORT).show();
                } else if(otp3.length() == 0){
                    Toast.makeText(OTPActivity.this, "Check the third digit", Toast.LENGTH_SHORT).show();
                } else if(otp4.length() == 0){
                    Toast.makeText(OTPActivity.this, "Check the fourth digit", Toast.LENGTH_SHORT).show();
                }else{
                    String _otp=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
                    if(otp.equals(_otp)) {
                        startActivity(navActivity);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}