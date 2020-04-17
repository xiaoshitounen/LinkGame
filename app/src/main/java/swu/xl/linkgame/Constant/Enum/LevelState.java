package swu.xl.linkgame.Constant.Enum;

/**
 * 关卡状态
 */
public enum LevelState {
    //没有闯关
    LEVEL_STATE_NO(0),
    //一星
    LEVEL_STATE_ONE(1),
    //二星
    LEVEL_STATE_TWO(2),
    //三星
    LEVEL_STATE_THREE(3),
    //闯关中
    LEVEL_STATE_DOING(4);

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

    //根据值得到状态
    public static LevelState getState(int value){
        LevelState levelState = LEVEL_STATE_NO;

        //找到对应的state
        switch (value){
            case 0:
                levelState = LEVEL_STATE_NO;
            case 1:
                levelState = LEVEL_STATE_ONE;
            case 2:
                levelState = LEVEL_STATE_TWO;
            case 3:
                levelState = LEVEL_STATE_THREE;
            case 4:
                levelState = LEVEL_STATE_DOING;
        }

        return levelState;
    }
}
