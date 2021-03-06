package cn.hzjdemo.hellodiary.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.PassAnnounceAdapter;
import cn.hzjdemo.hellodiary.adapter.itemDecoration.TopBottomSpaceItemDecoration;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;

/**
 * 往期揭晓
 */

public class PassAnnounceActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.rlv_pass_announce)
    RecyclerView rlvPassAnnounce;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipeRefresh;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private List<String> listDatas = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EmptyWrapper emptyWrapperAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pass_announce;
    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.pass_group_buy_str))
                .setOnLeftClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvPassAnnounce.setLayoutManager(linearLayoutManager);
        rlvPassAnnounce.addItemDecoration(new TopBottomSpaceItemDecoration(this, 10f, true));
        PassAnnounceAdapter passAnnounceAdapter = new PassAnnounceAdapter(this, R.layout.item_pass_announce, listDatas);
        emptyWrapperAdapter = new EmptyWrapper(passAnnounceAdapter);
        emptyWrapperAdapter.setEmptyView(R.layout.view_empty);

        rlvPassAnnounce.setAdapter(emptyWrapperAdapter);

        swipeRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatas.add(0, "新手");
                        emptyWrapperAdapter.notifyDataSetChanged();
                        if (null != swipeRefresh) {
                            swipeRefresh.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatas.add("老手");
                        emptyWrapperAdapter.notifyDataSetChanged();
                        if (null != swipeRefresh) {
                            swipeRefresh.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    protected void initData() {
        for (int i = 0; i < 20; i++) {
            listDatas.add("数据====" + i);
        }
        emptyWrapperAdapter.notifyDataSetChanged();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(PassAnnounceActivity.class)
                .launch();
    }
}
