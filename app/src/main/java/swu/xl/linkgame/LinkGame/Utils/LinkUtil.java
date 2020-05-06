package swu.xl.linkgame.LinkGame.Utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Constant.Enum.LevelMode;
import swu.xl.linkgame.LinkGame.Constant.LinkBoard;
import swu.xl.linkgame.LinkGame.Model.AnimalPoint;
import swu.xl.linkgame.LinkGame.Constant.LinkConstant;
import swu.xl.linkgame.LinkGame.Manager.LinkManager;
import swu.xl.linkgame.LinkGame.SelfView.AnimalView;
import swu.xl.linkgame.Util.PxUtil;

public class LinkUtil {
    /**
     * 设置所有的宝可梦的响应状态
     * @param status
     */
    public static void setBoardsStatus(boolean status){
        //获取所有宝可梦视图
        LinkManager manager = LinkManager.getLinkManager();
        List<AnimalView> animals = manager.getAnimals();

        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).setEnabled(status);
        }
    }

    /**
     * 返回一个布局中可以消除的两个AnimalView
     * @return
     */
    public static AnimalPoint[] getDoubleRemove(){
        //获得模板
        int[][] board = LinkManager.getLinkManager().getBoard();

        //存储两个相对坐标
        AnimalPoint point1 = null;
        AnimalPoint point2 = null;

        //找到两个点
        while (point1 == null && point2 == null){
            //寻找第一个点
            for (int i = 1; i < board.length-1; i++) {
                for (int j = 1; j < board[0].length-1; j++) {
                    //产生第一个点
                    point1 = new AnimalPoint(i,j);

                    for (int p = 1; p < board.length-1; p++) {
                        for (int q = 1; q < board[0].length-1; q++) {
                            //产生第二个点
                            point2 = new AnimalPoint(p,q);
                            Log.d(Constant.TAG,"第一个点："+point1.x+" "+point1.y);
                            Log.d(Constant.TAG,"第二个点："+point2.x+" "+point2.y);

                            //如果两个点不是同一个点
                            if (point1.x != point2.x || point1.y != point2.y){
                                //并且该两点图案相同 且不为0
                                if (board[point1.x][point1.y] == board[point2.x][point2.y]
                                && board[point1.x][point1.y] != 0){
                                    //并且可以被消除
                                    if (AnimalSearchUtil.canMatchTwoAnimalWithTwoBreak(board,point1,point2,null)){
                                        return new AnimalPoint[]{point1,point2};
                                    }
                                }
                            }

                        }
                    }

                }
            }

        }

        return new AnimalPoint[]{point1,point2};
    }

    /**
     * 返回一个布局存在的AnimalView
     * @return
     */
    public static int getExistAnimal(){
        //获取布局
        int[][] board = LinkManager.getLinkManager().getBoard();

        //产生随机数
        int random = 0;
        while (!LinkUtil.getBoardState()){
            //产生一个随机数
            random = new Random().nextInt(LinkUtil.getMaxData(board))+1;
            Log.d(Constant.TAG,"测试消除"+random);

            //判断该布局中是否有
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == random){
                        return random;
                    }
                }
            }
        }

        return random;
    }

    /**
     * 判断是否全部消除
     * @return
     */
    public static boolean getBoardState(){
        //获取布局
        int[][] board = LinkManager.getLinkManager().getBoard();

        //bug处理
        if (board == null){
            return false;
        }

        //判断状态
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] > 0){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 根据游戏时间获取相关星级评价
     * @param time
     * @return
     */
    public static char getStarByTime(int time){
        if (time <= 40){
            return '1';
        }else if (time <= 60){
            return '2';
        }else {
            return '3';
        }
    }

    /**
     * 根据游戏时间获取相关分数评价
     * @param time
     * @return
     */
    public static int getScoreByTime(int time){
        return (LinkConstant.TIME -time) * LinkConstant.BASE_SCORE / LinkConstant.TIME;
    }

    /**
     * 获取连击数
     */
    public static int getSerialClick(){
        //获取布局
        int[][] board = LinkManager.getLinkManager().getBoard();

        return (board.length-2) * (board[0].length-2) / 2;
    }

    /**
     * 索引坐标->真实坐标
     * @param point
     * @param context
     * @return
     */
    public static AnimalPoint getRealAnimalPoint(AnimalPoint point, Context context){
        LinkManager manager = LinkManager.getLinkManager();

        return new AnimalPoint(
                manager.getPadding_hor() + PxUtil.dpToPx(manager.getAnimal_size(),context) / 2 + point.y  * PxUtil.dpToPx(manager.getAnimal_size(),context),
                manager.getPadding_ver() + PxUtil.dpToPx(manager.getAnimal_size(),context) / 2 + point.x  * PxUtil.dpToPx(manager.getAnimal_size(),context)
        );
    }

    /**
     * 加载给定关卡号和难度的布局
     * @param level_id
     * @param level_mode
     * @return
     */
    public static int[][] loadLevelWithIdAndMode(int level_id, char level_mode){

        //1.先判断是什么类型的关卡
        int [][][] BOARD;
        if (level_mode == LevelMode.LEVEL_MODE_EASY.getValue()){
            BOARD = LinkConstant.BOARD_EASY;
        }else if (level_mode == LevelMode.LEVEL_MODE_NORMAL.getValue()){
            BOARD = LinkConstant.BOARD_NORMAL;
        }else {
            BOARD = LinkConstant.BOARD_HARD;
        }

        //2.获取需要加载的资源数量
        int resourceID = new Random().nextInt(BOARD.length);

        //3.获取随机产生的模板
        int[][] board = BOARD[resourceID];
        Log.d(Constant.TAG,"加载的board"+resourceID);

        //4.拷贝模板
        int row = board.length;
        int col = board[0].length;
        int[][] clone = new int[row][col];
        for (int i = 0; i < row; i++) {
            System.arraycopy(board[i], 0, clone[i], 0, col);
        }

        return clone;
    }

    /**
     * 加载所需的数量的图片
     * @param board
     * @return
     */
    public static List<Integer> loadPictureResourceWithBoard(int[][] board){
        //初始化存储集合
        List<Integer> list = new ArrayList<>();

        //求出二维数组中的最大值
        int max = getMaxData(board);

        //随机加载图片
        Random random = new Random();
        while (list.size() != max){
            //产生指定范围内的随机数
            int var = random.nextInt(LinkConstant.ANIMAL_RESOURCE.length);

            //重复的随机数不再加入
            int flag = 0;
            for (Integer integer : list) {
                if (integer == var) flag = 1;
            }

            //满足条件的加入
            if (flag == 0) list.add(var);
        }

        return list;
    }

    /**
     * 求解二维矩阵的最大值
     * @param matrix
     * @return
     */
    public static int getMaxData(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        int[] data = new int[row * col];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[index++] = matrix[i][j];
            }
        }

        //除开特殊情况
        if (data.length == 0){
            return 0;
        }

        return getMaxDataBySort(data);
    }

    /**
     * 返回数组中第k小的数值
     * @param data
     * @return
     */
    private static int getMaxDataBySort(int[] data){
        //排序
        mergeSort(data,0,data.length-1);

        return data[data.length-1];
    }

    /**
     * 归并排序 递归
     * @param nums
     * @param start
     * @param end
     */
    private static void mergeSort(int[] nums,int start,int end){
        //只要划分的区间长度仍然不为1
        if (start != end){
            int middle = (start+end) / 2;

            //分
            mergeSort(nums,start,middle);
            mergeSort(nums,middle+1,end);

            //治
            merge(nums,start,end,middle);
        }
    }

    /**
     * 归并
     * @param nums
     * @param start
     * @param end
     * @param middle
     */
    private static void merge(int[] nums,int start,int end,int middle){
        //模拟第一个序列的头指针
        int i = start;

        //模拟第二个序列的头指针
        int j = middle+1;

        //模拟临时数组的头指针
        int t = 0;

        //创建临时数组
        int[] temp = new int[end-start+1];

        //从头比较两个序列，小的放入临时数组temp
        while (i <= middle && j <= end){
            //比较大小
            if (nums[i] < nums[j]){
                //前一个序列
                temp[t++] = nums[i++];
            }else {
                //后一个序列
                temp[t++] = nums[j++];
            }
        }

        //单独处理没有处理完的第一个序列
        while (i <= middle){
            temp[t++] = nums[i++];
        }

        //单独处理没有处理完的第二个序列
        while (j <= end){
            temp[t++] = nums[j++];
        }

        //将临时数组的值赋值到原数组
        if (temp.length >= 0) System.arraycopy(temp, 0, nums, start, temp.length);
    }
}
