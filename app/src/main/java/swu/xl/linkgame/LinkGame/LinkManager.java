package swu.xl.linkgame.LinkGame;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Util.PxUtil;

/**
 * 使用单例模式
 * 游戏管理者，管理关于游戏的一切
 */
public class LinkManager {
    //掌管AnimalView分布规则（布局）
    private int[][] board;

    //掌管游戏时间
    private int time = LinkConstant.time;

    //偏移间距
    private int padding;

    //存储所有AnimalView
    private List<AnimalView> animals = new ArrayList<>();

    //保存上一个触摸的AnimalView
    private AnimalView lastAnimal;

    //单例模式
    private static LinkManager instance;
    private LinkManager(){}
    public static synchronized LinkManager getLinkManager(){
        if (instance == null){
            instance = new LinkManager();
        }
        return instance;
    }

    //开始游戏
    public void startGame(Context context, RelativeLayout layout, int width,int level_id, int level_mode){
        //清楚上一次游戏的痕迹
        board = null;
        time = LinkConstant.time;
        padding = 0;
        animals.clear();
        lastAnimal = null;

        //产生二维数组布局模板
        setBoard(LinkUtil.loadLevelWithIdAndMode(level_id,level_mode));

        //界面布局
        addAnimalViewToLayout(context,layout,width);
    }

    //在给定的布局上添加AnimalView
    private void addAnimalViewToLayout(Context context, RelativeLayout layout, int width){
        //随机加载AnimalView的显示图片
        List<Integer> resources = LinkUtil.loadPictureResourceWithBoard(getBoard());

        //横竖方向的个数
        int row_animal_num = getBoard().length;
        int col_animal_num = getBoard()[0].length;

        //计算两边的间距
        padding = (width - col_animal_num * PxUtil.dpToPx(LinkConstant.animal_size, context)) / 2;

        //依次添加到布局中
        for (int i = 0; i < row_animal_num; i++) {
            for (int j = 0; j < col_animal_num; j++) {
                //判断当前位置是否需要显示内容
                AnimalView animal;
                if (getBoard()[i][j] == 0){
                    animal = new AnimalView(
                            context,
                            0,
                            new AnimalPoint(i,j));
                    animal.setVisibility(View.INVISIBLE);
                }else {
                    //创建一个AnimalView
                    animal = new AnimalView(
                            context,
                            LinkConstant.ANIMAL_RESOURCE[resources.get(getBoard()[i][j]-1)],
                            getBoard()[i][j],
                            new AnimalPoint(i, j)
                    );
                }

                //创建布局约束
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        PxUtil.dpToPx(LinkConstant.animal_size,context),
                        PxUtil.dpToPx(LinkConstant.animal_size,context)
                );

                //左上间距
                layoutParams.leftMargin = padding + PxUtil.dpToPx(LinkConstant.animal_size,context) * j;
                layoutParams.topMargin = padding + PxUtil.dpToPx(LinkConstant.animal_size,context) * i;

                //设置内间距
                animal.setPadding(
                        PxUtil.dpToPx(LinkConstant.animal_padding,context),
                        PxUtil.dpToPx(LinkConstant.animal_padding,context),
                        PxUtil.dpToPx(LinkConstant.animal_padding,context),
                        PxUtil.dpToPx(LinkConstant.animal_padding,context)
                );

                //添加视图
                layout.addView(animal,layoutParams);

                //保存该视图
                if (animal.getFlag() != 0){
                    animals.add(animal);
                }
            }
        }
    }

    //游戏操作（选中两个相同的点）

    //暂停游戏

    //刷新游戏

    //使用道具

    //游戏结束

    //setter，getter方法
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<AnimalView> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalView> animals) {
        this.animals = animals;
    }

    public AnimalView getLastAnimal() {
        return lastAnimal;
    }

    public void setLastAnimal(AnimalView lastAnimal) {
        this.lastAnimal = lastAnimal;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
