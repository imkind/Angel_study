package com.chen.angel_study.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.chen.angel_study.R;

public class photoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 将layout布局转换成View
        View rootview = inflater.inflate(R.layout.activity_photo, container,false);

        return rootview;

    }
}