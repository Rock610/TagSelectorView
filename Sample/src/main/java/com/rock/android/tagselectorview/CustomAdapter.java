package com.rock.android.tagselectorview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by rock on 16/7/5.
 */
public class CustomAdapter extends BaseAdapter {

    private List<MyDataBean> dataBeen;
    private Context ctx;

    public CustomAdapter(List<MyDataBean> dataBeen, Context ctx) {
        this.dataBeen = dataBeen;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return dataBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
