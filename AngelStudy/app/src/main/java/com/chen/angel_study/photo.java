package com.chen.angel_study;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class photo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new photFragment()).commit();


    }
}