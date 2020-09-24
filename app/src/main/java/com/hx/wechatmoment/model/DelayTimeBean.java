package com.hx.wechatmoment.model;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
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

}
