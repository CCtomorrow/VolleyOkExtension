package com.yong.volleyok.request;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import java.io.UnsupportedEncodingException;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> JsonRequest <br>
 */
public abstract class JsonRequest<T> extends RequestWrapper<T> {

    /**
     * Default charset for JSON request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final String mRequestBody;

    public JsonRequest(HttpRequest httpRequest, HttpListener<T> listener) {
        this(null, httpRequest, listener);
    }

    public JsonRequest(String requestBody, HttpRequest httpRequest, HttpListener<T> listener) {
        super(httpRequest, listener);
        mRequestBody = requestBody;
    }

    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
