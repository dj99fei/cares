package com.dj99fei.cares;

/**
 * Created by chengfei on 2018/4/25.
 */

public class Application extends android.app.Application {

    static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }
}
