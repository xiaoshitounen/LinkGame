package swu.xl.linkgame.LinkGame;

import swu.xl.linkgame.R;

public interface LinkBoard {
    /**
     * 连连看图标背景
     */
    int ANIMAL_BG = R.drawable.animal_bg;
    int ANIMAL_SELECT_BG = R.drawable.animal_select_bg;

    /**
     * 连连看模板
     * 0：空白
     * 1~∞：神奇宝贝图片
     */
    int[][] board_test1 = {
            {0,0,0,0,0,0},
            {0,1,2,3,4,0},
            {0,2,4,1,4,0},
            {0,3,1,3,2,0},
            {0,1,4,2,3,0},
            {0,0,0,0,0,0}
    };

    int[][] board_test2 = {
            {0,0,0,0,0,0},
            {0,1,0,3,4,0},
            {0,2,0,1,4,0},
            {0,3,1,3,2,0},
            {0,1,4,2,3,0},
            {0,0,0,0,0,0}
    };
}
