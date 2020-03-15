package com.chen.angel_study;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class planFragment extends Fragment {

    public planFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_plan, container, false);
//        ArrayList<word> words = new ArrayList<word>();
//        words.add(new word("1", "one"));
//        words.add(new word("2", "two"));
//        words.add(new word("3", "three"));
//        words.add(new word("4", "four"));
//        words.add(new word("5", "five"));
//        words.add(new word("6", "six"));
//        words.add(new word("7", "seven"));
//        words.add(new word("8", "eight"));
//        words.add(new word("9", "nine"));

//        wordAdpater itemsAdapter = new wordAdpater(getActivity(), 0, words);
//
//        ListView listView = (ListView) rootView.findViewById(R.id.list);
//        listView.setAdapter(itemsAdapter);

        return rootView;
    }


}
