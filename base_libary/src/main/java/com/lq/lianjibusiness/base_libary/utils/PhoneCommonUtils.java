package com.lq.lianjibusiness.base_libary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import com.lq.lianjibusiness.base_libary.App.App;
import com.orhanobut.logger.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about phone
 * </pre>
 */
public final class PhoneCommonUtils {

    private PhoneCommonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the device is phone.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPhone() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * Return the unique device id.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the unique device id
     */


    /**
     * Return the IMEI.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the IMEI
     */
    public static String getIMEI() {
        try {
            return getImeiOrMeid(true);
        } catch (SecurityException e) {
            return "";
        }
    }

    public static String getIMEI2() {
        try {
            return getImeiOrMeid2(true);
        } catch (SecurityException e) {
            return "";
        }
    }

    /**
     * Return the MEID.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the MEID
     */
    public static String getMEID() {
        try {
            return getImeiOrMeid(false);
        } catch (SecurityException e) {
            return "";
        }
    }

    @SuppressLint("HardwareIds")
    public static String getImeiOrMeid(boolean isImei) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            TelephonyManager tm = getTelephonyManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (isImei) {
                    //返回第一个imei
                    //return getMinOne(tm.getImei(0), tm.getImei(1));
                    return TextUtils.isEmpty(tm.getImei(0)) ? tm.getImei(1) : tm.getImei(0);
                } else {
                    Log.d("ccc", "---44-getImeiOrMeid: ");
                    return TextUtils.isEmpty(tm.getImei(0)) ? tm.getImei(1) : tm.getImei(0);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d("ccc", "---33-getImeiOrMeid: ");
                String ids = getSystemPropertyByReflect(isImei ? "ril.gsm.imei" : "ril.cdma.meid");
                if (!TextUtils.isEmpty(ids)) {
                    String[] idArr = ids.split(",");
                    if (idArr.length == 2) {
                        //return getMinOne(idArr[0], idArr[1]);
                        return TextUtils.isEmpty(idArr[0]) ? idArr[1] : idArr[0];
                    } else {
                        return idArr[0];
                    }
                }

                String id0 = tm.getDeviceId();
                String id1 = "";
                try {
                    Method method = tm.getClass().getMethod("getDeviceId", int.class);
                    id1 = (String) method.invoke(tm,
                            isImei ? TelephonyManager.PHONE_TYPE_GSM
                                    : TelephonyManager.PHONE_TYPE_CDMA);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (isImei) {
                    if (id0 != null && id0.length() < 15) {
                        id0 = "";
                    }
                    if (id1 != null && id1.length() < 15) {
                        id1 = "";
                    }
                } else {
                    if (id0 != null && id0.length() == 14) {
                        id0 = "";
                    }
                    if (id1 != null && id1.length() == 14) {
                        id1 = "";
                    }
                }
                return getMinOne(id0, id1);
            } else {
                String deviceId = tm.getDeviceId();
                if (isImei) {
                    if (deviceId != null && deviceId.length() >= 15) {
                        return deviceId;
                    }
                } else {
                    if (deviceId != null && deviceId.length() == 14) {
                        return deviceId;
                    }
                }
            }
        } catch (SecurityException e) {
            Logger.i(e.getMessage());
        }
        return "";
    }

    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getImeiOrMeid2(boolean isImei) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        TelephonyManager tm = getTelephonyManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isImei) {
                return getMinOne(tm.getImei(0), tm.getImei(1));
            } else {
                return getMinOne(tm.getMeid(0), tm.getMeid(1));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String ids = getSystemPropertyByReflect(isImei ? "ril.gsm.imei" : "ril.cdma.meid");
            if (!TextUtils.isEmpty(ids)) {
                String[] idArr = ids.split(",");
                if (idArr.length == 2) {
                    return getMinOne(idArr[0], idArr[1]);
                } else {
                    return idArr[0];
                }
            }

            String id0 = tm.getDeviceId();
            String id1 = "";
            try {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                id1 = (String) method.invoke(tm,
                        isImei ? TelephonyManager.PHONE_TYPE_GSM
                                : TelephonyManager.PHONE_TYPE_CDMA);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (isImei) {
                if (id0 != null && id0.length() < 15) {
                    id0 = "";
                }
                if (id1 != null && id1.length() < 15) {
                    id1 = "";
                }
            } else {
                if (id0 != null && id0.length() == 14) {
                    id0 = "";
                }
                if (id1 != null && id1.length() == 14) {
                    id1 = "";
                }
            }
            return getMinOne(id0, id1);
        } else {
            String deviceId = tm.getDeviceId();
            if (isImei) {
                if (deviceId != null && deviceId.length() >= 15) {
                    return deviceId;
                }
            } else {
                if (deviceId != null && deviceId.length() == 14) {
                    return deviceId;
                }
            }
        }
        return "";
    }


    private static String getMinOne(String s0, String s1) {
        boolean empty0 = TextUtils.isEmpty(s0);
        boolean empty1 = TextUtils.isEmpty(s1);
        if (empty0 && empty1) return "";
        if (!empty0 && !empty1) {
            if (s0.compareTo(s1) <= 0) {
                return s0;
            } else {
                return s1;
            }
        }
        if (!empty0) return s0;
        return s1;
    }

    private static String getSystemPropertyByReflect(String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Exception e) {/**/}
        return "";
    }

    private static TelephonyManager getTelephonyManager() {
        TelephonyManager tm = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return tm;
    }
}
