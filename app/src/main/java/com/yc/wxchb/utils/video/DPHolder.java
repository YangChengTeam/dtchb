package com.yc.wxchb.utils.video;

import android.app.Application;
import android.util.Log;

import com.bytedance.sdk.dp.DPSdk;
import com.bytedance.sdk.dp.DPSdkConfig;
import com.bytedance.sdk.dp.DPUser;
import com.bytedance.sdk.dp.DPWidgetBannerParams;
import com.bytedance.sdk.dp.DPWidgetBubbleParams;
import com.bytedance.sdk.dp.DPWidgetDrawParams;
import com.bytedance.sdk.dp.DPWidgetGridParams;
import com.bytedance.sdk.dp.DPWidgetInnerPushParams;
import com.bytedance.sdk.dp.DPWidgetNewsParams;
import com.bytedance.sdk.dp.DPWidgetTextChainParams;
import com.bytedance.sdk.dp.DPWidgetVideoCardParams;
import com.bytedance.sdk.dp.DPWidgetVideoSingleCardParams;
import com.bytedance.sdk.dp.IDPWidget;
import com.bytedance.sdk.dp.IDPWidgetFactory;

import org.json.JSONObject;

/**
 * Create by hanweiwei on 2020-03-26.
 */
public final class DPHolder {
    private static volatile DPHolder sInstance;

    public static DPHolder getInstance() {
        if (sInstance == null) {
            synchronized (DPHolder.class) {
                if (sInstance == null) {
                    sInstance = new DPHolder();
                }
            }
        }
        return sInstance;
    }

    private DPHolder() {
    }

    public void init(Application context) {

        //1. 初始化，最好放到application.onCreate()执行
        //2. 【重要】如果needInitAppLog=false，请确保AppLog初始化一定要在合作sdk初始化前
        final DPSdkConfig config = new DPSdkConfig.Builder()
                .debug(true)
                .needInitAppLog(false)
                .initListener(new DPSdkConfig.InitListener() {
                    @Override
                    public void onInitComplete(boolean isSuccess) {
                        //注意：1如果您的初始化没有放到application，请确保使用时初始化已经成功
                        //     2如果您的初始化在application
                        //isSuccess=true表示初始化成功
                        //初始化失败，可以再次调用初始化接口（建议最多不要超过3次)

                        Log.e("DPHolder", "init result=" + isSuccess);
                    }
                })

//                //接入了红包功能需要传入的参数，没有接入的话可以忽略该配置
//                .luckConfig(new DPSdkConfig.LuckConfig()
//                        .application(context)
//                        .licenseStr("Zre23q+ugBYuAhUA9t7cvzlRXgvrb2rm9jMuQGbXL+n4FHJZj9c2I4WX7xeEG56e29+eCrq77ugnvuLkkS3340GhDVhagMQqLnefsGNKyHIY2k1g9Qpy1J0joc8eOvG0CCo+29LwqmRp5RYVZ7uki99/i3pMCeUiElgqgJwurNS6dSQ90oZkkjpNp9k9dfP0D8HZMGpNkGTVjwknS1NqQLYAnfUbGMJK+WOuk1ZFi2AYIEWBcKxaJnyyvMkq3OgezDMOcjXcbrjQzKWS8vRz9u0yIEvYrhaepa+KWTtR9TB3iO41")
//                        .luckKey("WuzlrGM95YOGlCmKm32l6youdsG/zE2y")
//                        .adCodeIdBox("946062125")
//                        .adCodeIdMoney("946062126")
//                        .adCodeIdSignIn("946062128")
//                        .loginCallback(new DPSdkConfig.LuckConfig.ILoginCallback() {
//                            @Override
//                            public void onLogin(Context context) {
//                                //注意：登录成功后需要调用接口刷新用户信息
//                                long uid = LoginActivity.getUserId();
//                                if (uid == 0) {
//                                    LoginActivity.login(context);
//                                } else {
//                                    notifyUserInfo();
//                                }
//                            }
//                        }))
                .build();
        //直播参数

        DPSdkConfig.LiveConfig liveConfig = null;
        try {
            liveConfig = Reflector.on("com.bytedance.dpdemo_live.LiveBridge").method("getLiveConfig").call();
        } catch (Reflector.DPReflectedException e) {
            e.printStackTrace();
        }

        if (DebugInfo.Bridge.onlylive() && liveConfig != null) {
            liveConfig.onlyLive();
        }
        config.setLiveConfig(liveConfig);

        DPSdk.init(context, "SDK_Setting_5059538.json", config);
    }

    public IDPWidget buildDrawWidget(DPWidgetDrawParams params) {
        //创建draw视频流组件
        return getFactory().createDraw(params);
    }

    public IDPWidget buildGridWidget(DPWidgetGridParams params) {
        //创建宫格组件
        return getFactory().createGrid(params);
    }

    public IDPWidget buildDoubleFeedWidget(DPWidgetGridParams params) {
        //创建双Feed组件
        return getFactory().createDoubleFeed(params);
    }

    public IDPWidget buildNewsTabsWidget(DPWidgetNewsParams params) {
        //创建多频道新闻组件
        return getFactory().createNewsTabs(params);
    }

    public IDPWidget buildNewsOneTabWidget(DPWidgetNewsParams params) {
        //创建单列表新闻组件
        return getFactory().createNewsOneTab(params);
    }

    public void loadVideoCard(DPWidgetVideoCardParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadVideoCard(params, callback);
    }

    public void loadSmallVideoCard(DPWidgetVideoCardParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadSmallVideoCard(params, callback);
    }

    public void loadCustomVideoCard(DPWidgetVideoCardParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadCustomVideoCard(params, callback);
    }

    public void loadVideoSingleCard(DPWidgetVideoSingleCardParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadVideoSingleCard(params, callback);
    }

    public void loadVideoSingleCard4News(DPWidgetVideoSingleCardParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadVideoSingleCard4News(params, callback);
    }

    public void loadTextChain(DPWidgetTextChainParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadTextChain(params, callback);
    }

    public void loadBubble(DPWidgetBubbleParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadBubble(params, callback);
    }

    public void loadBanner(DPWidgetBannerParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadBanner(params, callback);
    }

    public void loadInnerPush(DPWidgetInnerPushParams params, IDPWidgetFactory.Callback callback) {
        getFactory().loadInnerPush(params, callback);
    }

    public void enterNewsDetail(DPWidgetNewsParams params, long groupId, String data) {
        getFactory().enterNewsDetail(params, groupId, data);
    }

    public void loadPush(DPWidgetNewsParams params) {
        getFactory().pushNews(params);
    }

    public void uploadLog(String category, String event, JSONObject json) {
        getFactory().uploadLog(category, event, json);
    }

    public void notifyUserInfo() {
        //接入红包功能的开发者，在用户登录成功后需要刷新用户信息
        long uid = 1;
        if (uid == 0) {
            return;
        }
        getFactory().notifyUserInfo(new DPUser()
                .setUserId(uid)//必须透传用户uid
                .setName("test_name")//用户昵称，可选
                .setAvatarUrl("xxx")//用户图像，可选
        );
    }

    private IDPWidgetFactory getFactory() {
        //一定要初始化后才能调用，否则会发生异常问题
        return DPSdk.factory();
    }

}
