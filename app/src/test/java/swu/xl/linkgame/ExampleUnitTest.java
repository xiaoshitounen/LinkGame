package swu.xl.linkgame;

import org.junit.Test;
import org.litepal.LitePal;

import java.util.List;

import swu.xl.linkgame.Constant.Enum.LevelMode;
import swu.xl.linkgame.Model.XLLevel;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //测试枚举的值
    @Test
    public void enumValueTest(){
        assertEquals(1, LevelMode.LEVEL_MODE_EASY.getValue());
    }

}