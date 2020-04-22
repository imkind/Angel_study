package com.chen.angel_study.Activitys;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.Listen.IItemTouchHelperAdapter;
import com.chen.angel_study.MainActivity;
import com.chen.angel_study.Manager.ClockManager;
import com.chen.angel_study.Manager.PlanManager;
import com.chen.angel_study.Plans.Constants;
import com.chen.angel_study.Plans.Plan;
import com.chen.angel_study.R;
import com.chen.angel_study.receive.ClockReceive;
import com.chen.angel_study.service.ClockService;
import com.chen.angel_study.util.DateUtil;
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
    private ClockManager mClockManager = ClockManager.getInstance();


    private Switch aSwitch_sound;
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID

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

    private PendingIntent buildIntent(int id) {
        Intent intent = new Intent();
        intent.putExtra(ClockReceive.EXTRA_PLAN_ID, id);
        intent.setClass(mContext, ClockService.class);

        return PendingIntent.getService(mContext, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(mContext, R.raw.tishi, 1);
    }

    private void playSound() {
        soundPool.play(
                soundID,
                0.1f,      //左耳道音量【0~1】
                0.5f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1          //播放速度【1是正常，范围从0~2】
        );
    }


    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, int position) {

        final Plan plan = mDatabases.get(position);
        holder.tPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(((Integer)holder.tPlanIcon.getTag() == 0))
//                {

                    playSound();
                Log.d("sssssssssssss", "onClick: ");
                    if(plan.getmChose() == 0){
                        plan.setmChose(Constants.PlanFlag.IMPORTANT);
                        mClockManager.cancelAlarm(buildIntent(plan.getmId()));
                        mEventManager.SavePlan(plan);
                        mDatabases = mEventManager.findAll();
                        notifyDataSetChanged();
                }
            }
//            }
        });
        if(plan.getmChose() == 0) {
            //根据不同的状态渲染不同的图片
            if (plan.getmIsImportant() == Constants.PlanFlag.IMPORTANT) {
                holder.tPlanIcon.setImageResource(R.drawable.rciecle);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.tTitle.setTextColor(mContext.getResources().getColorStateList(R.color.red1));
            } else {
                holder.tPlanIcon.setImageResource(R.drawable.bcircle);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.tTitle.setTextColor(mContext.getResources().getColorStateList(R.color.blue1));
            }
            holder.tTitle.setText(plan.getmTitle());

        }else{
            if (plan.getmIsImportant() == Constants.PlanFlag.IMPORTANT) {
                holder.tPlanIcon.setImageResource(R.drawable.hciecle);
                holder.tEdit.setImageResource(R.drawable.hedite);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                holder.tPlanIcon.setImageResource(R.drawable.hciecle);
                holder.tEdit.setImageResource(R.drawable.hedite);
                holder.tTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            }
            holder.tTitle.setText(plan.getmTitle());
            holder.tTitle.setTextColor(mContext.getResources().getColorStateList(R.color.gray0));

        }

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
        holder.setIsRecyclable(false);
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
        return mDatabases == null ? 0 : mDatabases.size();
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
//        @BindView(R.id.check)
//        RadioButton tCheck;


        public PlanViewHolder(View itemView) {
            super(itemView);
            tPlanIcon = (ImageView) itemView.findViewById(R.id.iv_memo);
            tTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tEdit = (ImageView) itemView.findViewById(R.id.iv_edit);
            tPlanIcon.setTag(Constants.PlanIconTag.FIRST);
//            tCheck = (RadioButton)itemView.findViewById(R.id.check);
            initSound();
        }

        public View getItemView() {
            return this.itemView;
        }
    }
}