package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.PropMode;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.Model.XLProp;
import swu.xl.linkgame.Model.XLUser;
import swu.xl.linkgame.Music.BackgroundMusicManager;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.PxUtil;
import swu.xl.numberitem.NumberOfItem;

public class MainActivity extends BaseActivity implements View.OnClickListener {
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

    //根布局
    RelativeLayout root_main;

    //设置布局
    RelativeLayout inflate_setting;
    //帮助布局
    LinearLayout inflate_help;
    //商店布局
    RelativeLayout inflate_store;

    //是否加载设置布局完成标志
    boolean flag_setting = false;
    //是否加载帮助布局完成标志
    boolean flag_help = false;
    //是否加载商店布局完成标志
    boolean flag_store = false;

    //存储用户数据
    int user_money = 0;
    int fight_money = 0;
    int fight_num = 0;
    int bomb_money = 0;
    int bomb_num = 0;
    int refresh_money = 0;
    int refresh_num = 0;

    private BroadcastReceiver mBroadcastReceiver;

    @Xml(layouts = "activity_main")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        X2C.setContentView(this, R.layout.activity_main);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //数据库 LitePal
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();

        //向数据库装入数据
        initSQLite3();

        //初始化数据
        initView();

        //设置模式按钮的drawableLeft
        setDrawableLeft(mode_easy,R.drawable.main_mode_easy);
        setDrawableLeft(mode_normal,R.drawable.main_mode_normal);
        setDrawableLeft(mode_hard,R.drawable.main_mode_hard);

        //加载布局
        initInflate();

        //播放音乐
        playMusic();

