package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> String Request <br>
 */
public class StringRequest extends RequestWrapper<String> {

    public StringRequest(HttpRequest httpRequest, HttpListener<String> listener) {
        super(httpRequest, listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String result = getResponseString(response);
        if (result.equals(PARSEERROR)) {
            return Response.error(new ParseError());
        }
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }
}
