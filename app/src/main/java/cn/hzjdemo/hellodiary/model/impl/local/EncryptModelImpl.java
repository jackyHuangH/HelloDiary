package cn.hzjdemo.hellodiary.model.impl.local;

import android.support.annotation.NonNull;

import com.zenchn.apilib.util.Codec;


/**
 * 作    者：wangr on 2017/9/1 13:28
 * 描    述：
 * 修订记录：
 */

public class EncryptModelImpl {

    private EncryptModelImpl() {
    }


    public static String encryptPwdSHA256(@NonNull String text) {
        return Codec.SHA256.encrypt(text);
    }

    public static String encryptPwdMD5(@NonNull String text) {
        return Codec.MD5.encrypt(text);
    }
}
