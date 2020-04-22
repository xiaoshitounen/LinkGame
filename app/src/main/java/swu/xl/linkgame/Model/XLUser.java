package swu.xl.linkgame.Model;

import org.litepal.crud.LitePalSupport;

/**
 * 用户
 */
public class XLUser extends LitePalSupport {
    //用户持有的金币数
    private int u_money;
    //当前用户使用的游戏背景
    private int u_background;

    //setter,getter方法
    public int getU_money() {
        return u_money;
    }

    public void setU_money(int u_money) {
        this.u_money = u_money;
    }

    public int getU_background() {
        return u_background;
    }

    public void setU_background(int u_background) {
        this.u_background = u_background;
    }

    @Override
    public String toString() {
        return "XLUser{" +
                "u_money=" + u_money +
                ", u_background=" + u_background +
                '}';
    }
}
