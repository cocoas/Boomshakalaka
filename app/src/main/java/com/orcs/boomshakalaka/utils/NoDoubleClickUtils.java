package com.orcs.boomshakalaka.utils;

/**
 * Created by Administrator on 2016/5/11.
 * 防止重复点击的工具类
 */
public class NoDoubleClickUtils {
    private static long lastClickTime;
    private final static int SPACE_TIME = 500;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = currentTime - lastClickTime <= SPACE_TIME;
        lastClickTime = currentTime;
        return isClick2;
    }
}
