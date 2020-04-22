package swu.xl.linkgame.LinkGame.Model;

/**
 * 显示当前点的索引坐标
 */
public class AnimalPoint {
    public int x;
    public int y;

    /**
     * 两个参数的构造方法
     * @param x
     * @param y
     */
    public AnimalPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "AnimalPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
