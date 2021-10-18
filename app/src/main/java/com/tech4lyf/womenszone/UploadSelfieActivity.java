package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class UploadSelfieActivity extends AppCompatActivity {

    CameraView cameraView;
    Button btnCap;
    String imageData;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_selfie);
        cameraView=(CameraView) findViewById(R.id.camera);

        btnCap=(Button)findViewById(R.id.btn_capture);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        btnCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.addCameraListener(new CameraListener() {

                         @Override
                         public void onPictureTaken(byte[] picture) {
                             Bitmap bitmap= BitmapFactory.decodeByteArray(picture,0,picture.length);
                             Log.e("Image","Captured");

                             imageData=convert(bitmap);
                             Log.e("Data",imageData);
                             uploadSelfie();


                         }

                     }
                );
                cameraView.capturePicture();

            }
        });



    }
    @Override protected void onResume() {

        super.onResume();

        cameraView.start();

    }
    @Override protected void onPause() {

        super.onPause();

        cameraView.stop();

    }
    @Override protected void onDestroy() {

        super.onDestroy();

        cameraView.destroy();

    }
    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return android.util.Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    void uploadSelfie()
    {
        String otpUrl = getString(R.string.baseurl) + "panupload.php";
        Log.e("OTPURL", otpUrl);
        JSONObject postData = new JSONObject();
        try {
            postData.put("imageData", imageData);
            postData.put("userid", "kichi");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, otpUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.e("RESP", response.toString());
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