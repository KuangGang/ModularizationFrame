package com.kgandroid.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * <pre>
 *     author: KG
 *     time  : 2018/05/31
 *     desc  : App的Context获取
 * </pre>
 */
public final class AppContextUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private AppContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        AppContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
