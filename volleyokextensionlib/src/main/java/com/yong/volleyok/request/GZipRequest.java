package com.yong.volleyok.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * <b>Project:</b> com.yong.volleyok.request <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> GZip Request <br>
 */
public class GZipRequest extends RequestWrapper<String> {

    public GZipRequest(HttpRequest httpRequest, HttpListener<String> listener) {
        super(httpRequest, listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String output = "";
        try {
            GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
            InputStreamReader reader = new InputStreamReader(gStream);
            BufferedReader in = new BufferedReader(reader);
            String read;
            while ((read = in.readLine()) != null) {
                output += read;
            }
            in.close();
            reader.close();
            gStream.close();
        } catch (IOException e) {
            return Response.error(new ParseError());
        }
        return Response.success(output, HttpHeaderParser.parseCacheHeaders(response));
    }
}
