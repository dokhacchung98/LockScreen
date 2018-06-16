package com.example.admin.lockscreen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.R;

import java.util.List;

/**
 * Created by Admin on 1/16/2018.
 */

public class FragmentPassChangePatternt extends Fragment implements View.OnClickListener, PatternLockViewListener {
    private View view;
    private MenuActivity menuActivity;
    private PatternLockView patternLockView;
    private Button btnButton;
    private TextView textView;
    private SharedPreferences sharedPreferences;

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
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        LinearLayout linearLayout = view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2, 0)]);   btnButton = view.findViewById(R.id.btnHuyPat);
        textView = view.findViewById(R.id.txt);
        patternLockView = view.findViewById(R.id.pat);
        patternLockView.addPatternLockListener(this);
        btnButton.setOnClickListener(this);
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
        menuActivity.setPassTemp(PatternLockUtils.patternToString(patternLockView, pattern));
        menuActivity.switchFragment(menuActivity.getFragmentPassChange2Patternt());
        patternLockView.clearPattern();
    }

    @Override
    public void onCleared() {

    }
}
