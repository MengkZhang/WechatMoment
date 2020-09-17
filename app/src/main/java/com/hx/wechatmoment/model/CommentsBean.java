package com.hx.wechatmoment.model;

import java.io.Serializable;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class CommentsBean implements Serializable {
    /**
     * content : Good.
     * sender : {"username":"leihuang","nick":"Lei Huang","avatar":"https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/002.jpeg"}
     */

    private String content;
    private SenderBean sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }
}
