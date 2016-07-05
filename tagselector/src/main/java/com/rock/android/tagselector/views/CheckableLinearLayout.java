package com.rock.android.tagselector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    private CompoundButton _checkbox;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // find checked text view
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View v = getChildAt(i);

            if (v instanceof CompoundButton) {
                _checkbox = (CompoundButton)v;
                return;
            }
            if(v instanceof ViewGroup){
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i1 = 0; i1 < viewGroup.getChildCount(); i1++) {
                    View child = viewGroup.getChildAt(i1);
                    if (child instanceof CompoundButton) {
                        _checkbox = (CompoundButton)child;
                        return;
                    }
                }
            }

        }
    }

    @Override
    public boolean isChecked() {
        return _checkbox != null && _checkbox.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        if (_checkbox != null) {
            _checkbox.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
        if (_checkbox != null) {
            _checkbox.toggle();
        }
    }
}
