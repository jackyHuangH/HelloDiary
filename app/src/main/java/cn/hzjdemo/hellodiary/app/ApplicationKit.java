package cn.hzjdemo.hellodiary.app;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zenchn.apilib.base.ApiManager;
import com.zenchn.apilib.util.LoggerKit;
import com.zenchn.support.base.AbstractApplicationKit;
import com.zenchn.support.base.ActivityLifecycleCallback;
import com.zenchn.support.cache.ACache;
import com.zenchn.support.dafault.DefaultActivityLifecycle;
import com.zenchn.support.kit.Android;
import com.zenchn.support.widget.tips.SuperToast;

import javax.inject.Inject;

import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.GlobalConfig;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.di.component.DaggerAppComponent;
import cn.hzjdemo.hellodiary.di.module.AppModule;
import cn.hzjdemo.hellodiary.model.impl.local.ACacheModelImpl;
import cn.hzjdemo.hellodiary.ui.activity.LoginActivity;
import cn.hzjdemo.hellodiary.ui.activity.MainActivity;
import dagger.Lazy;


/**
 * 作    者：wangr on 2017/8/26 15:48
 * 描    述：
 * 修订记录：
 */
public class ApplicationKit extends AbstractApplicationKit implements ActivityLifecycleCallback {

    @Inject
    Lazy<DefaultActivityLifecycle> mLazyActivityLifecycle;

    private static AppComponent mApplicationComponent;

    private ApplicationKit() {
    }

    public void exitApp() {
        mLazyActivityLifecycle.get().exitApp();
    }

    public void navigateToLogin(boolean grantRefuse) {
        if (grantRefuse) {
            SuperToast.showDefaultMessage(getApplication(), getApplication().getString(R.string.login_error_grant_refused));
        }
        Activity topActivity = mLazyActivityLifecycle.get().getTopActivity();
        if (topActivity != null) {
            LoginActivity
                    .launch(topActivity);
        } else {
            Intent intent = new Intent(application, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
        }
    }

    private static class SingletonInstance {
        private static final ApplicationKit INSTANCE = new ApplicationKit();
    }

    public static ApplicationKit getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Override
    public void initSetting() {
        super.initSetting();
        //初始化依赖注入
        setupInjector();
        //初始化缓存
        initACache();
        initApiManager();
        LoggerKit.init(Constants.LOGGERKIT_TAG);
        initActivityLifecycle();
        clearNotify();
        registerPush();
        initAreaDb();
        initLeakCanary();
        initUpdatePlugin();
        initSmartRefreshLayout();
        initSwipeBack();
    }

    /**
     * 初始化Dagger applicationComponent
     * 这里只初始化一次，保证了单例
     */
    private void setupInjector() {
        mApplicationComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public static AppComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void initUpdatePlugin() {
        String curVersionName = Android.Package.getVersionName(getApplication());
        int curVersionCode = Android.Package.getVersionCode(getApplication());
        //创建更新配置类：
//        UpdateConfig.getConfig()
//                // 配置检查更新的API接口
//                .setUrl(UpdateWrapper.CHECK_UPDATE_URL)
//                .setUpdateStrategy(new AppUpdateStrategy())
//                .setUpdateChecker(new AppUpdateChecker(curVersionName))
//                .setUpdateParser(new AppUpdateParser(curVersionCode))
//                .setDownloadNotifier(new MateriaDownloadNotifier())
//                .setInstallNotifier(new MateriaInstallNotifier())
//                .setCheckNotifier(new MateriaUpdateNotifier());
    }

    private void initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * 配置滑动返回初始化
     */
    private void initSwipeBack() {
        //snake
//        Snake.init(this);
    }

    private void initLeakCanary() {
//        LeakCanary.install(getApplication());
    }

    private void initAreaDb() {
//        FileModelImpl.getInstance().copyDBToApp(new CopyDBCallback() {
//            @Override
//            public void onCopyDBToApp(boolean isSuccess) {
//                LoggerKit.d(isSuccess ? "复制db成功" : "复制db失败");
//            }
//        });
    }

    private void initApiManager() {
        ApiManager.init(GlobalConfig.BASE_REQUEST_HTTP_HOST);
    }

    /**
     * 初始化本地缓存
     */
    public void initACache() {
        try {
            ACache aCache = ACache.get(application);
            ACacheModelImpl.init(aCache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initActivityLifecycle() {
        mLazyActivityLifecycle.get().addCallback(this);
    }

    /**
     * 清理通知栏
     */
    public void clearNotify() {
        NotificationManager nm = (NotificationManager) application.getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    /**
     * 注册xgPush获取token
     */
    private void registerPush() {
//        XGRegisterWrapper
//                .getInstance()
//                .registerXgPush(application.getApplicationContext());
    }

    @Override
    public void onCrash(Thread thread, Throwable ex) {
        mLazyActivityLifecycle.get().exitApp();
    }

    @Override
    public void onBackground() {

    }

    @Override
    public void onForeground() {

    }

    @Override
    public void onDestroyedSelf() {

    }
}
