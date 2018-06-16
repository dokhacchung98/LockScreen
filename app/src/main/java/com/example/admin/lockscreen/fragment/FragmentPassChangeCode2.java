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
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.R;

/**
 * Created by Admin on 1/16/2018.
 */

public class FragmentPassChangeCode2 extends Fragment implements View.OnClickListener {
    private View view;
    private MenuActivity menuActivity;
    private Button btnButton;
    private TextView textView;
    private Button btnTiep;
    private EditText edt;
    private String pass = "";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pass_text_fragment, container, false);
        menuActivity = (MenuActivity) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        LinearLayout linearLayout = view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2, 0)]);
        btnButton = view.findViewById(R.id.btnHuy);
        btnTiep = view.findViewById(R.id.btnXong);
        edt = view.findViewById(R.id.edt);
        edt.setText("");
        textView = view.findViewById(R.id.txt);
        btnButton.setOnClickListener(this);
        btnTiep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHuy:
                menuActivity.switchFragment(menuActivity.getFragmentMenu());
                break;
            case R.id.btnXong:
                pass = edt.getText().toString().trim();
                if (!pass.isEmpty()) {
                    sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
                    String a = menuActivity.getPassTempNumber();
                    if (a.equals(pass)) {
                        editor = sharedPreferences.edit();
                        editor.putString(menuActivity.PASSSRING, pass);
                        editor.putString(menuActivity.PASSPATTERN, "");
                        editor.putString(menuActivity.PASSCODE, "");
                        editor.commit();
                        Toast.makeText(menuActivity, "Set password success", Toast.LENGTH_SHORT).show();
                        menuActivity.switchFragment(menuActivity.getFragmentMenu());
                        menuActivity.refesService();
                    } else {
                        edt.setText("");
                        textView.setText("Wrong password");
                        textView.setTextColor(Color.RED);
                    }
                }
                break;
        }
        Log.e("PA", pass);
    }
}
