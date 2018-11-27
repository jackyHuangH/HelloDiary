package cn.hzjdemo.hellodiary.presenter.contract;

import java.util.List;

import cn.hzjdemo.hellodiary.presenter.base.IPresenter;
import cn.hzjdemo.hellodiary.presenter.base.IView;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public interface HomeContract {
    interface View extends IView {
        void showNotice(List<String> info);
    }

    interface Presenter extends IPresenter {
        void getHomeNotice(String id);
    }

}
