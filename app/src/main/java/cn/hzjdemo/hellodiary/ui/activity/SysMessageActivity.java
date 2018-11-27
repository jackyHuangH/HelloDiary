package cn.hzjdemo.hellodiary.ui.activity;

import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.utils.TimeUtils;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.SysMessageAdapter;
import cn.hzjdemo.hellodiary.adapter.itemDecoration.TopBottomSpaceItemDecoration;
import cn.hzjdemo.hellodiary.albumloader.AlbumCollection;
import cn.hzjdemo.hellodiary.bean.MessageEntity;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.util.TitleBarBuilder;
import cn.hzjdemo.hellodiary.widgets.itemtouchhelper.CardConfig;
import cn.hzjdemo.hellodiary.widgets.itemtouchhelper.SwipeCardCallback;
import cn.hzjdemo.hellodiary.widgets.itemtouchhelper.SwipeCardLayoutManager;

/**
 * 系统消息
 */
public class SysMessageActivity extends BaseActivity implements AlbumCollection.AlbumCallbacks {

    private static final String TAG = "SysMessageActivity";

    @BindView(R.id.rlv_message)
    RecyclerView rlvMessage;
    @BindView(R.id.swipe_refresh_message)
    SmartRefreshLayout swipeRefreshMessage;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EmptyWrapper emptyWrapper;
    private List<MessageEntity> datas = new ArrayList<>();
    private String[] imageArray = Constants.imgUrls;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sys_message;
    }

    @Override
    public void initWidget() {
        TitleBarBuilder titleBarBuilder = new TitleBarBuilder(SysMessageActivity.this, llRoot);
        titleBarBuilder.setTitleText(getString(R.string.message))
                .setLeftIco(R.drawable.top_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SwipeCardLayoutManager swipeCardLayoutManager = new SwipeCardLayoutManager(this);

        rlvMessage.setLayoutManager(swipeCardLayoutManager);
        rlvMessage.addItemDecoration(new TopBottomSpaceItemDecoration(this, 10f, true));
        SysMessageAdapter sysMessageAdapter = new SysMessageAdapter(this, R.layout.item_list_sys_message, datas);

        emptyWrapper = new EmptyWrapper(sysMessageAdapter);
        emptyWrapper.setEmptyView(R.layout.view_empty);
        rlvMessage.setAdapter(emptyWrapper);

        //初始化配置
        CardConfig.initConfig(this);
        //将ItemTouchHelper关联RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCardCallback(rlvMessage, emptyWrapper, datas));
        itemTouchHelper.attachToRecyclerView(rlvMessage);

        swipeRefreshMessage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEntity messageEntity = new MessageEntity();
                        messageEntity.message = "新手" + TimeUtils.toDay();
                        messageEntity.imgUrl = imageArray[0];
                        datas.add(0, messageEntity);
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshMessage) {
                            swipeRefreshMessage.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEntity messageEntity = new MessageEntity();
                        messageEntity.message = "老司机" + TimeUtils.toDay();
                        messageEntity.imgUrl = imageArray[3];
                        datas.add(messageEntity);
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshMessage) {
                            swipeRefreshMessage.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    protected void initData() {

//        for (int i = 0; i < 20; i++) {
//            MessageEntity messageEntity = new MessageEntity();
//            messageEntity.message = "新的消息记录" + i;
//            messageEntity.imgUrl = imageArray[i % 4];
//            datas.add(messageEntity);
//        }
//        emptyWrapper.notifyDataSetChanged();


        //=================Loader加载机制使用=============================
        AlbumCollection albumCollection = new AlbumCollection();
        albumCollection.onCreate(this, this);
        albumCollection.loadAlbums();

    }

    @Override
    public void onAlbumLoad(Cursor cursor) {
        while (cursor != null && cursor.moveToNext()) {
            String albumCoverPath = cursor.getString(cursor.getColumnIndex("_data"));
            String albumName = cursor.getString(cursor.getColumnIndex("bucket_display_name"));
            String amount = cursor.getString(cursor.getColumnIndex("count"));

            MessageEntity messageEntity = new MessageEntity();
            messageEntity.message = albumName;
            messageEntity.imgUrl = albumCoverPath;
            Log.d(TAG, "onAlbumLoad: " + messageEntity.toString());
            datas.add(messageEntity);
        }
        emptyWrapper.notifyDataSetChanged();
    }

    @Override
    public void onAlbumReset() {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    //=======================================================

}
