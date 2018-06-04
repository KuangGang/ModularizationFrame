package com.kgandroid.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 获取网络图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        if (url == null || "".equals(url.trim()) || !url.startsWith("http://")) return null;
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    /**
     * @param cityName 北京市
     * @return 北京
     */
    public static String delShi(String cityName) {
        String lastName = "";
        if (cityName.contains("市")) {
            lastName = cityName.substring(0, cityName.lastIndexOf("市"));
        } else {
            lastName = cityName;
        }
        return lastName;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isZero(String str) {
        if ("0".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 截取字符串
     **/
    public static String interceptString(String content, int start, int end) {
        if (content.length() > end) {
            return content.substring(start, end);
        } else {
            return content;
        }
    }

    /**
     * 判断字符中是否全是中文
     *
     * @param str
     * @return 仅返回true的时候全是中文
     */
    public static boolean isAllChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ss = str.charAt(i);
            boolean s = String.valueOf(ss).matches("[\u4e00-\u9fa5]");
            if (!s) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     *
     * @param val
     * @return
     */
    public static boolean isEmpty(String val) {
        if (val == null || "".equals(val)) return true;
        return false;
    }

    //获取字体大小
    public static int adjustFontSize(int screenWidth, int screenHeight) {
        screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
        /**
         * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率
         rate = (float) w/320   w是实际宽度
         2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小
         实际字体大小 = 默认字体大小 x  rate
         */
        int rate = (int) (3.2 * (float) screenWidth / 320);
        return rate < 15 ? 15 : rate; //字体太小也不好看的
    }

    /**
     * 字符串转成浮点，错误值返回0
     *
     * @param str
     * @return
     */
    public static double todouble(String str) {
        if (str == null || "".equals(str)) return 0;
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    /**
     * 字符串转成浮点，错误值返回Null
     *
     * @param str
     * @return
     */
    public static Double toDouble(String str) {
        if (str == null || "".equals(str)) return null;
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 字符串转成Integer,错误返回Null
     *
     * @param str
     * @return
     */
    public static Integer toInteger(String str) {
        if (str == null || "".equals(str)) return null;
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


    /**
     * 获取车况评级
     *
     * @param conditionGradeSmall 评级显示字段
     * @param conditionGrade      判断是否小报告(需看车)
     * @return
     */
    public static String getConditionGrade(String conditionGradeSmall, String conditionGrade) {
        if (StringUtils.isEmpty(conditionGradeSmall) || "--".equals(conditionGradeSmall)) {
            if (!StringUtils.isEmpty(conditionGrade) && "需看车".equals(conditionGrade)) {
                return "需看车";
            }
            return "无评级";
        } else {
            return conditionGradeSmall.replace("--", "- -");
        }

    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 获取某个匹配字符串
     *
     * @param str   0天12小时5分10秒
     * @param regex ([0-9]+)天([0-9]+)小时([0-9]+)分([0-9]+)秒
     * @param index 1
     * @return
     */
    public static String getParamStr(String str, String regex, int index) {
        Pattern p = Pattern.compile(regex, index | Pattern.DOTALL);
        Matcher m = p.matcher(str);
        if (m.find()) {
            str = m.replaceAll("$" + index);
        } else {
            str = null;
        }
        return str;

    }

    /**
     * 根据竞价开始时间获取毫秒数
     *
     * @param str 0天12小时5分10秒
     * @return
     */
    public static long getMillisecond(String str) {
        long time = 0;
        String regex = "([0-9]+)天([0-9]+)小时([0-9]+)分([0-9]+)秒";
        if (!StringUtils.match(regex, str)) {
            regex = "([0-9]+)小时([0-9]+)分([0-9]+)秒";
            if (!StringUtils.match(regex, str)) {
                regex = "([0-9]+)分([0-9]+)秒";
                if (!StringUtils.match(regex, str)) {
                    regex = "([0-9]+)秒";
                    if (!StringUtils.match(regex, str)) {
                        str = null;
                    } else {
                        str = "0天0小时0分" + str;
                    }
                } else {
                    str = "0天0小时" + str;
                }
            } else {
                str = "0天" + str;
            }
        }
        regex = "([0-9]+)天([0-9]+)小时([0-9]+)分([0-9]+)秒";
        if (str != null) {
            int day = Integer.valueOf(StringUtils.getParamStr(str, regex, 1));
            int hour = Integer.valueOf(StringUtils.getParamStr(str, regex, 2));
            int minute = Integer.valueOf(StringUtils.getParamStr(str, regex, 3));
            int seconds = Integer.valueOf(StringUtils.getParamStr(str, regex, 4));
            time = day * 24 * 60 * 60 * 1000 + hour * 60 * 1000 + minute * 60 * 1000 + seconds * 1000;
//			System.out.println("day="+day+",hour="+hour+",minute="+minute+",seconds="+seconds);
//			System.out.println(time);

        }
        return time;
    }

    /**
     * 动态设置textview文字
     *
     * @param stringId
     * @param changeText
     * @param tvContent
     * @param context
     */
    public static String dynamicSetTextTool(int stringId, Object[] changeText, Context context) {// 动态文本工具方法
        String content = context.getResources().getString(stringId);
        String allContent = String.format(content, changeText);
        return allContent;
    }

    /**
     * 验证投标价格
     *
     * @param pricestr
     * @return 0:合法，1非浮点数，2整数位错误，3小数位错误
     */
    public synchronized static int vailBidPrice(String pricestr) {
        Double price = StringUtils.toDouble(pricestr);
        if (price == null) return 1;
        else if (price.doubleValue() < 0) return 2;
        String[] arr = pricestr.split("\\.");
        if (arr[0].length() > 3) return 2;
        if (arr.length > 1 && arr[1].length() > 2) return 3;
        return 0;
    }

    /**
     * 验证价格区间
     *
     * @param price 价格
     * @param min   最低价格 null为无下限
     * @param max   最高价格  null为无上线
     * @return 0合法，1 价格为空，2 低于下限，3高于上线
     */
    public static int vailPriceSection(Double price, Double min, Double max) {
        if (price == null) return 1;
        if (min != null && price.doubleValue() < min.doubleValue()) return 2;
        if (max != null && price.doubleValue() > max.doubleValue()) return 3;
        return 0;
    }


    /**
     * 给图片路劲增加后缀
     *
     * @param url
     * @param suffix
     * @return
     */
    public static String urlAddSuffix(String url, String suffix) {
        if (url == null || "".equals(url.trim()) || url.indexOf(".") == -1) return url;
        int potPos = url.lastIndexOf('.');
        return url.substring(0, potPos) + suffix + url.substring(potPos, url.length());
    }


    /**
     * 数组转列表
     *
     * @param data
     * @return
     */
    public static List<String> bytetoArray(String[] data) {
        List<String> strings = new ArrayList<String>();
        int size = data.length;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                strings.add(data[i]);
            }
        }
        return strings;
    }

    public static String[] arrayToByte(List<String> list) {
        int size = list.size();
        String[] data = new String[size];
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                data[i] = list.get(i);
            }
        }
        return data;
    }

    public static String formatDouble(double price) {
        return String.format(Locale.CHINA, "%1$.2f", price);
    }

    public static String formatDouble(String price) {
        return formatDouble(todouble(price));
    }

    /**
     * 验证密码
     *
     * @return
     */
    public static boolean checkPassword(String password) {
        String regex = "([a-z]|[A-Z]|[0-9])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static String joinStr(Object... objs) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            sb.append(obj);
        }
        return sb.toString();
    }

    public static String joinJson(HashMap<String, Object> params) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, Object> item : params.entrySet()) {
                jsonObject.put(item.getKey(), item.getValue());
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDecimalFormat(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        return decimalFormat.format(number);
    }

    /**
     * 检验投标价格是否有效
     *
     * @return 0：合法	1：价格为空	2：价格低于下限	3：价格高于上限	4:不是个数字	5：整数位错误	6：小数位错误
     **/
    public static int checkTenderPrice(String tenderPrice, double minPrice) {
        if (StringUtils.isEmpty(tenderPrice)) {
            return 1;
        }
        if (".".equals(tenderPrice) || tenderPrice.startsWith(".") || tenderPrice.endsWith(".")) {
            return 4;
        }
        double price = todouble(tenderPrice);
        if (price < minPrice) {
            return 2;
        }
        String[] priceArr = String.valueOf(tenderPrice).split("\\.");
        if (priceArr[0].length() > 3) {
            return 5;
        }
        if (priceArr.length > 1 && priceArr[1].length() > 2) {
            return 6;
        }
        if (price > 999.99d) {
            return 3;
        }
        return 0;
    }

    public static int formatInt(String num) {
        int result = 0;
        if (isEmpty(num)) {
            return result;
        }
        try {
            result = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

}
