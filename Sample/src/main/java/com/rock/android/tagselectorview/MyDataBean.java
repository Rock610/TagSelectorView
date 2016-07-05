package com.rock.android.tagselectorview;

import com.rock.android.tagselector.model.DataBean;

/**
 * Created by rock on 16/7/4.
 */
public class MyDataBean extends DataBean {

    int id;

    public MyDataBean(String name) {
        super(name);
    }

    public MyDataBean(String name, int id) {
        super(name);
        this.id = id;
    }
}
