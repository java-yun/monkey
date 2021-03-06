package com.monkey.common.utils;

import java.math.BigDecimal;

/**
 * 数字相关工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/11 10:12
 */
public class NumberUtils {

    /**
     * 获取指定范围的BigDecimal [lower, upper)
     * @param lower 下限
     * @param upper 上限
     * @return BigDecimal
     */
    public static BigDecimal getRandomBigDecimal(double lower, double upper) {
        return BigDecimal.valueOf(getRandomDouble(lower, upper));
    }

    /**
     * 获取指定范围的double [lower, upper)
     * @param lower 下限
     * @param upper 上限
     * @return double
     */
    public static double getRandomDouble(double lower, double upper) {
        return Math.random() * (upper - lower) + lower;
    }

    /**
     * 两个数相除，向上取整
     * @param divisor 除数
     * @param dividend 被除数
     * @return int
     */
    public static int getDivUpValue(int divisor, int dividend) {
        return divisor % dividend == 0 ? divisor/dividend : divisor/dividend + 1;
    }

}
