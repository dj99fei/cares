package com.dj99fei.cares;

import com.dj99fei.cares.alarm.Alarm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chengfei on 2018/4/25.
 */

public class AlarmPool {

    static AlarmPool pool = new AlarmPool();
    static final Integer LOCK = 1;

    public static AlarmPool getInstance() {
        return pool;
    }

    List<Alarm> mAlarms = new ArrayList<>();

    public void add(Alarm alarm) {
        synchronized (LOCK) {
            mAlarms.add(alarm);
        }
    }

    public void clear() {
        long now = System.currentTimeMillis();
        Iterator<Alarm> iterator = mAlarms.iterator();
        Alarm alarm;
        String content = toString();
        // send wechat
        synchronized (LOCK) {
            while (iterator.hasNext()) {
                alarm = iterator.next();
                if (alarm.handled || now - alarm.createTime >= 5 * 60 * 1000) {
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
