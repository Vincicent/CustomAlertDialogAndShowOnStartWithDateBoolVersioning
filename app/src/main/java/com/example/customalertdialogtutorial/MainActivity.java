package com.example.customalertdialogtutorial;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirstRun();

        Button mShowDialog = (Button) findViewById(R.id.btnShowDialog);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
                mBuilder.setCancelable(false);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    public void checkFirstRun() {
        String VersionName = this.getVersionName(this, MainActivity.class);
        String StoredVersionname = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Version",null);

        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        Date myDateStorage = new Date(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getLong("Date", 0));

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(myDateStorage);
        cal.add(Calendar.DAY_OF_YEAR, +7);
        Date daysAddAtStorageDate = cal.getTime();



        //if (!VersionName.equals(StoredVersionname)){
        if (daysAddAtStorageDate.getTime() < date.getTime()){
            // Place your dialog code here to display the dialog

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
            Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
            mBuilder.setCancelable(false);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Version",VersionName).apply();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putLong("Date",millis).apply();

        }
    }


    public static String getVersionName(Context context, Class cls) {
        try {
            ComponentName comp = new ComponentName(context, cls);
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                    comp.getPackageName(), 0);
            return pinfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}



/*
    Via TimeStamp

        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        Date myDateStorage = new Date(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getLong("Date", 0));

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(myDateStorage);
        cal.add(Calendar.DAY_OF_YEAR, +7);    //Plus de 7 Jours
        Date daysAddAtStorageDate = cal.getTime();

        if (daysAddAtStorageDate.getTime() < date.getTime()){   DoStuff      }

       getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putLong("Date",millis).apply();





    Via Boolean First Open

    //boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
    //if (isFirstRun){  Do stuff }

    //getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();





    Via Versionning

    public static String getVersionName(Context context, Class cls) {
        try {
            ComponentName comp = new ComponentName(context, cls);
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                    comp.getPackageName(), 0);
            return pinfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    String VersionName = this.getVersionName(this, MainActivity.class);
    String StoredVersionname = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Version",null);

    if (StoredVersionname == null || StoredVersionname.length() == 0){
        FirstRunDialog();
    }else if (StoredVersionname != VersionName) {
        UpdateDialog();
    }

    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Version",VersionName).apply();*/
