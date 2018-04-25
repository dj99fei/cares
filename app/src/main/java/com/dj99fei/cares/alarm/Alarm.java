package com.dj99fei.cares.alarm;

/**
 * Created by chengfei on 2018/4/25.
 */

public class Alarm {
    public boolean handled;
    public int priority;
    public long createTime;
    public String message;


    public Alarm(int priority, long createTime, String message) {
        this.priority = priority;
        this.createTime = createTime;
        this.message = message;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}
