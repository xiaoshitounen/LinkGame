package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.LinkGame.LinkConstant;
import swu.xl.linkgame.LinkGame.LinkManager;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.ScreenUtil;

public class LinkActivity extends AppCompatActivity {
    //屏幕密度
    float density;

    //屏幕宽度
    int screenWidth;

    //当前关卡模型数据
    XLLevel level;

    //AnimalView的容器
    RelativeLayout link_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //加载数据
        initData();

        //加载视图
        initView();

        //开始游戏
        LinkManager.getLinkManager().startGame(this,
                link_layout,screenWidth,
                density,
                level.getL_id(),
                level.getL_mode()
        );
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
        density = getResources().getDisplayMetrics().density;

        screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());

        link_layout = findViewById(R.id.link_layout);
    }
}
