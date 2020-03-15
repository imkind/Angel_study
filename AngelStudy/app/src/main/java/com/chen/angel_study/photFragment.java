package com.chen.angel_study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class photFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 将layout布局转换成View
        View view = inflater.inflate(R.layout.frag, container);
        TextView img=(TextView)view.findViewById(R.id.ima);
        img.setText("shiya");
        return view;

    }
}