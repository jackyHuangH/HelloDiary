package cn.hzjdemo.hellodiary.model.callback;


import cn.hzjdemo.hellodiary.http.callback.GrantCallback;

/**
 * 作    者：wangr on 2017/8/29 17:16
 * 描    述：
 * 修订记录：
 */
public interface ApiFailureCallback extends GrantCallback {

    /**
     * 请求数据错误
     *
     * @param errorDesc
     */
    void onFailure(String errorDesc);

}
