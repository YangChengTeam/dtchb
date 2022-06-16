package com.yc.jsdsp.utils.video;


/**
 * Create by hanweiwei on 2021/4/8.
 */
public class DebugInfo {

    /**
     * 反射拿到外部的调试信息
     */
    public static class Bridge {
        private static final String PKG = "com.bytedance.sdk.dp.DPDebugTools";

        public static boolean onlylive() {
            try {
                return Reflector.on(PKG)
                        .field("sOnlylive")
                        .get();
            } catch (Throwable ignore) {
            }
            return false;
        }
    }

}
