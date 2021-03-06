package cn.hzjdemo.hellodiary.di.module;

import android.content.Context;

import com.zenchn.support.dafault.DefaultActivityLifecycle;

import javax.inject.Singleton;

import cn.hzjdemo.hellodiary.app.ApplicationKit;
import cn.hzjdemo.hellodiary.di.scope.ApplicationContext;
import dagger.Module;
import dagger.Provides;

/**
 * @author:Hzj
 * @date :2018/7/30/030
 * desc  ：
 * record：
 */
@Module
public class AppModule {
    private ApplicationKit mApplicationKit;

    public AppModule(ApplicationKit applicationKit) {
        this.mApplicationKit = applicationKit;
    }

    @Singleton
    @Provides
    public ApplicationKit provideApplicationKit() {
        return this.mApplicationKit;
    }

    @ApplicationContext
    @Provides
    public Context provideContext(){
        return this.mApplicationKit.getApplication();
    }

    @Singleton
    @Provides
    public DefaultActivityLifecycle provideActivityLifecycle(){
        return DefaultActivityLifecycle.getInstance();
    }
}
