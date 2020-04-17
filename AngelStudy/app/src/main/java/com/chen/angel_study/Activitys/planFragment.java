package com.chen.angel_study.Activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.Listen.MyItemTouchHelperCallback;
import com.chen.angel_study.MainActivity;
import com.chen.angel_study.Manager.ClockManager;
import com.chen.angel_study.Manager.PlanManager;
import com.chen.angel_study.R;
import com.chen.angel_study.util.Toasts;


public class planFragment extends Fragment {


    private RecyclerViewAdapter mAdapter;
    private PlanManager mPlanManger = PlanManager.getInstance();
    private ClockManager mClockManager = ClockManager.getInstance();

    public planFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_plan, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mAdapter.setDatabases(mPlanManger.findAll());
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(mAdapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        setListener();
        return rootView;
    }


    protected void setListener() {
        mAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    private RecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), PlanDetailActivity.class);
            intent.putExtra(PlanDetailActivity.EXTRA_EDIT_EVENT, false);
            intent.putExtra(PlanDetailActivity.EXTRA_PLAN_DATA, mAdapter.getDatabases().get(position));
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toasts.showToast("Long clicked");
        }
    };


}
