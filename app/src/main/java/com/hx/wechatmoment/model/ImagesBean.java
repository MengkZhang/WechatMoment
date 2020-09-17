package com.hx.wechatmoment.model;

import java.io.Serializable;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class ImagesBean implements Serializable {
    /**
     * url : https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg
     */

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
