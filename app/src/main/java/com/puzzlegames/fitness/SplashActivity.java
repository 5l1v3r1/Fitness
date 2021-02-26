package com.puzzlegames.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wshunli.assets.CopyAssets;
import com.wshunli.assets.CopyCreator;
import com.wshunli.assets.CopyListener;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init(){

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    copyDatabase();
                } else {
                    init();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }


    private void copyDatabase(){
        String filePath = "/data/data/"+getPackageName()+"/mydb/";
        File dbFile = new File(filePath);

        if (!dbFile.exists()){
            dbFile.mkdirs();
        }

        if (new File(filePath + "data3110").exists()){
            startMainActivity();
        } else {
            CopyAssets.with(this)
                    .from("")
                    .to(filePath)
                    .setListener(new CopyListener() {
                        @Override
                        public void pending(CopyCreator copyCreator, String oriPath, String desPath, List<String> names) {

                        }

                        @Override
                        public void progress(CopyCreator copyCreator, File currentFile, int copyProgress) {

                        }

                        @Override
                        public void completed(CopyCreator copyCreator, Map<File, Boolean> results) {
                            startMainActivity();
                        }

                        @Override
                        public void error(CopyCreator copyCreator, Throwable e) {

                        }
                    })
            .copy();

        }


    }

    private void startMainActivity(){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (new File("/data/data/"+getPackageName()+"/mydb/data3110").exists()){
                    timer.cancel();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        }, 1000, 5000);



    }
}
