package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.LevelState;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.SelfView.XLImageView;
import swu.xl.linkgame.Util.PxUtil;
import swu.xl.linkgame.Util.ScreenUtil;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
    //屏幕密度
    float density;

    //屏幕宽度
    int screenWidth;

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
                screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
                Log.d(Constant.TAG,"屏幕宽度："+screenWidth);

                //循环展示
                for (int i = 0; i < levels.size(); i++){
                    //确定页数
                    int pager = i / Constant.level_pager_count;
                    //确定在当前页数的第几行
                    int pager_row = i % Constant.level_pager_count / Constant.level_row_count;
                    //确定在当前页数的第几列
                    int pager_col = i % Constant.level_pager_count % Constant.level_row_count;
                    //边距
                    int level_padding = (screenWidth - Constant.level_row_count *
                            PxUtil.dpToPx(Constant.level_width,density)) /
                            (Constant.level_row_count + 1);

                    //创建视图
                    XLImageView xlImageView = new XLImageView(
                            getApplicationContext(),
                            LevelState.getState(levels.get(i).getL_new()));

                    //设置id
                    xlImageView.setId(i);

                    //布局参数
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            PxUtil.dpToPx(Constant.level_width,density),
                            PxUtil.dpToPx(Constant.level_width,density)
                    );

                    //添加约束
                    layoutParams.leftMargin = screenWidth * pager + level_padding +
                            (level_padding + PxUtil.dpToPx(Constant.level_width,density)) * pager_col;
                    layoutParams.topMargin = ScreenUtil.getStateBarHeight(getApplicationContext()) +
                            PxUtil.dpToPx(Constant.level_top,density) +
                            level_padding + (level_padding + PxUtil.dpToPx(Constant.level_width,density)) * pager_row;

                    //最后一位需要添加右边距
                    if (i == levels.size()-1) {
                        layoutParams.rightMargin = level_padding;
                    }

                    //添加控件到容器中
                    level_layout.addView(xlImageView,layoutParams);

                    //点击事件
                    xlImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(Constant.TAG,"关卡"+v.getId());

                            jumpToLinkActivity(levels.get(v.getId()));
                        }
                    });
                }
            }
        });
    }

    /**
     * 界面跳转
     */
    public void jumpToLinkActivity(XLLevel level){
        //跳转界面
        Intent intent = new Intent(this, LinkActivity.class);
        //加入数据
        Bundle bundle = new Bundle();
        //加入关卡数据
        bundle.putParcelable("level",level);
        intent.putExtras(bundle);
        //跳转
        startActivity(intent);
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

                level_pager.smoothScrollTo(-screenWidth,0);
                break;
            case R.id.pager_down:
                Log.d(Constant.TAG,"下一页");

                level_pager.smoothScrollTo(screenWidth,0);
                break;
        }
    }
}
