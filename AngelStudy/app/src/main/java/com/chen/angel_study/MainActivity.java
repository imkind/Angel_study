package com.chen.angel_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView plans = (TextView) findViewById(R.id.textView_plan);

        plans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent planIntent = new Intent(MainActivity.this, plan.class);
                startActivity(planIntent);
            }
        });
    }
}
