package com.hx.wechatmoment.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class SenderBean {
    /**
     * username : cyao
     * nick : Cheng Yao
     * avatar : https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/001.jpeg
     */

    private String username;
    private String nick;
    private String avatar;
    /**
     * 是否有回复
     */
    private boolean hasReply;

    public String getUsername() {
        if (!TextUtils.isEmpty(username)) {
            return username;
        }
        return "errorName = 嗨学";
    }

    public boolean isHasReply() {
        return hasReply;
    }

    public void setHasReply(boolean hasReply) {
        this.hasReply = hasReply;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        if (!TextUtils.isEmpty(nick)) {
            return nick;
        }
        return "嗨学昵称";
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
