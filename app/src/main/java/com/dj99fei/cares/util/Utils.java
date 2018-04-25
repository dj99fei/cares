package com.dj99fei.cares.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by chengfei on 2018/4/25.
 */

public final class Utils {
    private Utils() {

    }

    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{300, 500}, 0);
    }

}
