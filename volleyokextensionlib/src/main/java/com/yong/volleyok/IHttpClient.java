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
     * byte请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request byteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener, Object tag);

    /**
     * String请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request stringRequest(HttpRequest httpRequest, HttpListener<String> listener, Object tag);

    /**
     * gzip请求
     *
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request gZipRequest(HttpRequest httpRequest, HttpListener<String> listener, Object tag);

    /**
     * JsonObject请求
     *
     * @param requestBody
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request jsonObjectRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONObject> listener, Object tag);

    /**
     * JsonArray请求
     *
     * @param requestBody
     * @param httpRequest
     * @param listener
     * @param tag
     * @return
     */
    Request jsonArrayRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONArray> listener, Object tag);

    /**
     * Gson请求，可以映射Model
     *
     * @param tClass
     * @param typeToken
     * @param httpRequest
     * @param listener
     * @param tag
     * @param <T>
     * @return
     */
    <T> Request gsonRequest(Class<T> tClass, TypeToken<T> typeToken, HttpRequest httpRequest, HttpListener<T> listener, Object tag);
}
