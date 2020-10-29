package cn.cxy.selectbeauty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * 屏幕/分辨率工具类
 */
public final class ScreenUtil {
    private static final Point SCREEN_SIZE = new Point(-1, -1);

    private ScreenUtil() {
        throw new AssertionError("Don't instance!");
    }

    /**
     * 获取屏幕尺寸
     */
    @SuppressLint("ObsoleteSdkInt")
    
    public static Point getScreenSize( Context context) {
        //Preconditions.checkNotNull(context, "context must be not null.");
        if (SCREEN_SIZE.x != -1 && SCREEN_SIZE.y != -1) {
            return new Point(SCREEN_SIZE);
        } else {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT > 16) {
                display.getRealSize(SCREEN_SIZE);
            } else {
                display.getSize(SCREEN_SIZE);
            }

            return new Point(SCREEN_SIZE);
        }
    }

    /**
     * dp转px
     */
    public static int dp2px( Context context, int dp) {
        //Preconditions.checkNotNull(context, "context must be null.");
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(1, (float) dp, metrics);
    }

    /**
     * px转dp
     */
    public static int px2dp( Context context, int px) {
        //Preconditions.checkNotNull(context, "context must be null.");
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((float) px / density);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight( Context context) {
        //Preconditions.checkNotNull(context, "context must be null.");
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }


    /**
     * 获取view高度
     */
    public static int getViewHeight(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredHeight();
    }
}

