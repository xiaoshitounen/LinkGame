package swu.xl.linkgame.Model;

import androidx.constraintlayout.solver.LinearSystem;

import org.litepal.crud.LitePalSupport;

/**
 * 道具
 */
public class XLProp extends LitePalSupport {
    //道具的种类 1：拳头 2：炸弹 3：刷新
    private char p_kind;
    //道具的的数量
    private int p_number;
    //道具的价格
    private int p_price;

    //setter，getter方法
    public char getP_kind() {
        return p_kind;
    }

    public void setP_kind(char p_kind) {
        this.p_kind = p_kind;
    }

    public int getP_number() {
        return p_number;
    }

    public void setP_number(int p_number) {
        this.p_number = p_number;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }

    @Override
    public String toString() {
        return "XLProp{" +
                "p_kind=" + p_kind +
                ", p_number=" + p_number +
                ", p_price=" + p_price +
                '}';
    }
}
