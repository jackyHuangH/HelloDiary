package cn.hzjdemo.hellodiary.ui.activity;

import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;

public class MultiSelectActivity extends BaseActivity {
    private static final String TAG = "MultiSelectActivity";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_multi_select;
    }


    @Override
    public void initWidget() {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
