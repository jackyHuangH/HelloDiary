package cn.hzjdemo.hellodiary.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import cn.hzjdemo.hellodiary.adapter.itemDelegate.RecordContentItemDelegate;
import cn.hzjdemo.hellodiary.adapter.itemDelegate.RecordDateItemDelegate;
import cn.hzjdemo.hellodiary.bean.JoinRecordBean;

/**
 * Created by Hzj on 2017/8/17.
 * 参与记录adapter
 */

public class JoinRecordAdapter extends MultiItemTypeAdapter<JoinRecordBean> {

    public JoinRecordAdapter(Context context, List<JoinRecordBean> datas) {
        super(context, datas);
        addItemViewDelegate(new RecordDateItemDelegate(mContext));
        addItemViewDelegate(new RecordContentItemDelegate());
    }

}
