package com.rock.android.tagselector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rock.android.tagselector.LogUtil;
import com.rock.android.tagselector.R;
import com.rock.android.tagselector.Utils;
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
    private int wrapperHeight;

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

    @Override
    public void setWrapperHeight(int height) {
        wrapperHeight = height;
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
                LogUtil.e(getTagText(), "clicked");
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

//        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -getAnimHeight());
        Animation ta = AnimationUtils.loadAnimation(getContext(), R.anim.ts_content_out);
//        configAnim(ta);
        mWrapper.startAnimation(ta);

        //代替动画监听，因为有时会不响应listener
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
//        TranslateAnimation ta = new TranslateAnimation(0, 0, -getAnimHeight(), 0);
//        configAnim(ta);

        Animation ta = AnimationUtils.loadAnimation(getContext(), R.anim.ts_content_in);
        mWrapper.startAnimation(ta);
        selectorView.show();
        isOpening = true;
        if (onStatusChangedListener != null) {
            onStatusChangedListener.opened(this);
        }
    }

    private int getAnimHeight() {
        int height;
        if(selectorView.getAnimHeight() > 0){
            height = Utils.dp2px(getContext(),selectorView.getAnimHeight());
        }else{
            height = Utils.dp2px(getContext(),selectorView.itemHeight()) * selectorView.getCount();
        }

        int max = wrapperHeight > 0? wrapperHeight : Utils.dp2px(getContext(),TagSelectView.DEFAULT_WRAPPER_HEIGHT);
        height = height > max ? max:height;


        return height;
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
        ta.setDuration(250);
        ta.setInterpolator(new DecelerateInterpolator());
        return ta;
    }

    @Override
    public void setup(Tags tags, FrameLayout selectorParent, FrameLayout wrapper) {
        setup(tags,selectorParent,wrapper,null);
    }

    @Override
    public void setup(Tags tags, FrameLayout selectorParent, FrameLayout wrapper,ITagSelector selectorView){

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
        mTags = tags;
        if(selectorView != null){
            this.selectorView = selectorView;
            wrapper.addView((View) selectorView);
        }else{
           setDefaultSelector(wrapper);
        }

        this.selectorView.setTabView(this);
        this.selectorView.setData(tags.tags);
        setTag(tags.defaultTag);

    }

    private void setDefaultSelector(FrameLayout wrapper){
        selectorView = new SimpleSingleSelectListView(getContext());

        FrameLayout.LayoutParams paramsList = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrapper.addView((View) selectorView, paramsList);

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
