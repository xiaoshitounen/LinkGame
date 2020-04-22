package swu.xl.linkgame.LinkGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import swu.xl.linkgame.Activity.FailureActivity;
import swu.xl.linkgame.Activity.SuccessActivity;
import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.Util.PxUtil;

/**
 * 使用单例模式
 * 游戏管理者，管理关于游戏的一切
 */
public class LinkManager {
    //掌管AnimalView分布规则（布局）
    private int[][] board;

    //掌管游戏时间
    private Timer timer;
    private float time = LinkConstant.time;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            //判断消息来源
            if (msg.what == Constant.timer){
                Log.d(Constant.TAG,"测试处理");

                //时间减少
                time -= 0.1;

                //设置文本
                listener.onTimeChanged(time);
            }
        }
    };

    //偏移间距
    private int padding;

    //存储所有AnimalView
    private List<AnimalView> animals = new ArrayList<>();

    //保存上一个触摸的AnimalView
    private AnimalView lastAnimal;

    //AnimalView的大小
    private int animal_size;

    //监听者
    private LinkGame listener;

    //是否暂停
    private boolean isPause = false;

    //单例模式
    private static LinkManager instance;
    private LinkManager(){}
    public static synchronized LinkManager getLinkManager(){
        if (instance == null){
            instance = new LinkManager();
        }
        return instance;
    }

    /**
     * 开始游戏
     * @param context
     * @param layout
     * @param width
     * @param level_id
     * @param level_mode
     */
    public void startGame(Context context, RelativeLayout layout, int width,int level_id, int level_mode){
        //清楚上一次游戏的痕迹
        clearLastGame();

        //产生二维数组布局模板
        setBoard(LinkUtil.loadLevelWithIdAndMode(level_id,level_mode));

        //界面布局
        addAnimalViewToLayout(context,layout,width);

        //开启定时器
        startTimer(time);
    }

    //清楚上一次游戏的痕迹
    private void clearLastGame() {
        board = null;
        time = LinkConstant.time;
        padding = 0;
        animals.clear();
        lastAnimal = null;
        animal_size = 0;
        isPause = false;
    }

    //在给定的布局上添加AnimalView
    private void addAnimalViewToLayout(Context context, RelativeLayout layout, int width){
        //随机加载AnimalView的显示图片
        List<Integer> resources = LinkUtil.loadPictureResourceWithBoard(getBoard());

        //横竖方向的个数
        int row_animal_num = getBoard().length;
        int col_animal_num = getBoard()[0].length;

        //根据数量动态设置AnimalView的大小
        Log.d(Constant.TAG,"行数："+row_animal_num+" 列数："+col_animal_num);
        if (row_animal_num <= 8 && col_animal_num <= 6){
            animal_size = LinkConstant.animal_size;
        }else if (row_animal_num >= 10){
            animal_size = LinkConstant.animal_size_more_small;
        }else {
            animal_size = LinkConstant.animal_size_small;
        }

        //计算两边的间距
        padding = (width - col_animal_num * PxUtil.dpToPx(animal_size, context)) / 2;

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
                        PxUtil.dpToPx(animal_size,context),
                        PxUtil.dpToPx(animal_size,context)
                );

                //左上间距
                layoutParams.leftMargin = padding + PxUtil.dpToPx(animal_size,context) * j;
                layoutParams.topMargin = padding + PxUtil.dpToPx(animal_size,context) * i;

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

    //开启定时器
    private void startTimer(float time){
        //取消之前的定时器
        if (timer != null){
            stopTimer();
        }

        //以游戏时间开启定时器
        timer = new Timer();

        //启动定时器每隔一秒，发送一次消息
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(Constant.timer);
            }
        },0,100);
    }

    //关闭定时器
    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    /**
     * 暂停游戏
     */
    public void pauseGame(){
        //判断是打开还是关闭
        if (!isPause){
            stopTimer();
        }else {
            startTimer(time);
        }

        //切换状态
        isPause = !isPause;
    }

    /**
     * 刷新游戏
     * @param context
     * @param layout
     * @param width
     * @param level_id
     * @param level_mode
     */
    public void refreshGame(Context context, RelativeLayout layout, int width,int level_id, int level_mode){
        //移除所有的子视图
        layout.removeAllViews();

        //重新开始游戏
        startGame(context,layout,width,level_id,level_mode);
    }

    /**
     * 结束游戏
     * @param context
     * @param level
     * @param time
     */
    public void endGame(Context context, XLLevel level, float time) {
        if (time < 0.1){
            Log.d(Constant.TAG, "失败啦");

            //界面跳转
            Intent intent = new Intent(context, FailureActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("level",level);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else {
            Log.d(Constant.TAG, "成功啦");

            Intent intent = new Intent(context, SuccessActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("level",level);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    //接口
    public interface LinkGame{
        //时间改变了
        void onTimeChanged(float time);
    }

    //setter，getter方法
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
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

    public int getAnimal_size() {
        return animal_size;
    }

    public void setAnimal_size(int animal_size) {
        this.animal_size = animal_size;
    }

    public LinkGame getListener() {
        return listener;
    }

    public void setListener(LinkGame listener) {
        this.listener = listener;
    }
}
