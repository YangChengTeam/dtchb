package com.lq.lianjibusiness.base_libary.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lq.lianjibusiness.base_libary.App.Constants;

import com.lq.lianjibusiness.base_libary.utils.IntegerDefault0Adapter;
import com.lq.lianjibusiness.base_libary.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 配置okhttpClinet
 */
public class RetrofitHelper {

    private static RetrofitHelper helper;

    private Retrofit mRetrofit;
    private OkHttpClient client;

    public static RetrofitHelper getInstance() {
        if (helper == null) {
            synchronized (RetrofitHelper.class) {
                helper = new RetrofitHelper();
            }
        }
        return helper;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
        String pathCache = Constants.PATH_CACHE;
        File cacheFile = new File(pathCache);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);

                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 60;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.addInterceptor(new MyInterceptor());
        builder.addInterceptor(new LoggingInterceptor());
        //builder.hostnameVerifier(hostnameVerifier);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(7, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(LjHost.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createApis(Class<T> service) {
        return mRetrofit.create(service);
    }

    public <T> T createApis(Class<T> service, String host) {
        return new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(service);
    }

    private static  Gson gson;
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(String.class, new IntegerDefault0Adapter())
                    .create();
        }
        return gson;
    }
}
