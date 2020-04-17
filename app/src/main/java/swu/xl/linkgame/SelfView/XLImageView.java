package swu.xl.linkgame.SelfView;

import android.content.Context;

import swu.xl.linkgame.Constant.Enum.LevelState;
import swu.xl.linkgame.R;

public class XLImageView extends androidx.appcompat.widget.AppCompatImageView {
    //四套显示样式
    public static final int[] resources = {
            R.drawable.level_undo,      //关卡没有被打开
            R.drawable.level_did_one,   //关卡闯关成功 一星
            R.drawable.level_did_two,   //关卡闯关成功 二星
            R.drawable.level_did_three, //关卡闯关成功 三星
            R.drawable.level_doing      //关卡正在被闯
    };

    /**
     * 构造方法
     * @param context
     */
    public XLImageView(Context context,LevelState levelState) {
        super(context);

        this.changeLevelState(levelState);
    }

    /**
     * 设置背景颜色
     */
    public void changeLevelState(LevelState levelState){
        //切换到对应状态的图片
        this.setBackgroundResource(resources[levelState.getValue()]);
    }
}
