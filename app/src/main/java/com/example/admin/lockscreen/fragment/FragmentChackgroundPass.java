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

public class FragmentChackgroundPass extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private MenuActivity menuActivity;
    private GridView gridView;
    private Button button;
    private int img[] = {R.drawable.bg, R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10};
    private int index;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<ItemImage> itemImages;
    private AdapterImage adapterImage;
    private View linearLayout;

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
        linearLayout = view.findViewById(R.id.bg);
        linearLayout.setBackgroundResource(MenuActivity.IMG[sharedPreferences.getInt(menuActivity.INDEX2, 0)]);
        itemImages = new ArrayList<>();
        itemImages.add(new ItemImage(1, new int[]{img[0]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[1]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[2]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[3]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[4]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[5]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[6]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[7]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[8]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[9]}, false));
        itemImages.add(new ItemImage(1, new int[]{img[10]}, false));
        index = sharedPreferences.getInt(menuActivity.INDEX2, 0);
        itemImages.get(index).setChose(true);
        gridView = view.findViewById(R.id.gvImg);
        button = view.findViewById(R.id.btnHuy);
        button.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        adapterImage = new AdapterImage(menuActivity, itemImages);
        gridView.setAdapter(adapterImage);
    }

    @Override
    public void onClick(View v) {
        menuActivity.switchFragment(menuActivity.getFragmentMenu());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        linearLayout.setBackgroundResource(MenuActivity.IMG[index]);
        index = position;
        for (ItemImage itemImage : itemImages) {
            itemImage.setChose(false);
        }
        itemImages.get(index).setChose(true);
        adapterImage.notifyDataSetChanged();
        editor.putInt(menuActivity.INDEX2, index);
        editor.commit();
        menuActivity.refesService();
    }
}
