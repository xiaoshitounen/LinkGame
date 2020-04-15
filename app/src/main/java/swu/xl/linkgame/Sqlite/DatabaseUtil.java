package swu.xl.linkgame.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseUtil extends SQLiteOpenHelper {
    //数据库名称
    public static final String DATABASE_NAME = "link_game.db";
    //数据库版本号
    public static final int DATABASE_VERSION = 1;

    //构造方法
    public DatabaseUtil(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
