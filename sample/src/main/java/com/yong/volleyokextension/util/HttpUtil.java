package com.yong.volleyokextension.util;

import com.yong.volleyok.HttpClient;
import com.yong.volleyokextension.App;

/**
 * <b>Project:</b> com.yong.volleyokextension.util <br>
 * <b>Create Date:</b> 2016/4/23 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 获取到HttpClient <br>
 */
public class HttpUtil {
    public static HttpClient getHttpClient() {
        return HttpClient.getInstance(App.getContext());
    }
}
