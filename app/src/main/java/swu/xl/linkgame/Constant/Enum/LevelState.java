package swu.xl.linkgame.Constant.Enum;

/**
 * 关卡状态
 */
public enum LevelState {
    LEVEL_STATE_NO(0),
    LEVEL_STATE_ONE(1),
    LEVEL_STATE_TWO(2),
    LEVEL_STATE_THREE(3);

    //存储传递的值
    private final int value;

    //构造方法
    LevelState(int value) {
        this.value = value;
    }

    //getter方法
    public int getValue() {
        return value;
    }
}
