package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> JsonObject Request <br>
 */
public class JsonObjectRequest extends JsonRequest<JSONObject> {

    public JsonObjectRequest(HttpRequest httpRequest, HttpListener<JSONObject> listener) {
        super(httpRequest, listener);
    }

    public JsonObjectRequest(String requestBody, HttpRequest httpRequest, HttpListener<JSONObject> listener) {
        super(requestBody, httpRequest, listener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
