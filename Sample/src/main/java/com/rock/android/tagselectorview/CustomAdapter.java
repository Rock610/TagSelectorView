package com.rock.android.tagselectorview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rock.android.tagselector.interfaces.SelectorAdapter;
import com.rock.android.tagselector.model.DataBean;

import java.util.List;

/**
 * Created by rock on 16/7/5.
 */
public class CustomAdapter extends BaseAdapter implements SelectorAdapter{

    private List<DataBean> dataBeen;
    private Context ctx;

    public CustomAdapter(List<DataBean> dataBeen, Context ctx) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_selector,parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.textView);
        tv.setText(dataBeen.get(position).name);

        return convertView;
    }

    @Override
    public void setData(List<? extends DataBean> list) {
        dataBeen = (List<DataBean>) list;
    }

    @Override
    public List<DataBean> getData() {
        return dataBeen;
    }

    @Override
    public int getItemHeight() {
        return 40;
    }
}
