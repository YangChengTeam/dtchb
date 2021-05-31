package com.yc.redkingguess.service.event;

/**
 * Created by suns  on 2020/10/14 10:57.
 */
public class LoginState {
    public static final int LOGIN = 1;//登录成功
    public static final int LOGIN_OUT = 2;//退出登录

    public static final int UPDATE_INFO = 3;//更新资料

    private int state;

    public LoginState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
