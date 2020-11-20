package com.yc.redevenlopes.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/19 14:31.
 */
public class UserAccreditInfo {

    public String nickname;

    public String face;

    public String city;

    public String iconUrl;

    public String gender;

    public String province;

    public String openid;

    public String accessToken;

    public String uid;

    @Override
    public String toString() {
        return "UserAccreditInfoNew{" +
                "nickname='" + nickname + '\'' +
                ", face='" + face + '\'' +
                ", city='" + city + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", province='" + province + '\'' +
                ", openid='" + openid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
