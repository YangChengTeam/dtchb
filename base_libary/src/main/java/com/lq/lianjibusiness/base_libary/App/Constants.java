package com.lq.lianjibusiness.base_libary.App;

import java.io.File;

/**
 * Created by ccc on 2020/9/15.
 */

public interface Constants {


    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    String SP_NAME = "tourism";

    /**
     * token
     */
    String SP_TOKEN = "token";

    /**
     * 手机号码
     */
    String SP_PHONE = "phoneNum";


    /**
     * 账号
     */
    String SP_ACCOUNT = "account";

    /**
     * 名称
     */
    String SP_REALNAME = "realName";

    /**
     * 是否第一次运行
     */
    String SP_ISFIRSTRUN = "isFirstRun";

    /**
     * 状态（0：启用，1：禁用）
     */
    String SP_STATUS= "status";

    /**
     * 状态（0：启用，1：禁用）
     */
    String SP_STOREID= "storeId";

    /**
     * 头像
     */
    String SP_HEADPORTRAIT= "headPortrait";

    /**
     * ID
     */
    String SP_ID= "id";

}
