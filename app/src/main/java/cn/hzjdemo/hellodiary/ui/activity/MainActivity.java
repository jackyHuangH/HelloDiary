package cn.hzjdemo.hellodiary.ui.activity;

import android.app.Activity;
import android.widget.FrameLayout;

import com.zenchn.support.fragmentutil.HFragmentManager;
import com.zenchn.support.router.Router;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
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
        //初始化所有fragment，
        // 注意：这里切换fragment时要保证每个fragment只有唯一一个实例，不能重复创建！！！
        Fragment[] fragments = {
                HomeFragment.getInstance(),
                ShowOrderFragment.getInstance(),
                MineFragment.getInstance()
        };

        //默认显示首页
        fragmentManagerHelper.add(fragments[0]);

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
                    //注意：这里切换fragment时要保证每个fragment只有唯一一个实例，不能重复创建！！！！
                    case 0:
                        fragmentManagerHelper.switchFragment(fragments[0]);
                        break;
                    case 1:
                        fragmentManagerHelper.switchFragment(fragments[1]);
                        break;
                    case 2:
                        fragmentManagerHelper.switchFragment(fragments[2]);
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

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(MainActivity.class)
                .launch();
    }
}
