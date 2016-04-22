package com.yong.volleyok.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <b>Project:</b> com.yong.volleyok <br>
 * <b>Create Date:</b> 2016/4/22 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 原始请求的包装 <br>
 * 封装之后，子类只需要实现{@link #parseNetworkResponse(NetworkResponse)}即可，
 * 所有的扩展请求必须继承这个类{@link com.yong.volleyok.request.RequestWrapper}，
 * 不能去继承{@link com.android.volley.Request}
 */
public abstract class RequestWrapper<T> extends com.android.volley.Request<T> {

    /**
     * 请求
     */
    private HttpRequest mHttpRequest;

    /**
     * 结果
     */
    private HttpListener<T> mHttpListener;

    public RequestWrapper(HttpRequest httpRequest, HttpListener<T> listener) {
        // 这里不需要错误的监听，下面已经做了处理
        super(httpRequest.getMethod(), httpRequest.getUrl(), null);
        this.mHttpRequest = httpRequest;
        this.mHttpListener = listener;
        setRetryPolicy(new DefaultRetryPolicy(10000, 2, 1.0F));
    }

    /**
     * 得到url，这里get方法作处理，把参数都拼接上去
     *
     * @return
     */
    @Override
    public String getUrl() {
        // 当get的时候做处理，把参数都连接起来
        try {
            if (getMethod() == Method.GET &&
                    (getParams() != null && getParams().size() != 0)) {
                String encodedParams = getEncodedUrlParams();
                String extra = "";
                if (encodedParams != null && encodedParams.length() > 0) {
                    if (!mHttpRequest.getUrl().endsWith("?")) {
                        extra += "?";
                    }
                    extra += encodedParams;
                }
                return mHttpRequest.getUrl() + extra;
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
    public String getEncodedUrlParams() throws AuthFailureError {
        StringBuilder encodedParams = new StringBuilder();
        String paramsEncoding = getParamsEncoding();
        Map<String, String> params = getParams();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (null == entry.getValue()) {
                    continue;
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
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
