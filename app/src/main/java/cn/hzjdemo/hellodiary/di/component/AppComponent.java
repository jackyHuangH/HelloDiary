package cn.hzjdemo.hellodiary.di.component;


import javax.inject.Singleton;

import cn.hzjdemo.hellodiary.app.ApplicationKit;
import cn.hzjdemo.hellodiary.di.module.AppModule;
import dagger.Component;

/**
 * @author:Hzj
 * @date :2018/7/30/030
 * desc  ：
 * record：
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ApplicationKit applicationKit);
}
