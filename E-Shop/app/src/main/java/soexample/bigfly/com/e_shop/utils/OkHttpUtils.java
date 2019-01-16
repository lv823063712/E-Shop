package soexample.bigfly.com.e_shop.utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtils {

    private final OkHttpClient okHttpClient;

    private OkHttpUtils() {
        this.okHttpClient = new OkHttpClient();
    }

    public static OkHttpUtils getInstance() {
        return ViewHolder.okHttp;
    }

    static class ViewHolder {
        private static final OkHttpUtils okHttp = new OkHttpUtils();
    }

    //同步
    public void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //get请求头入参
    public void getRequestHeader(String url, HashMap<String, String> map, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                builder.add(key, map.get(key));
            }
        }
        Request request = new Request.Builder().url(url)
                .addHeader("userId", map.get("userId"))
                .addHeader("sessionId", map.get("sessionId"))
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }


    //异步
    public void post(String url, HashMap<String, String> map, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                builder.add(key, map.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    //异步
    public void postPut(String url, HashMap<String, String> map, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                builder.add(key, map.get(key));
            }
        }
        Request request = new Request.Builder().url(url)
                .addHeader("userId", map.get("userId"))
                .addHeader("sessionId", map.get("sessionId"))
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    //put加载
    public void put(String url, Map<String, String> map, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url)
                .addHeader("userId", map.get("userId"))
                .addHeader("sessionId", map.get("sessionId"))
                .put(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
