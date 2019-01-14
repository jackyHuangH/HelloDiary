package cn.hzjdemo.hellodiary.ui.activity;

import com.bin.david.form.core.SmartTable;
import com.zenchn.apilib.util.JavaKit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.bean.TableEntity;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;

/**
 * 表格demo
 *
 * @author HZJ
 */
public class TableActivity extends BaseActivity {

    @BindView(R.id.table)
    SmartTable mTable;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_table;
    }

    @Override
    public void initWidget() {
        List<TableEntity> list=new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            TableEntity entity = new TableEntity();
            entity.setDate(JavaKit.Date.getYmdhms(System.currentTimeMillis()));
            entity.setNum1(Math.random());
        }

        mTable.setData(list);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

}
