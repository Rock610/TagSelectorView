package com.rock.android.tagselector.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rock.android.tagselector.R;

import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class BaseSelectorAdapter<T> extends BaseAdapter{

    protected List<T> mList;
    protected Activity mContext;

    public BaseSelectorAdapter(List<T> mList, Activity mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public BaseSelectorAdapter(Activity mContext) {
        this(null,mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0: mList.size();
    }

    public void addAll(List<T> list){
        if(mList == null){
            mList = list;
        }else{
            mList.addAll(list);
        }

        notifyDataSetChanged();
    }

    public void clear(){
        if(mList == null) return;

        mList.clear();
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mList;
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null:mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tag_selector,parent,false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tagNameTv);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.selectRB);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder,position,convertView,parent);

        return convertView;
    }

    protected void bindData(ViewHolder holder,int position, View convertView, ViewGroup parent){

    }

    public static class ViewHolder{
        public TextView text;
        public RadioButton radioButton;
    }
}
