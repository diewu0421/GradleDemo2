package com.example.gradledemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * 浙江集商优选电子商务有限公司
 * @author zenglw
 * @date   12/17/20 11:41 PM
 */
class MyView @JvmOverloads constructor(context: Context, attrSets: AttributeSet? = null, defStyleAttrs: Int = 0)
    : View(context, attrSets, defStyleAttrs) {


    override fun onSaveInstanceState(): Parcelable? {
        Log.e("MyView","onSaveInstanceState ")
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.e("MyView","onRestoreInstanceState ")
        super.onRestoreInstanceState(state)
    }

    val path = Paint().apply {
        setColor(Color.YELLOW)
        strokeWidth = 10f
        style = Paint.Style.FILL
        isAntiAlias = true
        isDither = true
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), path)
    }
}
