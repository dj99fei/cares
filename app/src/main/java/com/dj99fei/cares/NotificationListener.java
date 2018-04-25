package com.dj99fei.cares;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.dj99fei.cares.alarm.Alarm;
import com.dj99fei.cares.alarm.CriticalAlarmAction;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chengfei on 2018/4/24.
 */

public class NotificationListener extends NotificationListenerService {

    private static volatile int count;



    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1)
                .scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        AlarmPool.getInstance().clear();
                    }
                }, 0, 5, TimeUnit.MINUTES);
        String tickerText = sbn.getNotification().tickerText == null ? "" : sbn.getNotification().tickerText.toString();
        String title = sbn.getNotification().extras.get("android.title").toString();
        String text = sbn.getNotification().extras.get("android.text").toString();
        Log.e("chengfei", "title: " + title + "\ttext: " + text + "\ttickerText: " + tickerText);
        String packageName = sbn.getPackageName();

        CaresHolder.CareItem careItem = CaresHolder.getCare(packageName);
        if (careItem == null) {
            // don't care
            return;
        }
        if (careItem.matchWho(title, tickerText)) {
            CriticalAlarmAction.getInstance().alarm("请及时查看消息");
            AlarmPool.getInstance().add(new Alarm(100, System.currentTimeMillis(), tickerText));
        }

//        careItem.who
        count++;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
//        count--;
//        if (count == 0) {
//            Log.e("chengfei", "notification handled");
//        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
