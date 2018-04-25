package com.dj99fei.cares;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;

import rx.exceptions.Exceptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String string = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (TextUtils.isEmpty(string) || !string.contains(NotificationListener.class.getName())) {
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        try {
            CaresHolder caresHolder = new Gson().fromJson(new InputStreamReader(getAssets().open("config.json")), CaresHolder.class);
            CaresHolder.setInstance(caresHolder);
        } catch (IOException e) {
            Log.e("chengfei", Exceptions.getFinalCause(e).getMessage());
        }

    }
}
