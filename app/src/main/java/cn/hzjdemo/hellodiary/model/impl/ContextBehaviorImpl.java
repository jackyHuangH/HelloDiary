package cn.hzjdemo.hellodiary.model.impl;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import cn.hzjdemo.hellodiary.app.ApplicationKit;
import io.reactivex.Observable;


/**
 * 作    者：wangr on 2017/9/9 17:38
 * 描    述：
 * 修订记录：
 */

public class ContextBehaviorImpl {

    private ContextBehaviorImpl() {
    }

    @NonNull
    public static Context getApplicationContext() {
        return ApplicationKit.getInstance().getApplication();
    }

    @NonNull
    public static Observable<Application> getContextObservable() {
        Application application = ApplicationKit.getInstance().getApplication();
        return Observable.just(application);
    }
}
