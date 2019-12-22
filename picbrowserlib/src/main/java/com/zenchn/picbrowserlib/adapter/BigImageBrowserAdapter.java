package com.zenchn.picbrowserlib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zenchn.picbrowserlib.R;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作   者： by Hzj on 2017/12/18/018.
 * 描   述：大图浏览
 * 修订记录：
 * @author HZJ
 */

public class BigImageBrowserAdapter extends PagerAdapter {
    private static final String TAG = "BigImageBrowserAdapter";

    private List<ImageSourceInfo> mImageUrls;
    private Context mContext;
    private int mChildCount;

    public BigImageBrowserAdapter(Context context, ArrayList<ImageSourceInfo> imageUrls) {
        this.mImageUrls = imageUrls;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mImageUrls != null ? mImageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        Context context = container.getContext();
        ImageSourceInfo imageSourceInfo = mImageUrls.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.viewpage_item_photoview, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);

        //开启缩放
        photoView.enable();

        //加载图片
        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .dontAnimate()
                .error(R.drawable.default_no_pic)
                .placeholder(R.drawable.default_no_pic)
                //跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide
                .with(mContext)
                .load(imageSourceInfo.getSource())
                .apply(requestOptions)
                .into(photoView);

        photoView.setOnClickListener(v -> {
            if (mOnImageClickListener != null) {
                mOnImageClickListener.onImageClick();
            }
        });


        //防止v被添加前存在与另一个父容器中
        if (photoView.getParent() != null) {
            ((ViewGroup) photoView.getParent()).removeView(photoView);
        }
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        PhotoView photoView = (PhotoView) object;
        if (photoView == null) {
            return;
        }
        container.removeView(photoView);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // 重写getItemPosition,保证每次获取时都强制重绘UI
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    //---------------------------------Activity ondestroy调用释放资源-----------------
    @SuppressLint("CheckResult")
    public void destroy() {
        //清理内存缓存
        Glide.get(mContext).clearMemory();
    }

    //--------------------点击图片关闭--------------------
    public interface OnImageClickListener {
        void onImageClick();
    }

    private OnImageClickListener mOnImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }
}
