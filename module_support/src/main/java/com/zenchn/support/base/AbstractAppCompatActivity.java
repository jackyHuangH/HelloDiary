package com.zenchn.support.base;

import android.os.Bundle;
import android.view.InflateException;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.zenchn.support.R;
import com.zenchn.support.dafault.DefaultUiController;


/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public abstract class AbstractAppCompatActivity extends AppCompatActivity implements IActivity {

    protected IActivityLifecycle mActivityLifecycle;//代理activity生命周期管理（使用单例对象）
    protected IUiController mUiController;//基本的ui控制器

    public AbstractAppCompatActivity() {
        this.mActivityLifecycle = getDefaultActivityLifecycle();
        this.mUiController = getDefaultUiController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getLayoutRes() != 0) {
                setContentView(getLayoutRes());
                ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
            e.printStackTrace();
        }

        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityCreated(this, savedInstanceState);
        }// activity入栈

        //添加activity转场动画
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void finish() {
        super.finish();
        //重写activity转场动画
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityStarted(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityResumed(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityPaused(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityStopped(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivityDestroyed(this);
        }//activity出栈
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivityLifecycle != null) {
            mActivityLifecycle.onActivitySaveInstanceState(this, outState);
        }
    }

    @Nullable
    protected abstract IActivityLifecycle getDefaultActivityLifecycle();

    protected IUiController getDefaultUiController() {
        return new DefaultUiController(this,this);
    }

    @Override
    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    public void showProgress() {
        if (mUiController != null) {
            mUiController.showProgress();
        }
    }

    @Override
    public void showProgress(@Nullable CharSequence msg) {
        if (mUiController != null) {
            mUiController.showProgress(msg);
        }
    }

    @Override
    public void hideProgress() {
        if (mUiController != null) {
            mUiController.hideProgress();
        }
    }

    @Override
    public void showMessage(@NonNull CharSequence msg) {
        if (mUiController != null) {
            mUiController.showMessage(msg);
        }
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        if (mUiController != null) {
            mUiController.showResMessage(resId);
        }
    }

}
