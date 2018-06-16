package com.example.admin.lockscreen;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.lockscreen.fragment.FragmentBackgroundScreen;
import com.example.admin.lockscreen.fragment.FragmentChackgroundPass;
import com.example.admin.lockscreen.fragment.FragmentMenu;
import com.example.admin.lockscreen.fragment.FragmentPassChangeCode;
import com.example.admin.lockscreen.fragment.FragmentPassChangeCode2;
import com.example.admin.lockscreen.fragment.FragmentPassChangePatternt;
import com.example.admin.lockscreen.fragment.FragmentPassChange2Patternt;
import com.example.admin.lockscreen.fragment.FragmentPassChangeText;
import com.example.admin.lockscreen.fragment.FragmentPassChangeText2;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    public static final String DATA = "data";
    public static final String SOUND = "sound";
    public static final String VIBRETE = "vibrete";
    public static final String PASSPATTERN = "PatternLockView";
    public static final String PASSCODE = "CodeLockView";
    public static final String PASSSRING = "StringLockView";
    public static final String INDEX = "Index";
    public static final String INDEX2 = "Index2";
    public static final int OPEN = 1;
    public static final int CLOSE = 0;
    private MediaPlayer mediaPlayer;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private boolean sound = true;
    public static final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int IMG[] = {R.drawable.bg, R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10};

    private String passTemp = "";

    public String getPassTemp() {
        return passTemp;
    }

    public void setPassTemp(String passTemp) {
        this.passTemp = passTemp;
    }

    private String passTempText;
    private String passTempNumber;

    public String getPassTempText() {
        return passTempText;
    }

    public void setPassTempText(String passTempText) {
        this.passTempText = passTempText;
    }

    public String getPassTempNumber() {
        return passTempNumber;
    }

    public void setPassTempNumber(String passTempNumber) {
        this.passTempNumber = passTempNumber;
    }

    private String passWord1;
    private String passWord2;
    private String passWord3;

    public String getPassWord1() {
        return passWord1;
    }

    public void setPassWord1(String passWord1) {
        this.passWord1 = passWord1;
    }

    public String getPassWord2() {
        return passWord2;
    }

    public void setPassWord2(String passWord2) {
        this.passWord2 = passWord2;
    }

    public String getPassWord3() {
        return passWord3;
    }

    public void setPassWord3(String passWord3) {
        this.passWord3 = passWord3;
    }

    private FragmentMenu fragmentMenu;
    private FragmentBackgroundScreen fragmentBackgroundScreen;
    private FragmentChackgroundPass fragmentChackgroundPass;
    private FragmentPassChangePatternt fragmentPassChangePatternt;
    private FragmentPassChange2Patternt fragmentPassChange2Patternt;
    private FragmentPassChangeCode fragmentPassChangeCode;
    private FragmentPassChangeCode2 fragmentPassChangeCode2;
    private FragmentPassChangeText fragmentPassChangeText;
    private FragmentPassChangeText2 fragmentPassChangeText2;

    public FragmentPassChangeCode getFragmentPassChangeCode() {
        return fragmentPassChangeCode;
    }

    public FragmentPassChangeCode2 getFragmentPassChangeCode2() {
        return fragmentPassChangeCode2;
    }

    public FragmentPassChangeText getFragmentPassChangeText() {
        return fragmentPassChangeText;
    }

    public FragmentPassChangeText2 getFragmentPassChangeText2() {
        return fragmentPassChangeText2;
    }

    public FragmentPassChange2Patternt getFragmentPassChange2Patternt() {
        return fragmentPassChange2Patternt;
    }

    public FragmentMenu getFragmentMenu() {
        return fragmentMenu;
    }

    public FragmentBackgroundScreen getFragmentBackgroundScreen() {
        return fragmentBackgroundScreen;
    }

    public FragmentChackgroundPass getFragmentChackgroundPass() {
        return fragmentChackgroundPass;
    }

    public FragmentPassChangePatternt getFragmentPassChangePatternt() {
        return fragmentPassChangePatternt;
    }

    public void refesService() {
        if (isServiceRunning()) {
            Intent intent = new Intent(MenuActivity.this, MyService.class);
            stopService(intent);
            startService(intent);
        }
    }

    public void startService() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
        if (!isServiceRunning()) {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }
    }

    public void stopService() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.reenableKeyguard();
        if (isServiceRunning()) {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        }
    }

    public boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.admin.lockscreen.MyService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        passWord1 = sharedPreferences.getString(PASSPATTERN, "");
        passWord2 = sharedPreferences.getString(PASSCODE, "");
        passWord3 = sharedPreferences.getString(PASSSRING, "");
        fragmentMenu = new FragmentMenu();
        fragmentBackgroundScreen = new FragmentBackgroundScreen();
        fragmentChackgroundPass = new FragmentChackgroundPass();
        fragmentPassChangePatternt = new FragmentPassChangePatternt();
        fragmentPassChange2Patternt = new FragmentPassChange2Patternt();

        fragmentPassChangeCode = new FragmentPassChangeCode();
        fragmentPassChangeCode2 = new FragmentPassChangeCode2();
        fragmentPassChangeText = new FragmentPassChangeText();
        fragmentPassChangeText2 = new FragmentPassChangeText2();
        switchFragment(fragmentMenu);
    }

    public void switchFragment(Fragment fragment) {
        if (fragment == fragmentMenu) {
            fragmentMenu = new FragmentMenu();
        }
        if (fragment == fragmentBackgroundScreen) {
            fragmentBackgroundScreen = new FragmentBackgroundScreen();
        }
        if (fragment == fragmentChackgroundPass) {
            fragmentChackgroundPass = new FragmentChackgroundPass();
        }
        if (fragment == fragmentPassChangeCode) {
            fragmentPassChangeCode = new FragmentPassChangeCode();
        }
        if (fragment == fragmentPassChangeCode2) {
            fragmentPassChangeCode2 = new FragmentPassChangeCode2();
        }
        if (fragment == fragmentPassChangePatternt) {
            fragmentPassChangePatternt = new FragmentPassChangePatternt();
        }
        if (fragment == fragmentPassChange2Patternt) {
            fragmentPassChange2Patternt = new FragmentPassChange2Patternt();
        }
        if (fragment == fragmentPassChangeText) {
            fragmentPassChangeText = new FragmentPassChangeText();
        }
        if (fragment == fragmentPassChangeText2) {
            fragmentPassChangeText2 = new FragmentPassChangeText2();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.drawLayout, fragment);
        fragmentTransaction.commit();
    }

    public void sendAction() {
        Intent intent = new Intent("com.khacchung.startpreviewscreen");
        sendBroadcast(intent);
    }
}
