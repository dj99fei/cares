package com.dj99fei.cares;

import android.util.Log;

import com.dj99fei.cares.alarm.Alarm;
import com.dj99fei.cares.util.Constant;
import com.dj99fei.cares.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chengfei on 2018/4/25.
 */

public class AlarmPool {

    static AlarmPool pool = new AlarmPool();
    static final Integer LOCK = 1;
    public static final int CLEAR_PERIOD = 1; // unit minutes

    public AlarmPool() {
        // 5分钟清理一次，清理前把这一阶段的cares打包发出去
        Executors.newScheduledThreadPool(2)
                .scheduleAtFixedRate(() -> {
                    clear();
                }, 0, CLEAR_PERIOD, TimeUnit.MINUTES);
    }

    public static AlarmPool getInstance() {
        return pool;
    }

    List<Alarm> mAlarms = new ArrayList<>();
    Set<String> packages = new HashSet<>();

    public void add(String pkg, Alarm alarm) {
        packages.add(pkg);
        synchronized (LOCK) {
            mAlarms.add(alarm);
        }
    }

    public void handle(String pkg) {
        packages.remove(pkg);
        if (packages.size() == 0) {
            Utils.cancelVibrate(Application.getApplication());
        }
    }

    public void clear() {
        long now = System.currentTimeMillis();
        Iterator<Alarm> iterator = mAlarms.iterator();
        Alarm alarm;
        String content = toString();
        // send wechat
        Log.e(Constant.TAG, "latest cares: " + content);
        synchronized (LOCK) {
            while (iterator.hasNext()) {
                alarm = iterator.next();
                if (alarm.handled || now - alarm.createTime >= CLEAR_PERIOD * 60 * 1000) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Alarm alarm: mAlarms) {
            builder.append(alarm.message);
            builder.append('\n');
        }
        return builder.toString();
    }
}
