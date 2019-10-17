package cn.hzjdemo.hellodiary.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * application配置
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //其他初始化
        ApplicationKit
                .getInstance()
                .initKit(this);
    }
}
