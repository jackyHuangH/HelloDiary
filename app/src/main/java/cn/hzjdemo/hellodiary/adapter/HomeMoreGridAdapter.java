package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.widgets.AutoSizeTextView;

/**
 * Created by Hzj on 2017/8/15.
 * 首页更多推荐适配器
 */

public class HomeMoreGridAdapter extends BaseAdapter {
    private Context context;

    public HomeMoreGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeMoreViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_more_introduce, null);
            holder = new HomeMoreViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HomeMoreViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(Constants.imgUrls[position%10])
                .centerCrop()
                .into(holder.ivHomeMore);

        holder.tvNameHomeMore.setText("小米电视" + position);
        holder.tvPriceHomeMore.setText("299" + position);
        holder.pbHomeMore.setProgress(position * 20);
        holder.tvPercentHomeMore.setText(position * 20 + "%");
        holder.tvHomeMoreTip.setText("3C产品" + position);

        return convertView;
    }

    class HomeMoreViewHolder {
        @BindView(R.id.iv_home_more)
        ImageView ivHomeMore;
        @BindView(R.id.tv_name_home_more)
        TextView tvNameHomeMore;
        @BindView(R.id.tv_price_home_more)
        AutoSizeTextView tvPriceHomeMore;
        @BindView(R.id.pb_home_more)
        ProgressBar pbHomeMore;
        @BindView(R.id.tv_percent_home_more)
        TextView tvPercentHomeMore;
        @BindView(R.id.tv_home_more_tip)
        TextView tvHomeMoreTip;

        public HomeMoreViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
