package com.zenchn.support.base;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import com.zenchn.support.dafault.DefaultUiController;
import com.zenchn.support.widget.tips.SuperSnackBar;


/**
 * 描    述：使用SnackBar 代替Toast
 * 修订记录：
 *
 * @author HZJ
 */

public abstract class AbstractUiController extends DefaultUiController {

    public AbstractUiController(Context mContext, LifecycleOwner lifecycleOwner) {
        super(mContext, lifecycleOwner);
    }

    @Override
    public void showMessage(@NonNull CharSequence message) {
        View snackBarParentView = getSnackBarParentView();
        if (snackBarParentView != null) {
            SuperSnackBar.createShortSnackbar(snackBarParentView, message, SuperSnackBar.Info).show();
        }
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        showMessage(getMContext().getString(resId));
    }

    /**
     * 指定SnackBar的寄主
     *
     * @return
     */
    protected abstract View getSnackBarParentView();
}
