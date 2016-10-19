package com.rock.android.tagselector.interfaces;

import android.view.View;

/**
 * Created by rock on 2016/10/18.
 */

public interface BaseAdapter {

    //tab的个数
    int getCount();

    /**
     * 注意 此View 中必须包含一个id 为theTextView的TextView
     * */
    View getTabView(int position);

    ITagSelector getDropDownView(int position);

    Object getItem(int tabPosition,int selectorPosition);

    boolean getChangeAfterClicked(int tabPosition);

    String getTagName(int tabPosition,int selectorPosition);

    String getDefaultTagName(int position);

}
