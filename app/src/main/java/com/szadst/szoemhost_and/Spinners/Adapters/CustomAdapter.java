package com.szadst.szoemhost_and.Spinners.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szadst.szoemhost_and.R;
import com.szadst.szoemhost_and.Spinners.CustomItem;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    public CustomAdapter(@NonNull Context context, ArrayList<CustomItem> customList) {
        super(context, 0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }

        CustomItem item = (CustomItem) getItem(position);
        ImageView spinnerIV = (ImageView)convertView.findViewById(R.id.ivSpinnerLayout);
        TextView spinnerTV = (TextView)convertView.findViewById(R.id.tvSpinnerLayout);
        if (item != null) {
            spinnerIV.setImageResource(item.getSpinnerItemImage());
            spinnerTV.setText(item.getSpinnerItemName());
        }
        return convertView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cudtom_dropdown_layout, parent, false);
        }

        CustomItem item = (CustomItem) getItem(position);
        ImageView dropDownIV = (ImageView) convertView.findViewById(R.id.ivDropDownLayout);
        TextView droDownTV =(TextView) convertView.findViewById(R.id.tvDropDownLayout);
        if (item != null) {
            dropDownIV.setImageResource(item.getSpinnerItemImage());
            droDownTV.setText(item.getSpinnerItemName());
        }
        return convertView;    }
}
