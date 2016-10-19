package com.rock.android.tagselector.interfaces;

import android.view.View;

/**
 * Created by rock on 16/7/4.
 */
public interface ITagSelector {

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    void refresh();

    String getCheckedName();

    void setOnItemClickListener(OnItemClickListener listener);

    int selectedPosition();

    /**
     * item的高度，默认以itemHeight*itemCount 为动画行驶的高度
     * */
    int itemHeight();
    /**
     * 自定义动画行驶的高度
     * */
    int getAnimHeight();

    int getCount();

    void show();

    void hide();

    void setTabView(ITagSelectorTabView view);

    Object getCurrentItem(int position);

    void insert(int position,Object o);

    void remove(int position);

    void update(int position,Object o);

    View getAdapterView();

    void setDefaultItem(int position);

    android.widget.BaseAdapter getListAdapter();

}
