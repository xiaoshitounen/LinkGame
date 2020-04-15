package swu.xl.linkgame.Model;

import java.util.List;

/**
 * 用户
 */
public class XLUser {
    //用户的账号
    private int u_id;
    //用户持有的金币数
    private int u_money;
    //用户持有的道具
    private List<XLProp> u_props;
    //当前用户使用的游戏背景
    private int u_background;

    //setter,getter方法
    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getU_money() {
        return u_money;
    }

    public void setU_money(int u_money) {
        this.u_money = u_money;
    }

    public List<XLProp> getU_props() {
        return u_props;
    }

    public void setU_props(List<XLProp> u_props) {
        this.u_props = u_props;
    }

    public int getU_background() {
        return u_background;
    }

    public void setU_background(int u_background) {
        this.u_background = u_background;
    }
}
