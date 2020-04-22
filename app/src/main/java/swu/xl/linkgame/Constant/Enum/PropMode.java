package swu.xl.linkgame.Constant.Enum;

/**
 * 道具种类
 */
public enum PropMode {
    //简单模式
    PROP_FIGHT('1'),
    //普通模式
    PROP_BOMB('2'),
    //困难模式
    PROP_REFRESH('3');

    //存储传递的值
    private final char value;

    //构造方法
    PropMode(char value) {
        this.value = value;
    }

    //get方法
    public char getValue() {
        return value;
    }
}
