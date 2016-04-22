package com.yong.volleyok;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Project:</b> com.yong.volleyok <br>
 * <b>Create Date:</b> 2016/4/22 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 请求的封装，使用Builder模式进行构建 <br>
 */
public class HttpRequest {

    private Builder mBuilder;

    private HttpRequest(Builder builder) {
        this.mBuilder = builder;
    }

    public Map<String, String> getHeaders() {
        return mBuilder.headMaps;
    }

    public int getMethod() {
        return mBuilder.method;
    }

    public Map<String, String> getParams() {
        return mBuilder.params;
    }

    public Request.Priority getPriority() {
        return mBuilder.priority;
    }

    public String getContentType() {
        return mBuilder.contentType;
    }

    public String getParamsEncodeing() {
        return mBuilder.paramsEncodeing;
    }

    public RetryPolicy getRetryPolicy() {
        return mBuilder.retryPolicy;
    }

    public String getUrl() {
        return mBuilder.url;
    }

    public static final class Builder {

        String paramsEncodeing = "UTF-8";
        String url;
        int method = Request.Method.GET;
        Request.Priority priority = Request.Priority.NORMAL;
        String contentType = "application/x-www-form-urlencoded; charset=utf-8";
        // 请求头
        Map<String, String> headMaps = new HashMap<>();
        // 参数
        Map<String, String> params = new HashMap<>();

        // 超时以及重连次数
        RetryPolicy retryPolicy;

        public Builder(String url) {
            this.url = url;
        }

        /**
         * 增加 Http 头信息
         *
         * @param key   key
         * @param value value
         * @return
         */
        public Builder addHeader(String key, String value) {
            this.headMaps.put(key, value);
            return this;
        }

        /**
         * 增加 Http 头信息
         *
         * @param headers
         * @return
         */
        public Builder addheader(Map<String, String> headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                this.headMaps.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        /**
         * 设置 Http 请求方法
         *
         * @param method {@link Request.Method}
         * @return
         */
        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        /**
         * 增加请求参数
         *
         * @param key   key
         * @param value value
         * @return
         */
        public Builder addParam(String key, Object value) {
            this.params.put(key, String.valueOf(value));
            return this;
        }

        /**
         * 增加请求参数
         *
         * @param params map<string, object>
         * @return
         */
        public Builder addParam(Map<String, Object> params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                this.params.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return this;
        }

        /**
         * 设置请求优先级
         *
         * @param priority {@link Request.Priority}
         * @return
         */
        public Builder setPriority(Request.Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 设置文本类型
         *
         * @param contentType
         * @return
         */
        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        /**
         * 设置超时以及重连次数
         *
         * @param initialTimeoutMs  超时时间
         * @param maxNumRetries     重连次数
         * @param backoffMultiplier
         * @return
         */
        public Builder setRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
            this.retryPolicy = new DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, backoffMultiplier);
            return this;
        }

        /**
         * 构建 HttpRequest
         *
         * @return
         */
        public HttpRequest build() {
            return new HttpRequest(this);
        }

    }

}
