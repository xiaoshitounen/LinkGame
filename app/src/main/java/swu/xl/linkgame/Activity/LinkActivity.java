package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.R;

public class LinkActivity extends AppCompatActivity {
    //当前关卡
    XLLevel level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //加载数据
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

        Log.d(Constant.TAG,"--------");
        Log.d(Constant.TAG, String.valueOf(level));
    }

}
