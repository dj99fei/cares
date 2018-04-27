package com.dj99fei.cares;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.dj99fei.cares.alarm.Alarm;
import com.dj99fei.cares.alarm.CriticalAlarmAction;
import com.dj99fei.cares.alarm.NormalAlarmAction;
import com.dj99fei.cares.util.SpHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Actions;
import rx.schedulers.Schedulers;

import static com.dj99fei.cares.util.Constant.TAG;

/**
 * Created by chengfei on 2018/4/24.
 */

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();

        Observable.create((Observable.OnSubscribe<CareConfig>) subscriber -> {
            CareConfig careConfig = null;
            String careConfigStr = SpHelper.getInstance("config").get("cares_config", String.class);
            if (!TextUtils.isEmpty(careConfigStr)) {
                careConfig = parse(new StringReader(careConfigStr));
            }
            if (careConfig == null) {
                try {
                    careConfig = parse(new InputStreamReader(getAssets().open("config.json")));
                    final CareConfig toSave = careConfig;
                    if (toSave != null) {
                        SpHelper.getInstance("config").commit(editor -> editor.putString("cares_config", new Gson().toJson(toSave)));
                    }
                } catch (IOException e) {
                    Log.e(TAG, Exceptions.getFinalCause(e).getMessage());
                    e.printStackTrace();
                }
            }
            subscriber.onNext(careConfig);
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(config -> Checker.init(config), Actions.empty());
    }

    private CareConfig parse(Reader reader) {
        return new Gson().fromJson(reader, CareConfig.class);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        String tickerText = sbn.getNotification().tickerText == null ? "" : sbn.getNotification().tickerText.toString();
        String title = sbn.getNotification().extras.get("android.title") == null ? "" : sbn.getNotification().extras.get("android.title").toString();
        String text = sbn.getNotification().extras.get("android.text") == null ? "" : sbn.getNotification().extras.get("android.text").toString();
        String packageName = sbn.getPackageName();

        Checker checker = Checker.getInstance(packageName);
        if (checker == null) {
            // don't care
            return;
        }
        if (checker.testWho(title, tickerText)) {
            Alarm alarm = new Alarm(100, System.currentTimeMillis(), "title: " + title + "\ttext: " + text + "\ttickerText: " + tickerText);
            CriticalAlarmAction.getInstance().alarm(alarm.message);
            AlarmPool.getInstance().add(packageName, alarm);
        } else if (checker.testWhat(title, tickerText)) {
            Alarm alarm = new Alarm(80, System.currentTimeMillis(), "title: " + title + "\ttext: " + text + "\ttickerText: " + tickerText);
            NormalAlarmAction.getInstance().alarm(alarm.message);
            AlarmPool.getInstance().add(packageName, alarm);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        AlarmPool.getInstance().handle(sbn.getPackageName());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }
}
