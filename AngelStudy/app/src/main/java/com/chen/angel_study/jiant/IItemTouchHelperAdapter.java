package com.chen.angel_study.jiant;

public interface IItemTouchHelperAdapter {
    /**
     * 当item被移动时调用
     *
     * @param fromPosition item的起点
     * @param toPosition   item的终点
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 当item被侧滑时调用
     *
     * @param position 被侧滑的item的position
     */
    void onItemDismiss(int position);
}