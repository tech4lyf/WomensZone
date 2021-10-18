package com.tech4lyf.womenszone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {
    private TextView texthp1,texthp2,texthp3,texthp4,textp1,textp2,textp3,textp4;
    private CheckBox cbtc;
    private Button txtfls,txttru;
    private ImageView btn_back;
    private int CAMERA_PERMISSION_CODE = 1;
    private int CONTACT_PERMISSION_CODE = 1;




    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};




    boolean allPerm=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().hide();
        texthp1 = findViewById(R.id.txt_hp1);
        texthp2 = findViewById(R.id.txt_hp2);
        texthp3 = findViewById(R.id.txt_hp3);
        texthp4 = findViewById(R.id.txt_hp4);
        textp1 = findViewById(R.id.txt_p1);
        textp2 = findViewById(R.id.txt_p2);
        textp3 = findViewById(R.id.txt_p3);
        textp4 = findViewById(R.id.txt_p4);

        cbtc = findViewById(R.id.cb_tc);
        txtfls = findViewById(R.id.txt_fls);
        txttru = findViewById(R.id.txt_tru);
        btn_back = findViewById(R.id.btn_bck);

        String t = "I agree to Terms , Conditions and Privacy Policy .";
        SpannableString ss = new SpannableString(t);

        ForegroundColorSpan bf = new ForegroundColorSpan(getResources().getColor(R.color.blue));
        ForegroundColorSpan bf2 = new ForegroundColorSpan(getResources().getColor(R.color.blue));
        ss.setSpan(bf,11,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bf2,34,50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //txttc.setText(ss);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PermissionActivity.this,OnboardingActivity.class));
//                finish();
            }
        });
        txttru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkPermissions();
//                    requestCameraPermissions();
            }
        });
        txtfls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PermissionActivity.this,"Permissions are required inorder to proceed",Toast.LENGTH_LONG).show();
            }
        });

    }



    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;

                        }

                    }
                    // Show permissionsDenied
                    //updateViews();
//                    Toast.makeText(getApplicationContext(), "Accept all permissions", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PermissionActivity.this, PhoneActivity.class));
                    finish();
                }
                return;
            }
        }
    }

    private void requestCameraPermissions() {
        if(ContextCompat.checkSelfPermission(PermissionActivity.this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted Already", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PermissionActivity.this,PhoneActivity.class));
            finish();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("This permission is needed")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        }

    }

    private void requestContactPermissions() {
        if(ContextCompat.checkSelfPermission(PermissionActivity.this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted Already", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PermissionActivity.this,PhoneActivity.class));
            finish();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
                new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("This permission is needed")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this,new String[] {Manifest.permission.READ_CONTACTS}, CAMERA_PERMISSION_CODE);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION_CODE);
            }
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//
//
//            } else {
//                Toast.makeText(this, "Permission needed", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        else if(requestCode==CONTACT_PERMISSION_CODE)
//        {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startActivity(new Intent(PermissionActivity.this, PhoneActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Contact Permission needed", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }


}