package com.chen.angel_study;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.Activitys.BaseActivity;
import com.chen.angel_study.Activitys.PlanDetailActivity;
import com.chen.angel_study.Activitys.RecyclerViewAdapter;
import com.chen.angel_study.Activitys.photoFragment;
import com.chen.angel_study.Activitys.planFragment;
import com.chen.angel_study.Activitys.setFragment;
import com.chen.angel_study.Manager.PlanManager;
import com.ddz.floatingactionbutton.FloatingActionButton;
import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.OnClick;


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

    private RecyclerViewAdapter mAdapter;
    private PlanManager mPlanManger = PlanManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        planfragment = new planFragment();
        photofragment = new photoFragment();
        setfragment = new setFragment();

        initFragment();
        FabClick();
    }
    public void FabClick() {

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PlanDetailActivity.class);
                intent.putExtra(PlanDetailActivity.EXTRA_ADD_EVENT, true);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent();
            intent.setClass(this, PlanDetailActivity.class);
            intent.putExtra(PlanDetailActivity.EXTRA_ADD_EVENT, true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAdapter.setDatabases(mPlanManger.getPlans());
    }

}

