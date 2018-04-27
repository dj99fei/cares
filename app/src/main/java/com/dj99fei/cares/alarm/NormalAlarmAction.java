package com.dj99fei.cares.alarm;

import android.util.Log;

import com.dj99fei.cares.Application;
import com.dj99fei.cares.util.SpHelper;
import com.dj99fei.cares.util.Utils;

import static com.dj99fei.cares.util.Constant.TAG;

/**
 * Created by chengfei on 2018/4/25.
 */

public class NormalAlarmAction implements AlarmAction {


    static NormalAlarmAction instance = new NormalAlarmAction();

    private NormalAlarmAction() {

    }
    public static NormalAlarmAction getInstance() {
        return instance;
    }

    @Override
    public void alarm(String message) {
        Log.e(TAG,message);
        if (!SpHelper.getInstance("config").get("dnd", Boolean.class)) {
            Utils.vibrate(Application.getApplication());
        }
    }
}
