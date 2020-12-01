package com.lq.lianjibusiness.base_libary.http;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lq.lianjibusiness.base_libary.App.Constants;
import com.lq.lianjibusiness.base_libary.BuildConfig;
import com.lq.lianjibusiness.base_libary.utils.Base64Utils;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.lq.lianjibusiness.base_libary.utils.MD5Utils;
import com.lq.lianjibusiness.base_libary.utils.NetWorkUtils;
import com.lq.lianjibusiness.base_libary.utils.PrefUtils;
import com.lq.lianjibusiness.base_libary.utils.RSAUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by ccc on 2020/9/15.
 * 描述：retrofit拦截
 */
public class MyInterceptor implements Interceptor {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=UTF-8");
    public static final String appKey = "test";
    public static final String secret = "123456";
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl url = request.url();
        String params = url.encodedQuery();
        String token = PrefUtils.getString(Constants.SP_TOKEN, "");
     /*   if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            requestBuilder.addHeader("Connection", "close");
        }*/

        //获取请求方法
        String method = request.method();
        try {
            String json = "";
            Gson gson = new Gson();
            if (method.equals("POST")) {
                //post请求的封装
                Map<String, Object> map = new HashMap<>();
                map.putAll(setDefaultParams());


                int status = 0;
                if (request.body() instanceof FormBody) {
                    status = 0;

                    FormBody oldFormBody = (FormBody) request.body();
                    for (int i = 0; i < oldFormBody.size(); i++) {
                        map.put(oldFormBody.name(i), oldFormBody.value(i));
                    }

                    json = gson.toJson(map);
                } else if (request.body() instanceof MultipartBody) {
                    status = 1;
                    MultipartBody multipartBody = (MultipartBody) request.body();
//                    String body = null;
//                    if (multipartBody != null) {
//                        Buffer buffer = new Buffer();
//                        multipartBody.writeTo(buffer);
//
//                        Charset charset = UTF8;
//                        MediaType contentType = multipartBody.contentType();
//                        if (contentType != null) {
//                            charset = contentType.charset(UTF8);
//                        }
//                        if (charset != null) {
//                            body = buffer.readString(charset);
//                        }
//                    }
//                    Log.d("ccc", "-----------intercept: "+body);
                    requestBuilder.url(LjHost.HOST + url.toString().split(LjHost.HOST)[1]);
                    requestBuilder.method(request.method(), multipartBody);
                    request = requestBuilder.build();
                } else {
                    status = 0;
                    RequestBody requestBody = request.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset();
                    }
                    json = buffer.readString(charset);
                }

                if (TextUtils.isEmpty(json)) {
                    json = gson.toJson(map);
                }

                if (status == 1) {

                } else {
                    Logger.e("加密之前的参数--->" + json + "--methor:" + url.toString().split(LjHost.HOST)[1]);
//                    if (BuildConfig.DEBUG) {
//                        Logger.e("加密之前的参数--->" + json.toString());
//                    }
                    byte[] strbytes = RSAUtils.encryptByPublicKey(json.toString().getBytes());
                    if (BuildConfig.DEBUG) {
                        Logger.e("加密之后的数据--->" + "----" + Base64Utils.encode(strbytes));
                    }
                    requestBuilder.url(LjHost.HOST + url.toString().split(LjHost.HOST)[1]);
                    RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Base64Utils.encode(strbytes));
                    requestBuilder.method(request.method(), body);
                    request = requestBuilder.build();
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(request);
    }

    /**
     * 构建签名
     *
     * @param paramsMap 参数
     * @param secret    密钥
     * @return
     * @throws IOException
     */
    public static String buildSign(Map<String, ?> paramsMap, String secret) throws IOException {
        Set<String> keySet = paramsMap.keySet();
        List<String> paramNames = new ArrayList<String>(keySet);

        Collections.sort(paramNames);

        StringBuilder paramNameValue = new StringBuilder();

        for (String paramName : paramNames) {
            paramNameValue.append(paramName).append(paramsMap.get(paramName));
        }

        String source = secret + paramNameValue.toString() + secret;

        return MD5Utils.md5(source);
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private Map<String, String> setDefaultParams() {
        Map<String, String> params = new HashMap<>();
        params.put("version_num", DeviceUtils.getVersionName());
        params.put("version_code", DeviceUtils.getVersionCode() + "");
        params.put("deviceid", DeviceUtils.getAndroidID());
        params.put("android_version", DeviceUtils.getAndroidSDKVersion() + "");
        return params;
    }
}
