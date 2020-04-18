package swu.xl.linkgame.Constant.Enum;

/**
 * 关卡状态
 */
public enum LevelState {
    //没有闯关
    LEVEL_STATE_NO('0'),
    //一星
    LEVEL_STATE_ONE('1'),
    //二星
    LEVEL_STATE_TWO('2'),
    //三星
    LEVEL_STATE_THREE('3'),
    //闯关中
    LEVEL_STATE_DOING('4');

    //存储传递的值
    private final char value;

    //构造方法
    LevelState(char value) {
        this.value = value;
    }

    //getter方法
    public char getValue() {
        return value;
    }

    //根据值得到状态
    public static LevelState getState(char value){
        LevelState levelState = LEVEL_STATE_NO;

        //找到对应的state
        switch (value){
            case '0':
                levelState = LEVEL_STATE_NO;
                break;
            case '1':
                levelState = LEVEL_STATE_ONE;
                break;
            case '2':
                levelState = LEVEL_STATE_TWO;
                break;
            case '3':
                levelState = LEVEL_STATE_THREE;
                break;
            case '4':
                levelState = LEVEL_STATE_DOING;
        }

        return levelState;
    }
}
