package com.kgandroid.library.utils;

import android.content.Context;

/**
 * Description: 像素dip相互转换类
 * <br/>Date: 2015年1月26日11:56:22
 * @author  黄炜	huangwei@youxinpai.com
 */

public class DensityUtil {
    /** 
     * 根据手机的分辨率将dp的单位转成px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率将px(像素)的单位转成dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }
}
