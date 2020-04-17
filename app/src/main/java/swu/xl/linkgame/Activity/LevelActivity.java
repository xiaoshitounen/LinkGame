package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.ScreenUtil;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
    //屏幕密度
    float density;

    //关卡模式数据
    String mode;

    //关卡数据
    List<XLLevel> levels;

    //按钮
    ImageButton back;
    Button pager_up;
    Button pager_down;

    //文本
    TextView pager_text;

    //页面控制器
    HorizontalScrollView level_pager;

    //关卡根布局
    RelativeLayout level_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //加载数据
        initData();

        //加载视图
        initView();

        //加载关卡
        initLevel();
    }

    /**
     * 加载数据
     */
    private void initData() {
        //获取数据
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        mode = bundle.getString("mode");
        levels = bundle.getParcelableArrayList("levels");

        //依次查询每一个内容
        Log.d(Constant.TAG,"--------");
        Log.d(Constant.TAG,mode);
        assert levels != null;
        Log.d(Constant.TAG,levels.size()+"");
        for (XLLevel xlLevel : levels) {
            Log.d(Constant.TAG,xlLevel.toString());
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        density = getResources().getDisplayMetrics().density;

        back = findViewById(R.id.pager_back);
        back.setOnClickListener(this);
        pager_up = findViewById(R.id.pager_up);
        pager_up.setOnClickListener(this);
        pager_down = findViewById(R.id.pager_down);
        pager_down.setOnClickListener(this);

        pager_text = findViewById(R.id.pager_text);

        level_pager = findViewById(R.id.level_pager);

        level_layout = findViewById(R.id.level_root);
    }

    /**
     * 加载关卡
     */
    private void initLevel() {
        level_layout.post(new Runnable() {
            @Override
            public void run() {
                //屏幕的宽高
                int screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
                int screenHeight = ScreenUtil.getScreenHeight(getApplicationContext());
            }
        });
    }

    /**
     * 处理按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pager_back:
                Log.d(Constant.TAG,"返回按钮");
                finish();
                break;
            case R.id.pager_up:
                Log.d(Constant.TAG,"上一页");
                break;
            case R.id.pager_down:
                Log.d(Constant.TAG,"下一页");
                break;
        }
    }
}
