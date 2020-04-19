package swu.xl.linkgame.LinkGame;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装两个AnimalView之间的连接信息
 */
public class LinkInfo {
    //创建一个List用于存放两个AnimalView之间的连接信息
    private List<AnimalPoint> points = new ArrayList<>();

    //无参数构造方法
    public LinkInfo() {
    }

    //存储无转折的点信息
    public LinkInfo(AnimalPoint pointA, AnimalPoint pointB) {
        points.add(pointA);
        points.add(pointB);
    }

    //存储有一个转折的点信息
    public LinkInfo(AnimalPoint pointA, AnimalPoint pointC, AnimalPoint pointB) {
        points.add(pointA);
        points.add(pointC);
        points.add(pointB);
    }

    //存储有两个转折的点信息
    public LinkInfo(AnimalPoint pointA, AnimalPoint pointC, AnimalPoint pointD, AnimalPoint pointB) {
        points.add(pointA);
        points.add(pointC);
        points.add(pointD);
        points.add(pointB);
    }

    //setter、getter方法
    public List<AnimalPoint> getPoints() {
        return points;
    }

    public void setPoints(List<AnimalPoint> points) {
        this.points = points;
    }
}
