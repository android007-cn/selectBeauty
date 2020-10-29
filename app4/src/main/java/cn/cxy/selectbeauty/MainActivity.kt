package cn.cxy.selectbeauty

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private lateinit var selectImageTimer: Timer
    private var selectImageIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            if (button.text != "停") {
                startSelectImageTimer()

                button.text = "停"
            } else {
                stopTimer()
                button.visibility = GONE
                flowLayout.visibility = GONE
                showFinalSelectedImage()
            }
        }
    }

    private fun showFinalSelectedImage() {
        selectedIv.visibility = VISIBLE
        val selectedImageView = flowLayout.getChildAt(selectImageIndex) as ImageView
        selectedIv.setImageDrawable(selectedImageView.drawable)
        selectedIv.startAnimation(getScaleDownAnimation(500))
    }

    /**
     * 放大动画
     */
    private fun getScaleDownAnimation(animateTime: Long): ScaleAnimation {
        val scaleDownAnimation = ScaleAnimation(
            1f, 2f, 1f, 2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleDownAnimation.duration = animateTime
        scaleDownAnimation.fillAfter = true
        return scaleDownAnimation
    }

    private fun startSelectImageTimer() {
        selectImageTimer = fixedRateTimer("", false, 0, 500) {
            GlobalScope.launch(Dispatchers.Main) {
                clearImageBackGround()
                val view = flowLayout.getChildAt(selectImageIndex)
                view.setBackgroundResource(R.drawable.shape_rec)
                if (selectImageIndex >= flowLayout.childCount - 1) {
                    selectImageIndex = 0
                } else {
                    selectImageIndex++
                }
            }
        }
    }

    private fun stopTimer() {
        selectImageTimer.cancel()
    }

    private fun clearImageBackGround() {
        flowLayout.children.forEach {
            clearImageBackground(it)
        }
    }

    private fun clearImageBackground(view: View) {
        view.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }
}