package com.chen.angel_study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class setFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // 将layout布局转换成View
            View rootview = inflater.inflate(R.layout.activity_set, container,false);

            return rootview;

        }

}
