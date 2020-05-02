package swu.xl.linkgame.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import swu.xl.linkgame.Music.SoundPlayUtil;
import swu.xl.linkgame.R;

public class HelpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        View inflate = inflater.inflate(R.layout.help_view, container, false);

        //拦截事件
        inflate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //处理事件
        inflate.findViewById(R.id.main_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                if (getActivity() != null){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(HelpFragment.this);
                    transaction.commit();
                }else {
                    System.out.println("空的Activity");
                }
            }
        });

        return inflate;
    }
}
