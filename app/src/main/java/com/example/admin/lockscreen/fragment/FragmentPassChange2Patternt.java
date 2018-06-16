package com.example.admin.lockscreen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.R;

import java.util.List;

/**
 * Created by Admin on 1/16/2018.
 */

public class FragmentPassChange2Patternt extends Fragment implements View.OnClickListener, PatternLockViewListener {
    private View view;
    private MenuActivity menuActivity;
    private PatternLockView patternLockView;
    private Button btnButton;
    private TextView textView;
    private String pass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_pass_fragment, container, false);
        menuActivity = (MenuActivity) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        pass = menuActivity.getPassTemp();
        btnButton = view.findViewById(R.id.btnHuyPat);
        textView = view.findViewById(R.id.txt);
        patternLockView = view.findViewById(R.id.pat);
        patternLockView.addPatternLockListener(this);
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        LinearLayout linearLayout = view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2, 0)]);
        btnButton.setOnClickListener(this);
        textView.setText("Draw again password");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnHuyPat) {
            menuActivity.switchFragment(menuActivity.getFragmentMenu());
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        String t = PatternLockUtils.patternToString(patternLockView, pattern);
        if (t.equals(pass)) {
            Toast.makeText(menuActivity, "Set password success", Toast.LENGTH_SHORT).show();
            sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(menuActivity.PASSPATTERN, t);
            editor.putString(menuActivity.PASSSRING, "");
            editor.putString(menuActivity.PASSCODE, "");
            editor.commit();
            menuActivity.switchFragment(menuActivity.getFragmentMenu());
            menuActivity.refesService();
            patternLockView.clearPattern();
        } else {
            patternLockView.clearPattern();
            textView.setText("Password does not like");
            textView.setTextColor(Color.RED);
        }
    }

    @Override
    public void onCleared() {

    }
}
