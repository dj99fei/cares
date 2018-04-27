package com.dj99fei.cares;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Switch;

import com.dj99fei.cares.base.BaseActivity;
import com.dj99fei.cares.util.SpHelper;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Actions;

public class MainActivity extends BaseActivity {

    @BindView(R.id.dnd) Switch dndSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String string = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (TextUtils.isEmpty(string) || !string.contains(NotificationListener.class.getName())) {
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        RxCompoundButton.checkedChanges(dndSwitch)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    SpHelper.getInstance("config").commit(editor -> editor.putBoolean("dnd", state));
                }, Actions.empty());

        startService(new Intent(this, NotificationListener.class));
    }

    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }

}
