package com.rock.android.tagselector.model;

import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class Tags {

    public List<? extends DataBean> tags;

    public String defaultTag;

    public boolean isSelectFirst;

    public boolean isChangeAfterClicked;

    public int layoutRes;

    public int textViewId;

    public Tags(List<? extends DataBean> tags, String defaultTag, boolean isSelectFirst, boolean isChangeAfterClicked) {
        this.tags = tags;
        this.defaultTag = defaultTag;
        this.isSelectFirst = isSelectFirst;
        this.isChangeAfterClicked = isChangeAfterClicked;
    }

    public Tags() {
    }

    public Tags setTags(List<DataBean> tags) {
        this.tags = tags;
        return this;
    }

    public Tags setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
        return this;
    }

    public Tags setSelectFirst(boolean selectFirst) {
        isSelectFirst = selectFirst;
        return this;
    }

    public Tags setChangeAfterClicked(boolean changeAfterClicked) {
        isChangeAfterClicked = changeAfterClicked;
        return this;
    }

    public Tags setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
        return this;
    }

    public Tags setTextViewId(int textViewId) {
        this.textViewId = textViewId;
        return this;
    }
}
