package com.orcs.boomshakalaka.volley.toolbox;

import java.io.OutputStream;

/**
 * Created by huihui on 2016/3/20.
 */
public interface Mutipart {
    /**
     * 分隔线
     */
    public final static String BOUNDARY = "-----------------------------7db372eb000e2";
    public void handRequest(OutputStream out);
}
