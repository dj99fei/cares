package com.dj99fei.cares.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chengfei on 2018/4/26.
 */

public class DFA {

    private static Map<String, DFA> instances = new HashMap<>();

    private Map<String, Object> sensitiveMap = new HashMap<>();

    private DFA() {
    }

    public static DFA getInstance(String key) {
        if (instances.containsKey(key)) {
            return instances.get(key);
        }
        DFA dfa = new DFA();
        instances.put(key, dfa);
        return dfa;
    }

    public void addWord(String line) {
        Map<String, Object> nowMap = sensitiveMap;
        for(int i = 0; i < line.length(); i ++) {
            String c = Character.toString(line.charAt(i));
            if(nowMap.containsKey(c)) {
                nowMap = (HashMap<String, Object>)nowMap.get(c);
            }
            else {
                Map<String, Object> child= new HashMap<>();
                child.put("end", 0);
                nowMap.put(c, child);
                nowMap = (HashMap)nowMap.get(c);
            }
        }
        nowMap.put("end", 1);
    }

    public Set<String> test(String line) {
        Set<String> words= new HashSet<>();
        for (int i = 0; i < line.length(); i ++) {
            String key = Character.toString(line.charAt(i));
            if (sensitiveMap.containsKey(key)) {
                StringBuffer sb = new StringBuffer();
                Map<String, Object> nowMap = sensitiveMap;
                for (int j = i ; j < line.length(); j++) {
                    String c = Character.toString(line.charAt(j));
                    if(nowMap.containsKey(c)) {
                        sb.append(c);
                        nowMap = (HashMap<String, Object>) nowMap.get(c);
                        if (Integer.parseInt(nowMap.get("end").toString()) == 1) {
                            words.add(sb.toString());
                        }
                    }
                    else break;
                }
            }
        }

        return words;
    }
}
