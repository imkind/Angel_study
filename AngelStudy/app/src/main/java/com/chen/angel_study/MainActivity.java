package com.chen.angel_study;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private planFragment planfragment;
    private photoFragment photofragment;
    private setFragment setfragment;

    int lastfragment = 0;
    private Fragment[] fragments;
    private int lastFragment;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planfragment = new planFragment();
        photofragment = new photoFragment();
        setfragment = new setFragment();


        initFragment();
        intputt();

//        ImageButton numbers = (ImageButton) findViewById(R.id.bottom1);
//
//        numbers.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
//                startActivity(numbersIntent);
//            }
//        });



    }

    private void initFragment() {

        planfragment = new planFragment();
        photofragment = new photoFragment();
        setfragment = new setFragment();
        fragments = new Fragment[]{planfragment, photofragment, setfragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, planfragment).show(planfragment).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navi_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.id1: {
                            if (lastfragment != 0) {
                                switchFragment(lastfragment, 0);
                                lastfragment = 0;

                            }
                            return true;
                        }
                        case R.id.id2: {
                            if (lastfragment != 1) {
                                switchFragment(lastfragment, 1);
                                lastfragment = 1;
                            }
                            return true;
                        }
                        case R.id.id3: {
                            if (lastfragment != 2) {
                                switchFragment(lastfragment, 2);
                                lastfragment = 2;
                            }
                            return true;
                        }
                    }
                    return false;
                }
            };


    private void switchFragment(int lastfragment, int de) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if (fragments[de].isAdded() == false) {
            transaction.add(R.id.main_view, fragments[de]);
        }
        transaction.show(fragments[de]).commitAllowingStateLoss();
    }


    private void intputt(){

        final Context mContext = this;


       ImageButton button  = (ImageButton) findViewById(R.id.bottom1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.wenben,null);
                final EditText userInput = (EditText) view.findViewById(R.id.shuru);
                Button button2 = (Button) view.findViewById(R.id.ok);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setView(view);

                alertDialogBuilder.setCancelable(true).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String p= userInput.getText().toString();
                                item wordlist = (item)getApplication();

                                wordlist.setword(p,"0");

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

//                button2.setOnClickListener(new View.OnClickListener(){
//                    public void onClick(View v) {
//
//                        item words = (item)getApplicationContext();
//                        words.inwords( userInput.getText().toString(),"0");
//
//                    }
//                });

            }
        });



    }

}