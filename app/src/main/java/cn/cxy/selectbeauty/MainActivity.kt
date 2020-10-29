package cn.cxy.selectbeauty

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    lateinit var selectImageTimer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            if (button.text != "停") {
                countDownTv.visibility = VISIBLE
                countDownTv.setCallback(object : OnFinishCallback {
                    override fun onFinished() {
                        startSelectImageTimer()
                    }
                })

                button.text = "停"
            } else {
                stopTimer()
                button.visibility = GONE
                flowLayout.visibility = GONE
                selectedIv.visibility = VISIBLE
            }
        }
    }

    private fun startSelectImageTimer() {
        selectImageTimer = fixedRateTimer("", false, 0, 500) {
            GlobalScope.launch(Dispatchers.Main) {
                clearBackGround()
                val view = flowLayout.getChildAt(index)
                view.setBackgroundResource(R.drawable.shape_rec)
                if (index >= flowLayout.childCount - 1) {
                    index = 0
                } else {
                    index++
                }
            }
        }
    }

    var index = 0

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