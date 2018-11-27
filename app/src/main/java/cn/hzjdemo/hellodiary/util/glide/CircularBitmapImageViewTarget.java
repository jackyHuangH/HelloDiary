package cn.hzjdemo.hellodiary.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Hzj on 2017/8/23.
 * 自定义bitmapimageViewTarget 实现圆角,圆形图片
 */

public class CircularBitmapImageViewTarget extends BitmapImageViewTarget {
    private Context context;
    private ImageView imageView;
    private float radius;

    public CircularBitmapImageViewTarget(Context context, ImageView view, float radius) {
        super(view);
        this.context = context;
        this.imageView = view;
        this.radius = radius;
    }

    /**
     * 重写 setResource（），生成圆角的图片
     *
     * @param resource
     */
    @Override
    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), resource);
        /**
         *   设置图片的shape为圆形.
         *   bitmapDrawable.setCircular(true);
         */
        //若是需要制定圆角的度数，则调用setCornerRadius（）。
        bitmapDrawable.setCornerRadius(radius);

        this.imageView.setImageDrawable(bitmapDrawable);
    }
}
