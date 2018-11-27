package cn.hzjdemo.hellodiary.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.Unbinder;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.ShowOrderAdapter;
import cn.hzjdemo.hellodiary.bean.ShowOrderBean;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.activity.SendOrderActivity;
import cn.hzjdemo.hellodiary.ui.activity.ShowOrderDetailActivity;
import cn.hzjdemo.hellodiary.ui.base.BaseFragment;
import cn.hzjdemo.hellodiary.util.ItemClickSupport;
import cn.hzjdemo.hellodiary.util.StatusBarUtil;
import cn.hzjdemo.hellodiary.util.TitleBarBuilder;
import cn.hzjdemo.hellodiary.util.glide.BannerImageLoader;

/**
 * 首页晒单
 */

public class ShowOrderFragment extends BaseFragment {
    private static final String TAG = "ShowOrderFragment";

    @BindView(R.id.rlv_show_order)
    RecyclerView rlvShowOrder;
    @BindView(R.id.swipe_refresh_order)
    SmartRefreshLayout swipeRefreshOrder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindColor(R.color.white)
    int whiteColor;
    @BindColor(R.color.dark)
    int darkColor;
    @BindView(R.id.title_bar)
    RelativeLayout mTitleBar;
    Unbinder unbinder;

    private TextView tvTitle;
    private TextView tvRightTitle;
    private List<ShowOrderBean> datas = new ArrayList<>();

    private static ShowOrderFragment mFragment;
    private Banner headBanner;
    private int bannerHeight = 0;

    //累加滑动的距离,解决滑动一般dy=0;
    private int overScrollY = 0;

    private EmptyWrapper emptyWrapper;

    public static ShowOrderFragment getInstance() {
        if (null == mFragment)
            mFragment = new ShowOrderFragment();

        return mFragment;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void initWidget() {
        TitleBarBuilder titleBarBuilder = new TitleBarBuilder(getActivity(), llRoot);
        titleBarBuilder.setTitleText(getString(R.string.show_order))
                .setRightText(getString(R.string.show_something))
                .setRightTitleListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), SendOrderActivity.class));
                    }
                });
        tvTitle = titleBarBuilder.getTitle();
        tvRightTitle = titleBarBuilder.getRight_Title();
        //初始透明度
        tvTitle.setAlpha(0);
        tvRightTitle.setTextColor(whiteColor);

        resetTiltleBar();
        initRecyclerView();

//        headBanner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                bannerHeight = headBanner.getHeight();
//                headBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        });

        headBanner.post(new Runnable() {
            @Override
            public void run() {
                bannerHeight = headBanner.getHeight();
            }
        });

        //滑动改变标题栏透明度
        rlvShowOrder.addOnScrollListener(recyclerViewScrollListener);

        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imgs.add(Constants.imgUrls[i % 2]);
        }
        headBanner.setImages(imgs)
                .setImageLoader(new BannerImageLoader())
                .start();

        swipeRefreshOrder.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowOrderBean model4 = new ShowOrderBean();
                        for (int i = 0; i < 1; i++) {
                            model4.urlList.add(Constants.imgUrls[0]);
                        }
                        model4.isShowAll = false;
                        model4.setHasImg(true);
                        datas.add(0, model4);
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshOrder) {
                            swipeRefreshOrder.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowOrderBean model4 = new ShowOrderBean();
                        for (int i = 0; i < 1; i++) {
                            model4.urlList.add(Constants.imgUrls[3]);
                        }
                        model4.isShowAll = false;
                        model4.setHasImg(true);
                        datas.add(model4);
                        emptyWrapper.notifyItemInserted(datas.size());
                        if (null != swipeRefreshOrder) {
                            swipeRefreshOrder.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    private void initRecyclerView() {
        //头部轮播图
        View head_banner = LayoutInflater.from(getActivity()).inflate(R.layout.header_show_order, llRoot, false);
        headBanner = (Banner) head_banner.findViewById(R.id.banner_show_order);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rlvShowOrder.setLayoutManager(linearLayoutManager);
        ShowOrderAdapter showOrderAdapter = new ShowOrderAdapter(getActivity(),
                R.layout.item_list_show_order, datas, false);
        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(showOrderAdapter);
        headerAndFooterWrapper.addHeaderView(head_banner);

        emptyWrapper = new EmptyWrapper(headerAndFooterWrapper);
        emptyWrapper.setEmptyView(R.layout.view_empty);

        rlvShowOrder.setAdapter(emptyWrapper);

        // 列表点赞
        ItemClickSupport.addTo(rlvShowOrder, R.id.ll_order_praise, R.id.ll_item_show_order)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (v.getId() == R.id.ll_order_praise) {
                            showMessage("点赞了" + position);
                        } else if (v.getId() == R.id.ll_item_show_order) {
                            //跳转详情
                            Intent intent = new Intent(getActivity(), ShowOrderDetailActivity.class);
                            startActivity(intent);
                        }
                    }
                });
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

    private RecyclerView.OnScrollListener recyclerViewScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            overScrollY = recyclerView.computeVerticalScrollOffset();
            super.onScrolled(recyclerView, dx, dy);
