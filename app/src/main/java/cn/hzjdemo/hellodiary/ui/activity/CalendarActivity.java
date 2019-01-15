package cn.hzjdemo.hellodiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zenchn.support.router.Router;

import cn.hzjdemo.hellodiary.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(CalendarActivity.class)
                .launch();
    }
}
