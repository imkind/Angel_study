package com.chen.angel_study;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class wordAdpater extends ArrayAdapter<word>{

    public wordAdpater(Activity context, int resource, ArrayList<word> words){
        super(context,0,words);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listviewitem = convertView;
        if(listviewitem == null){
            listviewitem = LayoutInflater.from(getContext()).inflate(R.layout.file_item, parent,false);
        }

        word currentword = getItem(position);

        TextView nameView = (TextView)listviewitem.findViewById(R.id.text1);
        nameView.setText(currentword.outputnumber());

        TextView englishView = (TextView)listviewitem.findViewById(R.id.text2);
        englishView.setText(currentword.outenglish());

        return listviewitem;
    }
}