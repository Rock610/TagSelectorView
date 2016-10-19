package com.rock.android.tagselectorview;

import android.content.Context;

import com.rock.android.tagselector.SimpleAdapter;
import com.rock.android.tagselector.model.Tags;

import java.util.List;

/**
 * Created by rock on 2016/10/19.
 */

public class CustomAdapter extends SimpleAdapter {
    public CustomAdapter(List<Tags> tagsList, Context mContext) {
        super(tagsList, mContext);
    }

    @Override
    public String getDefaultTagName(int position) {
        switch (position){
            case 0:
                return "标签一";
            case 1:
                return "标签二";
            case 2:
                return "标签三";
        }
        return super.getDefaultTagName(position);
    }

    @Override
    public boolean getChangeAfterClicked(int tabPosition) {
        return false;
    }
}
