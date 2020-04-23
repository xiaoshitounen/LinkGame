package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.LinkGame.Utils.LinkUtil;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.SelfView.XLTextView;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {
    //星星
    ImageView left_star;
    ImageView middle_star;
    ImageView right_star;
    private List<ImageView> stars;

    //关卡文本
    XLTextView level_text;
    //分数文本
    XLTextView score_text;
    //时间文本
    XLTextView time_text;
    //连击文本
    XLTextView batter_text;

    //关卡菜单按钮
    ImageButton menu_btn;
    ImageButton refresh_btn;
    ImageButton next_btn;

    //关卡
    XLLevel level;

    @Xml(layouts = "activity_success")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_success);
        X2C.setContentView(this,R.layout.activity_success);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //找到相关控件
        initView();

        //数据加载配置
        initData();
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

        //设置关卡数据
        level_text.setText("第"+level.getL_id()+"关");

        //设置星星
        int star_size = level.getL_new() - '0';
        Log.d(Constant.TAG,"星星个数："+level.getL_new()+" "+star_size);
        for (int i = 1; i <= star_size; i++) {
            stars.get(i-1).setVisibility(View.VISIBLE);
        }

        //设置时间
        time_text.setText(level.getL_time()+"秒");

        //设置分数
        score_text.setText(LinkUtil.getScoreByTime(level.getL_time())+"分");

        //设置连击
        batter_text.setText(LinkUtil.getSerialClick()+"次");
    }

    /**
     * 找到相关的控件
     */
    private void initView() {
        stars = new ArrayList<>();
        left_star = findViewById(R.id.star_left);
        left_star.setVisibility(View.INVISIBLE);
        middle_star = findViewById(R.id.star_middle);
        middle_star.setVisibility(View.INVISIBLE);
        right_star = findViewById(R.id.star_right);
        right_star.setVisibility(View.INVISIBLE);
        stars.add(left_star);
        stars.add(middle_star);
        stars.add(right_star);

        level_text = findViewById(R.id.level_text);
        score_text = findViewById(R.id.score_text);
        time_text = findViewById(R.id.time_text);
        batter_text = findViewById(R.id.batter_text);

        menu_btn = findViewById(R.id.btn_menu);
        menu_btn.setOnClickListener(this);
        refresh_btn = findViewById(R.id.btn_refresh);
        refresh_btn.setOnClickListener(this);
        next_btn = findViewById(R.id.btn_next);
        next_btn.setOnClickListener(this);
    }

    //按钮的监听回调
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                //关卡菜单按钮
                Log.d(Constant.TAG,"关卡菜单按钮");

                jumpToActivity(0);

                break;
            case R.id.btn_refresh:
                //重新加载按钮
                Log.d(Constant.TAG,"重新加载按钮");

                jumpToActivity(1);

                break;
            case R.id.btn_next:
                //下一关按钮
                Log.d(Constant.TAG,"下一个关卡按钮");

                jumpToActivity(2);

                break;
        }
    }

    /**
     * 跳转界面
     * @param flag
     */
    private void jumpToActivity(int flag){
        if (flag == 0){
            //查询对应模式的数据
            List<XLLevel> XLLevels = LitePal.where("l_mode == ?", String.valueOf(level.getL_mode())).find(XLLevel.class);
            Log.d(Constant.TAG,XLLevels.size()+"");
            //依次查询每一个内容
            for (XLLevel xlLevel : XLLevels) {
                Log.d(Constant.TAG, xlLevel.toString());
            }

            //跳转界面
            Intent intent = new Intent(this, LevelActivity.class);
            //加入数据
            Bundle bundle = new Bundle();
            //加入关卡模式数据
            bundle.putString("mode","简单");
            //加入关卡数据
            bundle.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (flag == 1){
            //跳转界面
            Intent intent = new Intent(this, LinkActivity.class);
            //加入数据
            Bundle bundle = new Bundle();
            //加入关卡数据
            bundle.putParcelable("level",level);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            //下一关
            //跳转界面
            Intent intent = new Intent(this, LinkActivity.class);
            //加入数据
            Bundle bundle = new Bundle();
            //加入关卡数据
            XLLevel next_level = LitePal.find(XLLevel.class, level.getId() + 1);
            bundle.putParcelable("level",next_level);
            intent.putExtras(bundle);
            //跳转
            startActivity(intent);
        }
    }
}
