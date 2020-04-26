package swu.xl.linkgame.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.Objects;

import swu.xl.linkgame.Constant.Constant;

public class ScreenUtil {
    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        //窗口管理者
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //存储尺寸的
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //获取尺寸
        assert windowManager != null;
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        //窗口管理者
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //存储尺寸的
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //获取尺寸
        assert windowManager != null;
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }

    /**
     * 获取StatusBar状态栏的高度
     * @param context
     * @return
     */
    public static int getStateBarHeight(Context context){
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 判断设置是否有NavigationBar导航栏
     * @param context
     * @return
     */
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;

                Log.d(Constant.TAG,"手机没有导航栏");
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;

                Log.d(Constant.TAG,"手机有导航栏");
            }
        } catch (Exception e) {

        }

        return hasNavigationBar;
    }

    /**
     * 返回NavigationBar导航栏的高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int height = 0;

        //如果存在
        if (ScreenUtil.checkDeviceHasNavigationBar(context)){
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
            height = resources.getDimensionPixelSize(resourceId);
            Log.v("dbw", "Navi height:" + height);
        }

        return height;
    }

}
