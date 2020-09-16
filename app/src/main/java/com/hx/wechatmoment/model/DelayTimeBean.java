package com.hx.wechatmoment.model;

public class DelayTimeBean {
    private int state;
    private Object data;

    public DelayTimeBean() {}
    public DelayTimeBean(int state, Object data) {
        this.state = state;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
