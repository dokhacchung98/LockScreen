package com.example.admin.lockscreen.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.MyService;
import com.example.admin.lockscreen.R;
import com.rm.rmswitch.RMSwitch;

import java.util.List;

import static com.example.admin.lockscreen.MenuActivity.PASSCODE;
import static com.example.admin.lockscreen.MenuActivity.PASSPATTERN;
import static com.example.admin.lockscreen.MenuActivity.PASSSRING;

/**
 * Created by Admin on 1/16/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentMenu extends Fragment implements View.OnClickListener {
    private Button btnPass;
    private Button btnBackgroundScreen;
    private Button btnBackgroundPass;
    private Button btnDefault;
    private Button btnExit;
    private RMSwitch checkBoxOpen;
    private RMSwitch checkBoxSound;
    private RMSwitch checkBoxVibrate;
    private View view;
    private MenuActivity menuActivity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int hinhnen = 0;
    private ScrollView scrollView;
    private View tempView;
    private FloatingActionButton floatingActionButton;

    @SuppressLint("ValidFragment")

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_fragment, container, false);
        menuActivity = (MenuActivity) getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //refreshFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        tempView = view;
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnBackgroundPass = view.findViewById(R.id.btnBackgroundPass);
        btnBackgroundScreen = view.findViewById(R.id.btnBackgroundScreen);
        btnPass = view.findViewById(R.id.btnPass);
        btnDefault = view.findViewById(R.id.btndefault);
        btnExit = view.findViewById(R.id.btnExit);
        checkBoxOpen = view.findViewById(R.id.checkBoxOpen);
        checkBoxSound = view.findViewById(R.id.checkBoxSound);
        checkBoxVibrate = view.findViewById(R.id.checkBoxVibratea);

        scrollView = view.findViewById(R.id.bg);
        hinhnen = sharedPreferences.getInt(menuActivity.INDEX2, 0);
        scrollView.setBackgroundResource(MenuActivity.IMG[hinhnen]);

        checkBoxOpen.setOnClickListener(this);
        checkBoxSound.setOnClickListener(this);
        checkBoxVibrate.setOnClickListener(this);

        if (menuActivity.isServiceRunning())
            checkBoxOpen.setChecked(true);
        else checkBoxOpen.setChecked(false);

        int check = sharedPreferences.getInt(menuActivity.SOUND, menuActivity.OPEN);
        if (check == menuActivity.OPEN) {
            checkBoxSound.setChecked(true);
        } else {
            checkBoxSound.setChecked(false);
        }
        int check1 = sharedPreferences.getInt(menuActivity.VIBRETE, menuActivity.OPEN);
        if (check1 == menuActivity.OPEN) {
            checkBoxVibrate.setChecked(true);
        } else {
            checkBoxVibrate.setChecked(false);
        }

        btnExit.setOnClickListener(this);
        btnDefault.setOnClickListener(this);
        btnPass.setOnClickListener(this);
        btnBackgroundScreen.setOnClickListener(this);
        btnBackgroundPass.setOnClickListener(this);
        floatingActionButton = view.findViewById(R.id.floatA);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.startService();
                if (menuActivity.isServiceRunning()) {
                    menuActivity.sendAction();
                    checkBoxOpen.setChecked(true);
                }

            }
        });
    }

    private void showAlerPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(menuActivity);
        View view = getLayoutInflater().inflate(R.layout.dialog_pass1, null);
        Button btnNone = view.findViewById(R.id.btnPassNone);
        Button btnPat = view.findViewById(R.id.btnPassPat);
        Button btnCode = view.findViewById(R.id.btnPassCode);
        Button btnText = view.findViewById(R.id.btnPassText);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(menuActivity.PASSCODE, "");
                editor.putString(menuActivity.PASSSRING, "");
                editor.putString(menuActivity.PASSPATTERN, "");
                editor.commit();
                Toast.makeText(menuActivity, "Set the password is not successful", Toast.LENGTH_SHORT).show();
            }
        });
        btnPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.switchFragment(menuActivity.getFragmentPassChangePatternt());
                alertDialog.cancel();
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.switchFragment(menuActivity.getFragmentPassChangeText());
                alertDialog.cancel();
            }
        });
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.switchFragment(menuActivity.getFragmentPassChangeCode());
                alertDialog.cancel();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPass:
                final String pass = sharedPreferences.getString(PASSPATTERN, "");
                final String pass1 = sharedPreferences.getString(PASSCODE, "");
                final String pass2 = sharedPreferences.getString(PASSSRING, "");
                Log.e("PASSS", pass + "  -  " + pass1 + " - " + pass2);
                if (!pass.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(menuActivity);
                    builder.setCancelable(false);
                    final View view = getLayoutInflater().inflate(R.layout.pass_pattern, null);
                    final TextView textView = view.findViewById(R.id.txt);
                    textView.setText("Draw Again Password");
                    Button btn = view.findViewById(R.id.btnHuyPat);
                    final PatternLockView patternLockView = view.findViewById(R.id.pat);
                    builder.setView(view);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    patternLockView.addPatternLockListener(new PatternLockViewListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(List<PatternLockView.Dot> progressPattern) {

                        }

                        @Override
                        public void onComplete(List<PatternLockView.Dot> pattern) {
                            String pass1 = PatternLockUtils.patternToString(patternLockView, pattern);
                            String a = sharedPreferences.getString(PASSPATTERN, "");
                            if (pass1.equals(a)) {
                                //menuActivity.switchFragment(menuActivity.getFragmentPassChangePatternt());
                                showAlerPass();
                                alertDialog.cancel();
                            } else {
                                patternLockView.clearPattern();
                                textView.setText("Wrong password");
                                textView.setTextColor(Color.RED);
                            }
                        }

                        @Override
                        public void onCleared() {

                        }
                    });
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    return;
                } else if (!pass2.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(menuActivity);
                    builder.setCancelable(false);
                    final View view = getLayoutInflater().inflate(R.layout.pass_text_fragment, null);
                    Button btn = view.findViewById(R.id.btnHuy);
                    Button btnOk = view.findViewById(R.id.btnXong);
                    final EditText ed = view.findViewById(R.id.edt);
                    builder.setView(view);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String a = ed.getText().toString().trim();
                            if (!a.isEmpty()) {
                                if (a.equals(pass2)) {
                                    alertDialog.cancel();
                                    showAlerPass();
                                } else {
                                    Toast.makeText(getActivity(), "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    return;
                } else if (!pass1.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(menuActivity);
                    builder.setCancelable(false);
                    final View view = getLayoutInflater().inflate(R.layout.fragment_check_code, null);
                    final EditText txt1 = view.findViewById(R.id.edt);
                    Button btn = view.findViewById(R.id.btnHuy);
                    Button xong = view.findViewById(R.id.btnXong);
                    builder.setView(view);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    xong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String temp = txt1.getText().toString().trim();
                            if (!temp.isEmpty() && temp.length() == 4) {
                                if (temp.equals(pass1)) {
                                    alertDialog.cancel();
                                    showAlerPass();
                                } else {
                                    Toast.makeText(getActivity(), "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    return;
                }
                showAlerPass();
                break;
            case R.id.btnBackgroundPass:
                menuActivity.switchFragment(menuActivity.getFragmentChackgroundPass());
                break;
            case R.id.btnBackgroundScreen:
                menuActivity.switchFragment(menuActivity.getFragmentBackgroundScreen());
                break;
            case R.id.btndefault:
//                editor.putString(menuActivity.PASSCODE, "");
//                editor.putString(menuActivity.PASSSRING, "");
//                editor.putString(menuActivity.PASSPATTERN, "");
                editor.putInt(menuActivity.INDEX, 0);
                editor.putInt(menuActivity.INDEX2, 0);
                editor.putInt(menuActivity.SOUND, menuActivity.OPEN);
                editor.putInt(menuActivity.VIBRETE, menuActivity.OPEN);
                checkBoxVibrate.setChecked(true);
                checkBoxSound.setChecked(true);
                menuActivity.refesService();
                Toast.makeText(menuActivity, "Successful installation", Toast.LENGTH_SHORT).show();
                scrollView.setBackgroundResource(MenuActivity.IMG[0]);
                break;
            case R.id.btnExit:
                menuActivity.finish();
                break;
            case R.id.checkBoxOpen:
                if (checkBoxOpen.isChecked()) {
                    checkBoxOpen.setChecked(false);
                    menuActivity.stopService();
                    KeyguardManager keyguardManager = (KeyguardManager) menuActivity.getSystemService(Activity.KEYGUARD_SERVICE);
                    KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
                    lock.reenableKeyguard();
                } else {
                    KeyguardManager keyguardManager = (KeyguardManager) menuActivity.getSystemService(Activity.KEYGUARD_SERVICE);
                    KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
                    lock.disableKeyguard();
                    checkBoxOpen.setChecked(true);
                    menuActivity.startService();
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyService.class);
                    getActivity().startService(intent);
                }
                break;
            case R.id.checkBoxSound:
                if (checkBoxSound.isChecked()) {
                    checkBoxSound.setChecked(false);
                    editor.putInt(menuActivity.SOUND, menuActivity.CLOSE);
                    editor.commit();
                } else {
                    checkBoxSound.setChecked(true);
                    editor.putInt(menuActivity.SOUND, menuActivity.OPEN);
                    editor.commit();
                }
                menuActivity.refesService();
                break;
            case R.id.checkBoxVibratea:
                if (checkBoxVibrate.isChecked()) {
                    checkBoxVibrate.setChecked(false);
                    editor.putInt(menuActivity.VIBRETE, menuActivity.CLOSE);
                    editor.commit();
                } else {
                    checkBoxVibrate.setChecked(true);
                    editor.putInt(menuActivity.VIBRETE, menuActivity.OPEN);
                    editor.commit();
                }
                menuActivity.refesService();
                break;
        }
    }
}
