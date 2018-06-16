package com.example.admin.lockscreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS
    };
    private CountDownTimer thread;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final int NOTFIST = 1;
    public static final String FIRST = "first";
    private int check;
    private ProgressDialog progressDialog;
    public static final String PATH = Environment.getExternalStorageState() + "/lockscreen/";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        someMethod();
        initView();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permisson : PERMISSION_LIST) {
                if (checkSelfPermission(permisson) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSION_LIST, 0);
                    return;
                }
            }
        }
        someMethod();
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void someMethod() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 111);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void initView() {
        LinearLayout linearLayout = findViewById(R.id.bg);
        sharedPreferences = getSharedPreferences(MenuActivity.DATA, MODE_PRIVATE);
        check = sharedPreferences.getInt(FIRST, 0);
        if (/*check == NOTFIST*/true) {
            linearLayout.setOnClickListener(this);
            thread = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    openMenu();
                }
            }.start();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Writing files to phone memory");
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            copyAssets();
        }
    }

    @Override
    public void onClick(View v) {
        thread.cancel();
        openMenu();
    }

    private void openMenu() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("NewApi")
    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] file = null;
        try {
            file = assetManager.list("res_image");
            if (file != null) {
                for (String fileName : file) {
                    Log.e("NAME", fileName);
                    InputStream inputStream = assetManager.open("res_image/" + fileName);
                    OutputStream outputStream = new FileOutputStream(new File(getExternalFilesDir(null), fileName));
                    copyFile(inputStream, outputStream);
                    inputStream.close();
                    inputStream = null;
                    outputStream.flush();
                    outputStream.close();
                    outputStream = null;
                }
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.cancel();
                    editor = sharedPreferences.edit();
                    editor.putInt(FIRST, NOTFIST);
                    editor.commit();
                    if (Settings.canDrawOverlays(this))
                        openMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) {
        byte b[] = new byte[1024];
        int read;
        try {
            while ((read = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, read);
            }
            Log.e("NAME1", "Ghi xong");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

