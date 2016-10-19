package com.rock.android.tagselector.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class Tags implements Serializable{

    private static final long serialVersionUID = -1371154113061363482L;
    public List<DataBean> tags;

    public Tags(List<DataBean> tags) {
        this.tags = tags;
    }

    public Tags() {
    }


    public Tags setTags(List<DataBean> tags) {
        this.tags = tags;
        return this;
    }
}
