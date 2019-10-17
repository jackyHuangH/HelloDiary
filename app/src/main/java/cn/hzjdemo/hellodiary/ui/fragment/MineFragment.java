package cn.hzjdemo.hellodiary.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.zenchn.support.utils.TimeUtils;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.activity.BuyRecordActivity;
import cn.hzjdemo.hellodiary.ui.activity.GroupBuyRecordActivity;
import cn.hzjdemo.hellodiary.ui.activity.LoginActivity;
import cn.hzjdemo.hellodiary.ui.activity.MyShowOrderActivity;
import cn.hzjdemo.hellodiary.ui.activity.SysMessageActivity;
import cn.hzjdemo.hellodiary.ui.activity.TableActivity;
import cn.hzjdemo.hellodiary.ui.activity.UserInfoActivity;
import cn.hzjdemo.hellodiary.ui.activity.WebDetailActivity;
import cn.hzjdemo.hellodiary.ui.base.BaseFragment;
import cn.hzjdemo.hellodiary.wrapper.glide.CircleTransform;

/**
 * 我的
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.btn_message)
    TextView btnMessage;
    @BindView(R.id.ll_my_show_order)
    LinearLayout llMyShowOrder;
    @BindView(R.id.ll_my_raid_record)
    LinearLayout llMyRaidRecord;
    @BindView(R.id.ll_my_prize_record)
    LinearLayout llMyPrizeRecord;
    @BindView(R.id.ll_mine_introduce)
    LinearLayout llMineIntroduce;
    @BindView(R.id.ll_raid_rules)
    LinearLayout llRaidRules;
    @BindView(R.id.rl_mine_bg)
    RelativeLayout rlMineBg;//我的信息背景
    @BindView(R.id.bt_go_login)
    Button mBtGoLogin;

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    public void initWidget() {
        int hour = TimeUtils.getNowHour();
        if (hour > 6 && hour < 18) {
            //白天
            rlMineBg.setBackgroundResource(R.drawable.mine_bg_light);
        } else {
            //黑夜
            rlMineBg.setBackgroundResource(R.drawable.mine_bg_night);
        }

        tvUserName.setText("超级学校霸王");

        //加载圆形头像
        RequestBuilder<Bitmap> bmRequestBuilder = Glide.with(getActivity())
                .asBitmap()
                .load(R.drawable.default_no_pic)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher);
        bmRequestBuilder.apply(new RequestOptions()
                .transform(new CircleTransform()))
                .into(ivUserHead);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.frag_mine;
    }


    @OnClick({R.id.iv_user_head, R.id.btn_message, R.id.ll_my_show_order, R.id.ll_my_raid_record,
            R.id.ll_my_prize_record, R.id.ll_mine_introduce, R.id.ll_raid_rules})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head: {
                //用户资料
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_message: {
                //消息
                Intent intent = new Intent(getActivity(), SysMessageActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_my_show_order: {
                //我的晒单
                MyShowOrderActivity.launch(getActivity());
            }
            break;
            case R.id.ll_my_raid_record: {
                //夺宝记录
                GroupBuyRecordActivity.launch(getActivity());
            }
            break;
            case R.id.ll_my_prize_record: {
                //中奖记录
                BuyRecordActivity.launch(getActivity());
            }
            break;
            case R.id.ll_mine_introduce: {
                //表格
                TableActivity.launch(getActivity());
            }
            break;
            case R.id.ll_raid_rules: {
                //夺宝规则
                Intent intent = new Intent(getActivity(), WebDetailActivity.class);
                intent.putExtra("title", getString(R.string.group_buy_rule_str));
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }

    @OnClick(R.id.bt_go_login)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
