package cn.hzjdemo.hellodiary.model.callback;


/**
 * 作    者：wangr on 2017/8/29 17:16
 * 描    述：
 * 修订记录：
 */
public interface ModelCallback {

    // 请求数据错误
    void onFailure(String errorDesc);

}
