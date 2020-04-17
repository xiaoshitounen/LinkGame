package swu.xl.linkgame.Constant.Enum;

/**
 * 关卡模式
 */
public enum LevelMode {
    //简单模式
    LEVEL_MODE_EASY(1),
    //普通模式
    LEVEL_MODE_NORMAL(2),
    //困难模式
    LEVEL_MODE_HARD(3);

    //存储传递的值
    private final int value;

    //构造方法
    LevelMode(int value) {
        this.value = value;
    }

    //get方法
    public int getValue() {
        return value;
    }
}
