package com.dj99fei.cares.util;

import android.content.SharedPreferences;

import com.dj99fei.cares.Application;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by chengfei on 2018/4/27.
 */

public final class SpHelper {

    SharedPreferences sp;
    static Map<String, SpHelper> sps = new HashMap<>();

    private SpHelper(String key) {
        sp = Application.getApplication().getSharedPreferences(key, 0);
    }

    public static SpHelper getInstance(String key) {
        if (sps.containsKey(key)) {
            return sps.get(key);
        }
        SpHelper helper = new SpHelper(key);
        sps.put(key, helper);
        return helper;
    }

    public void commit(Action1<SharedPreferences.Editor> action) {
        SharedPreferences.Editor editor = sp.edit();
        action.call(editor);
        editor.apply();
    }

    public <T> T get(String key, Class<T> clazz) {
        if (clazz.isInstance(1)) {
            return (T) Integer.valueOf(sp.getInt(key, 0));
        } else if (clazz.isInstance(true)) {
            return (T) Boolean.valueOf(sp.getBoolean(key, false));
        } else if (clazz.isInstance("")) {
            return (T) sp.getString(key, "");
        } else if (clazz.isInstance(1l)) {
            return (T) Long.valueOf(sp.getLong(key, 0l));
        } else if (clazz.isInstance(1f)) {
            return (T) Float.valueOf(sp.getFloat(key, 0f));
        } else
            throw new IllegalArgumentException("clazz not support");
    }
}
