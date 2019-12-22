package cn.hzjdemo.hellodiary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;
import com.zenchn.picbrowserlib.ui.PictureBrowseActivity;
import com.zenchn.support.widget.TitleBar;
import com.zenchn.support.widget.tips.SuperToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.widgets.CommonSharePop;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.MultiImageView;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.entity.PhotoInfo;
import cn.hzjdemo.hellodiary.wrapper.glide.CircleTransform;

/**
 * Created by Hzj on 2017/8/21.
 * 晒单详情
 */

public class ShowOrderDetailActivity extends BaseActivity {
    @BindView(R.id.iv_order_user_head)
    ImageView ivOrderUserHead;
    @BindView(R.id.tv_order_user_name)
    TextView tvOrderUserName;
    @BindView(R.id.tv_order_send_time)
    TextView tvOrderSendTime;
    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.nine_pics)
    MultiImageView ninePics;
    @BindView(R.id.tv_order_see_num)
    TextView tvOrderSeeNum;
    @BindView(R.id.iv_order_has_praise)
    ImageView ivOrderHasPraise;
    @BindView(R.id.tv_detail_praise_num)
    TextView tvDetailPraiseNum;
    @BindView(R.id.ll_give_praise)
    LinearLayout llGivePraise;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.bt_delete_detail)
    TextView btDelete;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private boolean can_delete = false;
    public static final String CAN_DELETE_EXTRA = "can_delete";
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_order_detail;
    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.show_order_detail))
                .setOnLeftClickListener(this)
                .rightIcon(R.drawable.top_share_black)
                .setOnRightClickListener(new TitleBar.OnRightClickListener() {
                    @Override
                    public void onRightViewClick(View v) {
                        //TODO 分享
                        CommonSharePop sharePop = new CommonSharePop.Builder(ShowOrderDetailActivity.this, v, handler)
                                .setShareTitle("")
                                .setShareContent("")
                                .setShareUrl("")
                                .setShareImageUrl("")
                                .create();
                        sharePop.showPopWin();
                    }
                });
        //加载圆形头像
        RequestBuilder<Bitmap> bmRequestBuilder = Glide.with(this)
                .asBitmap()
                .load(Constants.imgUrls[6])
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop();
        bmRequestBuilder.apply(new RequestOptions()
                .transform(new CircleTransform()))
                .into(ivOrderUserHead);

        initData();
    }

    protected void initData() {
        if (null != getIntent()) {
            Intent intent = getIntent();
            if (intent.hasExtra(CAN_DELETE_EXTRA)) {
                can_delete = intent.getBooleanExtra(CAN_DELETE_EXTRA, false);
            }
        }

        btDelete.setVisibility(can_delete ? View.VISIBLE : View.GONE);

        String[] mUrls = Constants.imgUrls;

        List<PhotoInfo> imageInfoList = new ArrayList<>();
        final ArrayList<String> imageUrls = new ArrayList<>();
        final ArrayList<ImageSourceInfo> imageSourceInfoArrayList = new ArrayList<>();
        for (int i = 0; i < mUrls.length; i++) {
            PhotoInfo imageInfo = new PhotoInfo();
            imageInfo.url = Constants.imgUrls[i];
            imageInfoList.add(imageInfo);
            imageUrls.add(imageInfo.url);
            ImageSourceInfo imageSourceInfo = new ImageSourceInfo(Constants.imgUrls[i], true);
            imageSourceInfoArrayList.add(imageSourceInfo);

        }
        ninePics.setList(imageInfoList);
        ninePics.setVisibility(View.VISIBLE);
        ninePics.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(ShowOrderDetailActivity.this, ShowPictureProgressActivity.class);
//                intent.putStringArrayListExtra(ShowPictureProgressActivity.IMAGE_URLS, imageUrls);
//                intent.putExtra(ShowPictureProgressActivity.CURRENT_POSITION, position);
//                startActivity(intent);
                PictureBrowseActivity.launch(ShowOrderDetailActivity.this, imageSourceInfoArrayList, position);
            }
        });

    }


    @OnClick(R.id.ll_give_praise)
    public void givePraise() {
        //todo 点赞
        SuperToast.showDefaultMessage(this, "点了赞👍");
    }

    @OnClick(R.id.bt_delete_detail)
    public void doDelete() {
        //TODO 删除
        SuperToast.showDefaultMessage(this, "删了除");
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
