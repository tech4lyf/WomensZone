package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    int toError=0;

    JSONParser jsonParser;
    JSONObject jsonObject, jsonObject1;

    String fName, lName, phone, address, state, city;
    Button btn_con;
    TextInputEditText tipfName, tiplName, tipPhone, tipAddr;
    AutoCompleteTextView tipState, tipCity, tipSal;
    private String[] statesArr = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    private String[] cityArr = {"Chennai", "Kolkata"};
    private String[] salArr = {"Ms.", "Mrs."};
    String sal = "";
    String parent = "";
    AutoCompleteTextView tvState;
    SharedPreferences sh;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_con = (Button) findViewById(R.id.btn_con);
        tipfName = (TextInputEditText) findViewById(R.id.tipFName);
        tiplName = (TextInputEditText) findViewById(R.id.tipLName);
        tipAddr = (TextInputEditText) findViewById(R.id.tipAddress);
        tipState = (AutoCompleteTextView) findViewById(R.id.tipState);
        tipCity = (AutoCompleteTextView) findViewById(R.id.tipCity);
        sh = getSharedPreferences("UserData", MODE_PRIVATE);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);

        jsonParser=new JSONParser();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, statesArr);

        tvState = (AutoCompleteTextView) findViewById(R.id.tipState);
        tvState.setAdapter(arrayAdapter);
        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                tvState.showDropDown();
            }
        });

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, salArr);

        tipSal = (AutoCompleteTextView) findViewById(R.id.tipSal);
        tipSal.setAdapter(arrayAdapter2);
        tipSal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                tipSal.showDropDown();
            }
        });

        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fName = tipfName.getText().toString();
                lName = tiplName.getText().toString();
                sal = tipSal.getText().toString();
                phone = sh.getString("phone", "");
                //fName=tipPhone.getText().toString();
                address = tipAddr.getText().toString();
                city = tipCity.getText().toString();
                state = tipState.getText().toString();

                if (fName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (lName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
                } else if (sal.isEmpty() || sal.equals("Select Salution")) {
                    Toast.makeText(getApplicationContext(), "Please select Salution", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
                } else if (city.isEmpty() || city.equals("Select City")) {
                    Toast.makeText(getApplicationContext(), "Please enter city", Toast.LENGTH_SHORT).show();
                } else if (state.isEmpty() | state.equals("Select State")) {
                    Toast.makeText(getApplicationContext(), "Please enter state", Toast.LENGTH_SHORT).show();
                } else {
                    getParentVolley();

                }

            }
        });
    }

    public class GetParent extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> params = new HashMap<>();
            params.put("phone", phone);


            try {
                toError=0;
                jsonObject1 = jsonParser.makeHttpRequest(getString(R.string.baseurl) + "getprereg.php", "GET", params);
            }
            catch (Exception ex)
            {
                toError=1;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(toError==0) {
                try {
                    if (jsonObject1.getString("status").equals("true")) {
                        parent = jsonObject1.getString("parent");
                        new Register().execute();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please pre-register to continue...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Poor Internet connection! Please connect with other network", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();

        }
    }

    public class Register extends AsyncTask<Void,Void,Void>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String,String> params=new HashMap<>();
            params.put("fName",fName);
            params.put("lName",lName);
            params.put("phone",phone);
            params.put("address",address);
            params.put("state",state);
            params.put("city",city);
            params.put("parent",parent);


            //jsonObject=jsonParser.makeHttpRequest(getString(R.string.baseurl)+"register.php?fName="+fName+"&lName="+lName+"&phone="+phone+"&address="+address+"&state="+state+"&city="+city,"GET",params);
            try {
                toError=0;
                jsonObject = jsonParser.makeHttpRequest(getString(R.string.baseurl) + "register.php", "GET", params);
                Log.e("URL", getString(R.string.baseurl) + "register.php?fName=" + fName + "&lName=" + lName + "&phone=" + phone + "&address=" + address + "&state=" + state + "&city=" + city);
            }
            catch (Exception ex)
            {
                toError=1;
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressDialog.dismiss();
            if(toError==0) {
                Toast.makeText(getApplicationContext(), "Registration Success...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, GreetActivity.class);
                intent.putExtra("name", fName + " " + lName);
                intent.putExtra("sal", sal);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Poor Internet connection! Please connect with other network", Toast.LENGTH_LONG).show();
            }
        }
    }

    void getParentVolley()
    {
        String parentUrl = getString(R.string.baseurl) + "getprereg.php?phone=" + phone;
        Log.e("PARENTURL", parentUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, parentUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.e("RESP", response.toString());
                    if (response.getString("status").equals("true")) {
                        parent = response.getString("parent");
                        new Register().execute();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please pre-register to continue...", Toast.LENGTH_SHORT).show();
                    }
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

    void registerUser()
    {
        HashMap<String,String> params=new HashMap<>();
        params.put("fName",fName);
        params.put("lName",lName);
        params.put("phone",phone);
        params.put("address",address);
        params.put("state",state);
        params.put("city",city);
        params.put("parent",parent);

        String regUrl = getString(R.string.baseurl) + "register.php?fName=" + fName + "&lName=" + lName + "&phone=" + phone + "&address=" + address + "&state=" + state + "&city=" + city+"&parent="+parent;
        Log.e("REGURL", regUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, regUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.e("RESP", response.toString());

                    if(response.getString("status").equals("success")) {
                        Toast.makeText(getApplicationContext(), "Registration Success...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, GreetActivity.class);
                        intent.putExtra("name", fName + " " + lName);
                        intent.putExtra("sal", sal);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
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