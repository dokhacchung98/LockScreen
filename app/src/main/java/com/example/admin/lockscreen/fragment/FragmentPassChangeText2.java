package com.example.admin.lockscreen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.R;

/**
 * Created by Admin on 1/16/2018.
 */

public class FragmentPassChangeText2 extends Fragment implements View.OnClickListener {
    private View view;
    private MenuActivity menuActivity;
    private Button btnButton;
    private EditText txt1, txt2, txt3, txt4;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnXoa, btnXong;
    private String pass = "";
    private int dem = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pass_fragment_code, container, false);
        menuActivity = (MenuActivity) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        RelativeLayout linearLayout = view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2, 0)]);
        textView = view.findViewById(R.id.txt);
        btnButton = view.findViewById(R.id.btnHuy);
        txt1 = view.findViewById(R.id.edt1);
        txt2 = view.findViewById(R.id.edt11);
        txt3 = view.findViewById(R.id.edt12);
        txt4 = view.findViewById(R.id.edt13);
        btn0 = view.findViewById(R.id.btn0);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);
        btn8 = view.findViewById(R.id.btn8);
        btn9 = view.findViewById(R.id.btn9);
        btnXoa = view.findViewById(R.id.btnXoa);
        btnXong = view.findViewById(R.id.btnXong);
        pass = "";
        dem = 0;
        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
        txt4.setText("");
        btnButton.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHuy:
                menuActivity.switchFragment(menuActivity.getFragmentMenu());
                break;
            case R.id.btn0:
                if (dem < 4) {
                    pass += "0";
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
                    pass += "1";
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
                    pass += "2";
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
                    pass += "3";
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
                    pass += "4";
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
                    pass += "5";
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
                    pass += "6";
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
                    pass += "7";
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
                    pass += "8";
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
                    pass += "9";
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
                    pass = pass.substring(0, pass.length() - 1);
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
                if (pass.length() == 4) {
                    sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
                    String a = menuActivity.getPassTempText();
                    if (pass.equals(a)) {
                        editor = sharedPreferences.edit();
                        editor.putString(menuActivity.PASSCODE, pass);
                        editor.putString(menuActivity.PASSPATTERN, "");
                        editor.putString(menuActivity.PASSSRING, "");
                        editor.commit();
                        menuActivity.setPassTempText(pass);
                        menuActivity.switchFragment(menuActivity.getFragmentMenu());
                        Toast.makeText(menuActivity, "Set password success", Toast.LENGTH_SHORT).show();
                        menuActivity.refesService();
                    } else {
                        textView.setTextColor(Color.RED);
                        textView.setText("Wrong password");
                    }
                }
                break;
        }
        Log.e("PA", pass);
    }
}
