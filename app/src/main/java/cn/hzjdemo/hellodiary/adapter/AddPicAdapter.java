package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zenchn.support.kit.AndroidKit;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.entity.PhotoInfo;

/**
 * Created by Hzj on 2017/8/21.
 * 晒一晒添加图片适配器
 */

public class AddPicAdapter extends CommonAdapter<PhotoInfo> {
    private int screenWidth;

    public AddPicAdapter(Context context, int layoutId, int screenWidth, List<PhotoInfo> datas) {
        super(context, layoutId, datas);
        this.screenWidth = screenWidth;
    }

    @Override
    protected void convert(ViewHolder holder, PhotoInfo imageInfo, int position) {
        RelativeLayout rlRoot = holder.getView(R.id.rl_add_pic);//根布局
        ImageView ivImg = holder.getView(R.id.iv_img);//显示的图片
        String s = imageInfo.url;//加号

        int space = AndroidKit.Dimens.dp2px( 7);
        int marginSide = AndroidKit.Dimens.dp2px(15);
        int imgWidth = (screenWidth - marginSide * 2 - space * 2) / 3;
        ViewGroup.LayoutParams layoutParams_root= rlRoot.getLayoutParams();
        layoutParams_root.width=imgWidth;
        layoutParams_root.height=imgWidth;
        rlRoot.setLayoutParams(layoutParams_root);

        if (s.equals(Constants.NO_PIC)) {
            holder.setVisible(R.id.iv_plus, true);
            holder.setVisible(R.id.iv_img, false);
        } else {
            holder.setVisible(R.id.iv_plus, false);
            holder.setVisible(R.id.iv_img, true);

            Glide.with(mContext)
                    .load(s)
                    .centerCrop()
                    .into(ivImg);

        }
    }
}
