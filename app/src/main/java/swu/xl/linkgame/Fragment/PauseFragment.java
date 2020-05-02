package swu.xl.linkgame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Activity.LevelActivity;
import swu.xl.linkgame.Activity.LinkActivity;
import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.LevelState;
import swu.xl.linkgame.LinkGame.Manager.LinkManager;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.Music.SoundPlayUtil;
import swu.xl.linkgame.R;

public class PauseFragment extends Fragment {
    XLLevel level;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //接受数据
        level = getArguments().getParcelable("level");

        //加载布局
        View inflate = inflater.inflate(R.layout.pause_view, container, false);

        //拦截事件
        inflate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //处理事件
        inflate.findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                //回到关卡

                //查询对应模式的数据
                List<XLLevel> XLLevels = LitePal.where("l_mode == ?", String.valueOf(level.getL_mode())).find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels.size()+"");
                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels) {
                    Log.d(Constant.TAG, xlLevel.toString());
                }

                //跳转界面
                Intent intent = new Intent(getActivity(), LevelActivity.class);
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
        inflate.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                //回到游戏
                LinkManager.getLinkManager().pauseGame();

                //移除自己
                if (getActivity() != null){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(PauseFragment.this);
                    transaction.commit();
                }else {
                    System.out.println("空的Activity");
                }
            }
        });
        inflate.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                //下一关

                //加入关卡数据
                XLLevel next_level = LitePal.find(XLLevel.class, level.getId() + 1);

                //判断是否开启
                if (next_level.getL_new() != LevelState.LEVEL_STATE_NO.getValue()){
                    //跳转界面
                    Intent intent = new Intent(getActivity(), LinkActivity.class);
                    //加入数据
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("level",next_level);
                    intent.putExtras(bundle);
                    //跳转
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "下一关还没有开启", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return inflate;
    }
}
