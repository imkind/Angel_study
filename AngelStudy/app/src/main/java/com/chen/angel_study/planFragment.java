package com.chen.angel_study;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.data.PlanContract;
import com.chen.angel_study.data.PlanContract.PlanEntry;
import com.chen.angel_study.data.PlanDpHelper;
import com.chen.angel_study.jiant.MyItemTouchHelperCallback;

import java.util.ArrayList;

import static com.chen.angel_study.data.PlanContract.PlanEntry.COLUMN_NAME_PLAN;
import static com.chen.angel_study.data.PlanContract.PlanEntry.COLUMN_NAME_TIME;


public class planFragment extends Fragment {

    private PlanDpHelper mDbHelper;
    private SQLiteDatabase db;
    private reAdapter adapter;

    ArrayList<word> words = new ArrayList<word>();;

    public planFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_plan, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Cursor cursor = getActivity().getContentResolver().query(
                PlanEntry.CONTENT_URI, null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PLAN));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME));
                words.add(new word(title, content));

                Log.i("显示", "title:" + title);
                Log.i("显示", "content:" + content);

            }while(cursor.moveToNext());
        }
        cursor.close();

        words.add(new word("1", "one"));
        words.add(new word("2", "two"));
        adapter = new reAdapter(getActivity(), words);
        recyclerView.setAdapter(adapter);


        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);

//        item wordlist = (item)getActivity().getApplication();

        adapter.setOnItemClickListener(new reAdapter.OnItemClickListener() {

            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "click " + position, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new reAdapter.OnItemLongClickListener() {

            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }



    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mDbHelper = new PlanDpHelper(getActivity());

        intputt();

    }

    private void intputt(){

        final Context mContext = getActivity();

        ImageButton button  = (ImageButton) getActivity().findViewById(R.id.bottom1);
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
                                db = mDbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(COLUMN_NAME_PLAN,planString);
                                values.put(PlanEntry.COLUMN_NAME_TIME,"wewr");
                                Uri newUri = getActivity().getContentResolver().insert(PlanEntry.CONTENT_URI, values);
                                if (newUri == null) {
                                    Log.d("显示","ID");
                                } else {
                                    Log.d("显示","ID11");
                                }

//                                item wordlist = (item)getApplication();
//                                wordlist.setword(p,"0");
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }


}
