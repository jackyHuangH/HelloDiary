package cn.hzjdemo.hellodiary.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.widget.dialog.DialogMaster;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 闪屏页，主要执行冷加载，一些数据初始化
 */
public class SplashActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "SplashActivity";

    @BindView(R.id.bt_goto_diary)
    Button mBtGotoDiary;
    @BindView(R.id.bt_multi_select)
    Button mBtMultiSelect;
    @BindView(R.id.bt_show_dialog)
    Button mBtShowDialog;
    @BindView(R.id.bt_interval)
    Button mBtInterval;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void initWidget() {
    }

    @OnClick(R.id.bt_goto_diary)
    public void onMBtGotoDiaryClicked() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.bt_multi_select)
    public void onMBtMultiSelectClicked() {
//        startActivity(new Intent(SplashActivity.this, MultiSelectActivity.class));
    }

    @OnClick(R.id.bt_show_dialog)
    public void onShowDialogClicked() {
//        showDialog();
//        rxjava2Review();
//        requestFromFlowable();
//        interval();
//        readFile();
        showRangeDatePicker();
    }

    @OnClick(R.id.bt_interval)
    public void onRxIntervalClicked() {
        testInterval();
    }

    public void testInterval() {
        int maxCount = 5;

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .doOnNext(i -> Log.d(TAG, "next: " + i))
                .map(aLong -> maxCount - aLong)
                .takeUntil(count -> count <= 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mBtInterval.setText(String.valueOf(aLong));
                    Log.d(TAG, "onNext: " + aLong);
                });
    }


    private void showRangeDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "You picked the following date: From- " + dayOfMonth + "/" + (++monthOfYear) + "/" + year + " To " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        showMessage(date);
    }


    private void showDialog() {
        int screenWidth = AndroidKit.Dimens.getScreenWidth();
        int screenHeight = AndroidKit.Dimens.getScreenHeight();

        new DialogMaster.Builder()
                .setLayout(R.layout.dialog_choose_building_type)
                .setContext(this)
                .setGravity(Gravity.BOTTOM)
                .setDialogHeight(600)
                .setDialogWidth(screenWidth)
                .setItemClickListener(R.id.bt_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("住宅房屋");
                    }
                })
                .setItemClickListener(R.id.bt_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("非住宅房屋");
                    }
                })
                .setItemClickListener(R.id.bt_3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("幕墙房屋");
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }


}
