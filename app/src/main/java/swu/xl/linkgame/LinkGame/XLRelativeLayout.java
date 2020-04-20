package swu.xl.linkgame.LinkGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Util.PxUtil;

public class XLRelativeLayout extends RelativeLayout {
    //点的信息
    private LinkInfo linkInfo;

    //画笔
    private Paint paint;

    public XLRelativeLayout(Context context) {
        super(context);

        //让onDraw方法执行
        setWillNotDraw(false);

        float density = getResources().getDisplayMetrics().density;
    }

    public XLRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //让onDraw方法执行
        setWillNotDraw(false);
    }

    //setter getter
    public LinkInfo getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;

        //初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        invalidate();
    }

    //重绘
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //linkInfo不为空
        if (linkInfo != null){

            Log.d(Constant.TAG,"刷新界面");

            //获取点数据
            List<AnimalPoint> points = linkInfo.getPoints();

            //判断linkInfo是否有数据
            if (points.size() != 0){
                //画笔
                paint.setColor(Color.RED);
                paint.setStrokeWidth(10);

                //画路径
                for (int i = 0; i < points.size()-1; i++) {
                    //转换坐标
                    AnimalPoint realPoint1 = LinkUtil.getRealAnimalPoint(
                            points.get(i),
                            getContext()
                    );
                    AnimalPoint realPoint2 = LinkUtil.getRealAnimalPoint(
                            points.get(i+1),
                            getContext()
                    );

                    //测试
                    Log.d(Constant.TAG,points.get(i).toString()+" "+realPoint1.toString());
                    Log.d(Constant.TAG,points.get(i+1).toString()+" "+realPoint2.toString());

                    //画线
                    canvas.drawLine(
                            realPoint1.x,
                            realPoint1.y,
                            realPoint2.x,
                            realPoint2.y,
                            paint
                    );
                }
            }
        }
    }
}
