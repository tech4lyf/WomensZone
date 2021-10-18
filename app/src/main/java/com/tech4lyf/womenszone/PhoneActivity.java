package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetHost;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {

    EditText etnum;
    Button btncon;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String phone,otp,isNew;
    int toError=0;
    ProgressDialog progressDialog;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        etnum = findViewById(R.id.et_num);
        btncon = findViewById(R.id.btn_con);
        jsonParser=new JSONParser();
        requestQueue = Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);

        etnum=(EditText) findViewById(R.id.et_num);
        btncon.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                phone=etnum.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("phone", phone);
                myEdit.commit();
                //new GetOTP().execute();
                getOTP();
            }
        });
}



   public void getOTP() {

       String otpUrl = getString(R.string.baseurl) + "checkuser.php?phone=" + phone;
       Log.e("OTPURL", otpUrl);

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, otpUrl, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               progressDialog.dismiss();
               try {
                   Log.e("RESP", response.toString());
                   otp = response.getString("otp");
                   isNew = response.getString("isNew");
                   Intent intent = new Intent(PhoneActivity.this, OTPActivity.class);
                   Toast.makeText(getApplicationContext(), "Your OTP is:" + otp, Toast.LENGTH_LONG).show();
                   Toast.makeText(getApplicationContext(), "Your OTP is:" + otp, Toast.LENGTH_LONG).show();
                   intent.putExtra("phone", phone);
                   intent.putExtra("otp", otp);
                   intent.putExtra("isNew", isNew);
                   startActivity(intent);
                   finish();
               }
               catch (Exception ex){
                   Log.e("Ex",ex.toString());
                   Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
               Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
           }
       });
       requestQueue.add(jsonObjectRequest);
       
       progressDialog.setMessage("Loading...");
       progressDialog.show();
   }
}