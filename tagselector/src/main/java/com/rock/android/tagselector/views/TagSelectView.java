package com.rock.android.tagselector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rock.android.tagselector.model.DataBean;
import com.rock.android.tagselector.R;
import com.rock.android.tagselector.model.Tags;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class TagSelectView extends FrameLayout {

    private LinearLayout mTabWrapperLayout;
    private FrameLayout listContentLayout;
    private FrameLayout wrapperLayout;
    private List<ITagSelectorTabView> mTabSelectViews = new ArrayList<>();

    private ITagSelectorTabView lastOpenedTab;

    private OnTagViewStatusChangedListener onTagViewStatusChangedListener;

    private OnTagSelectedListener onTagSelectedListener;

    public TagSelectView setOnTagSelectedListener(OnTagSelectedListener onTagSelectedListener) {
        this.onTagSelectedListener = onTagSelectedListener;
        return this;
    }

    public TagSelectView setOnTagViewStatusChangedListener(OnTagViewStatusChangedListener onTagViewStatusChangedListener) {
        this.onTagViewStatusChangedListener = onTagViewStatusChangedListener;
        return this;
    }

    public interface OnTagSelectedListener{
        void onTagSelected(int selectorListPosition,int tabPosition);
    }

    public interface OnTagViewStatusChangedListener{
        void onOpened(ITagSelectorTabView openedView);

        void onClosed(ITagSelectorTabView closedView);
    }

    public TagSelectView(Context context) {
        super(context);
        init();
    }

    public TagSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_tag_selector_view, this);

        mTabWrapperLayout = (LinearLayout) findViewById(R.id.tabViewWrapperLayout);
        listContentLayout = (FrameLayout) findViewById(R.id.listContentLayout);
        wrapperLayout = (FrameLayout) findViewById(R.id.wrapperLayout);

        listContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastOpenedTab != null){
                    lastOpenedTab.close();
                }
            }
        });

        listContentLayout.setVisibility(View.GONE);

    }

    public void attach(List<Tags<DataBean>> tagsList) {
        if (tagsList == null) return;

        LinearLayout.LayoutParams tabParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < tagsList.size(); i++) {
            Tags tags = tagsList.get(i);
            mTabWrapperLayout.addView((View) newTabView(i,tags), tabParams);
        }
    }

    protected ITagSelectorTabView newTabView(final int tabPosition, Tags tags) {
        final ITagSelectorTabView tabView = new TagSelectorTabTabView(getContext());
        tabView.setUp(tags, listContentLayout, wrapperLayout);
        tabView.setOnStatusChangedListener(new ITagSelectorTabView.OnStatusChangedListener() {
            @Override
            public void willDismiss(ITagSelectorTabView view) {
                Log.e(view.getTextView().getText().toString(),"will dismiss");
            }

            @Override
            public void dismissed(ITagSelectorTabView view) {
                Log.e(view.getTextView().getText().toString(),"dismissed");
                if(onTagViewStatusChangedListener != null){
                    onTagViewStatusChangedListener.onClosed(view);
                }

                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_bottom,0);
            }

            @Override
            public void willOpen(ITagSelectorTabView view) {

                Log.e(view.getTextView().getText().toString(),"will open");
                if(lastOpenedTab != null && lastOpenedTab != view){
                    lastOpenedTab.close(false);
                }

            }

            @Override
            public void opened(ITagSelectorTabView view) {
                lastOpenedTab = view;
                Log.e(view.getTextView().getText().toString(),"opened");
                if(onTagViewStatusChangedListener != null){
                    onTagViewStatusChangedListener.onOpened(view);
                }

                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_top,0);
            }
        });

        tabView.getTagSelectorView().setOnItemClickListener(new ITagSelector.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(onTagSelectedListener != null){
                    onTagSelectedListener.onTagSelected(position,tabPosition);
                }
            }
        });

        mTabSelectViews.add(tabView);

        return tabView;
    }

    public ITagSelectorTabView getTabView(int position) {
        return mTabSelectViews.get(position);
    }
}
