package com.rock.android.tagselector;

import android.util.Log;

/**
 * Created by rock on 16/7/12.
 */
public class LogUtil {

    public static final boolean DEBUG = false;

    public static void e(String tag,Object o){
        if(DEBUG){
            Log.e(tag,String.valueOf(o));
        }
    }

    public static void d(String tag,Object o){
        if(DEBUG){
            Log.d(tag,String.valueOf(o));
        }
    }

    public static void print(String tag,Object o){
        if(DEBUG){
            System.out.println(tag+"---"+o);
        }
    }

    public static void print(Object o){
        if(DEBUG){
            System.out.println(o);
        }
    }
}
