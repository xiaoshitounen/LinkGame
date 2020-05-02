package swu.xl.linkgame.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Music.BackgroundMusicManager;
import swu.xl.linkgame.Music.SoundPlayUtil;
import swu.xl.linkgame.R;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        View inflate = inflater.inflate(R.layout.setting_view, container, false);

        //拦截事件
        inflate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //处理事件
        SeekBar seekBar_music = inflate.findViewById(R.id.seek_bar_music);
        float progress_music = BackgroundMusicManager.getInstance(getContext()).getBackgroundVolume() * 100;
        Log.d(Constant.TAG,"背景音乐进度:"+progress_music);
        seekBar_music.setProgress((int) progress_music);
        seekBar_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(Constant.TAG,"当前进度："+progress);

                BackgroundMusicManager.getInstance(getContext()).setBackgroundVolume((float) (progress / 100.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //音乐按钮
        SeekBar seekBar_effect = inflate.findViewById(R.id.seek_bar_effect);
        float progress_effect = SoundPlayUtil.getInstance(getContext()).getVoice() * 100;
        Log.d(Constant.TAG,"音效进度:"+progress_effect);
        seekBar_effect.setProgress((int) progress_effect);
        seekBar_effect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(Constant.TAG,"当前进度："+progress);

                SoundPlayUtil.getInstance(getContext()).setVoice((float) (progress / 100.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //移除该视图
        inflate.findViewById(R.id.setting_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                if (getActivity() != null){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(SettingFragment.this);
                    transaction.commit();
                }else {
                    System.out.println("空的Activity");
                }
            }
        });

        return inflate;
    }
}
