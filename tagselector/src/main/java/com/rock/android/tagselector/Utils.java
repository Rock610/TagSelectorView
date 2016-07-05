package com.rock.android.tagselector;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by rock on 16/7/4.
 */
public class Utils {

    public static int dp2px(Context context, int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getApplicationContext().getResources().getDisplayMetrics());
    }
}
