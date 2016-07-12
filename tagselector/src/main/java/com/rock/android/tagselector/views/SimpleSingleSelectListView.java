package com.rock.android.tagselector.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.rock.android.tagselector.R;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;
import com.rock.android.tagselector.interfaces.SelectorAdapter;
import com.rock.android.tagselector.model.DataBean;

import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class SimpleSingleSelectListView extends ListView implements ITagSelector<DataBean> {

//    private List<DataBean> mListData;
    private SelectorAdapter mAdapter;

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

    private void init() {
        if (isInEditMode()) return;

        setBackgroundColor(Color.WHITE);
        setChoiceMode(CHOICE_MODE_SINGLE);
        setDividerHeight(1);
        setDivider(getResources().getDrawable(R.drawable.horizontal_line));
        mAdapter = new SimpleAdapter((Activity) getContext());
        setAdapter((SimpleAdapter) mAdapter);

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
    public void setData(@NonNull List<DataBean> list) {

        setItemChecked(INVALID_POSITION,true);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<DataBean> getData() {
        return (List<DataBean>) mAdapter.getData();
    }

    @Override
    public String getCheckedName() {

        if (getCheckedItemPosition() != INVALID_POSITION) {
            return mAdapter.getData().get(getCheckedItemPosition()).name;
        }
        return "";
    }

    @Override
    public DataBean getDataByPosition(int position) {
        return mAdapter.getData().get(position);
    }

    @Override
    public int selectedPosition() {
        return getCheckedItemPosition();
    }

    @Override
    public int itemHeight() {
        return mAdapter.getItemHeight();
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

    /**
     * to set a custom Adapter to the listView,
     *
     * you must implement SelectorAdapter and extends ListAdapter
     *
     * */
    @Override
    public void setListAdapter(SelectorAdapter adapter) {
        setAdapter((ListAdapter) adapter);
        mAdapter = adapter;
    }

    class SimpleAdapter extends BaseSelectorAdapter<DataBean> implements SelectorAdapter{

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

        @Override
        public void setData(List<? extends DataBean> list) {
            if(mList == null){
                mList = (List<DataBean>) list;
            }else{
                mList.clear();
                mList.addAll(list);
            }
        }

        @Override
        public List<DataBean> getData() {
            return mList;
        }

        @Override
        public int getItemHeight() {
            return 50;
        }
    }

}
