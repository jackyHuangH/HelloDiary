package cn.hzjdemo.hellodiary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.Unbinder;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.HomeHotGridAdaper;
import cn.hzjdemo.hellodiary.adapter.HomeMoreGridAdapter;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.presenter.contract.HomeContract;
import cn.hzjdemo.hellodiary.presenter.impl.HomePresenterImpl;
import cn.hzjdemo.hellodiary.ui.activity.RaidDetailActivity;
import cn.hzjdemo.hellodiary.ui.base.BaseFragment;
import cn.hzjdemo.hellodiary.util.StatusBarUtil;
import cn.hzjdemo.hellodiary.util.glide.BannerImageLoader;
import cn.hzjdemo.hellodiary.util.glide.GlideApp;
import cn.hzjdemo.hellodiary.widgets.AutoSizeTextView;
import cn.hzjdemo.hellodiary.widgets.GridViewForScrollView;
import cn.hzjdemo.hellodiary.widgets.MarqueeView;
import cn.hzjdemo.hellodiary.widgets.ObservableScrollView;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    @BindView(R.id.rl_root)
    RelativeLayout mRoot;
    @BindView(R.id.scrollview_home)
    ObservableScrollView scrollView;
    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.marquee_home)
    MarqueeView marqueeHome;
    @BindView(R.id.gv_hot_home)
    GridViewForScrollView gvHotHome;
    @BindView(R.id.ll_home_quick_open)
    LinearLayout llHomeQuickOpen;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.gv_more_introduce_home)
    GridViewForScrollView gvMoreIntroduceHome;

    @BindColor(R.color.mangoFour)
    int mangoColor;
    @BindColor(R.color.dark)
    int darkColor;
    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.title_bar)
    RelativeLayout mTitleBar;
    Unbinder unbinder;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private int bannerHeight = 0;

    private static HomeFragment mHomeFragment;
    //处理magicIndicator单独使用时的点击事件
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private HomePresenterImpl homePresenter=new HomePresenterImpl(this);
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    private boolean statusbarTransparent = true;//状态栏是否透明

    public static HomeFragment getInstance() {
        if (null == mHomeFragment) {
            mHomeFragment = new HomeFragment();
        }

        return mHomeFragment;
    }

    @Override
    public void initWidget() {
        title.setText("首页");
        title.setAlpha(0);

        resetTiltleBar();
        bannerHome.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bannerHeight = bannerHome.getHeight();
                bannerHome.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollView.setScrollViewListener(scrollViewListener);
            }
        });

        initData();

    }

    /**
     * 获取状态栏高度并重新设置标题栏位置
     */
    private void resetTiltleBar() {
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(getActivity());
        RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) mTitleBar.getLayoutParams();
        layoutParam.setMargins(0, statusBarHeight, 0, 0);

        mTitleBar.setLayoutParams(layoutParam);

    }

    private ObservableScrollView.ScrollViewListener scrollViewListener = new ObservableScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            if (y <= 0) {
                title.setAlpha(0);
                statusbarTransparent = true;
                initStatusBar();
            } else if (y > 0 && y <= bannerHeight) {
                float alpha = (float) y / bannerHeight;
                //设置标题透明度
                title.setAlpha(alpha);
                statusbarTransparent = false;
                initStatusBar();
            } else {
                title.setAlpha(1);
            }
        }
    };

    @Nullable
    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (statusbarTransparent) {
            mImmersionBar
                    .transparentStatusBar() //状态栏透明，不写默认透明
                    .statusBarDarkFont(false); //状态栏字体深色，不写默认亮色（白色）
        } else {
            mImmersionBar
                    .statusBarColor(android.R.color.white)     //状态栏颜色，不写默认透明色
                    .statusBarDarkFont(true, 0.2f);   //状态栏字体是深色，不写默认为亮色
        }
        mImmersionBar.navigationBarColor(R.color.black) //导航栏颜色，不写默认黑色
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                .navigationBarWithKitkatEnable(true)//是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                .init();
    }

    protected void initData() {
        final int windowWidth = Android.Dimens.getScreenWidth();

        //TODO 轮播图
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imgs.add(Constants.imgUrls[i]);
        }
        bannerHome.setImages(imgs)
                .setImageLoader(new BannerImageLoader())
                .start();
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });

        //TODO 公告
        homePresenter.getHomeNotice("2");

        //点击监听
        marqueeHome.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                showMessage( "点击了" + position);
            }
        });

        //todo 热门拼团
        HomeHotGridAdaper homeHotAdaper = new HomeHotGridAdaper(getActivity());
        gvHotHome.setAdapter(homeHotAdaper);
        gvHotHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RaidDetailActivity.class);
                startActivity(intent);
            }
        });

        //todo 快速拼团
        for (int i = 0; i < 16; i++) {
            View quickView = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_quick_open, llHomeQuickOpen, false);
            ImageView iv_quick = (ImageView) quickView.findViewById(R.id.iv_home_quick_open);
            TextView tv_quick_name = (TextView) quickView.findViewById(R.id.tv_name_home_quick);
            AutoSizeTextView tv_quick_price = (AutoSizeTextView) quickView.findViewById(R.id.tv_price_home_quick);

            GlideApp.with(getActivity())
                    .load(Constants.imgUrls[i % 10])
                    .centerCrop()
                    .into(iv_quick);
            tv_quick_name.setText("小米笔记本" + i + "代");
            tv_quick_price.setText("¥" + 399 + i);

            final int finalI = i;
            quickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RaidDetailActivity.class);
                    intent.putExtra("has_start_group", true);
                    startActivity(intent);
                }
            });

            llHomeQuickOpen.addView(quickView);
        }

        //todo 更多推荐
        final List<String> mTitles = new ArrayList<>();
        mTitles.add("精选好货");
        mTitles.add("数码3C");
        mTitles.add("奢侈品");
        mTitles.add("土豪专区");
        mTitles.add("吃货专区嘿嘿");
        mTitles.add("精选好货");

//        mTitles.add("数码3C");
//        mTitles.add("奢侈品");
//        mTitles.add("土豪专区");
//        mTitles.add("吃货专区嘿嘿");


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        //自适应模式,平分宽度
        commonNavigator.setAdjustMode(mTitles.size() < 6 ? true : false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(darkColor);
                simplePagerTitleView.setSelectedColor(mangoColor);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index, true);
                        showMessage("我要重新加载数据了" + mTitles.get(index));
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(Android.Dimens.dp2px(1));
                indicator.setColors(mangoColor);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

        HomeMoreGridAdapter homeMoreAdapter = new HomeMoreGridAdapter(getActivity());
        gvMoreIntroduceHome.setAdapter(homeMoreAdapter);
        gvMoreIntroduceHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RaidDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.frag_home;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != marqueeHome)
            marqueeHome.startFlipping();
        if (null != bannerHome)
            bannerHome.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != marqueeHome) {
            marqueeHome.stopFlipping();
        }
        if (null != bannerHome) {
            bannerHome.stopAutoPlay();
        }
    }

    //==========presenter 回调

    @Override
    public void showNotice(List<String> list) {
        // 在代码里设置自己的动画
        marqueeHome.startWithList(list, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

}
