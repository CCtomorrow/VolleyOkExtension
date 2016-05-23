package com.yong.volleyok;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <b>Project:</b> com.yong.volleyok <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 请求 <br>
 */
public interface IHttpClient {

    /**
     * String请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request request(HttpRequest httpRequest, final HttpListener<String> listener, Object tag);

    /**
     * byte请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request byteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener, Object tag);

    /**
     * JsonObject请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request jsonObjectRequest(HttpRequest httpRequest, HttpListener<JSONObject> listener, Object tag);

    /**
     * JsonArray请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request jsonArrayRequest(HttpRequest httpRequest, HttpListener<JSONArray> listener, Object tag);

    /**
     * Gson请求，可以映射Model
     *
     * @param tClass      映射的Model
     * @param httpRequest
     * @param listener
     * @param tag
     * @param <T>
     * @return
     */
    <T> Request gsonRequest(Class<T> tClass, HttpRequest httpRequest, HttpListener<T> listener, Object tag);

    /**
     * Gson请求，可以映射Model
     *
     * @param typeToken   例如List<Model>
     * @param httpRequest
     * @param listener
     * @param tag
     * @param <T>
     * @return
     */
    <T> Request gsonRequest(TypeToken<T> typeToken, HttpRequest httpRequest, HttpListener<T> listener, Object tag);

    /**
     * 取消请求
     *
     * @param tag
     */
    void cancelRequest(Object tag);
}
