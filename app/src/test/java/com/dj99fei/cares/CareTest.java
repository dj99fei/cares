package com.dj99fei.cares;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by chengfei on 2018/4/25.
 */

public class CareTest {

    @Test
    public void testMatchWho() {
        CaresHolder.CareItem item = new CaresHolder.CareItem("程飞|熊娜|巩守强|选座交易管理沟通", "@你|需求|飞哥");
        String tickerText = "巩守强在群组[V5 选座交易管理沟通]: 飞哥下周还在吗？我请客，大家一起吃个饭";
        assertTrue(item.matchWho(tickerText));
    }


    @Test
    public void testMatchWhat() {
        CaresHolder.CareItem item = new CaresHolder.CareItem("", "@你|需求|吃*饭");
        String tickerText = "巩守强在群组[V5 选座交易管理沟通]: 飞哥下周还在吗？我请客，大家一起吃个饭";
        assertTrue(item.matchWhat(tickerText));
    }
}
