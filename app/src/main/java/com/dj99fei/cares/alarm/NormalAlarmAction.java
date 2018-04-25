package com.dj99fei.cares.alarm;

import com.dj99fei.cares.Application;
import com.dj99fei.cares.util.Utils;

/**
 * Created by chengfei on 2018/4/25.
 */

public class NormalAlarmAction implements AlarmAction {

    @Override
    public void alarm(String message) {
        Utils.vibrate(Application.getApplication());
    }
}
