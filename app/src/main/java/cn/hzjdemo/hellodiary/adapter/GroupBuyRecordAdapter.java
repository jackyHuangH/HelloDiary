package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;

/**
 * Created by Hzj on 2017/8/22.
 * 拼团记录适配器
 */

public class GroupBuyRecordAdapter extends CommonAdapter<String>{
    private boolean can_take_prize;//是否可以领奖

    public GroupBuyRecordAdapter(Context context, int layoutId, List<String> datas, boolean can_take_prize) {
        super(context, layoutId, datas);
        this.can_take_prize=can_take_prize;
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setVisible(R.id.rl_take_prize,can_take_prize);
        holder.setText(R.id.tv_raid_good_name,s);
        Glide.with(mContext)
                .load(Constants.imgUrls[2])
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into((ImageView) holder.getView(R.id.iv_raid_good_pic));

    }
}
