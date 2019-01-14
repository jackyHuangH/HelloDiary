package cn.hzjdemo.hellodiary.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.zenchn.support.base.AbstractFragment;
import com.zenchn.support.utils.StringUtils;
import com.zenchn.support.widget.TitleBar;

import javax.inject.Inject;

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
public abstract class BaseFragment<P extends BasePresenterImpl> extends AbstractFragment implements BaseView, TitleBar.OnLeftClickListener {

    protected ImmersionBar mImmersionBar;

    @Inject
    protected P mPresenter;

    @Override
    protected void initInstanceState(Bundle savedInstanceState) {
        super.initInstanceState(savedInstanceState);
        //如果要在Fragment单独使用沉浸式，请在onSupportVisible实现沉浸式
        if (isStatusBarEnabled()) {
            initStatusBar();
        }

        //初始化依赖注入
        AppComponent applicationComponent = ApplicationKit.getApplicationComponent();
        componentInject(applicationComponent);

        //今日头条屏幕适配方案配置
        AutoSizeConfig.getInstance().setCustomFragment(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
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
                .statusBarColor(android.R.color.white)
                .statusBarDarkFont(true, 0.2f);

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
    public void onLeftViewClick(View v) {
        onFragmentBackPressed();
    }

    /**
     * 模拟activity的onBackPressed()事件
     * 方便fragment 调用
     */
    protected void onFragmentBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    protected void onSupportVisible() {
        //Fragment对用户可见时
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
}
