package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> Gson Request <br>
 */
public class GsonRequest<T> extends RequestWrapper<T> {

    private static Gson mGson = new Gson();
    private Class<T> mClass;
    private TypeToken<T> mTypeToken;

    public GsonRequest(Class<T> tClass, HttpRequest httpRequest, HttpListener<T> listener) {
        super(httpRequest, listener);
        mClass = tClass;
    }

    public GsonRequest(TypeToken<T> typeToken,
                       HttpRequest httpRequest, HttpListener<T> listener) {
        super(httpRequest, listener);
        mTypeToken = typeToken;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String result = getResponseString(response);
        if (result.equals(PARSEERROR)) {
            return Response.error(new ParseError());
        }
        if (mTypeToken == null) {
            return Response.success(mGson.fromJson(result, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return (Response<T>) Response.success(mGson.fromJson(result, mTypeToken.getType()), HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
