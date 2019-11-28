package cn.hzjdemo.hellodiary.ui.activity;

import android.annotation.SuppressLint;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.zenchn.picbrowserlib.adapter.BigImageProgressAdapter;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.widgets.viewpager.BaseViewPager;


/**
 * 左右滑动显示图片
 */
public class ShowPictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String IMAGE_URLS = "imageUrls";
    public static final String CURRENT_POSITION = "currentPosition";

    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.viewpager)
    BaseViewPager viewPager;
    @BindView(R.id.viewPagerLayout)
    FrameLayout mViewPagerLayout;

    private int currentPosition = 0; //当前位置
    private BigImageProgressAdapter mViewPagerAdapter;
    private List<String> imageUrls;

    @SuppressLint("NewApi")
    private void initialize() {
        if (getIntent() != null) {
            imageUrls = getIntent().getStringArrayListExtra(IMAGE_URLS);
            currentPosition = getIntent().getIntExtra(CURRENT_POSITION, 0);
        }
        ArrayList<ImageSourceInfo> list = new ArrayList<>();
        ImageSourceInfo info;
        for (String imageUrl : imageUrls) {
            info = new ImageSourceInfo(imageUrl, true);
            list.add(info);
        }

        mViewPagerAdapter = new BigImageProgressAdapter(this, list);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        mViewPagerAdapter.notifyDataSetChanged();
        tvIndex.setText((currentPosition + 1) + "/" + imageUrls.size());

        mViewPagerAdapter.setOnImageClickListener(new BigImageProgressAdapter.OnImageClickListener() {
            @Override
            public void onImageClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvIndex.setText((position + 1) + "/" + imageUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void initWidget() {
        initialize();
    }


    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        if (mViewPagerAdapter != null) {
            mViewPagerAdapter.destroy();
        }
        super.onDestroy();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_pic;
    }
}
