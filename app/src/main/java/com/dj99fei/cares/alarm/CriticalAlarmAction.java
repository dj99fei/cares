package com.dj99fei.cares.alarm;

/**
 * Created by chengfei on 2018/4/25.
 */

public class CriticalAlarmAction implements AlarmAction {


    static CriticalAlarmAction criticalAlarm = new CriticalAlarmAction();

    public static CriticalAlarmAction getInstance() {
        return criticalAlarm;
    }

    NormalAlarmAction mNormalAlarm = new NormalAlarmAction();

    @Override
    public void alarm(String message) {
        mNormalAlarm.alarm(message);
    }
}
