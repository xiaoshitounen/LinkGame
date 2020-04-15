package swu.xl.linkgame;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.LitePal;

import java.util.List;

import swu.xl.linkgame.Constant.Enum.LevelMode;
import swu.xl.linkgame.Model.XLLevel;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("swu.xl.linkgame", appContext.getPackageName());
    }

    //测试简单模式查询的值
    @Test
    public void modeEasyCountTest(){
        List<XLLevel> XLLevels = LitePal.where("l_mode == ?", "0").find(XLLevel.class);
        assertEquals(20,XLLevels.size());
    }
}
