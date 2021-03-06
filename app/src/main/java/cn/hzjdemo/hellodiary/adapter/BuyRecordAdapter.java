package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.widgets.zhihu.ZhiHuAdImageView;

/**
 * Created by Hzj on 2017/8/22.
 * 购买记录适配器
 */

public class BuyRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mData;

    private static final int NORMAL_TYPE = 0;
    private static final int ZHIHU_TYPE = 1;

    public BuyRecordAdapter(List<String> datas) {
        this.mData = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 6 == 0) {
            return ZHIHU_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ZHIHU_TYPE) {
            return new ZhiHuViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_zhihu_ad, parent, false));
        } else {
            return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_buy_record, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String s = mData.get(position);
        Context context = holder.itemView.getContext();
        if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).mBtnConfirmReceived.setVisibility(position % 2 == 0 ? View.VISIBLE : View.GONE);
            ((NormalViewHolder) holder).mTvOrderGoodName.setText(s);
            Glide.with(context)
                    .load(Constants.imgUrls[position%4])
                    .placeholder(R.drawable.default_no_pic)
                    .centerCrop()
                    .into(((NormalViewHolder) holder).mIvGoodPic);
        } else if (holder instanceof ZhiHuViewHolder) {
            Glide.with(context)
                    .load(Constants.imgUrls[4])
                    .into(((ZhiHuViewHolder) holder).zhIv);
        }

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_status)
        TextView mTvOrderStatus;
        @BindView(R.id.tv_buy_date)
        TextView mTvBuyDate;
        @BindView(R.id.ll_time)
        LinearLayout mLlTime;
        @BindView(R.id.tv_order_good_name)
        TextView mTvOrderGoodName;
        @BindView(R.id.tv_deal_price)
        TextView mTvDealPrice;
        @BindView(R.id.iv_good_pic)
        ImageView mIvGoodPic;
        @BindView(R.id.iv_is_group_price)
        ImageView mIvIsGroupPrice;
        @BindView(R.id.tv_take_good_address)
        TextView mTvTakeGoodAddress;
        @BindView(R.id.btn_confirm_received)
        Button mBtnConfirmReceived;
        @BindView(R.id.rl_take_good_address)
        LinearLayout mRlTakeGoodAddress;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ZhiHuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.zh_iv)
        public ZhiHuAdImageView zhIv;

        public ZhiHuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
