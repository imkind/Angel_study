package com.chen.angel_study;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class plan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        ArrayList<word> words = new ArrayList<word>();
        words.add(new word("1", "one"));
        words.add(new word("2", "two"));
        words.add(new word("3", "three"));
        words.add(new word("4", "four"));
        words.add(new word("5", "five"));
        words.add(new word("6", "six"));
        words.add(new word("7", "seven"));
        words.add(new word("8", "eight"));
        words.add(new word("9", "nine"));


        wordAdpater itemsAdapter = new wordAdpater(this, 0, words);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
