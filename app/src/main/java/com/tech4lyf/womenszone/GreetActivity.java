package com.tech4lyf.womenszone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GreetActivity extends AppCompatActivity {

    Button btnCon;
    TextView tvName;

    String urltc,urlpp;
    TextView txtpp,txttc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet);

        tvName=(TextView)findViewById(R.id.tvUserName);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking

            tvName.setText(intent.getStringExtra("sal")+" "+intent.getStringExtra("name"));
        }

        btnCon=(Button) findViewById(R.id.btnCont);



        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actKYC=new Intent(GreetActivity.this,MainWinActivity.class);
                startActivity(actKYC);
            }
        });


        urltc=getString(R.string.baseurl)+"terms.html";
        urlpp=getString(R.string.baseurl)+"privacy.html";

        txttc = findViewById(R.id.txt_tc2);
        txtpp = findViewById(R.id.txt_tc4);

        txttc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

                // below line is setting toolbar color
                // for our custom chrome tab.
                customIntent.setToolbarColor(ContextCompat.getColor(GreetActivity.this, R.color.purple_200));

                // we are calling below method after
                // setting our toolbar color.
                openCustomTab(GreetActivity.this, customIntent.build(), Uri.parse(urltc));

            }
        });


        txtpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

                // below line is setting toolbar color
                // for our custom chrome tab.
                customIntent.setToolbarColor(ContextCompat.getColor(GreetActivity.this, R.color.purple_200));

                // we are calling below method after
                // setting our toolbar color.
                openCustomTab(GreetActivity.this, customIntent.build(), Uri.parse(urlpp));

            }
        });

    }

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

}