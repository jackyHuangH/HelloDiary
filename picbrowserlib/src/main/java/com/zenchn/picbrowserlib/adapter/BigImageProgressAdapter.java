package com.zenchn.picbrowserlib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zenchn.picbrowserlib.R;
import com.zenchn.picbrowserlib.annotation.ImageSourceType;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;
import com.zenchn.picbrowserlib.widget.RingProgressBar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * 作   者： by Hzj on 2017/12/18/018.
 * 描   述：显示图片加载进度的图片预览,网络图片显示进度，本地图片不显示
 * 修订记录：
 */

public class BigImageProgressAdapter extends PagerAdapter {
    private static final String TAG = "BigImageAdapter";

    private List<ImageSourceInfo> mImageUrls;
    private Context mContext;
    private int mChildCount;

    public BigImageProgressAdapter(Context context, List<ImageSourceInfo> imageUrls) {
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
    public View instantiateItem(@NonNull ViewGroup container, final int position) {
        final Context context = container.getContext();
        final ImageSourceInfo imageSourceInfo = mImageUrls.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
        final RingProgressBar progressBar = (RingProgressBar) view.findViewById(R.id.pb);

        //开启缩放
        photoView.enable();

        if (imageSourceInfo.getSourceType() == ImageSourceType.URL) {
            //网络图片显示加载进度
            progressBar.setVisibility(View.VISIBLE);
            ProgressManager.getInstance().addResponseListener((String) imageSourceInfo.getSource(), new ProgressListener() {
                @Override
                public void onProgress(ProgressInfo progressInfo) {
                    progressBar.setProgress(progressInfo.getPercent());
                    Log.d(TAG, "图片加载进度: " + progressInfo.getPercent());
                }

                @Override
                public void onError(long id, Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "图片加载失败: " + e.toString());
                }
            });
        }

        Glide
                .with(context)
                .load(imageSourceInfo.getSource())
                .fitCenter()
                .error(R.drawable.default_no_pic)
                //必须设置成None才能显示进度条
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(photoView);

        photoView.setOnClickListener(v -> {
            if (mOnImageClickListener != null) {
                mOnImageClickListener.onImageClick();
            }
        });

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        if (view == null) {
            return;
        }
        container.removeView(view);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 重写getItemPosition,保证每次获取时都强制重绘UI
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    //=============================================================destroy
    @SuppressLint("CheckResult")
    public void destroy() {
        //清理内存缓存
        Glide.get(mContext).clearMemory();
        //开子线程清理
        new Thread() {
            @Override
            public void run() {
                super.run();
                //清理磁盘缓存
                Glide.get(mContext).clearDiskCache();
            }
        }.start();
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
