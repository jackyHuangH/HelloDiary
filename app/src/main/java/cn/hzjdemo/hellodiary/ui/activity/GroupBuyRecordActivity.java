package cn.hzjdemo.hellodiary.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.widget.TitleBar;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.GroupBuyRecordAdapter;
import cn.hzjdemo.hellodiary.adapter.itemDecoration.TopBottomSpaceItemDecoration;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;

/**
 * 拼团记录
 */
public class GroupBuyRecordActivity extends BaseActivity {
    @BindView(R.id.rlv_raid_record)
    RecyclerView rlvRaidRecord;
    @BindView(R.id.swipe_refresh_raid_record)
    SmartRefreshLayout swipeRefreshRaidRecord;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EmptyWrapper emptyWrapper;
    private List<String> datas = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_raid_record;
    }



    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.group_buy_records))
                .setOnLeftClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvRaidRecord.setLayoutManager(linearLayoutManager);
        rlvRaidRecord.addItemDecoration(new TopBottomSpaceItemDecoration(this, 10f, true));
        GroupBuyRecordAdapter raidRecordAdapter = new GroupBuyRecordAdapter(this,
                R.layout.item_list_group_buy_record, datas, false);

        emptyWrapper = new EmptyWrapper(raidRecordAdapter);
        emptyWrapper.setEmptyView(R.layout.view_empty);
        rlvRaidRecord.setAdapter(emptyWrapper);

        swipeRefreshRaidRecord.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(0, "新手2017-08-17");
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshRaidRecord) {
                            swipeRefreshRaidRecord.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add("老司机2017-08-17");
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshRaidRecord) {
                            swipeRefreshRaidRecord.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    protected void initData() {

        for (int i = 0; i < 20; i++) {
            datas.add("夺宝记录" + i);
        }
        emptyWrapper.notifyDataSetChanged();
    }


    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
