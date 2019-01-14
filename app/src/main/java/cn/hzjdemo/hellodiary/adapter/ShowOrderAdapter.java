package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.bean.ShowOrderBean;
import cn.hzjdemo.hellodiary.ui.activity.ShowPictureActivity;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.MultiImageView;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.entity.PhotoInfo;
import cn.hzjdemo.hellodiary.wrapper.glide.GlideApp;

/**
 * Created by Hzj on 2017/8/18.
 * 晒单列表适配器
 */

public class ShowOrderAdapter extends CommonAdapter<ShowOrderBean> {
    private boolean can_delete;

    public ShowOrderAdapter(Context context, int layoutId, List<ShowOrderBean> datas, boolean can_delete) {
        super(context, layoutId, datas);
        this.can_delete = can_delete;
    }

    @Override
    protected void convert(ViewHolder holder, ShowOrderBean showOrderBean, int position) {
        holder.setVisible(R.id.bt_delete_show_order, can_delete);

        //有图片
        if (showOrderBean.isHasImg()) {
            holder.setVisible(R.id.gl_order_pics, true);
            MultiImageView nineLayout = holder.getView(R.id.gl_order_pics);
            final ArrayList<PhotoInfo> imageInfoList = new ArrayList<>();
            final List<String> imageDetails = showOrderBean.urlList;
            if (imageDetails != null) {
                for (String imageDetail : imageDetails) {
                    PhotoInfo info = new PhotoInfo();
                    info.url = imageDetail;//预览大图
                    imageInfoList.add(info);
                }
            }
            nineLayout.setList(imageInfoList);
            nineLayout.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, ShowPictureActivity.class);
                    intent.putStringArrayListExtra(ShowPictureActivity.IMAGE_URLS, (ArrayList<String>) imageDetails);
                    intent.putExtra(ShowPictureActivity.CURRENT_POSITION, position);
                    mContext.startActivity(intent);
                }
            });
        } else {
            //没图片
            holder.setVisible(R.id.gl_order_pics, false);
        }

        //加载圆形头像
        ImageView ivUserHead = holder.getView(R.id.iv_order_user_head);

        GlideApp.with(mContext)
                .load(Constants.imgUrls[3])
                .transform(new CircleCrop())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .into(ivUserHead);

    }
}
