package cn.cxy.selectbeauty

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
                val selectedImageView = flowLayout.getChildAt(selectImageIndex) as ImageView
                selectedIv.setImageDrawable(selectedImageView.drawable)
                selectedIv.visibility = VISIBLE
            }
        }
    }

    private fun startSelectImageTimer() {
        selectImageTimer = fixedRateTimer("", false, 0, 500) {
            GlobalScope.launch(Dispatchers.Main) {
                clearBackGround()
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
        Log.d("MainActivity", "醒醒！！！")
    }

    private fun clearBackGround() {
        flowLayout.children.forEach {
            clearBackground(it)
        }
    }

    private fun clearBackground(view: View) {
        view.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }
}