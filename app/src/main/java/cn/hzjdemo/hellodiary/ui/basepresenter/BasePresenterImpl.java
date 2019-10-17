package cn.hzjdemo.hellodiary.ui.basepresenter;


import android.util.Log;

import com.zenchn.apilib.callback.rx.RxApiCallback;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import cn.hzjdemo.hellodiary.ui.base.BaseView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author:Hzj
 * @date :2018/7/30/030
 * desc  ：
 * record：
 */
public abstract class BasePresenterImpl<V extends BaseView> implements IPresenter, RxApiCallback {

    private static final String TAG = "BasePresenterImpl";

    protected CompositeDisposable mCompositeDisposable;
    protected V mView;


    public BasePresenterImpl(V view) {
        this.mView = view;
    }

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        //do something
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mView = null;
        releaseDisposable();
    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(TAG, "onLifecycleChanged:" + event.name());
    }

    @Override
    public void onApiGrantRefuse() {
        if (null != mView) {
            mView.hideProgress();
            mView.onApiGrantRefuse();
        }
    }

    @Override
    public void onApiFailure(String errMsg) {
        if (null != mView) {
            mView.hideProgress();
            mView.onApiFailure(errMsg);
        }
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 { Activity#onDestroy()} 中使用 {@link #releaseDisposable()} 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    @Override
    public void onRegisterObserver(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable.add(disposable);

        Log.d(TAG, "onRegisterObserver:添加了 " + disposable);
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void releaseDisposable() {
        if (mCompositeDisposable != null) {
            //保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();

            Log.d(TAG, "unDispose:取消所有正在执行的订阅 ");
        }
    }
}
