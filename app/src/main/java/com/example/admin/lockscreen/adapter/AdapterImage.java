package com.example.admin.lockscreen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.admin.lockscreen.ItemImage;
import com.example.admin.lockscreen.R;

import java.util.ArrayList;

/**
 * Created by Admin on 1/15/2018.
 */

public class AdapterImage extends ArrayAdapter<ItemImage> {
    private ArrayList<ItemImage> itemImages;
    private LayoutInflater layoutInflater;

    public AdapterImage(@NonNull Context context, ArrayList<ItemImage> itemImages) {
        super(context, android.R.layout.simple_list_item_1,itemImages);
        layoutInflater = LayoutInflater.from(context);
        this.itemImages = itemImages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.chose_item_layout, parent, false);
        ImageView imageView = convertView.findViewById(R.id.imgChose);
        ImageView imgTick = convertView.findViewById(R.id.imgTick);
        ItemImage itemImage = itemImages.get(position);
        Log.e("AAA", "" + itemImage.isChose());
        imageView.setBackgroundResource(itemImage.getImg()[0]);
        if (itemImage.isChose()) {
            imgTick.setVisibility(View.VISIBLE);
        } else {
            imgTick.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
