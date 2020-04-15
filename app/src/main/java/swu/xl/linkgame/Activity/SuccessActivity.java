package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        //找到相关控件
        initView();

        //设置相关信息
    }

    /**
     * 通过FindById找到相关的控件
     */
    private void initView() {
        stars = new ArrayList<>();
        left_star = findViewById(R.id.star_left);
        middle_star = findViewById(R.id.star_middle);
        right_star = findViewById(R.id.star_right);
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
                break;
            case R.id.btn_refresh:
                //重新加载按钮
                Log.d(Constant.TAG,"重新加载按钮");
                break;
            case R.id.btn_next:
                //下一关按钮
                Log.d(Constant.TAG,"下一个关卡按钮");
                break;
        }
    }
}
