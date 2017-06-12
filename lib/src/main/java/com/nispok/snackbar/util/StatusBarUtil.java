package com.nispok.snackbar.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by chengyun.wu on 17/6/9.
 *
 * @author chengyun.wu
 */

public class StatusBarUtil {
    /**
     * 获取状态栏的高度
     */
    public static int mStatusBarHeight = 0;

    public static int getStatusBarHeight(Context context) {
        if (context == null)
            return 0;
        if (mStatusBarHeight != 0) {
            return mStatusBarHeight;
        }
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            mStatusBarHeight = context.getResources()
                    .getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mStatusBarHeight;
    }

    public static boolean isStatusTransparent(Context context){
        return false;
    }
}
