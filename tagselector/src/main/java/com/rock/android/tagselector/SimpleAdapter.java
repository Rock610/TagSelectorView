package com.rock.android.tagselector;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rock.android.tagselector.interfaces.BaseAdapter;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.model.Tags;
import com.rock.android.tagselector.views.SimpleSingleSelectListView;

import java.util.List;

/**
 * Created by rock on 2016/10/18.
 */

public class SimpleAdapter implements BaseAdapter {

    private List<Tags> tagsList;
    private Context mContext;

    public SimpleAdapter(List<Tags> tagsList, Context mContext) {
        this.tagsList = tagsList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return tagsList.size();
    }

    @Override
    public View getTabView(int position) {
        return newTagView(R.layout.layout_tab_1);
    }

    @Override
    public ITagSelector getDropDownView(int position) {
        SimpleSingleSelectListView simpleSingleSelectListView = new SimpleSingleSelectListView(mContext);
        SimpleSingleSelectListView.SimpleAdapter adapter = (SimpleSingleSelectListView.SimpleAdapter) simpleSingleSelectListView.getListAdapter();
        adapter.setData(tagsList.get(position).tags);
        if(position == 0){
            simpleSingleSelectListView.setDefaultItem(0);
        }
        return simpleSingleSelectListView;
    }

    @Override
    public Object getItem(int tabPosition,int selectorPosition) {
        return tagsList.get(tabPosition).tags.get(selectorPosition);
    }

    @Override
    public boolean getChangeAfterClicked(int tabPosition) {
        return true;
    }

    @Override
    public String getTagName(int tabPosition, int selectorPosition) {
        return tagsList.get(tabPosition).tags.get(selectorPosition).name;
    }

    private View newTagView (int res) {
        if(res != 0){
            return LayoutInflater.from(mContext).inflate(res,null);
        }

        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView tv = new TextView(mContext);
        tv.setId(R.id.theTextView);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.arrow_bottom,0);
        tv.setGravity(Gravity.CENTER);

        layout.addView(tv,params);

        return layout;
    }
}
