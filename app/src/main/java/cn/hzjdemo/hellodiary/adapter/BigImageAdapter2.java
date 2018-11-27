package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.luck.picture.lib.photoview.OnPhotoTapListener;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.List;

import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.util.glide.GlideApp;
import cn.hzjdemo.hellodiary.widgets.RingProgressBar;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * 作   者： by Hzj on 2017/12/18/018.
 * 描   述：
 * 修订记录：
 */

public class BigImageAdapter2 extends PagerAdapter {
    private static final String TAG = "BigImageAdapter";

    private List<String> mImageUrls;
    private Context mContext;
    private int mChildCount;

    public BigImageAdapter2(Context context, List<String> imageUrls) {
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

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        final Context context = container.getContext();

        final String imageUrl = mImageUrls.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);

        PhotoView ivPic = (PhotoView) view.findViewById(R.id.photoview);
        final RingProgressBar progressBar = (RingProgressBar) view.findViewById(R.id.pb);

        ProgressManager.getInstance().addResponseListener(imageUrl, new me.jessyan.progressmanager.ProgressListener() {
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

        GlideApp
                .with(context)
                .load(imageUrl)
                .fitCenter()
                .error(R.drawable.default_no_pic)
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
                .into(ivPic);

        ivPic.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView imageView, float v, float v1) {
                if (mOnImageClickListener != null) {
                    mOnImageClickListener.onImageClick();
                }
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
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
        if (photoView==null) {
            return;
        }

        //及时释放资源
        photoView.destroyDrawingCache();
        view.destroyDrawingCache();

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
    public void destroy() {
//        for (PhotoView photoView : mViewList) {
//            photoView.destroyDrawingCache();
//        }
//        mViewList.clear();
//        mViewList = null;
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
