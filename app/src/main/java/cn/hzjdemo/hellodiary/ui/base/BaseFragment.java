package cn.hzjdemo.hellodiary.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;
import com.zenchn.support.base.AbstractFragment;
import com.zenchn.support.fragmentutil.BackHandlerHelper;
import com.zenchn.support.fragmentutil.FragmentBackHandler;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.utils.StringUtils;
import com.zenchn.support.widget.TitleBar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.app.ApplicationKit;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.basepresenter.BasePresenterImpl;
import me.jessyan.autosize.AutoSizeConfig;


/**
 * @author:Hzj
 * @date :2018/9/18/018
 * desc  ：fragment 抽象基类，继承此fragment即可
 * record：
 */
public abstract class BaseFragment<P extends BasePresenterImpl> extends AbstractFragment implements BaseView, FragmentBackHandler,
        TitleBar.OnLeftClickListener {

    protected ImmersionBar mImmersionBar;
    /**
     * 视图是否加载完毕
     */
    private boolean mIsViewPrepare = false;
    /**
     * 视图是否第一次展现
     */
    private boolean mFirstTimeVisible = false;
    @Inject
    protected P mPresenter;

    @Override
    protected void initInstanceState(Bundle savedInstanceState) {
        //初始化依赖注入,在initWidget()前调用
        AppComponent applicationComponent = ApplicationKit.getApplicationComponent();
        componentInject(applicationComponent);
        //添加lifecycle绑定
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
        super.initInstanceState(savedInstanceState);
        //今日头条屏幕适配方案配置
        AutoSizeConfig.getInstance().setCustomFragment(true);
    }

    /**
     * Viewpager+Fragment 每次对用户可见时，可调用此方法，相当于fragment的resume
     */
    @Override
    public void onSupportVisible() {
        //如果要在Fragment单独使用沉浸式，请在onSupportVisible实现沉浸式
        if (isStatusBarEnabled()) {
            initStatusBar();
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 依赖注入入口
     */
    @Nullable
    protected abstract void componentInject(AppComponent appComponent);

    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true);

        //是否需要监听键盘
        if (addOnKeyboardListener() != null) {
            mImmersionBar
                    .keyboardEnable(true)
                    //单独指定软键盘模式
                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .setOnKeyboardListener(addOnKeyboardListener());
        }
        mImmersionBar.init();
    }

    protected OnKeyboardListener addOnKeyboardListener() {
        return null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewPrepare = true;
        lazyLoadDataIfPrepared();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared();
        }
    }

    private void lazyLoadDataIfPrepared() {
        if (getUserVisibleHint() && mIsViewPrepare && !mFirstTimeVisible) {
            onLazyLoad();
            mFirstTimeVisible = true;
        }
    }

    /**
     * 懒加载
     */
    protected void onLazyLoad() {
    }

    @Override
    public void onLeftViewClick(View v) {
        onFragmentBackPressed();
    }

    /**
     * 模拟activity的onBackPressed()事件
     * 方便fragment 调用
     */
    @Override
    public boolean onFragmentBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }


    @Override
    public void onApiGrantRefuse() {
        ApplicationKit.getInstance().navigateToLogin(true);
    }

    @Override
    public void showMessage(@NonNull CharSequence msg) {
        if (StringUtils.isNonNull(msg)) {
            super.showMessage(msg);
        }
    }

    @Override
    public void onApiFailure(String msg) {
        showMessage(msg);
    }

    @Override
    public void onPause() {
        AndroidKit.Keyboard.hideSoftInput(getActivity());
        super.onPause();
    }
}
