package cn.hzjdemo.hellodiary.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.support.widget.TitleBar;
import com.zenchn.support.widget.tips.SuperToast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.bean.TakeAddressBean;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.widgets.CommonPayWayPop;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.tv_order_good_name)
    TextView tvOrderGoodName;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_group_price)
    TextView tvGroupPrice;
    @BindView(R.id.iv_good_pic)
    ImageView ivGoodPic;
    @BindView(R.id.iv_is_group_price)
    ImageView ivIsGroupPrice;
    @BindView(R.id.tv_take_good_address)
    TextView tvTakeGoodAddress;
    @BindView(R.id.btn_edit_address)
    Button btnEditAddress;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.btn_pay_now)
    LinearLayout btnPayNow;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    public static final String EXTRA_PAY_WAY = "pay_way";
    public static final int REQUEST_EDIT_ADDRESS = 0x01;
    private int pay_way = -1;//支付方式

    @Override
    public int getLayoutRes() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.confirm_order_str))
                .setOnLeftClickListener(this);

        initData();
    }

    protected void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_PAY_WAY)) {
                pay_way = intent.getIntExtra(EXTRA_PAY_WAY, -1);
            }
        }

    }


    @OnClick({R.id.btn_edit_address})
    public void onEditAddressClicked(View view) {
        //修改地址
        Intent intent = new Intent(ConfirmOrderActivity.this, SetAddressActivity.class);
        startActivityForResult(intent, REQUEST_EDIT_ADDRESS);
    }

    @OnClick({R.id.btn_pay_now})
    public void onPayClicked(View view) {
        //立即支付
        //todo 立即支付
        final CommonPayWayPop payWayPop = new CommonPayWayPop.Builder(ConfirmOrderActivity.this, view)
                .setPayMoney(50f)
                .setOnConfirmClickListener(new CommonPayWayPop.OnConfirmClickListener() {
                    @Override
                    public void onPayClick(int pay_way) {
                        SuperToast.showDefaultMessage(ConfirmOrderActivity.this, "前往支付..." + (pay_way == 0 ? "微信" : "支付宝"));
                    }
                })
                .create();
        payWayPop.showPopWin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_ADDRESS) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    TakeAddressBean takeAddressBean = (TakeAddressBean) data.getSerializableExtra(SetAddressActivity.EXTRA_ADDRESS);
                    if (null != takeAddressBean) {
                        String address = takeAddressBean.getProvince() + takeAddressBean.getCity() +
                                takeAddressBean.getAddress();
                        tvTakeGoodAddress.setText(address);
                    }
                }
            }
        }
    }
}
