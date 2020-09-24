package com.hx.wechatmoment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class ImagesBean implements Parcelable {
    /**
     * url : https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg
     */

    private String url;

    protected ImagesBean(Parcel in) {
        url = in.readString();
    }

    public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
        @Override
        public ImagesBean createFromParcel(Parcel in) {
            return new ImagesBean(in);
        }

        @Override
        public ImagesBean[] newArray(int size) {
            return new ImagesBean[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }
}
