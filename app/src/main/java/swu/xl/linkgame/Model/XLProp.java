package swu.xl.linkgame.Model;

/**
 * 道具
 */
public class XLProp {
    //道具号
    private int p_id;
    //道具的种类
    private char p_kind;
    //道具的名称
    private String p_name;
    //道具的的数量
    private int p_number;
    //道具的价格
    private int p_price;

    //setter，getter方法
    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public char getP_kind() {
        return p_kind;
    }

    public void setP_kind(char p_kind) {
        this.p_kind = p_kind;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
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
}
