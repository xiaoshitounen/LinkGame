package swu.xl.linkgame.LinkGame;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import swu.xl.linkgame.Util.PxUtil;

public class LinkUtil {
    /**
     * 索引坐标->真实坐标
     * @param point
     * @param context
     * @return
     */
    public static AnimalPoint getRealAnimalPoint(AnimalPoint point, Context context){
        LinkManager manager = LinkManager.getLinkManager();

        return new AnimalPoint(
                manager.getPadding() + PxUtil.dpToPx(LinkConstant.animal_size,context) / 2 + point.y  * PxUtil.dpToPx(LinkConstant.animal_size,context),
                manager.getPadding() + PxUtil.dpToPx(LinkConstant.animal_size,context) / 2 + point.x  * PxUtil.dpToPx(LinkConstant.animal_size,context)
        );
    }

    /**
     * 加载给定关卡号和难度的布局
     * @param level_id
     * @param level_mode
     * @return
     */
    public static int[][] loadLevelWithIdAndMode(int level_id, int level_mode){
        int row = LinkConstant.board_test1.length;
        int col = LinkConstant.board_test1[0].length;

        int[][] clone = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                clone[i][j] = LinkConstant.board_test1[i][j];
            }
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
