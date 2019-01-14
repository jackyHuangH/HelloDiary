package cn.hzjdemo.hellodiary.ui.activity;

import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.zenchn.support.managers.HFragmentManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.ui.fragment.HomeFragment;
import cn.hzjdemo.hellodiary.ui.fragment.MineFragment;
import cn.hzjdemo.hellodiary.ui.fragment.ShowOrderFragment;
import cn.hzjdemo.hellodiary.widgets.BottomTabView;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.fl_home_container)
    FrameLayout flHomeContainer;
    @BindView(R.id.bottom_tab)
    BottomTabView bottomTab;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initWidget() {
        HFragmentManager fragmentManagerHelper = new HFragmentManager(getSupportFragmentManager(), R.id.fl_home_container);
        //默认显示首页
        fragmentManagerHelper.add(HomeFragment.getInstance());

        //初始化底部tab
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "首页", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_home, R.drawable.tb_home_touch));
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "晒单", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_order, R.drawable.tb_order_touch));
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "我的", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_mine, R.drawable.tb_mine_touch));
        bottomTab.setTabItemViews(tabItemViews);
        bottomTab.setOnTabItemSelectListener(new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                switch (position) {
                    case 0:
                        fragmentManagerHelper.switchFragment(HomeFragment.getInstance());
                        break;
                    case 1:
                        fragmentManagerHelper.switchFragment(ShowOrderFragment.getInstance());
                        break;
                    case 2:
                        fragmentManagerHelper.switchFragment(MineFragment.getInstance());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .transparentStatusBar() //状态栏透明，不写默认透明
                .statusBarDarkFont(false) //状态栏字体深色，不写默认亮色（白色）
                .navigationBarColor(R.color.black) //导航栏颜色，不写默认黑色
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                .navigationBarWithKitkatEnable(true)//是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                .init();
    }
}
