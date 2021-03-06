package cn.hzjdemo.hellodiary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hzjdemo.hellodiary.Constants;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.adapter.AddPicAdapter;
import cn.hzjdemo.hellodiary.adapter.itemDecoration.GridSpaceItemDecoration;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.ui.fragment.PromptDialogFragment;
import cn.hzjdemo.hellodiary.ui.photoviewer.ShowPictureProgressActivity;
import cn.hzjdemo.hellodiary.widgets.wechatcicleimage.entity.PhotoInfo;

/**
 * 晒一晒
 */

public class SendOrderActivity extends BaseActivity {

    @BindView(R.id.et_send_content)
    EditText etSendContent;
    @BindView(R.id.rlv_add_pic)
    RecyclerView rlvAddPic;
    @BindView(R.id.bt_send_now)
    Button btSendNow;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    public static final int IMAGE_PICKER = 0x100;
    public static final int IMAGE_PREVIEW = 0x101;
    private List<PhotoInfo> addPicList = new ArrayList<>();//选择的要上传的图片集合
    private AddPicAdapter addPicAdapter;
    private int selected_img_num = 0;//当前已选图片的数量

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_order;
    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.show_something))
                .setOnLeftClickListener(this);

        //添加图片列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rlvAddPic.setLayoutManager(gridLayoutManager);
        int space = AndroidKit.Dimens.dp2px(7);
        rlvAddPic.addItemDecoration(new GridSpaceItemDecoration(3, addPicList.size(), space));

        int windowWidth = AndroidKit.Dimens.getScreenWidth();
        addPicAdapter = new AddPicAdapter(this, R.layout.item_plus_img, windowWidth, addPicList);
        rlvAddPic.setAdapter(addPicAdapter);
        //添加默认的加号至末尾
        addPicList.add(new PhotoInfo(Constants.NO_PIC));
        addPicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String s = addPicList.get(position).url;
                if (s.equals(Constants.NO_PIC)) {
                    //选取图片

                } else {
                    // 预览图片
                    Intent intent = new Intent(SendOrderActivity.this, ShowPictureProgressActivity.class);
                    ArrayList<String> imgInfo = new ArrayList<>();
                    for (int i = 0; i < addPicList.size(); i++) {
                        String bigImageUrl = addPicList.get(i).url;
                        if (!bigImageUrl.equals(Constants.NO_PIC)) {
                            imgInfo.add(addPicList.get(i).url);
                        }
                    }
                    intent.putStringArrayListExtra(ShowPictureProgressActivity.IMAGE_URLS, imgInfo);
                    intent.putExtra(ShowPictureProgressActivity.CURRENT_POSITION, position);
                    startActivityForResult(intent, IMAGE_PREVIEW);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    @OnClick(R.id.bt_send_now)
    public void onSubmitClicked(View view) {
        showPromptDialog("加载中。。。");
    }


    private void showPromptDialog(final String text) {
        PromptDialogFragment promptDialog = new PromptDialogFragment()
                .setContent(text);
        promptDialog.show(getSupportFragmentManager(), text);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER) {//选择图片

        } else if (requestCode == IMAGE_PREVIEW) {//预览图片删除后的结果

        }
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(SendOrderActivity.class)
                .launch();
    }
}
