package cn.hzjdemo.hellodiary.ui.base;


import com.zenchn.support.base.IActivity;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public interface BaseView extends IActivity {
    void onApiFailure();

    void onApiGrantRefuse();
}