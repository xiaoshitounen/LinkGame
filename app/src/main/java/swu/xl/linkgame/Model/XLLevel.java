package swu.xl.linkgame.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

/**
 * 关卡类
 */
public class XLLevel extends LitePalSupport implements Parcelable {
    //id
    private int id;
    //关卡号
    private int l_id;
    //闯关时间
    private int l_time;
    //关卡模式 1：简单 2：普通 3：困难
    private char l_mode;
    //闯关状态 0：没有闯关 1：1星 2：2星 3：3星
    private char l_new;

    //setter、getter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public int getL_time() {
        return l_time;
    }

    public void setL_time(int l_time) {
        this.l_time = l_time;
    }

    public char getL_mode() {
        return l_mode;
    }

    public void setL_mode(char l_mode) {
        this.l_mode = l_mode;
    }

    public char getL_new() {
        return l_new;
    }

    public void setL_new(char l_new) {
        this.l_new = l_new;
    }

    @Override
    public String toString() {
        return "XLLevel{" +
                "id=" + id +
                ", l_id=" + l_id +
                ", l_time=" + l_time +
                ", l_mode=" + l_mode +
                ", l_new=" + l_new +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(l_id);
        dest.writeInt(l_time);
        dest.writeCharArray(new char[]{l_mode,l_new});
    }

    public static final Creator<XLLevel> CREATOR = new Creator<XLLevel>() {
        @Override
        public XLLevel createFromParcel(Parcel in) {
            //必须按顺序读取
            XLLevel xlLevel = new XLLevel();
            xlLevel.id = in.readInt();
            xlLevel.l_id = in.readInt();
            xlLevel.l_time = in.readInt();
            char[] temp = new char[2];
            in.readCharArray(temp);
            xlLevel.l_mode = temp[0];
            xlLevel.l_new = temp[1];

            return xlLevel;
        }

        @Override
        public XLLevel[] newArray(int size) {
            return new XLLevel[size];
        }
    };
}
