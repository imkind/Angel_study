package com.chen.angel_study.Activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.Listen.IItemTouchHelperAdapter;
import com.chen.angel_study.Manager.PlanManager;
import com.chen.angel_study.Plans.Constants;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.R;
import com.chen.angel_study.util.Toasts;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlanViewHolder>implements IItemTouchHelperAdapter {
    //数据源
    private List<Plan> mDatabases;

    private Context mContext;
    //点击事件
    private OnItemClickListener mOnItemClickListener;

    private PlanManager mEventManager = PlanManager.getInstance();


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    //构造器
    public RecyclerViewAdapter(Context context) {
        super();
        mContext = context;
    }


    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.iterm_layout, parent, false);
        return new PlanViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, int position) {
        final Plan plan = mDatabases.get(position);
        //根据不同的状态渲染不同的图片
            if (plan.getmIsImportant() == Constants.PlanFlag.IMPORTANT) {
                holder.tPlanIcon.setImageResource(R.drawable.mark2);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                holder.tPlanIcon.setImageResource(R.drawable.mark1);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        holder.tTitle.setText(plan.getmTitle());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });

        //点击编辑按钮，跳屏
        holder.tEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PlanDetailActivity.class);
                intent.putExtra(PlanDetailActivity.EXTRA_EDIT_EVENT, true);
                intent.putExtra(PlanDetailActivity.EXTRA_PLAN_DATA, plan);
                mContext.startActivity(intent);
            }
        });
    }

    public void setDatabases(List<Plan> plans) {
        mDatabases = plans;
        //设置数据源，重新渲染一次
        notifyDataSetChanged();
    }

    public List<Plan> getDatabases() {
        return mDatabases;
    }

    @Override
    public int getItemCount() {
        return mDatabases.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDatabases, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

    }
    //滑动删除
    @Override
    public void onItemDismiss(int position) {
        final Plan event = mDatabases.get(position);
        if (mEventManager.removeEvent(event.getmId())) {
            Toasts.showToast("删除成功");
        }
        mDatabases.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_memo)
        ImageView tPlanIcon;
        @BindView(R.id.tv_title)
        TextView tTitle;
        @BindView(R.id.iv_edit)
        ImageView tEdit;

        public PlanViewHolder(View itemView) {
            super(itemView);
            tPlanIcon = (ImageView) itemView.findViewById(R.id.iv_memo);
            tTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tEdit = (ImageView) itemView.findViewById(R.id.iv_edit);
            tPlanIcon.setTag(Constants.PlanIconTag.FIRST);
        }

        public View getItemView() {
            return this.itemView;
        }
    }
}