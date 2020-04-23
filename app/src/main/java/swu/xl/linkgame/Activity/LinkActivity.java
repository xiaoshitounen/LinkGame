package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.LevelState;
import swu.xl.linkgame.Constant.Enum.PropMode;
import swu.xl.linkgame.LinkGame.Utils.AnimalSearchUtil;
import swu.xl.linkgame.LinkGame.SelfView.AnimalView;
import swu.xl.linkgame.LinkGame.Constant.LinkConstant;
import swu.xl.linkgame.LinkGame.Model.LinkInfo;
import swu.xl.linkgame.LinkGame.Manager.LinkManager;
import swu.xl.linkgame.LinkGame.Utils.LinkUtil;
import swu.xl.linkgame.LinkGame.SelfView.XLRelativeLayout;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.Model.XLProp;
import swu.xl.linkgame.Model.XLUser;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.PxUtil;
import swu.xl.linkgame.Util.ScreenUtil;

public class LinkActivity extends AppCompatActivity implements View.OnClickListener,LinkManager.LinkGame {
    //屏幕宽度,高度
    int screenWidth;
    int screenHeight;

    //信息布局的bottom
    int message_bottom;

    //当前关卡模型数据
    XLLevel level;

    //用户
    XLUser user;

    //道具
    List<XLProp> props;

    //道具信息等布局
    RelativeLayout message_show_layout;

    //道具布局
    RelativeLayout props_layout;

    //时间信息等布局
    RelativeLayout time_show_layout;

    //AnimalView的容器
    XLRelativeLayout link_layout;

    //存储点的信息集合
    LinkInfo linkInfo;

    //游戏管理者
    LinkManager manager;

    //显示关卡的文本
    TextView level_text;
    //显示金币的文本
    TextView money_text;
    //显示时间的文本
    TextView time_text;

    //拳头道具
    View prop_fight;
    //炸弹道具
    View prop_bomb;
    //刷新道具
    View prop_refresh;

    //显示拳头道具数量的文本
    TextView fight_num_text;

    //显示炸弹道具数量的文本
    TextView bomb_num_text;

    //显示刷新道具数量的文本
    TextView refresh_num_text;

    //记录金币的变量
    int money;

    //记录拳头道具的数量
    int fight_num;

    //记录炸弹道具的数量
    int bomb_num;

    //记录刷新道具的数量
    int refresh_num;

    //暂停
    ImageView pause;
    
    //根布局
    RelativeLayout root_link;

    //暂停布局
    LinearLayout inflate_pause;

    //是否加载暂停布局完成标志
    boolean flag_pause = false;

