package com.chen.angel_study;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class planFragment extends Fragment {

    public planFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<word> words = new ArrayList<word>();
        View rootView = inflater.inflate(R.layout.activity_plan, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        reAdapter adapter = new reAdapter(getActivity(),words);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);



        item wordlist = (item)getActivity().getApplication();

        words.add(new word("1", "one"));
        words.add(new word("2", "two"));
        words.add(new word("3", "three"));
        words.add(new word("4", "four"));
        words.add(new word("5", "five"));
        words.add(new word("6", "six"));
        words.add(new word("7", "seven"));
        words.add(new word("8", "eight"));
        words.add(new word("5", "five"));
        words.add(new word("6", "six"));
        words.add(new word("7", "seven"));
        words.add(new word("8", "eight"));
        if(wordlist.returnwordt()==2){
            words.add(new word(wordlist.returnword1(),wordlist.returnword2()));

        }

//        wordAdpater itemsAdapter = new wordAdpater(getActivity(), 0, words);
//
//        ListView listView = (ListView) rootView.findViewById(R.id.lists);

//        listView.setAdapter(itemsAdapter);
        adapter.setOnItemClickListener(new reAdapter.OnItemClickListener() {

            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "click " +position, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new reAdapter.OnItemLongClickListener() {

            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long click "+position,Toast.LENGTH_SHORT).show();
            }
        });





        recyclerView.setAdapter(adapter);
        return rootView;
    }


}
