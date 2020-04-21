package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.LinkGame.AnimalSearch;
import swu.xl.linkgame.LinkGame.AnimalView;
import swu.xl.linkgame.LinkGame.LinkConstant;
import swu.xl.linkgame.LinkGame.LinkInfo;
import swu.xl.linkgame.LinkGame.LinkManager;
import swu.xl.linkgame.LinkGame.XLRelativeLayout;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.PxUtil;
import swu.xl.linkgame.Util.ScreenUtil;

public class LinkActivity extends AppCompatActivity implements View.OnClickListener {
    //屏幕宽度
    int screenWidth;

    //当前关卡模型数据
    XLLevel level;

    //AnimalView的容器
    XLRelativeLayout link_layout;

    //存储点的信息集合
    LinkInfo linkInfo;

    //游戏管理者
    LinkManager manager;

    //关卡显示的文本
    TextView level_text;

    //拳头道具
    View prop_fight;
    //炸弹道具
    View prop_bomb;
    //刷新道具
    View prop_refresh;

    //暂停
    ImageView pause;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        //沉浸式状态栏
        ImmersionBar.with(this).barAlpha(1.0f).init();

        //加载数据
        initData();

        //加载视图
        initView();

        //开始游戏
        manager.startGame(this,
                link_layout,screenWidth,
                level.getL_id(),
                level.getL_mode()
        );

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
                                        AnimalSearch.canMatchTwoAnimalWithTwoBreak(
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
    }

    /**
     * 加载视图
     */
    private void initView() {
        screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());

        link_layout = findViewById(R.id.link_layout);

        linkInfo = new LinkInfo();

        manager = LinkManager.getLinkManager();

        level_text = findViewById(R.id.link_level_text);
        level_text.setText(String.valueOf(level.getL_id()));

        prop_fight = findViewById(R.id.prop_fight);
        prop_fight.setOnClickListener(this);
        prop_bomb = findViewById(R.id.prop_bomb);
        prop_bomb.setOnClickListener(this);
        prop_refresh = findViewById(R.id.prop_refresh);
        prop_refresh.setOnClickListener(this);

        pause = findViewById(R.id.link_pause);
        pause.setOnClickListener(this);
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
                break;
            case R.id.prop_bomb:
                Log.d(Constant.TAG,"炸弹道具");
                break;
            case R.id.prop_refresh:
                Log.d(Constant.TAG,"刷新道具");
                break;
            case R.id.link_pause:
                Log.d(Constant.TAG,"暂停");
                break;
        }
    }
}