        //广播接受者
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (!TextUtils.isEmpty(action)) {
                    switch (action) {
                        case Intent.ACTION_SCREEN_OFF:
                            Log.d(Constant.TAG, "屏幕关闭，变黑");

                            if (BackgroundMusicManager.getInstance(getBaseContext()).isBackgroundMusicPlaying()) {
                                Log.d(Constant.TAG, "正在播放音乐，关闭");

                                //暂停播放
                                BackgroundMusicManager.getInstance(getBaseContext()).pauseBackgroundMusic();
                            }

                            break;
                        case Intent.ACTION_SCREEN_ON:
                            Log.d(Constant.TAG, "屏幕开启，变亮");
                            break;
                        case Intent.ACTION_USER_PRESENT:
                            Log.d(Constant.TAG, "解锁成功");
                            break;
                        default:
                            break;
                    }
                }
            }
        };
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }

    /**
     * 初始化数据库
     */
    private void initSQLite3() {
        //查找当前数据库的内容
        List<XLUser> users = LitePal.findAll(XLUser.class);
        List<XLLevel> levels = LitePal.findAll(XLLevel.class);
        List<XLProp> props = LitePal.findAll(XLProp.class);

        //如果用户数据为空，装入数据
        if (users.size() == 0){
            XLUser user = new XLUser();
            user.setU_money(1000);
            user.setU_background(0);
            user.save();
        }

        //如果关卡数据为空，装入数据
        if (levels.size() == 0){
            //简单模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('1');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }

            //普通模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('2');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }

            //困难模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('3');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }
        }

        //如果道具数据为空，装入数据
        if (props.size() == 0){
            //1.装入拳头道具
            XLProp prop_fight = new XLProp();
            prop_fight.setP_kind('1');
            prop_fight.setP_number(9);
            prop_fight.setP_price(10);
            prop_fight.save();

            //2.装入炸弹道具
            XLProp prop_bomb = new XLProp();
            prop_bomb.setP_kind('2');
            prop_bomb.setP_number(9);
            prop_bomb.setP_price(10);
            prop_bomb.save();

            //3.装入刷新道具
            XLProp prop_refresh = new XLProp();
            prop_refresh.setP_kind('3');
            prop_refresh.setP_number(9);
            prop_refresh.setP_price(10);
            prop_refresh.save();
        }
    }

    /**
     * 数据的初始化
     */
    private void initView() {
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
        root_main = findViewById(R.id.root_main);
    }

    /**
     * 用给定资源设置指定按钮的drawableLeft
     */
    private void setDrawableLeft(Button btn, int main_mode_resource) {
        //获取指定的drawable
        Drawable drawable = getResources().getDrawable(main_mode_resource);
        //设置其drawable的左上右下
        drawable.setBounds(PxUtil.dpToPx(20,this),PxUtil.dpToPx(2,this), PxUtil.dpToPx(60,this),PxUtil.dpToPx(42,this));
        //设置放在控件的左上右下
        btn.setCompoundDrawables(drawable,null,null,null);
    }

    /**
     * 加载布局
     */
    private void initInflate() {
        //加载设置布局
        new Thread(new Runnable() {
            @Xml(layouts = "setting_view")
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {
                //加载布局
                inflate_setting = (RelativeLayout) X2C.inflate(MainActivity.this, R.layout.setting_view, null);

                //拦截事件 防止事件被传递下去
                inflate_setting.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                //获取当前的音量
                float backgroundVolume = BackgroundMusicManager.getInstance(MainActivity.this).getBackgroundVolume();
                Log.d(Constant.TAG,"当前的音量是："+backgroundVolume);

                //设置音量
                SeekBar seekBar = inflate_setting.findViewById(R.id.seek_bar_music);
                seekBar.setProgress((int) (backgroundVolume * 100));

                //改变标志
                flag_setting = true;
            }
        }).start();

        //加载帮助布局
        new Thread(new Runnable() {
            @Xml(layouts = "help_view")
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {
                //加载布局
                inflate_help = (LinearLayout) X2C.inflate(MainActivity.this, R.layout.help_view, null);

                //拦截事件 防止事件被传递下去
                inflate_help.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                //改变标志
                flag_help = true;
            }
        }).start();

        //加载商店布局
        new Thread(new Runnable() {
            @Xml(layouts = "store_view")
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {
                //加载布局
                inflate_store = (RelativeLayout) X2C.inflate(MainActivity.this, R.layout.store_view, null);

                //拦截事件 防止事件被传递下去
                inflate_store.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                //查询用户数据
                List<XLUser> users = LitePal.findAll(XLUser.class);
                XLUser user = users.get(0);
                user_money = user.getU_money();

                //查询道具数据
                List<XLProp> props = LitePal.findAll(XLProp.class);
                for (XLProp prop : props) {
                    if (prop.getP_kind() == PropMode.PROP_FIGHT.getValue()){
                        //拳头道具
                        fight_money = prop.getP_price();
                        fight_num = prop.getP_number();
                    }else if (prop.getP_kind() == PropMode.PROP_BOMB.getValue()){
                        //炸弹道具
                        bomb_money = prop.getP_price();
                        bomb_num = prop.getP_number();
                    }else {
                        //刷新道具
                        refresh_money = prop.getP_price();
                        refresh_num = prop.getP_number();
                    }
                }

                //找到显示道具数量的文本
                NumberOfItem prop_fight = inflate_store.findViewById(R.id.prop_fight);
                prop_fight.setCount(fight_num);
                NumberOfItem prop_bomb = inflate_store.findViewById(R.id.prop_bomb);
                prop_bomb.setCount(bomb_num);
                NumberOfItem prop_refresh = inflate_store.findViewById(R.id.prop_refresh);
                prop_refresh.setCount(refresh_num);

                //找到显示道具价值的文本
                TextView user_money_text = inflate_store.findViewById(R.id.store_user_money);
                user_money_text.setText(String.valueOf(user_money));
                TextView fight_money_text = inflate_store.findViewById(R.id.store_fight_money);
                fight_money_text.setText(String.valueOf(fight_money));
                TextView bomb_money_text = inflate_store.findViewById(R.id.store_bomb_money);
                bomb_money_text.setText(String.valueOf(bomb_money));
                TextView refresh_money_text = inflate_store.findViewById(R.id.store_refresh_money);
                refresh_money_text.setText(String.valueOf(refresh_money));

                //改变标志
                flag_store = true;
            }
        }).start();
    }

    /**
     * 播放背景音乐
     */
    private void playMusic() {
        //判断是否正在播放
        if (!BackgroundMusicManager.getInstance(this).isBackgroundMusicPlaying()) {

            //播放
            BackgroundMusicManager.getInstance(this).playBackgroundMusic(
                    R.raw.bg_music,
                    true
            );
        }
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

                //跳转界面
                Intent intent_easy = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_easy = new Bundle();
                //加入关卡模式数据
                bundle_easy.putString("mode","简单");
                //加入关卡数据
                bundle_easy.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels1);
                intent_easy.putExtras(bundle_easy);
                //跳转
                startActivity(intent_easy);

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

                //跳转界面
                Intent intent_normal = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_normal = new Bundle();
                //加入关卡模式数据
                bundle_normal.putString("mode","简单");
                //加入关卡数据
                bundle_normal.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels2);
                intent_normal.putExtras(bundle_normal);
                //跳转
                startActivity(intent_normal);

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

                //跳转界面
                Intent intent_hard = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_hard = new Bundle();
                //加入关卡模式数据
                bundle_hard.putString("mode","简单");
                //加入关卡数据
                bundle_hard.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels3);
                intent_hard.putExtras(bundle_hard);
                //跳转
                startActivity(intent_hard);

                break;
            case R.id.main_setting:
                Log.d(Constant.TAG,"设置按钮");

                if (loadView(flag_setting,inflate_setting)){
                    //音乐按钮
                    SeekBar seekBar = inflate_setting.findViewById(R.id.seek_bar_music);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            Log.d(Constant.TAG,"当前进度："+progress);

                            BackgroundMusicManager.getInstance(MainActivity.this).setBackgroundVolume((float) (progress / 100.0));
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });


                    //移除该视图
                    inflate_setting.findViewById(R.id.setting_finish).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root_main.removeViewInLayout(inflate_setting);
                        }
                    });
                }

                break;
            case R.id.main_help:
                Log.d(Constant.TAG,"帮助按钮");

                if (loadView(flag_help,inflate_help)){
                    //移除该视图
                    inflate_help.findViewById(R.id.main_know).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root_main.removeViewInLayout(inflate_help);
                        }
                    });
                }

                break;
            case R.id.main_store:
                Log.d(Constant.TAG,"商店按钮");

                if (loadView(flag_store,inflate_store)){

                    //购买拳头
                    LinearLayout fight = inflate_store.findViewById(R.id.store_fight);

                    fight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(Constant.TAG,"购买拳头");

                            refreshSQLite(PropMode.PROP_FIGHT);
                        }
                    });

                    //购买炸弹
                    inflate_store.findViewById(R.id.store_bomb).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(Constant.TAG,"购买炸弹");

                            refreshSQLite(PropMode.PROP_BOMB);
                        }
                    });

                    //购买刷新
                    inflate_store.findViewById(R.id.store_refresh).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(Constant.TAG,"购买刷新");

                            refreshSQLite(PropMode.PROP_REFRESH);
                        }
                    });

                    //移除该视图
                    inflate_store.findViewById(R.id.store_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root_main.removeViewInLayout(inflate_store);
                        }
                    });
                }

                break;
        }
    }

    /**
     * 加载视图
     * @param flag
     * @param inflate
     * @return
     */
    private boolean loadView(boolean flag, View inflate){
        if (flag){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            //layoutParams.setMargins(0, ScreenUtil.getScreenHeight(getApplicationContext()),0,0);
            root_main.addView(inflate,layoutParams);

            return true;
        }else {
            Toast.makeText(this, "正在加载视图，请稍后点击", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    /**
     * 刷新数据库的内容
     * @param mode
     */
    private void refreshSQLite(PropMode mode){
        //道具对象
        XLProp prop = new XLProp();

        switch (mode){
            case PROP_FIGHT:
                user_money -= fight_money;
                fight_num++;

                prop.setP_number(fight_num);
                prop.update(1);

                //道具购买提示
                Toast.makeText(this, "成功购买一个锤子道具，消耗"+fight_money+"金币", Toast.LENGTH_SHORT).show();
                break;
            case PROP_BOMB:
                user_money -= bomb_money;
                bomb_num++;

                prop.setP_number(bomb_num);
                prop.update(2);

                //道具购买提示
                Toast.makeText(this, "成功购买一个炸弹道具，消耗"+bomb_money+"金币", Toast.LENGTH_SHORT).show();
                break;
            case PROP_REFRESH:
                user_money -= refresh_money;
                refresh_num++;

                prop.setP_number(refresh_num);
                prop.update(3);

                //道具购买提示
                Toast.makeText(this, "成功购买一个重排道具，消耗"+refresh_money+"金币", Toast.LENGTH_SHORT).show();
                break;
        }

        //刷新用户数据
        XLUser user = new XLUser();
        user.setU_money(user_money);
        user.update(1);

        //重新设置金币
        TextView user_money_text = inflate_store.findViewById(R.id.store_user_money);
        user_money_text.setText(String.valueOf(user_money));

        //找到显示道具数量的文本
        NumberOfItem prop_fight = inflate_store.findViewById(R.id.prop_fight);
        prop_fight.setCount(fight_num);
        NumberOfItem prop_bomb = inflate_store.findViewById(R.id.prop_bomb);
        prop_bomb.setCount(bomb_num);
        NumberOfItem prop_refresh = inflate_store.findViewById(R.id.prop_refresh);
        prop_refresh.setCount(refresh_num);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver);
    }
}
