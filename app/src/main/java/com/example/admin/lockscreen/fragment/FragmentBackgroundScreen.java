package com.example.admin.lockscreen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.admin.lockscreen.adapter.AdapterImage;
import com.example.admin.lockscreen.ItemImage;
import com.example.admin.lockscreen.MenuActivity;
import com.example.admin.lockscreen.R;

import java.util.ArrayList;

/**
 * Created by Admin on 1/16/2018.
 */

public class FragmentBackgroundScreen extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private MenuActivity menuActivity;
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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int viTri;
    private ArrayList<ItemImage> itemImages;
    private AdapterImage adapterImage;
    private GridView gridView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_background_pass_fragment, container, false);
        menuActivity = (MenuActivity) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = menuActivity.getSharedPreferences(menuActivity.DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        viTri = sharedPreferences.getInt(menuActivity.INDEX, 0);
        LinearLayout linearLayout=view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2,0)]);
        gridView = view.findViewById(R.id.gvImg);
        itemImages = new ArrayList<>();
        itemImages.add(new ItemImage(12, img, false));
        itemImages.add(new ItemImage(9, img2, false));
        itemImages.add(new ItemImage(9, img3, false));
        itemImages.add(new ItemImage(9, img4, false));
        itemImages.add(new ItemImage(9, img5, false));
        itemImages.get(viTri).setChose(true);
        adapterImage = new AdapterImage(menuActivity, itemImages);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(adapterImage);
        button=view.findViewById(R.id.btnHuy);
        button.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (ItemImage itemImage : itemImages)
            itemImage.setChose(false);
        itemImages.get(position).setChose(true);
        adapterImage.notifyDataSetChanged();
        editor.putInt(menuActivity.INDEX, position);
        editor.commit();
        menuActivity.refesService();

    }

    @Override
    public void onClick(View v) {
        menuActivity.switchFragment(menuActivity.getFragmentMenu());
    }
}
