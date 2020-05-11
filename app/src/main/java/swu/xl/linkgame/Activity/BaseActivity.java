package swu.xl.linkgame.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Music.BackgroundMusicManager;
import swu.xl.linkgame.Util.StateUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //判断是否进入后台
        if (StateUtil.isBackground(this)) {
            Log.d(Constant.TAG,"后台");

            //暂停播放
            BackgroundMusicManager.getInstance(this).pauseBackgroundMusic();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //继续播放背景音乐
        if (!BackgroundMusicManager.getInstance(this).isBackgroundMusicPlaying()) {
            BackgroundMusicManager.getInstance(this).resumeBackgroundMusic();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Log.d(Constant.TAG,"系统返回");
    }
}
