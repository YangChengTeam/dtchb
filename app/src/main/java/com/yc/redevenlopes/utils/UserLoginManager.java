package com.yc.redevenlopes.utils;

import android.app.Activity;
import android.util.Log;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.redevenlopes.dialog.LoadDialog;
import com.yc.redevenlopes.homeModule.module.bean.UserAccreditInfo;
import com.yc.redevenlopes.listener.ThirdLoginListener;

import java.util.Map;

/**
 * Created by suns  on 2020/11/19 14:08.
 */
public class UserLoginManager {

    private static UserLoginManager instance;

    private Activity mActivity = null;
    private ThirdLoginListener mLoginListener = null;
    private LoadDialog loadingView = null;

    private UserLoginManager() {
    }

    public static UserLoginManager getInstance() {
        synchronized (UserLoginManager.class) {
            if (instance == null) {
                instance = new UserLoginManager();
            }
        }
        return instance;
    }

    public UserLoginManager setCallBack(Activity activity, ThirdLoginListener loginListener) {
        this.mActivity = activity;
        this.mLoginListener = loginListener;
        this.loadingView = new LoadDialog(activity);
        return this;
    }


    /**
     * 授权并登陆
     *
     * @param shareMedia
     */
    public void oauthAndLogin(SHARE_MEDIA shareMedia) {
        boolean isAuth = UMShareAPI.get(mActivity).isAuthorize(mActivity, shareMedia);
        if (isAuth) {
            UMShareAPI.get(mActivity).getPlatformInfo(mActivity, shareMedia, umAuthListener);
        } else {
            UMShareAPI.get(mActivity).doOauthVerify(mActivity, shareMedia, umAuthListener);
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showProgressDialog("正在登录中，请稍候...");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> data) {
            try {
                Log.e("TAG", "action:  " + action);
                switch (action) {

                    case UMAuthListener.ACTION_AUTHORIZE: {
                        oauthAndLogin(share_media);
                        break;
                    }
                    case UMAuthListener.ACTION_GET_PROFILE: {
                        if (!data.isEmpty()) {
                            Log.e("TAG", "onComplete: $data");
                            UserAccreditInfo userDataInfo = new UserAccreditInfo();
                            userDataInfo.nickname = data.get("name");
                            userDataInfo.face=data.get("profile_image_url");
                            userDataInfo.city = data.get("city");
                            userDataInfo.iconUrl = data.get("iconurl");
                            userDataInfo.gender = data.get("gender");
                            userDataInfo.province = data.get("province");
                            userDataInfo.openid = data.get("openid");
                            userDataInfo.accessToken = data.get("accessToken");
                            userDataInfo.uid = data.get("uid");
                            if (mLoginListener != null) {
                                mLoginListener.onLoginResult(userDataInfo);
                            }

                        } else {
                            ToastUtil.showToast("登录失败，请重试!");
                        }
                        closeProgressDialog();
                    }
                }
            } catch (Exception e) {

                closeProgressDialog();
                ToastUtil.showToast("登录失败，请重试!");
                deleteOauth(share_media);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            closeProgressDialog();

            ToastUtil.showToast("登录失败,请重试！");
            deleteOauth(share_media);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("登录取消");
            closeProgressDialog();
        }

    };

    /**
     * 取消授权
     *
     * @param shareMedia
     */
    private void deleteOauth(SHARE_MEDIA shareMedia) {
        if (null == mActivity) {
            return;
        }
        UMShareAPI.get(mActivity).deleteOauth(mActivity, shareMedia, null);
    }

    /**
     * 显示进度框
     *
     * @param message
     */
    private void showProgressDialog(String message) {
        if (mActivity != null && !mActivity.isFinishing()) {

            if (null == loadingView) {
                loadingView = new LoadDialog(mActivity);
            }
            loadingView.setText(message);
            loadingView.show();

        }

    }

    /**
     * 关闭进度框
     */
    private void closeProgressDialog() {
        try {
            if (mActivity != null && !mActivity.isFinishing()) {

                if (loadingView != null && loadingView.isShowing()) {
                    loadingView.dismiss();
                    loadingView = null;
                }


            }

        } catch (Exception e) {
//            LogUtil.msg("close dialog error->>" + e.message)
        }
    }
}
