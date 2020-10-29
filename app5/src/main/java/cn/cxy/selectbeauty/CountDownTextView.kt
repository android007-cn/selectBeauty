package cn.cxy.selectbeauty

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountDownTextView(context: Context, attrs: AttributeSet? = null) :
    AppCompatTextView(context, attrs) {
    private var mOnFinishCallback: OnFinishCallback? = null
    private var startNum = 5
    private var endNum = 1
    private val intervalTime = 500 //重绘间隔时间
    private var currentNum = startNum

    init {
        background = getCircleDrawable()
        text = currentNum.toString()
    }

    fun setCallback(onFinishCallback: OnFinishCallback) {
        mOnFinishCallback = onFinishCallback
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 隔一段时间重绘一次, 动画效果
        handler.postDelayed(runnable, intervalTime.toLong())
    }

    private val runnable = Runnable {
        changeText()
        invalidate()
    }

    private fun changeText() {
        if (currentNum > endNum) {
            currentNum--
        } else {
            mOnFinishCallback?.onFinished()
            visibility = GONE
        }
        text = currentNum.toString()
    }

    private fun getCircleDrawable() = GradientDrawable().also {
        it.shape = GradientDrawable.OVAL
        val strokeWidth = 4 // 边框宽度
        val strokeColor = Color.parseColor("#FFD700") //边框颜色
        it.setStroke(strokeWidth, strokeColor)
        it.setColor(Color.parseColor("#DA70D6"))
    }
}

interface OnFinishCallback {
    fun onFinished()
}
