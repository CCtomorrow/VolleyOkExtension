package com.yong.volleyok.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * <b>Project:</b> com.yong.volleyok <br>
 * <b>Create Date:</b> 2016/4/22 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 原始请求的包装 <br>
 * 封装之后，子类只需要实现{@link #parseNetworkResponse(NetworkResponse)}即可，
 * 所有的扩展请求必须继承这个类{@link com.yong.volleyok.request.RequestWrapper}，
 * 不需要去继承{@link com.android.volley.Request}
 */
public abstract class RequestWrapper<T> extends com.android.volley.Request<T> {

    protected static final String PARSEERROR = "ParseError";

    /**
     * 请求
     */
    protected HttpRequest mHttpRequest;

    /**
     * 结果
     */
    protected HttpListener<T> mHttpListener;

    public RequestWrapper(HttpRequest httpRequest, HttpListener<T> listener) {
        // 这里不需要错误的监听，下面已经做了处理
        super(httpRequest.getMethod(), httpRequest.getUrl(), null);
        this.mHttpRequest = httpRequest;
        this.mHttpListener = listener;
        setRetryPolicy(getRetryPolicy());
    }

    /**
     * 得到url，这里get方法作处理，把参数都拼接上去
     *
     * @return 请求地址，如果是get请求返回完整地址
     */
    @Override
    public String getUrl() {
        // 当get的时候做处理，把参数都连接起来
        try {
            if (getMethod() == Method.GET && (getParams() != null && getParams().size() > 0)) {
                String params = getUrlParams();
                StringBuilder extra = new StringBuilder();
                if (!TextUtils.isEmpty(params)) {
                    if (!mHttpRequest.getUrl().endsWith("?")) {
                        extra.append("?");
                    }
                    extra.append(params);
                }
                return mHttpRequest.getUrl() + extra.toString();
            }
        } catch (AuthFailureError e) {
        }
        return mHttpRequest.getUrl();

    }

    /**
     * 拼接get请求的参数的拼接
     *
     * @return
     * @throws AuthFailureError
     */
    public String getUrlParams() throws AuthFailureError {
        StringBuilder resultParams = new StringBuilder();
        Map<String, String> params = getParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (null == entry.getValue()) {
                continue;
            }
            resultParams.append(entry.getKey());
            resultParams.append('=');
            resultParams.append(entry.getValue());
            resultParams.append('&');
        }
        if (resultParams.length() <= 0)
            return resultParams.toString();
        // 去掉最后的&
        return resultParams.substring(0, resultParams.length() - 1);
    }

    /**
     * 得到请求头
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHttpRequest.getHeaders();
    }

    /**
     * 请求参数
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mHttpRequest.getParams();
    }

    /**
     * 请求的ContentType
     *
     * @return
     */
    @Override
    public String getBodyContentType() {
        return mHttpRequest.getContentType();
    }

    /**
     * 请求的优先级，这里RequestQueue里面会根据这个把请求进行排序
     *
     * @return
     */
    @Override
    public Priority getPriority() {
        return mHttpRequest.getPriority();
    }

    /**
     * 设置请求时长，请求失败之后的次数
     *
     * @return
     */
    @Override
    public RetryPolicy getRetryPolicy() {
        return mHttpRequest.getRetryPolicy();
    }

    /**
     * 拿到请求的返回的字符串
     *
     * @return
     */
    public String getResponseString(NetworkResponse response) {
        StringBuilder output = new StringBuilder();
        String contentEncoding = response.headers.get("Content-Encoding");
        if (!TextUtils.isEmpty(contentEncoding) && contentEncoding.contains("gzip")) {
            // Gzip数据
            try {
                GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
                InputStreamReader reader = new InputStreamReader(gStream);
                BufferedReader in = new BufferedReader(reader);
                String read;
                while ((read = in.readLine()) != null) {
                    output.append(read);
                }
                in.close();
                reader.close();
                gStream.close();
            } catch (IOException e) {
                return PARSEERROR;
            }
        } else {
            output.append(new String(response.data));
        }
        return output.toString();
    }

    /**
     * 请求成功
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        if (mHttpListener != null) {
            mHttpListener.onSuccess(response);
        }
    }

    /**
     * 请求失败
     *
     * @param error Error details
     */
    @Override
    public void deliverError(VolleyError error) {
        if (mHttpListener != null) {
            mHttpListener.onError(error);
        }
    }

}
