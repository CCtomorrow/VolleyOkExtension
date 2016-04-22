package com.yong.volleyok;

import com.android.volley.VolleyError;

/**
 * <b>Project:</b> com.yong.volleyok <br>
 * <b>Create Date:</b> 2016/4/22 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 回调响应 <br>
 */
public interface HttpListener<T> {
    /**
     * 服务器响应成功
     *
     * @param result 响应的理想数据。
     */
    void onSuccess(T result);

    /**
     * 网络交互过程中发生错误
     *
     * @param error {@link VolleyError}
     */
    void onError(VolleyError error);
}
