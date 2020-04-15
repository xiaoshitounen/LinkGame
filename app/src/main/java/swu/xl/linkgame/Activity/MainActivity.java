package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gyf.immersionbar.ImmersionBar;

import org.litepal.LitePal;

import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.PxUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //屏幕密度
    float density;

    //简单模式
    Button mode_easy;
    //普通模式
    Button mode_normal;
    //困难模式
    Button mode_hard;

    //设置按钮
    Button btn_setting;
    //帮助按钮
    Button btn_help;
    //商店按钮
    Button btn_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //数据库 LitePal
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();

        //初始化数据
        initView();

        //设置模式按钮的drawableLeft
        setDrawableLeft(mode_easy,R.drawable.main_mode_easy);
        setDrawableLeft(mode_normal,R.drawable.main_mode_normal);
        setDrawableLeft(mode_hard,R.drawable.main_mode_hard);
    }

    /**
     * 数据的初始化
     */
    private void initView() {
        density = getResources().getDisplayMetrics().density;

        mode_easy = findViewById(R.id.main_mode_easy);
        mode_easy.setOnClickListener(this);
        mode_normal = findViewById(R.id.main_mode_normal);
        mode_normal.setOnClickListener(this);
        mode_hard = findViewById(R.id.main_mode_hard);
        mode_hard.setOnClickListener(this);
        btn_setting = findViewById(R.id.main_setting);
        btn_setting.setOnClickListener(this);
        btn_help = findViewById(R.id.main_help);
        btn_help.setOnClickListener(this);
        btn_store = findViewById(R.id.main_store);
        btn_store.setOnClickListener(this);
    }

    /**
     * 用给定资源设置指定按钮的drawableLeft
     */
    private void setDrawableLeft(Button btn, int main_mode_resource) {
        //获取指定的drawable
        Drawable drawable = getResources().getDrawable(main_mode_resource);
        //设置其drawable的左上右下
        drawable.setBounds(PxUtil.dpToPx(20,density),PxUtil.dpToPx(2,density), PxUtil.dpToPx(60,density),PxUtil.dpToPx(42,density));
        //设置放在控件的左上右下
        btn.setCompoundDrawables(drawable,null,null,null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_mode_easy:
                Log.d(Constant.TAG,"简单模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels1 = LitePal.where("l_mode == ?", "1").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels1.size()+"");

                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels1) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                break;
            case R.id.main_mode_normal:
                Log.d(Constant.TAG,"普通模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels2 = LitePal.where("l_mode == ?", "2").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels2.size()+"");

                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels2) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                break;
            case R.id.main_mode_hard:
                Log.d(Constant.TAG,"困难模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels3 = LitePal.where("l_mode == ?", "3").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels3.size()+"");
                
                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels3) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                break;
            case R.id.main_setting:
                Log.d(Constant.TAG,"设置按钮");
                break;
            case R.id.main_help:
                Log.d(Constant.TAG,"帮助按钮");
                break;
            case R.id.main_store:
                Log.d(Constant.TAG,"商店按钮");
                break;
        }
    }
}
