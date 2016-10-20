package com.rock.android.tagselector.interfaces;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by rock on 16/7/4.
 */
public interface ITagSelectorTabView {

    interface OnStatusChangedListener {
        void willDismiss(ITagSelectorTabView view);
        void dismissed(ITagSelectorTabView view);
        void willOpen(ITagSelectorTabView view);
        void opened(ITagSelectorTabView view);
    }

    interface OnViewClickListener{
        void onViewClick();
    }

    void setTag(String tag);

    void close();

    void close(boolean withAnim,boolean isCloseParent);

    void open();

    void toggle();

    void setup(FrameLayout selectorParent, FrameLayout wrapper,ITagSelector selectorView,View tabView);

    void setChangeAfterClick(boolean b);

    TextView getTextView();

    boolean isOpening();

    boolean isChangeTagAfterClicked();

    void setOnViewClickListener(OnViewClickListener onViewClickListener);

    void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener);

    View getContentView();

    ITagSelector getTagSelectorView();

}
