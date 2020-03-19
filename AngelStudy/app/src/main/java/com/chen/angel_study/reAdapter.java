package com.chen.angel_study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class reAdapter extends RecyclerView.Adapter<reAdapter.MyViewHolder>implements IItemTouchHelperAdapter {

    private ArrayList<word> mwordlist;
    private  Context context;

    public reAdapter(Context context, ArrayList<word> wordList) {
        this.context= context;
        mwordlist = wordList;
    }

    public void setDatas(ArrayList<word> data) {
        mwordlist.clear();
        this.mwordlist.addAll(data);
        notifyDataSetChanged();
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        word lists = mwordlist.get(position);
        holder.Image.setText(lists.outputnumber());
        holder.Number.setText(lists.outenglish());

        holder.Image.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                if (onItemClickListener != null)
                onItemClickListener.onItemClick(pos);
            }
        });

        holder.Number.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(view, pos);
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mwordlist.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mwordlist, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDismiss(int position) {
        mwordlist.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Image;
        TextView Number;
        public MyViewHolder(View itemView) {
            super(itemView);
            Image = (TextView) itemView.findViewById(R.id.text1);
            Number = (TextView) itemView.findViewById(R.id.text2);



        }

    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public interface IItemTouchHelperAdapter {

        void onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }



}

