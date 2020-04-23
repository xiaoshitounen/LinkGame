package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.LevelState;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.SelfView.XLImageView;
import swu.xl.linkgame.Util.PxUtil;
import swu.xl.linkgame.Util.ScreenUtil;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
    //屏幕宽度
    int screenWidth;

    //记录屏幕当前的偏移程度
    int offset = 0;

    //关卡模式数据
    String mode;

    //关卡数据
    List<XLLevel> levels;

    //按钮
    ImageButton back;
    Button pager_up;
    Button pager_down;

    //确定总页数
    int pager;

    //文本
    TextView pager_text;

    //页面控制器
    HorizontalScrollView level_pager;

    //关卡根布局
    RelativeLayout level_layout;

    @Xml(layouts = "activity_level")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_level);
        X2C.setContentView(this,R.layout.activity_level);

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
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //屏幕的宽高
        screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        Log.d(Constant.TAG,"屏幕宽度："+screenWidth);

        back = findViewById(R.id.pager_back);
        back.setOnClickListener(this);
        pager_up = findViewById(R.id.pager_up);
        pager_up.setOnClickListener(this);
        pager_down = findViewById(R.id.pager_down);
        pager_down.setOnClickListener(this);

        pager_text = findViewById(R.id.pager_text);

        level_pager = findViewById(R.id.level_pager);
        level_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //禁止HorizontalScrollView滑动
                //滑动会影响页面控制器
                //HorizontalScrollView滑动时也没有回调方法
                return true;
            }
        });

        level_layout = findViewById(R.id.level_root);
    }

    /**
     * 加载关卡
     */
    private void initLevel() {
        level_layout.post(new Runnable() {
            @Override
            public void run() {
                //循环展示
                for (int i = 0; i < levels.size(); i++){
                    //确定页数
                    pager = i / Constant.LEVEL_PAGER_COUNT;
                    pager_text.setText("1/"+(pager+1));
                    //确定在当前页数的第几行
                    int pager_row = i % Constant.LEVEL_PAGER_COUNT / Constant.LEVEL_ROW_COUNT;
                    //确定在当前页数的第几列
                    int pager_col = i % Constant.LEVEL_PAGER_COUNT % Constant.LEVEL_ROW_COUNT;
                    //边距
                    int level_padding = (screenWidth - Constant.LEVEL_ROW_COUNT *
                            PxUtil.dpToPx(Constant.LEVEL_SIZE,getApplicationContext())) /
                            (Constant.LEVEL_ROW_COUNT + 1);

                    //创建视图
                    XLImageView xlImageView = new XLImageView(
                            getApplicationContext(),
                            LevelState.getState(levels.get(i).getL_new()));

                    //设置id
                    xlImageView.setId(i);

                    //布局参数
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            PxUtil.dpToPx(Constant.LEVEL_SIZE,getApplicationContext()),
                            PxUtil.dpToPx(Constant.LEVEL_SIZE,getApplicationContext())
                    );

                    //添加约束
                    layoutParams.leftMargin = screenWidth * pager + level_padding +
                            (level_padding + PxUtil.dpToPx(Constant.LEVEL_SIZE,getApplicationContext())) * pager_col;
                    layoutParams.topMargin = ScreenUtil.getStateBarHeight(getApplicationContext()) +
                            PxUtil.dpToPx(Constant.LEVEL_TOP,getApplicationContext()) +
                            level_padding + (level_padding + PxUtil.dpToPx(Constant.LEVEL_SIZE,getApplicationContext())) * pager_row;

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

                            //判断是否可以进入该关卡
                            if (LevelState.getState(levels.get(v.getId()).getL_new()) != LevelState.LEVEL_STATE_NO){
                                jumpToLinkActivity(levels.get(v.getId()));
                            }else {
                                Toast.makeText(LevelActivity.this, "当前关卡不可进入", Toast.LENGTH_SHORT).show();
                            }
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
     * 左右滑动一个屏幕关卡视图
     * @param direction 1:右滑 -1:左滑
     * @return 返回值表示是否滑动
     */
    public boolean scrollLevelsOfDirection(int direction){

        if ((direction == 1 && offset == (pager * screenWidth)) || (direction == -1 && offset == 0)){
            //如果当前向右滑动 且 当前已经处于最后一页 或
            //如果当前向左滑动 且 当前已经处于第一页
            return false;
        }else if(direction == 1 && offset == ((pager-1) * screenWidth)) {
            //如果当前向右滑动 且 滑动后处于最后一页
            //右边的按钮设置不可用
            pager_down.setEnabled(false);
            pager_down.setBackgroundResource(R.drawable.level_page_down_enable);
        }else if(direction == -1 && offset == screenWidth){
            //如果当前向左滑动 且 滑动后处于第一页
            //左边的按钮设置不可用
            pager_up.setEnabled(false);
            pager_up.setBackgroundResource(R.drawable.level_page_up_enable);
        }else{
            //恢复
            pager_up.setEnabled(true);
            pager_up.setBackgroundResource(R.drawable.level_page_up);
            pager_down.setEnabled(true);
            pager_down.setBackgroundResource(R.drawable.level_page_down);
        }

        //滑动视图
        level_pager.smoothScrollTo(offset + screenWidth * direction,0);

        //修改偏移值
        offset = offset + screenWidth * direction;

        //修改显示内容
        pager_text.setText((offset / screenWidth+1) + "/" + (pager+1));

        return true;
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

                startActivity(new Intent(LevelActivity.this,MainActivity.class));

                //淡入淡出切换动画
                //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                //从左向右滑动动画
                //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                //自定义动画
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.pager_up:
                Log.d(Constant.TAG,"上一页");

                //左滑
                scrollLevelsOfDirection(-1);
                break;
            case R.id.pager_down:
                Log.d(Constant.TAG,"下一页");

                //右滑
                scrollLevelsOfDirection(1);
                break;
        }
    }
}
