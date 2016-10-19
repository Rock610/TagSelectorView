package com.rock.android.tagselector.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rock.android.tagselector.R;
import com.rock.android.tagselector.Utils;
import com.rock.android.tagselector.interfaces.BaseAdapter;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 16/7/4.
 */
public class TagSelectView extends FrameLayout {

    public static final int DEFAULT_WRAPPER_HEIGHT = 250;//默认wrapper高度
    protected LinearLayout mTabWrapperLayout;
    protected FrameLayout listContentLayout;
    protected FrameLayout wrapperLayout;
    protected List<ITagSelectorTabView> mTabSelectViews = new ArrayList<>();

    protected ITagSelectorTabView lastOpenedTab;

    protected OnTagViewStatusChangedListener onTagViewStatusChangedListener;

    protected OnTagSelectedListener onTagSelectedListener;

    protected int tabHeight;

    protected int bottomLineColor;

    protected Drawable dividerDrawable;

    protected int wrapperHeight;

    protected int tabTextColor;
    protected View bottomLine;

    public TagSelectView setOnTagSelectedListener(OnTagSelectedListener onTagSelectedListener) {
        this.onTagSelectedListener = onTagSelectedListener;
        return this;
    }

    public TagSelectView setOnTagViewStatusChangedListener(OnTagViewStatusChangedListener onTagViewStatusChangedListener) {
        this.onTagViewStatusChangedListener = onTagViewStatusChangedListener;
        return this;
    }

    public interface OnTagSelectedListener {
        void onTagSelected(int selectorListPosition, int tabPosition);
    }

    public interface OnTagViewStatusChangedListener {
        void onOpened(ITagSelectorTabView openedView);

        void onClosed(ITagSelectorTabView closedView);
    }

    public TagSelectView(Context context) {
        super(context);
        init(null);
    }

    public TagSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TagSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TagSelectView, 0, 0);
            tabHeight = a.getDimensionPixelSize(R.styleable.TagSelectView_tabHeight, 0);
            bottomLineColor = a.getColor(R.styleable.TagSelectView_tabBottomLineColor, getResources().getColor(R.color.lineColor));
            dividerDrawable = a.getDrawable(R.styleable.TagSelectView_tabDivider);
            wrapperHeight = a.getDimensionPixelSize(R.styleable.TagSelectView_wrapperHeight, Utils.dp2px(getContext(), DEFAULT_WRAPPER_HEIGHT));
            tabTextColor = a.getColor(R.styleable.TagSelectView_selectorTabTextColor, getResources().getColor(R.color.defaultTabTextColor));
            a.recycle();
        }

        LayoutInflater.from(getContext()).inflate(R.layout.layout_tag_selector_view, this);

        bottomLine = findViewById(R.id.bottomLine);
        FrameLayout.LayoutParams paramsLine = (LayoutParams) bottomLine.getLayoutParams();
        paramsLine.topMargin = tabHeight;

        setBottomLineColor(bottomLineColor);


        mTabWrapperLayout = (LinearLayout) findViewById(R.id.tabViewWrapperLayout);

        setDividerDrawable(dividerDrawable);
        mTabWrapperLayout.getLayoutParams().height = tabHeight;

        listContentLayout = (FrameLayout) findViewById(R.id.listContentLayout);
        setTabHeight(tabHeight);

        wrapperLayout = (FrameLayout) findViewById(R.id.wrapperLayout);
        setWrapperHeight(wrapperHeight);

        listContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastOpenedTab != null) {
                    lastOpenedTab.close();
                }
            }
        });

        listContentLayout.setVisibility(View.GONE);

    }

    public TagSelectView setTabHeight(int tabHeight) {
        this.tabHeight = tabHeight;
        FrameLayout.LayoutParams params = (LayoutParams) listContentLayout.getLayoutParams();
        params.topMargin = tabHeight;
        return this;
    }

    public TagSelectView setBottomLineColor(int bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
        bottomLine.setBackgroundColor(bottomLineColor);
        return this;
    }

    public TagSelectView setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
        mTabWrapperLayout.setDividerDrawable(dividerDrawable);
        return this;
    }

    public TagSelectView setWrapperHeight(int wrapperHeight) {
        this.wrapperHeight = wrapperHeight;
        wrapperLayout.getLayoutParams().height = wrapperHeight;
        return this;
    }

    public TagSelectView setTabTextColor(int tabTextColor) {
        this.tabTextColor = tabTextColor;
        for (ITagSelectorTabView mTabSelectView : mTabSelectViews) {
            mTabSelectView.getTextView().setTextColor(tabTextColor);
        }
        return this;
    }

    public void setAdapter(BaseAdapter adapter) {
        LinearLayout.LayoutParams tabParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < adapter.getCount(); i++) {
            final ITagSelectorTabView tabView = getTagSelectorTabView(adapter, i);
            mTabWrapperLayout.addView((View) tabView, tabParams);

        }
    }

    @NonNull
    protected ITagSelectorTabView getTagSelectorTabView(BaseAdapter adapter, final int tabPosition) {
        final ITagSelectorTabView tabView = new TagSelectorTabTabView(getContext());
        final ITagSelector dropDownView = adapter.getDropDownView(tabPosition);
                //new SimpleSingleSelectListView(getContext());
//        dropDownView.setContentView(adapter.getDropDownView(tabPosition));
        tabView.setup(listContentLayout, wrapperLayout,dropDownView , adapter.getTabView(tabPosition));
        tabView.setChangeAfterClick(adapter.getChangeAfterClicked(tabPosition));
        tabView.setTag(adapter.getTagName(tabPosition,0));
        tabView.getTextView().setTextColor(tabTextColor);

        tabView.setOnStatusChangedListener(new ITagSelectorTabView.OnStatusChangedListener() {
            @Override
            public void willDismiss(ITagSelectorTabView view) {
            }

            @Override
            public void dismissed(ITagSelectorTabView view) {
                if (onTagViewStatusChangedListener != null) {
                    onTagViewStatusChangedListener.onClosed(view);
                }

                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_bottom, 0);
            }

            @Override
            public void willOpen(ITagSelectorTabView view) {

                if (lastOpenedTab != null && lastOpenedTab != view) {
                    lastOpenedTab.close(false);
                }

            }

            @Override
            public void opened(ITagSelectorTabView view) {
                lastOpenedTab = view;
                if (onTagViewStatusChangedListener != null) {
                    onTagViewStatusChangedListener.onOpened(view);
                }

                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_top, 0);
            }
        });

        tabView.getTagSelectorView().setOnItemClickListener(new ITagSelector.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                tabView.close();
                if (onTagSelectedListener != null) {
                    onTagSelectedListener.onTagSelected(position, tabPosition);
                }
            }
        });

        mTabSelectViews.add(tabView);
        return tabView;
    }

