package swu.xl.linkgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //数据库
        LitePal.initialize(this);

        SQLiteDatabase db = LitePal.getDatabase();

        //startActivity(new Intent(this,FailureActivity.class));
    }
}