//            overScrollY += dy;

            float statusBarAlpha = 0;
            if (overScrollY <= 0) {
                statusBarAlpha = 0;
                tvRightTitle.setTextColor(whiteColor);
                changeStatusBar(true, statusBarAlpha);
            } else if (overScrollY > 0) {
                statusBarAlpha = (float) overScrollY / bannerHeight;
                if (statusBarAlpha > 1) {
                    statusBarAlpha = 1;
                }
                tvRightTitle.setTextColor(darkColor);
                changeStatusBar(false, statusBarAlpha);
            }
            Log.d(TAG, "overScrollY: " + overScrollY);
            Log.d(TAG, "initStatusBar透明度: " + statusBarAlpha);
            //设置标题透明度
            tvTitle.setAlpha(statusBarAlpha);
        }
    };

    private void changeStatusBar(boolean statusbarTransparent, float alpha) {
        mImmersionBar = ImmersionBar.with(this);
        if (statusbarTransparent) {
            mImmersionBar.transparentStatusBar()
                    .statusBarDarkFont(false)
                    .init();
        } else {
            mImmersionBar.statusBarColor(R.color.white)
                    .statusBarAlpha(alpha)
                    .statusBarDarkFont(true, alpha)
                    .init();
        }
    }

    protected void initData() {
        String[] mUrls = Constants.imgUrls;

        //一张图片
        ShowOrderBean model4 = new ShowOrderBean();
        for (int i = 0; i < 1; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        model4.setHasImg(true);
        datas.add(model4);

        //五张图片
        ShowOrderBean model5 = new ShowOrderBean();
        for (int i = 0; i < 5; i++) {
            model5.urlList.add(mUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        model5.setHasImg(true);
        datas.add(model5);

        //90张图片
        ShowOrderBean model6 = new ShowOrderBean();
        for (int i = 0; i < 90; i++) {
            model6.urlList.add(mUrls[i % 10]);
        }
        model6.setHasImg(true);
        model6.isShowAll = true;
        datas.add(model6);

        //40张图片
        ShowOrderBean model7 = new ShowOrderBean();
        for (int i = 0; i < 40; i++) {
            model7.urlList.add(mUrls[i % 10]);
        }
        model7.setHasImg(true);
        model7.isShowAll = true;
        datas.add(model7);

        //没有图片
        ShowOrderBean model8 = new ShowOrderBean();
        model8.setHasImg(false);
        datas.add(model8);

//        for (int i = 0; i < 20; i++) {
//            datas.add(model6);
//        }

        emptyWrapper.notifyDataSetChanged();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.frag_show_order;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (null != headBanner)
            headBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != headBanner)
            headBanner.stopAutoPlay();
    }

    @Nullable
    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
