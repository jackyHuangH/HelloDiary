package cn.hzjdemo.hellodiary.wrapper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.model.impl.local.ContextModel;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author:Hzj
 * @date :2019/05/15 14:02
 * desc  ：通知工具类
 * record：
 */
public class NotifyWrapper {
    /*通知栏id*/
    public static final int NOTIFICATION_ID = 100;
    private static boolean isCreateChannel = false;

    /*短文本通知*/
    public static Integer NOTIFY_TYPE_SHORT = 1;
    /*长文本通知*/
    public static Integer NOTIFY_TYPE_LONG = 2;
    /*带图片的通知*/
    public static Integer NOFITY_TYPE_IMG = 3;

    @SuppressLint("NewApi")
    public static Notification buildNotification(String content) {
        Context applicationContext = ContextModel.getApplicationContext();
        NotificationManager manager = (NotificationManager) applicationContext.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            String channelId = applicationContext.getPackageName();
            String channelName = "BackgroundLocation";
            if (!isCreateChannel) {
                createNotificationChannel(manager, channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                isCreateChannel = true;
            }
            builder = new NotificationCompat.Builder(applicationContext, channelId);
        } else {
            builder = new NotificationCompat.Builder(applicationContext);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentText(content)
                .setWhen(System.currentTimeMillis());

        notification = builder.build();

        return notification;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(NotificationManager nm, String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        //是否在桌面icon右上角展示小圆点
        channel.enableLights(true);
        channel.enableVibration(true);
        //小圆点颜色
        channel.setLightColor(Color.BLUE);
        //是否在久按桌面图标时显示此渠道的通知
        channel.setShowBadge(true);
        nm.createNotificationChannel(channel);
    }

    /**
     * 弹出通知
     *
     * @param target  点击通知跳转的目标页面
     * @param title   通知标题
     * @param content 通知内容
     */
    public static void sendSimpleNotification(Class<?> target, String title, String content) {
        Context applicationContext = ContextModel.getApplicationContext();
        NotificationManager manager = (NotificationManager) applicationContext.getSystemService(NOTIFICATION_SERVICE);
        //创建点击通知时发送的广播
        Intent intent = new Intent(applicationContext, target);
        PendingIntent pi = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建通知
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            String channelId = applicationContext.getPackageName();
            String channelName = "北斗短报文消息";
            createNotificationChannel(manager, channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            builder = new NotificationCompat.Builder(applicationContext, channelId);
        } else {
            builder = new NotificationCompat.Builder(applicationContext);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.mipmap.ic_launcher))
                //设置通知标题
                .setContentTitle(title)
                //设置通知内容
                .setContentText(content)
                //设置点击通知后自动删除
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                //设置点击通知时的响应事件
                .setContentIntent(pi);

        int defaults = 0;
        defaults |= Notification.DEFAULT_SOUND;
        defaults |= Notification.DEFAULT_VIBRATE;

        builder.setDefaults(defaults);

        Notification notification = builder.build();
        //发送通知
        manager.notify(NOTIFY_TYPE_SHORT, notification);
    }

    /**
     * 响铃和震动
     */
    public static void ringNotice() {
        Context applicationContext = ContextModel.getApplicationContext();

        //开启振动
        Vibrator vibrator = (Vibrator) applicationContext.getSystemService(Context.VIBRATOR_SERVICE);
        //创建一次性振动
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(800);
        }

        //播放系统提示音
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(applicationContext, uri);
        rt.play();

    }
}
