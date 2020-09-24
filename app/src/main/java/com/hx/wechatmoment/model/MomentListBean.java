package com.hx.wechatmoment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentListBean extends BaseResVo implements Serializable {
    /**
     * content : 沙发！
     * images : [{"url":"https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"},{"url":"https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/002.jpeg"},{"url":"https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/003.jpeg"}]
     * sender : {"username":"cyao","nick":"Cheng Yao","avatar":"https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/001.jpeg"}
     * comments : [{"content":"Good.","sender":{"username":"leihuang","nick":"Lei Huang","avatar":"https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/002.jpeg"}},{"content":"Like it too","sender":{"username":"weidong","nick":"WeiDong Gu","avatar":"https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/003.jpeg"}}]
     * error : losted
     * unknown error : STARCRAFT2
     */

    private String content;
    private SenderBean sender;
    @SerializedName(value = "error", alternate = "unknown error")
    private String error;
    private List<ImagesBean> images;
    private List<CommentsBean> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderBean getSender() {
        if (sender != null) {
            return sender;
        } else {
            setContent("error = " + getError());
            return new SenderBean();
        }
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }
}
