package com.rock.android.tagselector.interfaces;

import com.rock.android.tagselector.model.DataBean;

import java.util.List;

/**
 * Created by rock on 16/7/5.
 */
public interface SelectorAdapter {

    void setData(List<? extends DataBean> list);

    List<? extends DataBean> getData();

    void notifyDataSetChanged();

    int getItemHeight();
}
