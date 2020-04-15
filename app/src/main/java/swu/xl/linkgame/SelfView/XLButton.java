package swu.xl.linkgame.SelfView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class XLButton extends androidx.appcompat.widget.AppCompatButton {
    public XLButton(Context context) {
        super(context);
    }

    public XLButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写设置字体方法
    @Override
    public void setTypeface(@Nullable Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/造字工房乐真体.ttf");

        super.setTypeface(tf);
    }
}
