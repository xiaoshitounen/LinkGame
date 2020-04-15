package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.R;

public class FailureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        //关卡菜单按钮
        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG,"关卡菜单按钮被点击了");
            }
        });

        //刷新按钮
        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG,"刷新按钮被点击了");
            }
        });
    }
}
