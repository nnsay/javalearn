package com.neuralgalaxy.commons.utilities;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>
 *
 * @author <a href="mailto:wo@renzhen.la">haiker</a>
 * @version 2017/9/25 下午3:50
 */
public class Maths {

    /**
     * 提供精确的加法运算。
     *
     * @param v1 v1
     * @param v2 v2
     * @return out
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法
     *
     * @param v1 v1
     * @param v2 v2
     * @return out
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     *
     * @param v1 v1
     * @param v2 v2
     * @return out
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }


    /**
     * 乘法
     *
     * @param v1    v1
     * @param v2    v2
     * @param scale 保留小数位数，使用{@link BigDecimal#ROUND_DOWN}
     * @return out
     */
    public static double multiply(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, RoundingMode.DOWN).doubleValue();
    }


    /**
     * 乘法
     *
     * @param v1           v1
     * @param v2           v2
     * @param scale        保留小数位数
     * @param roundingMode 保留小数位数模式
     * @return 计算结果
     */
    public static double multiply(double v1, double v2, int scale, RoundingMode roundingMode) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, roundingMode).doubleValue();
    }


    /**
     * 除法
     *
     * @param v1    v1
     * @param v2    v2
     * @param scale 保留小数位数，使用{@link RoundingMode#DOWN}
     * @return out
     */
    public static double divide(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, RoundingMode.DOWN).doubleValue();
    }

    /**
     * 除法
     *
     * @param v1           v1
     * @param v2           v2
     * @param scale        保留小数位数
     * @param roundingMode 保留小数位数模式
     * @return out
     */
    public static double divide(double v1, double v2, int scale, RoundingMode roundingMode) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, roundingMode).doubleValue();
    }

    /**
     * 格式化输出,{@link RoundingMode#DOWN} 保留两位小数，千分位不带逗号
     *
     * @param v1 v1
     * @return string
     */
    public static String foramt(double v1) {
        return format(v1, 2);
    }

    /**
     * 格式话输出
     *
     * @param v1     数字
     * @param digits 小数保留位数
     * @return 格式化结果
     */
    public static String format(double v1, int digits) {
        return format(v1, digits, RoundingMode.DOWN);
    }

    /**
     * 格式化输出，千分位不带逗号
     *
     * @param v1           数字
     * @param digits       保留小数位数
     * @param roundingMode 小数位数保留方式
     * @return 格式化结果
     */
    public static String format(double v1, int digits, RoundingMode roundingMode) {
        DecimalFormat f = new DecimalFormat("##0.0000000000");
        f.setMaximumFractionDigits(digits);
        f.setRoundingMode(roundingMode);
        return f.format(v1);
    }

    /**
     * 格式化输出,不显示小数位以0结尾的内容
     *
     * @param d 要格式的数字
     * @return
     */
    public static String format(Double d) {
        if (d == null) {
            return "0";
        }
        String formatter = "#.##########";
        DecimalFormat format = new DecimalFormat(formatter);
        return format.format(d);
    }


    /**
     * 格式刷输出，{@link RoundingMode#DOWN} 保留两位小数
     *
     * @param v1 数字
     * @return 格式化结果
     */
    public static String pretty(double v1) {
        return pretty(v1, 2, RoundingMode.DOWN);
    }

    /**
     * 格式刷输出，{@link RoundingMode#DOWN}
     *
     * @param v1     数字
     * @param digits 保留小数位数
     * @return 格式化结果
     */
    public static String pretty(double v1, int digits) {
        return pretty(v1, digits, RoundingMode.DOWN);
    }

    /**
     * 格式刷输出
     *
     * @param v1           v1
     * @param digits       保留小数位数
     * @param roundingMode 小数位数保留方式
     * @return 优化格式结果
     */
    public static String pretty(double v1, int digits, RoundingMode roundingMode) {
        DecimalFormat f = new DecimalFormat("###,###,###,###,##0.0000000000");
        f.setMaximumFractionDigits(digits);
        f.setRoundingMode(roundingMode);
        return f.format(v1);
    }

    /**
     * {@link RoundingMode#DOWN} 保留2位
     *
     * @param d number
     * @return 保留结果
     */
    public static double scale(double d) {
        return new BigDecimal(Double.toString(d)).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    /**
     * {@link RoundingMode#DOWN} 保留 scale位小数
     *
     * @param d     数
     * @param scale 保留小数位数
     * @return out
     */
    public static double scale(double d, int scale) {
        return new BigDecimal(Double.toString(d)).setScale(scale,RoundingMode.DOWN).doubleValue();
    }

    /**
     * @param d            数
     * @param scale        保留小数位数
     * @param roundingMode 小数位round模式
     * @return out
     */
    public static double scale(double d, int scale, RoundingMode roundingMode) {
        return new BigDecimal(Double.toString(d)).setScale(scale, roundingMode).doubleValue();
    }

    /**
     * 人币民分转元
     * @param d 分
     * @return 元
     */
    public static String fenToYuan(Long d) {
        if(d == null) {
            return "0";
        }
        BigDecimal b1 = new BigDecimal(d.toString());
        BigDecimal b2 = new BigDecimal("100");
        return format(b1.divide(b2).doubleValue());
    }
}
