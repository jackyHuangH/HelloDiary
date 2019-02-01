package cn.hzjdemo.hellodiary.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.zenchn.support.widget.tips.SuperToast;

import cn.hzjdemo.hellodiary.R;

/**
 * @author:Hzj
 * @date :2019/2/1/001
 * desc  ：自定义桌面小部件类
 * record：AppWidgetProvider是Android中提供的用于实现桌面小工具的类，
 * 其本质是一个广播，即BroadcastReceiver
 */
public class TestWidgetProvider extends AppWidgetProvider {

    private static final String CLICK_ACTION = "cn.hzjdemo.hellodiary.appwidget.action.CLICK";

    /**
     * 每次窗口小部件被更新都调用一次此方法
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_test);
        Intent intent = new Intent(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, R.id.iv_app_widget, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //点击时发送广播
        remoteViews.setOnClickPendingIntent(R.id.iv_app_widget, pendingIntent);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    /**
     * 接收窗口小部件点击时发送的广播
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(CLICK_ACTION)) {
            SuperToast.showDefaultMessage(context, "你好啊：" + intent.getAction());
        }
    }

    /**
     * 每删除一次窗口小部件就调用一次
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SuperToast.showDefaultMessage(context, "onDeleted：" + appWidgetIds);
    }

    /**
     * 当小部件大小改变时调用此方法
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param newOptions
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        SuperToast.showDefaultMessage(context, "onAppWidgetOptionsChanged：");
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        SuperToast.showDefaultMessage(context, "onEnabled：");
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        SuperToast.showDefaultMessage(context, "onDisabled:最后一个该窗口小部件删除");
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
