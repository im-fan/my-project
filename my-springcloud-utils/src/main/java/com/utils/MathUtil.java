
package com.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

public class MathUtil {

    //小数点后2位
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.add(b2));
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.add(b2));
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.subtract(b2));
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.subtract(b2));
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.multiply(b2));
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.multiply(b2));
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1, String v2) {
        if ("".equals(v2) || Double.parseDouble(v2) == 0) {
            return "0";
        }
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(double v1, double v2) {
        return div(String.valueOf(v1), String.valueOf(v2), DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP));
    }


    /**
     * double 截取 只保留2位小数
     * @param d
     * @return
     */
    public static String format_2(double d) {
        return new DecimalFormat("0.00").format(d);
    }

    /**
     * float 截取 只保留2位小数
     * @param f
     * @return
     */
    public static String format_2(float f) {
        return new DecimalFormat("0.00").format(f);
    }

    /**
     * float 截取 只保留2位小数
     * @param f
     * @return
     */
    public static String format_2_round(float f) {
        BigDecimal b = new BigDecimal(f);
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(b.divide(one, 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 去掉浮点数最后的零
     * @return
     */
    public static String removeLastZero(Object floatingNumberObj) {
        String floatingNumber = String.valueOf(floatingNumberObj);
        if (floatingNumber.matches("\\d+\\.?\\d+")) {
            if (floatingNumber.indexOf(".") > 0) {
                // 正则表达
                floatingNumber = floatingNumber.replaceAll("0+$", "");// 去掉后面无用的零
                floatingNumber = floatingNumber.replaceAll("\\.$", "");// 如小数点后面全是零则去掉小数点
            }
        }
        return floatingNumber;
    }

    /**
     * 金额展示（保留两位小数）
     * @param price
     * @return
     */
    public static String generateDisplayPrice(float price) {
        return new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static int getMinIntValue(Float value){
        if (value == null){
            return 0;
        }
        return value.intValue();
    }

    public static <T> T random(Map<T, Integer> map) {
        int total = 0;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            total += entry.getValue();
        }
        Random random = new Random();
        int result = random.nextInt(total);

        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= result)
                return entry.getKey();
            result = result - entry.getValue();
        }
        return null;
    }

}
