package cn.hzjdemo.hellodiary.model.callback;

/**
 * 作    者：wangr on 2017/9/10 17:21
 * 描    述：
 * 修订记录：
 */

public interface DbFailureCallback {

    // 请求数据错误
    void onFailure(String errorDesc);

}
