package com.dj99fei.cares.alarm;

/**
 * Created by chengfei on 2018/4/25.
 */

public final class CriticalAlarmAction implements AlarmAction {

    private CriticalAlarmAction() {

    }

    static CriticalAlarmAction criticalAlarm = new CriticalAlarmAction();

    public static CriticalAlarmAction getInstance() {
        return criticalAlarm;
    }

    NormalAlarmAction mNormalAlarm = NormalAlarmAction.getInstance();

    @Override
    public void alarm(String message) {
        mNormalAlarm.alarm(message);
    }
}
