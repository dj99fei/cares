package com.dj99fei.cares;

import com.dj99fei.cares.util.DFA;

import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by chengfei on 2018/4/26.
 */

public class DFATest {
    @Test
    public void test() throws Exception {
        DFA dfa = DFA.getInstance("test");
        dfa.addWord("巩守强");
        Set<String> set = dfa.test("守强在群组[V5 选座交易管理沟通]: 飞下周还在吗？我请客，大家一起吃个饭");
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    public void testChecker() {
        Checker checker = new Checker(new CareConfig.CareItem("巩守强,付瑶", "@你,需求评审"));
        assertFalse(checker.testWho("强在群组[V5 选座交易管理沟通]: 飞下周还在吗？我请客，大家一起吃个饭"));
        assertTrue(checker.testWho("巩守强在群组[V5 选座交易管理沟通]: 飞下周还在吗？我请客，大家一起吃个饭"));
        assertTrue(checker.testWhat("守强在群组[V5 选座交易管理沟通]:@你 飞下周还在吗？我请客，大家一起吃个饭"));
        assertFalse(checker.testWhat("评"));
        assertTrue(checker.testWho("000--付瑶"));
    }
}
