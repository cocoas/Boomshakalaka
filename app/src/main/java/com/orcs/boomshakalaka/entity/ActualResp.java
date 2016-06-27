package com.orcs.boomshakalaka.entity;

/**
 * Created by Administrator on 2016/6/8.
 * 用来测试可扩展listview的实体类
 */
public class ActualResp {

    private String name;
    private String content;

    public ActualResp(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
