package com.rock.android.tagselector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rock.android.tagselector.R;
import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;
import com.rock.android.tagselector.model.Tags;

/**
 * Created by rock on 16/7/4.
 */
public class TagSelectorTabTabView extends RelativeLayout implements ITagSelectorTabView {

    protected TextView mTextView;
    protected FrameLayout mWrapper;
    protected FrameLayout selectorParent;

    protected boolean isOpening;

    protected Tags mTags;

    private OnStatusChangedListener onStatusChangedListener;
    private OnViewClickListener onViewClickListener;

    private View contentView;
    private ITagSelector selectorView;

    @Override
    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    public void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener) {
        this.onStatusChangedListener = onStatusChangedListener;
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @Override
    public ITagSelector getTagSelectorView() {
        return selectorView;
    }

    public TagSelectorTabTabView(Context context) {
        super(context);
        init();
    }

    public TagSelectorTabTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagSelectorTabTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getTagText(), "clicked");
                toggle();

                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick();
                }

            }
        });
    }

    private String getTagText() {
        return mTextView.getText().toString();
    }

    @Override
    public void setTag(String tag) {
        mTextView.setText(tag);
    }

    @Override
    public void close() {
        close(true);
    }

    @Override
    public void close(boolean withAnim) {
        if (onStatusChangedListener != null) {
            onStatusChangedListener.willDismiss(this);
        }

        mWrapper.clearAnimation();

        if (!isOpening) return;

        if (!withAnim) {
            afterClose();
            return;
        }

        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -getAnimHeight());

        configAnim(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mWrapper.startAnimation(ta);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                afterClose();
            }
        }, ta.getDuration());
    }

    private void afterClose() {
        isOpening = false;
        selectorView.hide();
        if (selectorParent != null && selectorParent.getVisibility() != View.GONE) {
            selectorParent.setVisibility(View.GONE);
        }
        if (onStatusChangedListener != null) {
            onStatusChangedListener.dismissed(TagSelectorTabTabView.this);
        }
    }

    @Override
    public void open() {

        if (onStatusChangedListener != null) {
            onStatusChangedListener.willOpen(this);
        }
        mWrapper.clearAnimation();
        if (isOpening) return;

        if (selectorParent != null && selectorParent.getVisibility() != View.VISIBLE) {
            selectorParent.setVisibility(View.VISIBLE);
        }
        TranslateAnimation ta = new TranslateAnimation(0, 0, -getAnimHeight(), 0);
        configAnim(ta);
        mWrapper.startAnimation(ta);
        selectorView.show();
        isOpening = true;
        if (onStatusChangedListener != null) {
            onStatusChangedListener.opened(this);
        }
    }

    private int getAnimHeight() {
        int itemHeight = selectorView.itemHeight();
        return itemHeight * selectorView.getCount();
    }

    @Override
    public void toggle() {
        if (isOpening) {
            close();
        } else {
            open();
        }
    }

    private TranslateAnimation configAnim(TranslateAnimation ta) {
        ta.setInterpolator(new AccelerateDecelerateInterpolator());
        ta.setDuration(200);
        return ta;
    }

    @Override
    public void setUp(Tags tags, FrameLayout selectorParent, FrameLayout wrapper) {

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        try {
            View view = newTagView(tags.layoutRes, tags.textViewId);
            contentView = view;
            addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mWrapper = wrapper;
        this.selectorParent = selectorParent;
        selectorView = new SimpleSingleSelectListView(getContext());

        selectorView.setTabView(this);
        FrameLayout.LayoutParams paramsList = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrapper.addView((View) selectorView, paramsList);
        mTags = tags;
        selectorView.setData(tags.tags);

        setTag(tags.defaultTag);

        selectorView.setOnItemClickListener(new ITagSelector.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTags.tags.get(position);
            }
        });
    }

    @Override
    public TextView getTextView() {
        return mTextView;
    }

    @Override
    public boolean isOpening() {
        return isOpening;
    }

    @Override
    public boolean isChangeTagAfterClicked() {
        return mTags.isChangeAfterClicked;
    }

    /**
     * 包装一个内部view,这里直接以TextView为例
     * 必须给mTextView赋值
     */
    protected View newTagView (int res,int textViewid) throws Exception {
        if(res != 0){
            View view = LayoutInflater.from(getContext()).inflate(res,null);
            if(view == null){
                throw new RuntimeException("can not inflate view by id==="+res);
            }
            if(textViewid != 0){
                mTextView = (TextView) view.findViewById(textViewid);
            }

            if(mTextView == null){
                throw new RuntimeException("can not find TextView by id==="+textViewid);
            }
            return view;
        }

        RelativeLayout layout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView tv = new TextView(getContext());
        mTextView = tv;
        tv.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.arrow_bottom,0);
        tv.setGravity(Gravity.CENTER);

        layout.addView(tv,params);
        return layout;
    }
}
