package com.hx.wechatmoment.bean;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class UserInfoBean extends BaseResVo {
    /**
     * profile-image : https://thoughtworks-mobile-2018.herokuapp.com/images/user/profile-image.jpg
     * avatar : https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar.png
     * nick : Huan Huan
     * username : hengzeng
     */

    @SerializedName("profile-image")
    private String profileimage;
    private String avatar;
    private String nick;
    private String username;

    protected UserInfoBean(Parcel in) {
        super(in);
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
