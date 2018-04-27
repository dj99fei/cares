package com.dj99fei.cares;

import com.dj99fei.cares.util.DFA;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.dj99fei.cares.CareConfig.DIVIDER;

/**
 * Created by chengfei on 2018/4/26.
 */

public class Checker {

    static Map<String, Checker> checkers = new HashMap<>();


    public static Checker getInstance(String key) {
        return checkers.get(key);
    }

    public static void init(CareConfig config) {
        if (config == null || config.cares == null) {
            return;
        }

        for (Map.Entry<String, CareConfig.CareItem> entry : config.cares.entrySet()) {
            Checker checker = new Checker(entry.getValue());
            checkers.put(entry.getKey(), checker);
        }
    }
    DFA who;
    DFA what;

    public Checker(CareConfig.CareItem config) {
        what = DFA.getInstance("what");
        who = DFA.getInstance("who");
        String[] who = config.who.split(DIVIDER);
        if (who != null && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                this.who.addWord(who[i]);
            }
        }

        what = DFA.getInstance("what");
        String[] what = config.what.split(DIVIDER);
        if (what != null && what.length > 0) {
            for (int i = 0; i < what.length; i++) {
                this.what.addWord(what[i]);
            }
        }
    }


    public boolean test(DFA which, String... content) {
        if (content == null || content.length == 0) {
            return false;
        }

        for (String s : content) {
            Set<String> matchPart = which.test(s);
            if (matchPart.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean testWho(String... content) {
        return test(who, content);
    }

    public boolean testWhat(String... content) {
        return test(what, content);
    }

}
