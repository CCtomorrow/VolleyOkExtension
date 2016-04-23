package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> Byte Request <br>
 */
public class ByteRequest extends RequestWrapper<byte[]> {

    public ByteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener) {
        super(httpRequest, listener);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
