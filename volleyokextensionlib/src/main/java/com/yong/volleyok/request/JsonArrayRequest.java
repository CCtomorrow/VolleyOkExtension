package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> JsonArrayRequest <br>
 */
public class JsonArrayRequest extends RequestWrapper<JSONArray> {

    public JsonArrayRequest(HttpRequest httpRequest, HttpListener<JSONArray> listener) {
        super(httpRequest, listener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        String result = getResponseString(response);
        if (result.equals(PARSEERROR)) {
            return Response.error(new ParseError());
        }
        try {
            return Response.success(new JSONArray(result), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
