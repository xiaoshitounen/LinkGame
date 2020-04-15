package swu.xl.linkgame.Util;

public class PxUtil {

    /**
     * 将传递的 整型dp 值转化为 px
     * @param dp
     * @param density
     * @return
     */
    public static int dpToPx(int dp, float density){
        return (int) (dp * density);
    }

    /**
     * 将传递的 浮点型dp 值转化为 px
     * @param dp
     * @param density
     * @return
     */
    public static int dpToPx(float dp, float density){
        return (int) (dp * density);
    }

    /**
     * 将传递的 整型px 值转化为 dp
     * @param px
     * @param density
     * @return
     */
    public static float pxToDp(int px, float density){
        return  (px / density);
    }

    /**
     * 将传递的 浮点型px 值转化为 dp
     * @param px
     * @param density
     * @return
     */
    public static float pxToDp(float px, float density){
        return  (px / density);
    }

    /**
     * 将传递的 sp 值转化为 px
     * @param sp
     * @param density
     * @return
     */
    public static int spToPx(int sp, float density){
        return (int) (sp * density);
    }

    /**
     * 将传递的 px 值 转化为 sp
     * @param px
     * @param density
     * @return
     */
    public static int pxToSp(int px, float density){
        return (int) (px / density);
    }
}
