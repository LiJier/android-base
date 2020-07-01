package com.core.math

import java.math.BigDecimal

/**
 * Create by LiJie at 2019-05-29
 * 数字、计算相关扩展方法
 */

/**
 * 字符转Float，失败时为0
 */
fun String?.toFloatOrZero(): Float {
    return this?.toFloatOrNull() ?: 0F
}

/**
 * 字符转Int，失败时为0
 */
fun String?.toIntOrZero(): Int {
    return this?.toIntOrNull() ?: 0
}

/**
 * 相对精确的加法计算
 */
infix fun Number.add(number: Number): Number {
    val b1 = BigDecimal(this.toString())
    val b2 = BigDecimal(number.toString())
    return b1.add(b2)
}

/**
 * 相对精确的减法计算
 */
infix fun Number.sub(number: Number): Number {
    val b1 = BigDecimal(this.toString())
    val b2 = BigDecimal(number.toString())
    return b1.subtract(b2)
}

/**
 * 相对精确的乘法计算
 */
infix fun Number.mul(number: Number): Number {
    val b1 = BigDecimal(this.toString())
    val b2 = BigDecimal(number.toString())
    return b1.multiply(b2)
}

/**
 * 相对精确的除法计算，精确的小数点后两位
 */
infix fun Number.div(number: Number): Number {
    val b1 = BigDecimal(this.toString())
    val b2 = BigDecimal(number.toString())
    return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toFloat()
}

/**
 * 相对精确的除法计算，可以指定小数位
 * @param scale 精确位数
 */
fun Number.div(number: Number, scale: Int = 2): Number {
    if (scale < 0) {
        throw  IllegalArgumentException(
            "The scale must be a positive integer or zero"
        )
    }
    val b1 = BigDecimal(this.toString())
    val b2 = BigDecimal(number.toString())
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)
}

/**
 * 精确到小数点后几位
 * @param scale 精确位数
 */
fun Number.scale(scale: Int = 2): Number {
    val b1 = BigDecimal(this.toString())
    return b1.setScale(scale, BigDecimal.ROUND_HALF_UP)
}