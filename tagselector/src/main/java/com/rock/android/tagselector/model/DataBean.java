package com.rock.android.tagselector.model;

import java.io.Serializable;

/**
 * Created by rock on 16/7/4.
 *
 * 模拟后台来的数据
 */
public class DataBean implements Serializable{


    private static final long serialVersionUID = 4602009937739085873L;

    public DataBean(String name) {
        this.name = name;
    }

    public DataBean() {
    }

    public String name;
}
