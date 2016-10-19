package com.rock.android.tagselector.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rock.android.tagselector.R;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;
import com.rock.android.tagselector.model.DataBean;

import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class SimpleSingleSelectListView extends ListView implements ITagSelector {

    private SimpleAdapter mAdapter;

    private ITagSelectorTabView tabView;

    private ITagSelector.OnItemClickListener onItemClickListener;

    @Override
    public void setOnItemClickListener(ITagSelector.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SimpleSingleSelectListView(Context context) {
        super(context);
        init();
    }

    public SimpleSingleSelectListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleSingleSelectListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        if (isInEditMode()) return;

        setBackgroundColor(Color.WHITE);
        setChoiceMode(CHOICE_MODE_SINGLE);
        setDividerHeight(1);
        setDivider(getResources().getDrawable(R.drawable.horizontal_line));
        mAdapter = new SimpleAdapter((Activity) getContext());
        setAdapter(mAdapter);

        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(tabView.isChangeTagAfterClicked()){
                    tabView.setTag(mAdapter.getData().get(position).name);
                }

                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public String getCheckedName() {

        if (getCheckedItemPosition() != INVALID_POSITION) {
            return mAdapter.getData().get(getCheckedItemPosition()).name;
        }
        return "";
    }

    @Override
    public DataBean getCurrentItem(int position) {
        return mAdapter.getData().get(position);
    }

    @Override
    public void insert(int position, Object o) {
        mAdapter.getData().add(position, (DataBean) o);
    }

    @Override
    public void remove(int position) {
        mAdapter.getData().remove(position);
    }

    @Override
    public void update(int position, Object o) {
        mAdapter.getData().set(position, (DataBean) o);
    }

    @Override
    public View getAdapterView() {
        return this;
    }

    @Override
    public void setDefaultItem(int position) {
        if(getCheckedItemPosition() == INVALID_POSITION){
            setItemChecked(position,true);
        }
    }

    @Override
    public BaseAdapter getListAdapter() {
        return mAdapter;
    }

    @Override
    public int selectedPosition() {
        return getCheckedItemPosition();
    }

    @Override
    public int itemHeight() {
        return 50;
    }

    @Override
    public int getAnimHeight() {
        return 250;
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void setTabView(ITagSelectorTabView view) {
        tabView = view;
    }

    public class SimpleAdapter extends BaseSelectorAdapter<DataBean>{

        public SimpleAdapter(List<DataBean> mList, Activity mContext) {
            super(mList, mContext);
        }

        public SimpleAdapter(Activity mContext) {
            super(mContext);
        }

        @Override
        protected void bindData(ViewHolder holder, int position, View convertView, ViewGroup parent) {
            super.bindData(holder, position, convertView, parent);

            holder.text.setText(mList.get(position).name);
        }

        public void setData(List<DataBean> list) {
            if(mList == null){
                mList = list;
            }else{
                mList.clear();
                mList.addAll(list);
            }
        }

        public List<DataBean> getData() {
            return mList;
        }

        public int getItemHeight() {
            return 50;
        }
    }

}
