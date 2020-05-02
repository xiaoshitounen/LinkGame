package swu.xl.linkgame.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.litepal.LitePal;

import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.PropMode;
import swu.xl.linkgame.Model.XLProp;
import swu.xl.linkgame.Model.XLUser;
import swu.xl.linkgame.Music.SoundPlayUtil;
import swu.xl.linkgame.R;
import swu.xl.numberitem.NumberOfItem;

public class StoreFragment extends Fragment {
    //存储数据
    private int user_money = 0;
    private int fight_money = 0;
    private int fight_num = 0;
    private int bomb_money = 0;
    private int bomb_num = 0;
    private int refresh_money = 0;
    private int refresh_num = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        final View inflate = inflater.inflate(R.layout.store_view, container, false);

        //拦截事件
        inflate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //处理事件
        //查询用户数据
        List<XLUser> users = LitePal.findAll(XLUser.class);
        XLUser user = users.get(0);
        //存储用户数据
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
        NumberOfItem prop_fight = inflate.findViewById(R.id.prop_fight);
        prop_fight.setCount(fight_num);
        NumberOfItem prop_bomb = inflate.findViewById(R.id.prop_bomb);
        prop_bomb.setCount(bomb_num);
        NumberOfItem prop_refresh = inflate.findViewById(R.id.prop_refresh);
        prop_refresh.setCount(refresh_num);

        //找到显示道具价值的文本
        TextView user_money_text = inflate.findViewById(R.id.store_user_money);
        user_money_text.setText(String.valueOf(user_money));
        TextView fight_money_text = inflate.findViewById(R.id.store_fight_money);
        fight_money_text.setText(String.valueOf(fight_money));
        TextView bomb_money_text = inflate.findViewById(R.id.store_bomb_money);
        bomb_money_text.setText(String.valueOf(bomb_money));
        TextView refresh_money_text = inflate.findViewById(R.id.store_refresh_money);
        refresh_money_text.setText(String.valueOf(refresh_money));

        //购买拳头
        LinearLayout fight = inflate.findViewById(R.id.store_fight);

        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG,"购买拳头");

                refreshSQLite(PropMode.PROP_FIGHT,inflate);
            }
        });

        //购买炸弹
        inflate.findViewById(R.id.store_bomb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                Log.d(Constant.TAG,"购买炸弹");

                refreshSQLite(PropMode.PROP_BOMB,inflate);
            }
        });

        //购买刷新
        inflate.findViewById(R.id.store_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                Log.d(Constant.TAG,"购买刷新");

                refreshSQLite(PropMode.PROP_REFRESH,inflate);
            }
        });

        //移除该视图
        inflate.findViewById(R.id.store_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放点击音效
                SoundPlayUtil.getInstance(getContext()).play(3);

                if (getActivity() != null){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(StoreFragment.this);
                    transaction.commit();
                }else {
                    System.out.println("空的Activity");
                }
            }
        });

        return inflate;
    }

    /**
     * 刷新数据库的内容
     * @param mode
     */
    private void refreshSQLite(PropMode mode,View inflate){
        //道具对象
        XLProp prop = new XLProp();

        switch (mode){
            case PROP_FIGHT:
                user_money -= fight_money;
                fight_num++;

                prop.setP_number(fight_num);
                prop.update(1);

                //道具购买提示
                Toast.makeText(getContext(), "成功购买一个锤子道具，消耗"+fight_money+"金币", Toast.LENGTH_SHORT).show();
                break;
            case PROP_BOMB:
                user_money -= bomb_money;
                bomb_num++;

                prop.setP_number(bomb_num);
                prop.update(2);

                //道具购买提示
                Toast.makeText(getContext(), "成功购买一个炸弹道具，消耗"+bomb_money+"金币", Toast.LENGTH_SHORT).show();
                break;
            case PROP_REFRESH:
                user_money -= refresh_money;
                refresh_num++;

                prop.setP_number(refresh_num);
                prop.update(3);

                //道具购买提示
                Toast.makeText(getContext(), "成功购买一个重排道具，消耗"+refresh_money+"金币", Toast.LENGTH_SHORT).show();
                break;
        }

        //刷新用户数据
        XLUser user = new XLUser();
        user.setU_money(user_money);
        user.update(1);

        //重新设置金币
        TextView user_money_text = inflate.findViewById(R.id.store_user_money);
        user_money_text.setText(String.valueOf(user_money));

        //找到显示道具数量的文本
        NumberOfItem prop_fight = inflate.findViewById(R.id.prop_fight);
        prop_fight.setCount(fight_num);
        NumberOfItem prop_bomb = inflate.findViewById(R.id.prop_bomb);
        prop_bomb.setCount(bomb_num);
        NumberOfItem prop_refresh = inflate.findViewById(R.id.prop_refresh);
        prop_refresh.setCount(refresh_num);
    }
}
