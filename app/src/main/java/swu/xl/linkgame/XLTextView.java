package swu.xl.linkgame;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class XLTextView extends androidx.appcompat.widget.AppCompatTextView {
    public XLTextView(Context context) {
        super(context);
    }

    public XLTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //重写设置字体方法
    @Override
    public void setTypeface(@Nullable Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/造字工房乐真体.ttf");

        super.setTypeface(tf);
    }
}
