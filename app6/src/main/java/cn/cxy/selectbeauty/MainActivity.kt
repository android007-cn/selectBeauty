package cn.cxy.selectbeauty

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private lateinit var selectImageTimer: Timer
    private var selectImageIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        queryData()
    }

    private fun initListeners() {
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

    private fun queryData() {
        val networkService = getNetworkService()
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { networkService.query() }
            result.forEach { addNewViewByUrl(it.url) }
        }
    }

    private fun addNewViewByUrl(url: String) {
        val imageView = ImageView(this)
        imageView.setPadding(dp2px(5))
        Glide.with(this).load(url).into(imageView)
        val layoutParams = LinearLayout.LayoutParams(dp2px(100), dp2px(100))
        layoutParams.setMargins(dp2px(10), dp2px(10), dp2px(10), dp2px(10))
        flowLayout.addView(imageView, layoutParams)
    }

    fun dp2px(dp: Int): Int {
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(1, dp.toFloat(), metrics).toInt()
    }


    fun getNetworkService(): NetworkService {
        val okHttpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gitee.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NetworkService::class.java)
    }
}