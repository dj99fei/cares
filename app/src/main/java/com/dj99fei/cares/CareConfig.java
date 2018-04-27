package com.dj99fei.cares;

import android.support.annotation.Keep;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by chengfei on 2018/4/24.
 */

@Keep
public class CareConfig implements Serializable {

    public static final String DIVIDER = ",";
    Map<String, CareItem> cares;

    public static class CareItem implements Serializable {

        public CareItem(String who, String what) {
            this.who = who;
            this.what = what;
        }

        public String who;
        public String what;


        public boolean matchWho(String... texts) {
            return match(who, texts);
        }

        private boolean match(String content, String... texts) {
            if (texts == null) {
                return false;
            }
            for (String s : texts) {
                if (Pattern.compile(".*(" + content + ").*").matcher(s).matches()) {
                    return true;
                }
            }
            return false;
        }

        public boolean matchWhat(String... texts) {
            return match(what, texts);
        }

        @Override
        public String toString() {
            return "CareItem{" +
                    "who='" + who + '\'' +
                    ", what='" + what + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        if (cares == null) {
            return "map is empty";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, CareItem> item : cares.entrySet()) {
            builder
                    .append(item.getKey())
                    .append(item.getValue())
                    .append("\n");
        }
        return builder.toString();
    }
}

