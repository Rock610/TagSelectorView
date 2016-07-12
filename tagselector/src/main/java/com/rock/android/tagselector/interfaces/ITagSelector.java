package com.rock.android.tagselector.interfaces;

import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public interface ITagSelector<T> {

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setData(List<T> list);

    void refresh();

    List<T> getData();

    String getCheckedName();

    void setOnItemClickListener(OnItemClickListener listener);

    T getDataByPosition(int position);

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


    void setListAdapter(SelectorAdapter adapter);
}
