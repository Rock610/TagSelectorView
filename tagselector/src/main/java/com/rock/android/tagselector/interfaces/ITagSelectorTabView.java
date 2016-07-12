package com.rock.android.tagselector.interfaces;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rock.android.tagselector.model.Tags;

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

    void close(boolean withAnim);

    void open();

    void toggle();

    void setup(Tags tags, FrameLayout selectorParent, FrameLayout wrapper);

    void setup(Tags tags, FrameLayout selectorParent, FrameLayout wrapper,ITagSelector selectorView);

    TextView getTextView();

    boolean isOpening();

    boolean isChangeTagAfterClicked();

    void setOnViewClickListener(OnViewClickListener onViewClickListener);

    void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener);

    View getContentView();

    ITagSelector getTagSelectorView();

    void setWrapperHeight(int height);
}