    @Xml(layouts = "activity_link")
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_link);
        X2C.setContentView(this,R.layout.activity_link);

        //沉浸式状态栏
        ImmersionBar.with(this).barAlpha(1.0f).init();

        //加载数据
        initData();

        //加载视图
        initView();

        //加载布局
        initInflate();

        //监听触摸事件
        link_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取触摸点相对于布局的坐标
                int x = (int) event.getX();
                int y = (int) event.getY();

                //触摸事件
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    for (final AnimalView animal : manager.getAnimals()) {
                        //获取AnimalView实例的rect
                        RectF rectF = new RectF(
                                animal.getLeft(),
                                animal.getTop(),
                                animal.getRight(),
                                animal.getBottom());

                        //判断是否包含
                        if (rectF.contains(x,y) && animal.getVisibility() == View.VISIBLE){
                            //获取上一次触摸的AnimalView
                            final AnimalView lastAnimal = manager.getLastAnimal();

                            //如果不是第一次触摸 且 触摸的不是同一个点
                            if (lastAnimal != null && lastAnimal != animal){

                                Log.d(Constant.TAG,lastAnimal+" "+animal);

                                //如果两者的图片相同，且两者可以连接
                                if(animal.getFlag() == lastAnimal.getFlag() &&
                                        AnimalSearchUtil.canMatchTwoAnimalWithTwoBreak(
                                        manager.getBoard(),
                                        lastAnimal.getPoint(),
                                        animal.getPoint(),
                                        linkInfo
                                )){
                                    //当前点改变背景和动画
                                    animal.changeAnimalBackground(LinkConstant.ANIMAL_SELECT_BG);
                                    animationOnSelectAnimal(animal);

                                    //画线
                                    link_layout.setLinkInfo(linkInfo);

                                    //延迟操作
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //修改模板
                                            manager.getBoard()[lastAnimal.getPoint().x][lastAnimal.getPoint().y] = 0;
                                            manager.getBoard()[animal.getPoint().x][animal.getPoint().y] = 0;

                                            //输出模板
                                            for (int i = 0; i < manager.getBoard().length; i++) {
                                                for (int j = 0; j < manager.getBoard()[0].length; j++) {
                                                    System.out.print(manager.getBoard()[i][j]+" ");
                                                }
                                                System.out.println("");
                                            }

                                            //隐藏
                                            lastAnimal.setVisibility(View.INVISIBLE);
                                            lastAnimal.clearAnimation();
                                            animal.setVisibility(View.INVISIBLE);
                                            animal.clearAnimation();

                                            //上一个点置空
                                            manager.setLastAnimal(null);

                                            //去线
                                            link_layout.setLinkInfo(null);

                                            //获得金币
                                            money += 2;
                                            money_text.setText(String.valueOf(money));
                                        }
                                    },500);
                                }else {
                                    //否则

                                    //上一个点恢复原样
                                    lastAnimal.changeAnimalBackground(LinkConstant.ANIMAL_BG);
                                    if (lastAnimal.getAnimation() != null){
                                        //清楚所有动画
                                        lastAnimal.clearAnimation();
                                    }

                                    //设置当前点的背景颜色和动画
                                    animal.changeAnimalBackground(LinkConstant.ANIMAL_SELECT_BG);
                                    animationOnSelectAnimal(animal);

                                    //将当前点作为选中点
                                    manager.setLastAnimal(animal);
                                }
                            }else if (lastAnimal == null){
                                //第一次触摸 当前点改变背景和动画
                                animal.changeAnimalBackground(LinkConstant.ANIMAL_SELECT_BG);
                                animationOnSelectAnimal(animal);

                                //将当前点作为选中点
                                manager.setLastAnimal(animal);
                            }
                        }
                    }
                }

                return true;
            }
        });
    }

    /**
     * 加载数据
     */
    private void initData() {
        //获取数据
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        level = bundle.getParcelable("level");

        Log.d(Constant.TAG,"--------");
        Log.d(Constant.TAG, String.valueOf(level));

        //查询用户数据
        List<XLUser> users = LitePal.findAll(XLUser.class);
        user = users.get(0);
        money = user.getU_money();

        //查询道具数据
        props = LitePal.findAll(XLProp.class);
        for (XLProp prop : props) {
            if (prop.getP_kind() == PropMode.PROP_FIGHT.getValue()){
                //拳头道具
                fight_num = prop.getP_number();
            }else if (prop.getP_kind() == PropMode.PROP_BOMB.getValue()){
                //炸弹道具
                bomb_num = prop.getP_number();
            }else {
                //刷新道具
                refresh_num = prop.getP_number();
            }
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        screenHeight = ScreenUtil.getScreenHeight(getApplicationContext());
        Log.d(Constant.TAG,"屏幕宽度："+PxUtil.pxToDp(screenWidth,this)+" "+"屏幕高度："+PxUtil.pxToDp(screenWidth,this));

        message_show_layout = findViewById(R.id.message_show);
        message_show_layout.setPadding(0,ScreenUtil.getStateBarHeight(this)+ PxUtil.dpToPx(5,this),0,0);
        time_show_layout = findViewById(R.id.time_show);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                PxUtil.dpToPx(120, this),
                PxUtil.dpToPx(120, this)
        );
        layoutParams.setMargins(
                PxUtil.dpToPx(-40, this),
                ScreenUtil.getStateBarHeight(this) - PxUtil.dpToPx(20,this),
                0,0);
        time_show_layout.setLayoutParams(layoutParams);
        time_show_layout.post(new Runnable() {
            @Override
            public void run() {
                message_bottom = time_show_layout.getBottom();

                //开始游戏
                manager.startGame(getApplicationContext(),
                        link_layout,
                        screenWidth,
                        screenHeight-message_bottom-ScreenUtil.getNavigationBarHeight(getApplicationContext()),
                        level.getL_id(),
                        level.getL_mode()
                );

                Log.d(Constant.TAG,"屏幕高度："+PxUtil.pxToDp(screenHeight,getApplicationContext()));
                Log.d(Constant.TAG,"时间文本的bottom：："+PxUtil.pxToDp(message_bottom,getApplicationContext()));
                Log.d(Constant.TAG,"AnimalView内容的高度："+PxUtil.pxToDp(screenHeight-message_bottom,getApplicationContext()));
            }
        });

        link_layout = findViewById(R.id.link_layout);
        ViewGroup.LayoutParams params_link_layout = link_layout.getLayoutParams();
        params_link_layout.height = screenHeight-message_bottom;
        link_layout.setLayoutParams(params_link_layout);

        linkInfo = new LinkInfo();

        manager = LinkManager.getLinkManager();

        level_text = findViewById(R.id.link_level_text);
        level_text.setText(String.valueOf(level.getL_id()));
        money_text = findViewById(R.id.link_money_text);
        time_text = findViewById(R.id.link_time_text);

        props_layout = findViewById(R.id.link_props);
        prop_fight = findViewById(R.id.prop_fight);
        prop_fight.setOnClickListener(this);
        prop_bomb = findViewById(R.id.prop_bomb);
        prop_bomb.setOnClickListener(this);
        prop_refresh = findViewById(R.id.prop_refresh);
        prop_refresh.setOnClickListener(this);
        pause = findViewById(R.id.link_pause);
        final RelativeLayout.LayoutParams params_pause = new RelativeLayout.LayoutParams(
                PxUtil.dpToPx(45, this),
                PxUtil.dpToPx(45, this)
        );
        props_layout.post(new Runnable() {
            @Override
            public void run() {
                //添加向右依赖
                params_pause.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                //设置右边距
                params_pause.rightMargin = (screenWidth-props_layout.getRight()) / 2;

                //设置给控件
                pause.setLayoutParams(params_pause);
            }
        });

        pause.setOnClickListener(this);

        manager.setListener(this);

        //找到显示道具数量的控件
        fight_num_text = findViewById(R.id.link_prop_fight_text);
        bomb_num_text = findViewById(R.id.link_prop_bomb_text);
        refresh_num_text = findViewById(R.id.link_prop_refresh_text);

        //设置金币
        money_text.setText(String.valueOf(money));

        //设置道具数量
        fight_num_text.setText(String.valueOf(fight_num));
        bomb_num_text.setText(String.valueOf(bomb_num));
        refresh_num_text.setText(String.valueOf(refresh_num));

        root_link = findViewById(R.id.root_link);
    }

    /**
     * 加载布局
     */
    @Xml(layouts = "pause_view")
    private void initInflate() {
        //加载帮助布局
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载布局
                inflate_pause = (LinearLayout) X2C.inflate(LinkActivity.this, R.layout.pause_view, null);

                //改变标志
                flag_pause = true;
            }
        }).start();
    }

    /**
     * 选中的AnimalView动画
     * @param animal
     */
    private void animationOnSelectAnimal(AnimalView animal){
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.05f,
                1.0f, 1.05f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        scaleAnimation.setDuration(100);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);


        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(
                -20f,
                20f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setDuration(500);
        rotateAnimation.setStartOffset(100);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setInterpolator(new BounceInterpolator());

        //组合动画
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);

        //开启动画
        animal.startAnimation(animationSet);
        animationSet.startNow();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prop_fight:
                Log.d(Constant.TAG,"拳头道具");

                if (fight_num > 0){
                    //随机消除一对可以消除的AnimalView
                    manager.fightGame();

                    //数量减1
                    fight_num--;
                    fight_num_text.setText(String.valueOf(fight_num));

                    //数据库处理
                    XLProp prop = props.get(0);
                    prop.setP_number(fight_num);
                    prop.update(1);
                }else {
                    Toast.makeText(this, "道具已经用完", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.prop_bomb:
                Log.d(Constant.TAG,"炸弹道具");

                if (bomb_num > 0){
                    //随机消除某一种所有的AnimalView
                    manager.bombGame();

                    //数量减1
                    bomb_num--;
                    bomb_num_text.setText(String.valueOf(bomb_num));

                    //数据库处理
                    XLProp prop = props.get(1);
                    prop.setP_number(bomb_num);
                    prop.update(2);
                }else {
                    Toast.makeText(this, "道具已经用完", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.prop_refresh:
                Log.d(Constant.TAG,"刷新道具");

                if (refresh_num > 0){
                    //刷新游戏
                    manager.refreshGame(
                            getApplicationContext(),
                            link_layout,
                            screenWidth,
                            screenHeight-message_bottom-ScreenUtil.getNavigationBarHeight(getApplicationContext()),
                            level.getL_id(),
                            level.getL_mode());

                    //数量减1
                    refresh_num--;
                    refresh_num_text.setText(String.valueOf(refresh_num));

                    //数据库处理
                    XLProp prop = props.get(2);
                    prop.setP_number(refresh_num);
                    prop.update(3);
                }else {
                    Toast.makeText(this, "道具已经用完", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.link_pause:
                Log.d(Constant.TAG,"暂停");

                //暂停游戏
                if (flag_pause){
                    //定时器暂停
                    manager.pauseGame();

                    //加载布局
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //layoutParams.setMargins(0, ScreenUtil.getScreenHeight(getApplicationContext()),0,0);
                    root_link.addView(inflate_pause,layoutParams);

                    //按钮的事件回调
                    inflate_pause.findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //回到关卡

                            //查询对应模式的数据
                            List<XLLevel> XLLevels = LitePal.where("l_mode == ?", String.valueOf(level.getL_mode())).find(XLLevel.class);
                            Log.d(Constant.TAG,XLLevels.size()+"");
                            //依次查询每一个内容
                            for (XLLevel xlLevel : XLLevels) {
                                Log.d(Constant.TAG, xlLevel.toString());
                            }

                            //跳转界面
                            Intent intent = new Intent(LinkActivity.this, LevelActivity.class);
                            //加入数据
                            Bundle bundle = new Bundle();
                            //加入关卡模式数据
                            bundle.putString("mode","简单");
                            //加入关卡数据
                            bundle.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    inflate_pause.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //回到游戏
                            manager.pauseGame();

                            //移除自己
                            root_link.removeView(inflate_pause);
                        }
                    });
                    inflate_pause.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //下一关

                            //加入关卡数据
                            XLLevel next_level = LitePal.find(XLLevel.class, level.getId() + 1);

                            //判断是否开启
                            if (next_level.getL_new() != LevelState.LEVEL_STATE_NO.getValue()){
                                //跳转界面
                                Intent intent = new Intent(LinkActivity.this, LinkActivity.class);
                                //加入数据
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("level",next_level);
                                intent.putExtras(bundle);
                                //跳转
                                startActivity(intent);
                            }else {
                                Toast.makeText(LinkActivity.this, "下一关还没有开启", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
        }
    }

    //时间发生改变的时间
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeChanged(float time) {
        //如果时间小于0
        if (time <= 0.0){
            manager.pauseGame();
            manager.endGame(this,level,time);
        }else {
            //保留小数后一位
            time_text.setText(new DecimalFormat("##0.0").format(time)+"秒");
        }

        //如果board全部清除了
        if (LinkUtil.getBoardState()){
            //结束游戏
            manager.pauseGame();
            level.setL_time((int) (LinkConstant.TIME -time));
            level.setL_new(LinkUtil.getStarByTime((int) time));
            manager.endGame(this,level,time);

            //关卡结算
            level.update(level.getId());

            //下一关判断
            XLLevel next_level = LitePal.find(XLLevel.class, level.getId() + 1);
            if (next_level.getL_new() == '0'){
                next_level.setL_new('4');
                next_level.update(level.getId()+1);
            }

            //金币道具清算
            user.setU_money(money);
            user.update(1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //暂停游戏
        manager.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //开启游戏
        if (manager.isPause()){
            manager.pauseGame();
        }
    }
}
