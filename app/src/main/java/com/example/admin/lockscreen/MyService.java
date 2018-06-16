package com.example.admin.lockscreen;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/12/2018.
 */

public class MyService extends Service implements View.OnClickListener, View.OnTouchListener, PatternLockViewListener {
    public static final String DATA = "data";
    private static final String SOUND = "sound";
    private static final String PASSPATTERN = "PatternLockView";
    private static final String INDEX = "Index";
    private static final String INDEX2 = "Index2";
    private static final int OPEN = 1;
    private FrameLayout myGroup;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private MyReciver myReciver;
    private IntentFilter intentFilter;
    private LinearLayout backgroundLR;
    private int[] img = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
            R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9,
            R.drawable.img10, R.drawable.img11, R.drawable.img12};
    private int[] img2 = {
            R.drawable.l1,
            R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l5,
            R.drawable.l6, R.drawable.l7, R.drawable.l8, R.drawable.l9
    };
    private int[] img3 = {
            R.drawable.la1,
            R.drawable.la2, R.drawable.la3, R.drawable.la4, R.drawable.la5,
            R.drawable.la6, R.drawable.la7, R.drawable.la8, R.drawable.la9
    };
    private int[] img4 = {
            R.drawable.lb1,
            R.drawable.lb2, R.drawable.lb3, R.drawable.lb4, R.drawable.lb5,
            R.drawable.lb6, R.drawable.lb7, R.drawable.lb8, R.drawable.lb9
    };
    private int[] img5 = {
            R.drawable.lc1,
            R.drawable.lc2, R.drawable.lc3, R.drawable.lc4, R.drawable.lc5,
            R.drawable.lc6, R.drawable.lc7, R.drawable.lc8, R.drawable.lc9
    };
    private int bgPass[] = {R.drawable.bg, R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10};

    private ArrayList<ItemImage> itemImages;
    private int height;
    private int width;
    private View view;
    private View viewPass;
    private View viewCoundown;
    private LinearLayout bgCoundown;
    private TextView txtCoundown;
    private ImageView imageView;
    private int x;
    private int y;
    private boolean check = false;
    int index = 1;
    int tempIndex = 1;
    private MediaPlayer mediaPlayer;
    private MediaPlayer zip;
    private SharedPreferences sharedPreferences;
    private boolean sound;
    private boolean pass;
    private String passString1, passString2, passString3;
    private PatternLockView patternLockView;
    int amm;
    int n;
    int hinhNen;
    int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_IMMERSIVE;
    private int soLanSai = 5;
    private CountDownTimer countDownTimer;
    private TextView txtSoLanSai;
    private int time;
    private boolean lockPass = false;

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnXoa, btnXong;
    private EditText txt1, txt2, txt3, txt4;
    private TextView txt;
    private String passtempcode = "";
    private int dem = 0;
    private int rung;
    private View background;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("Service", "on create");
        super.onCreate();
        itemImages = new ArrayList<>();
        itemImages.add(new ItemImage(12, img));
        itemImages.add(new ItemImage(9, img2));
        itemImages.add(new ItemImage(9, img3));
        itemImages.add(new ItemImage(9, img4));
        itemImages.add(new ItemImage(9, img5));
        sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        amm = sharedPreferences.getInt(INDEX, 0);
        hinhNen = sharedPreferences.getInt(INDEX2, 0);
        n = itemImages.get(amm).getN();
        int checsk = sharedPreferences.getInt(SOUND, OPEN);
        rung = sharedPreferences.getInt(MenuActivity.VIBRETE, OPEN);
        passString1 = sharedPreferences.getString(PASSPATTERN, "");
        passString2 = sharedPreferences.getString(MenuActivity.PASSCODE, "");
        passString3 = sharedPreferences.getString(MenuActivity.PASSSRING, "");
        if (!passString1.isEmpty() || !passString2.isEmpty() || !passString3.isEmpty()) {
            pass = true;
        } else {
            pass = false;
        }
        if (checsk == OPEN) {
            sound = true;
        } else {
            sound = false;
        }
        myReciver = new MyReciver(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.CALL_STATE_RINGING");
        intentFilter.addAction("android.intent.action.CALL_STATE_OFFHOOK");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("com.khacchung.startpreviewscreen");
        this.registerReceiver(myReciver, intentFilter);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.mp3);
        zip = MediaPlayer.create(this, R.raw.zip2);
        mediaPlayer.setLooping(false);
        getWindow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "on start command");
        myGroup = new FrameLayout(this);
        initWindown();
        return START_STICKY;
    }

    private void createPassView() {
        myGroup.removeAllViews();
        if (pass) {
            passtempcode = "";
            dem = 0;
//            backgroundLR = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.pass_background, null);
//            backgroundLR.setBackgroundResource(bgPass[hinhNen]);
            if (!passString1.isEmpty()) {
                viewPass = LayoutInflater.from(this).inflate(R.layout.pass_layout, null);
                txt = viewPass.findViewById(R.id.txt);
                txtSoLanSai = viewPass.findViewById(R.id.solansai);
                txtSoLanSai.setText("You have " + soLanSai + " input times");
                background = viewPass.findViewById(R.id.bg);
                background.setBackgroundResource(bgPass[hinhNen]);
                patternLockView = viewPass.findViewById(R.id.checkPat);
                patternLockView.addPatternLockListener(this);
            } else if (!passString2.isEmpty()) {
                viewPass = LayoutInflater.from(this).inflate(R.layout.pass_fragment_code, null);
                txtSoLanSai = viewPass.findViewById(R.id.solansai);
                txtSoLanSai.setText("You have " + soLanSai + " input times");
                background = viewPass.findViewById(R.id.bg);
                background.setBackgroundResource(bgPass[hinhNen]);
                txt1 = viewPass.findViewById(R.id.edt1);
                txt2 = viewPass.findViewById(R.id.edt11);
                txt3 = viewPass.findViewById(R.id.edt12);
                txt4 = viewPass.findViewById(R.id.edt13);
                btn0 = viewPass.findViewById(R.id.btn0);
                btn1 = viewPass.findViewById(R.id.btn1);
                btn2 = viewPass.findViewById(R.id.btn2);
                btn3 = viewPass.findViewById(R.id.btn3);
                btn4 = viewPass.findViewById(R.id.btn4);
                btn5 = viewPass.findViewById(R.id.btn5);
                btn6 = viewPass.findViewById(R.id.btn6);
                btn7 = viewPass.findViewById(R.id.btn7);
                btn8 = viewPass.findViewById(R.id.btn8);
                btn9 = viewPass.findViewById(R.id.btn9);
                btnXoa = viewPass.findViewById(R.id.btnXoa);
                btnXong = viewPass.findViewById(R.id.btnXong);
                txt = viewPass.findViewById(R.id.txt);
                Button button = viewPass.findViewById(R.id.btnHuy);
                button.setVisibility(View.GONE);
                btn0.setOnClickListener(this);
                btn1.setOnClickListener(this);
                btn2.setOnClickListener(this);
                btn3.setOnClickListener(this);
                btn4.setOnClickListener(this);
                btn5.setOnClickListener(this);
                btn6.setOnClickListener(this);
                btn7.setOnClickListener(this);
                btn8.setOnClickListener(this);
                btn9.setOnClickListener(this);
                btnXoa.setOnClickListener(this);
                btnXong.setOnClickListener(this);
            } else if (!passString3.isEmpty()) {
                viewPass = LayoutInflater.from(this).inflate(R.layout.pass_text_fragment, null);
                txtSoLanSai = viewPass.findViewById(R.id.solansai);
                txtSoLanSai.setText("You have " + soLanSai + " input times");
                background = viewPass.findViewById(R.id.bg);
                background.setBackgroundResource(bgPass[hinhNen]);
                Button button = viewPass.findViewById(R.id.btnHuy);
                button.setVisibility(View.GONE);
                txt = viewPass.findViewById(R.id.edt);
                btn0 = viewPass.findViewById(R.id.btnXong);
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = txt.getText().toString().trim();
                        if (!a.isEmpty()) {
                            if (a.equals(passString3)) {
                                removeAllView();
                                soLanSai = 5;
                                txtSoLanSai.setText("You have " + soLanSai + " input times");
                            } else {
//                                TextView textView = viewPass.findViewById(R.id.txt);
//                                textView.setTextColor(Color.RED);
//                                textView.setText("Wrong password");
                                txt.setText("");
                                soLanSai--;
                                txtSoLanSai.setText("You have " + soLanSai + " input times");
                                if (soLanSai == 0) {
                                    lockPass();
                                }
                            }
                        }
                    }
                });
            }
            txtSoLanSai.setVisibility(View.VISIBLE);
        }
        view = LayoutInflater.from(this).inflate(R.layout.lock_layuot, null);
        imageView = view.findViewById(R.id.imgOpen);
        imageView.setOnTouchListener(this);
        myGroup.setSystemUiVisibility(flag);
        if (pass) {
            myGroup.addView(viewPass, layoutParams);
            if (lockPass) {
                myGroup.addView(viewCoundown, layoutParams);
            }
        }
        myGroup.addView(view, layoutParams);
    }

    private void initWindown() {
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        createPassView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SERVICE", "onDestroy");
        this.unregisterReceiver(myReciver);
    }

    public void removeAllView() {
        if (myGroup.isShown()) {
            windowManager.removeView(myGroup);
            if (rung == OPEN) {
                Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
        }
    }

    public void showLayoutLock() {
        if (!myGroup.isShown()) {
            imageView.setBackgroundResource(itemImages.get(amm).getImg()[0]);
            // backgroundLR.setBackgroundResource(bgPass[hinhNen]);
            myGroup.removeAllViews();
//            if (pass)
//                myGroup.addView(viewPass);
//            myGroup.addView(view);
            createPassView();
            windowManager.addView(myGroup, layoutParams);
        } else {
            imageView.setBackgroundResource(itemImages.get(amm).getImg()[0]);
            //backgroundLR.setBackgroundResource(bgPass[hinhNen]);
//            myGroup.removeAllViews();
//            if (pass)
//                myGroup.addView(viewPass);
//            myGroup.addView(view);
            createPassView();
            windowManager.updateViewLayout(myGroup, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        // windowManager.removeView(myGroup);
        switch (v.getId()) {
            case R.id.btn0:
                if (dem < 4) {
                    passtempcode += "0";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn1:
                if (dem < 4) {
                    passtempcode += "1";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn2:
                if (dem < 4) {
                    passtempcode += "2";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn3:
                if (dem < 4) {
                    passtempcode += "3";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn4:
                if (dem < 4) {
                    passtempcode += "4";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn5:
                if (dem < 4) {
                    passtempcode += "5";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn6:
                if (dem < 4) {
                    passtempcode += "6";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn7:
                if (dem < 4) {
                    passtempcode += "7";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn8:
                if (dem < 4) {
                    passtempcode += "8";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btn9:
                if (dem < 4) {
                    passtempcode += "9";
                    dem++;
                    switch (dem) {
                        case 1:
                            txt1.setText("*");
                            break;
                        case 2:
                            txt2.setText("*");
                            break;
                        case 3:
                            txt3.setText("*");
                            break;
                        case 4:
                            txt4.setText("*");
                            break;
                    }
                }
                break;
            case R.id.btnXoa:
                if (dem > 0) {
                    passtempcode = passtempcode.substring(0, passtempcode.length() - 1);
                    switch (dem) {
                        case 1:
                            txt1.setText("");
                            break;
                        case 2:
                            txt2.setText("");
                            break;
                        case 3:
                            txt3.setText("");
                            break;
                        case 4:
                            txt4.setText("");
                            break;
                    }
                    dem--;
                }
                break;
            case R.id.btnXong:
                if (passtempcode.length() == 4) {
                    if (passtempcode.equals(passString2)) {
                        //  windowManager.removeView(myGroup);
                        removeAllView();
                        soLanSai = 5;
                        txtSoLanSai.setText("You have " + soLanSai + " input times");
                    } else {
                        passtempcode = "";
                        dem = 0;
                        txt1.setText("");
                        txt2.setText("");
                        txt3.setText("");
                        txt4.setText("");
//                        txt.setText("Wrong password");
//                        txt.setTextColor(Color.RED);
                        soLanSai--;
                        txtSoLanSai.setText("You have " + soLanSai + " input times");
                        if (soLanSai == 0) {
                            lockPass();
                        }
                    }
                }
                break;
        }
    }


    private void getWindow() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels - getNavBar();
        width = displayMetrics.widthPixels;
        Log.e("WH", "" + width + " " + height + "   " + getNavBar());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = (int) event.getX();
                y = (int) event.getY();
                if (x >= (width / 2 - 50) && x <= (width / 2 + 50) && y <= height / n + 50) {
                    check = true;
                    if (!mediaPlayer.isPlaying() && sound) {
                        mediaPlayer.start();
                    }
                }
                Log.e("Move", x + " - " + y);
            }
            break;
            case MotionEvent.ACTION_MOVE:
                if (check) {
                    int x1 = (int) event.getRawX();
                    int y1 = (int) event.getRawY();
                    Log.e("move", x1 + " - " + y1);
                    index = y1 / ((height / n));
                    if (index > n) {
                        index = n;
                    }
                    if (tempIndex != index) {
                        tempIndex = index;
                        int a = index - 1;
                        if (a < 0) {
                            a = 0;
                        }
                        if (!zip.isPlaying() && sound)
                            zip.start();
                        imageView.setBackgroundResource(itemImages.get(amm).getImg()[a]);
                        // windowManager.updateViewLayout(frameLayout, layoutParams);
                    }
                    //Log.e("KKKKKKKKKKKKKKKK", " " + index);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (index != n) {
                    index = 1;
                    imageView.setBackgroundResource(itemImages.get(amm).getImg()[0]);
                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(this, R.raw.mp3);
                    }
                    if (sound) {
                        final MediaPlayer ting = MediaPlayer.create(this, R.raw.ting);
                        ting.start();
                        ting.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                ting.release();
                            }
                        });
                    }
                    // myGroup.removeView(view);
                    // windowManager.updateViewLayout(myGroup, layoutParams);
                    if (pass) {
                        myGroup.removeView(view);
                        windowManager.updateViewLayout(myGroup, layoutParams);
                        return true;
                    }
                    windowManager.removeView(myGroup);
                }
                index = 1;
                check = false;
                break;
        }
        return true;
    }

    private int getNavBar() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void onStarted() {
    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {
    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        String s = PatternLockUtils.patternToString(patternLockView, pattern);
        if (s.equals(passString1)) {
            soLanSai = 5;
            txtSoLanSai.setText("You have " + soLanSai + " input times");
            //windowManager.removeView(myGroup);
            removeAllView();
            patternLockView.clearPattern();
        } else {
//            txt.setText("Wrong password");
//            txt.setTextColor(Color.RED);
            patternLockView.clearPattern();
            soLanSai--;
            txtSoLanSai.setText("You have " + soLanSai + " input times");
            if (soLanSai == 0) {
                lockPass();
            }
        }
    }

    private void lockPass() {
        viewCoundown = LayoutInflater.from(this).inflate(R.layout.pass_background, null);
        bgCoundown = viewCoundown.findViewById(R.id.bg);
        bgCoundown.setBackgroundResource(bgPass[hinhNen]);
        txtCoundown = viewCoundown.findViewById(R.id.txt);
        myGroup.addView(viewCoundown, layoutParams);
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lockPass = true;
                time = (int) millisUntilFinished / 1000;
                txtCoundown.setText("Please wait " + time + " seconds");
                Log.e("TIME", time + "");
            }

            @Override
            public void onFinish() {
                txtCoundown.setText("Please wait " + 0 + " seconds");
                myGroup.removeView(viewCoundown);
                soLanSai = 5;
                txtSoLanSai.setText("You have " + soLanSai + " input times");
                lockPass = false;
            }
        }.start();
    }

    @Override
    public void onCleared() {
    }
}
