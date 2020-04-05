package com.chen.angel_study;

<<<<<<< Updated upstream
import android.content.Intent;
=======
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream

public class MainActivity extends AppCompatActivity {

    @Override
=======
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.chen.angel_study.data.PlanContract;
import com.chen.angel_study.data.PlanContract.PlanEntry;
import com.chen.angel_study.data.PlanDpHelper;
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

    private PlanDpHelper mDbHelper;

>>>>>>> Stashed changes
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
=======
        planfragment = new planFragment();
        photofragment = new photoFragment();
        setfragment = new setFragment();

        mDbHelper = new PlanDpHelper(this);


        initFragment();
//        intputt();


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
>>>>>>> Stashed changes

        TextView plans = (TextView) findViewById(R.id.textView_plan);

        plans.setOnClickListener(new View.OnClickListener() {

<<<<<<< Updated upstream
            @Override
            public void onClick(View view) {
                Intent planIntent = new Intent(MainActivity.this, plan.class);
                startActivity(planIntent);
            }
        });
=======
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

                                String planString= userInput.getText().toString();
                                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(PlanEntry.COLUMN_NAME_PLAN,planString);
                                values.put(PlanEntry.COLUMN_NAME_TIME,"wewr");
                                Uri newUri = getContentResolver().insert(PlanEntry.CONTENT_URI, values);
                                if (newUri == null) {
                                    Log.v("显示","ID");
                                } else {
                                    Log.v("显示","ID11");
                                }

//                                item wordlist = (item)getApplication();
//                                wordlist.setword(p,"0");


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

>>>>>>> Stashed changes
    }
}