//    protected ITagSelectorTabView newTabView(final int tabPosition, Tags tags) {
//        final ITagSelectorTabView tabView = new TagSelectorTabTabView(getContext());
//        if (tags.selector != null) {
//            tabView.setup(tags, listContentLayout, wrapperLayout, tags.selector);
//        } else {
//            tabView.setup(tags, listContentLayout, wrapperLayout);
//        }
//        tabView.getTextView().setTextColor(tabTextColor);
//
//        tabView.setOnStatusChangedListener(new ITagSelectorTabView.OnStatusChangedListener() {
//            @Override
//            public void willDismiss(ITagSelectorTabView view) {
//            }
//
//            @Override
//            public void dismissed(ITagSelectorTabView view) {
//                if (onTagViewStatusChangedListener != null) {
//                    onTagViewStatusChangedListener.onClosed(view);
//                }
//
//                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_bottom, 0);
//            }
//
//            @Override
//            public void willOpen(ITagSelectorTabView view) {
//
//                if (lastOpenedTab != null && lastOpenedTab != view) {
//                    lastOpenedTab.close(false);
//                }
//
//            }
//
//            @Override
//            public void opened(ITagSelectorTabView view) {
//                lastOpenedTab = view;
//                if (onTagViewStatusChangedListener != null) {
//                    onTagViewStatusChangedListener.onOpened(view);
//                }
//
//                view.getTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_top, 0);
//            }
//        });
//
//        tabView.getTagSelectorView().setOnItemClickListener(new ITagSelector.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                tabView.close();
//                if (onTagSelectedListener != null) {
//                    onTagSelectedListener.onTagSelected(position, tabPosition);
//                }
//            }
//        });
//
//        mTabSelectViews.add(tabView);
//
//        return tabView;
//    }

    public ITagSelectorTabView getTabView(int position) {
        return mTabSelectViews.get(position);
    }

    public void dismissAll() {
        if (lastOpenedTab != null) {
            lastOpenedTab.close();
        }
    }

    public boolean isShowing() {
        return lastOpenedTab != null && lastOpenedTab.isOpening();
    }

}
