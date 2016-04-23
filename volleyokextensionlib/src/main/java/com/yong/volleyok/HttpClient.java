package com.yong.volleyok;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.yong.volleyok.okhttp.OkHttp3Stack;
import com.yong.volleyok.request.ByteRequest;
import com.yong.volleyok.request.GZipRequest;
import com.yong.volleyok.request.GsonRequest;
import com.yong.volleyok.request.JsonArrayRequest;
import com.yong.volleyok.request.JsonObjectRequest;
import com.yong.volleyok.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;

/**
 * <b>Project:</b> com.yong.volleyok.http <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 请求的具体实现类 <br>
 */
public class HttpClient implements IHttpClient {

    private static HttpClient INSTANCE;
    private static final int[] sLock = new int[0];

    private final RequestQueue mRequestQueue;
    private final Context mContext;

    private HttpClient(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context,
                new OkHttp3Stack(new OkHttpClient()));
    }

    /**
     * 这里使用Application的Context
     *
     * @param context
     * @return
     */
    public static HttpClient getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (sLock) {
                if (null == INSTANCE) {
                    INSTANCE = new HttpClient(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 添加请求
     *
     * @param request
     */
    public void addRequest(Request request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public Request ByteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener) {
        return byteRequest(httpRequest, listener, null);
    }

    @Override
    public Request byteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener, Object tag) {
        ByteRequest request = new ByteRequest(httpRequest, listener);
        addRequest(request, tag);
        return request;
    }

    public Request stringRequest(HttpRequest httpRequest, HttpListener<String> listener) {
        return stringRequest(httpRequest, listener, null);
    }

    @Override
    public Request stringRequest(HttpRequest httpRequest, HttpListener<String> listener, Object tag) {
        StringRequest request = new StringRequest(httpRequest, listener);
        addRequest(request, tag);
        return request;
    }

    public Request gZipRequest(HttpRequest httpRequest, HttpListener<String> listener) {
        return gZipRequest(httpRequest, listener, null);
    }

    @Override
    public Request gZipRequest(HttpRequest httpRequest, HttpListener<String> listener, Object tag) {
        GZipRequest request = new GZipRequest(httpRequest, listener);
        addRequest(request, tag);
        return request;
    }

    public Request jsonObjectRequest(HttpRequest httpRequest, HttpListener<JSONObject> listener) {
        return jsonObjectRequest(null, httpRequest, listener);
    }

    public Request jsonObjectRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONObject> listener) {
        return jsonObjectRequest(requestBody, httpRequest, listener, null);
    }

    @Override
    public Request jsonObjectRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONObject> listener, Object tag) {
        JsonObjectRequest request = new JsonObjectRequest(requestBody, httpRequest, listener);
        addRequest(request, tag);
        return request;
    }

    public Request jsonArrayRequest(HttpRequest httpRequest, HttpListener<JSONArray> listener) {
        return jsonArrayRequest(httpRequest, listener, null);
    }

    public Request jsonArrayRequest(HttpRequest httpRequest, HttpListener<JSONArray> listener, Object tag) {
        return jsonArrayRequest(null, httpRequest, listener, tag);
    }

    @Override
    public Request jsonArrayRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONArray> listener, Object tag) {
        JsonArrayRequest request = new JsonArrayRequest(requestBody, httpRequest, listener);
        addRequest(request, tag);
        return request;
    }

    public <T> Request gsonRequest(Class<T> tClass, HttpRequest httpRequest, HttpListener<T> listener) {
        return gsonRequest(tClass, httpRequest, listener, null);
    }

    public <T> Request gsonRequest(Class<T> tClass, HttpRequest httpRequest, HttpListener<T> listener, Object tag) {
        return gsonRequest(tClass, null, httpRequest, listener, tag);
    }

    @Override
    public <T> Request gsonRequest(Class<T> tClass, TypeToken<T> typeToken,
                                   HttpRequest httpRequest, HttpListener<T> listener, Object tag) {
        GsonRequest<T> request = new GsonRequest<T>(tClass, typeToken, httpRequest, listener);
        addRequest(request, tag);
        return request;
    }
}
