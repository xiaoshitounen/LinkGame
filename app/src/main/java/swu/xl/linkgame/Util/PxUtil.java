package swu.xl.linkgame.Util;

import android.content.Context;

import swu.xl.linkgame.Constant.Constant;

public class PxUtil {

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 将传递的 整型dp 值转化为 px
     * @param dp
     * @param context
     * @return
     */
    public static int dpToPx(int dp, Context context){
        return (int) (dp * getScreenDensity(context));
    }

    /**
     * 将传递的 浮点型dp 值转化为 px
     * @param dp
     * @param context
     * @return
     */
    public static int dpToPx(float dp, Context context){
        return (int) (dp * getScreenDensity(context));
    }

    /**
     * 将传递的 整型px 值转化为 dp
     * @param px
     * @param context
     * @return
     */
    public static float pxToDp(int px, Context context){
        return  (px / getScreenDensity(context));
    }

    /**
     * 将传递的 浮点型px 值转化为 dp
     * @param px
     * @param context
     * @return
     */
    public static float pxToDp(float px, Context context){
        return  (px / getScreenDensity(context));
    }

    /**
     * 将传递的 sp 值转化为 px
     * @param sp
     * @param context
     * @return
     */
    public static int spToPx(int sp, Context context){
        return (int) (sp * getScreenDensity(context));
    }

    /**
     * 将传递的 px 值 转化为 sp
     * @param px
     * @param context
     * @return
     */
    public static int pxToSp(int px, Context context){
        return (int) (px / getScreenDensity(context));
    }
}
