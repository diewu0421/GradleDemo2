package com.example.gradledemo

import android.graphics.Color

/**
 * @Description: 颜色处理工具
 * @Author: XGY
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/3/5 9:41
 * @Version: 1.0.0
 */
object ColorUtils {
    /**
     * DES: 颜色过度(根据fraction值来计算当前的颜色)
     * AUTHOR: zenglw
     * TIME: 2020/3/5 9:42
     */
    @JvmStatic
    fun getCurrentColor(fraction: Float, startColor: Int, endColor: Int): Int {
        val redStart = Color.red(startColor)
        val blueStart = Color.blue(startColor)
        val greenStart = Color.green(startColor)
        val alphaStart = Color.alpha(startColor)
        val redEnd = Color.red(endColor)
        val blueEnd = Color.blue(endColor)
        val greenEnd = Color.green(endColor)
        val alphaEnd = Color.alpha(endColor)
        val redDifference = redEnd - redStart
        val blueDifference = blueEnd - blueStart
        val greenDifference = greenEnd - greenStart
        val alphaDifference = alphaEnd - alphaStart
        val redCurrent = (redStart + fraction * redDifference).toInt()
        val blueCurrent = (blueStart + fraction * blueDifference).toInt()
        val greenCurrent = (greenStart + fraction * greenDifference).toInt()
        val alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
    }

    @JvmStatic
    fun getColor(colorId: Int): Int {
        return ColorUtils.getColor(colorId)
    }
}