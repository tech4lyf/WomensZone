package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WomensZoneActivity extends AppCompatActivity {

    CardView cvReg,cvMem,cvShop,cvLoan;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;
    SharedPreferences sh;
    String isKycDone="0";
    String isPayDone="0";
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womens_zone);

        cvReg=(CardView) findViewById(R.id.cardKyc);
        cvMem=(CardView) findViewById(R.id.cardMem);
        cvShop=(CardView) findViewById(R.id.cardShop);
        cvLoan=(CardView) findViewById(R.id.cardLoan);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);

        sh = getSharedPreferences("UserData", MODE_PRIVATE);
        phone = sh.getString("phone", "");

        getUserData();

        if(isKycDone.equals("0")) {
            Toast.makeText(getApplicationContext(), "KYC Status: Incomplete" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "KYC Status: Completed" , Toast.LENGTH_SHORT).show();
        }


        cvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKycDone.equals("1") && isPayDone.equals("1")) {
                    Toast.makeText(getApplicationContext(), "User already completed the process...", Toast.LENGTH_SHORT).show();
                }
                else if(isKycDone.equals("0")&&isPayDone.equals("1")){
                    Toast.makeText(getApplicationContext(), "User completed the payment. But KYC Pending!", Toast.LENGTH_SHORT).show();
                    Intent actKYC = new Intent(WomensZoneActivity.this, KYCInfoActivity.class);
                    actKYC.putExtra("skipPayment","true");
                    startActivity(actKYC);
                }
                else if(isKycDone.equals("1")&&isPayDone.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "User completed the KYC. But Payment Pending!", Toast.LENGTH_SHORT).show();
                    Intent actKYC = new Intent(WomensZoneActivity.this, PaymentActivity.class);
                    startActivity(actKYC);
                }
                else {
                    Intent actKYC = new Intent(WomensZoneActivity.this, KYCInfoActivity.class);
                    startActivity(actKYC);
                }
            }
        });

        cvMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actKYC=new Intent(WomensZoneActivity.this,MembersActivity.class);
                startActivity(actKYC);
            }
        });

        cvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actKYC=new Intent(WomensZoneActivity.this,StaytunedActivity.class);
                startActivity(actKYC);
            }
        });

        cvLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actKYC=new Intent(WomensZoneActivity.this,LoansActivity.class);
                startActivity(actKYC);
            }
        });
    }

    void getUserData()
    {
        String otpUrl = getString(R.string.baseurl) + "getuserdata.php?phone=" + phone;
        Log.e("GETUSERURL", otpUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, otpUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.e("RESP", response.toString());

                    String userid=response.getString("userid");
                    isKycDone=response.getString("isKycDone");
                    isPayDone=response.getString("isPayDone");

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("userid", userid);
                    myEdit.commit();


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