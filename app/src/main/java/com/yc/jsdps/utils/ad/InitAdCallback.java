package com.yc.jsdps.utils.ad;

import com.qq.e.comm.util.AdError;

public interface InitAdCallback {
    void onSuccess();

    void onFailure(AdError adError);
}
