## 基于okhttp和volley的Http请求库

## Blog地址
[http://blog.csdn.net/qy274770068/article/details/51228180](http://blog.csdn.net/qy274770068/article/details/51228180)

### 可用API
```

    /**
     * String请求
     */
    Request request(HttpRequest httpRequest, HttpListener<String> listener, Object tag);

    /**
     * byte请求
     */
    Request byteRequest(HttpRequest httpRequest, HttpListener<byte[]> listener, Object tag);

    /**
     * JsonObject请求
     */
    Request jsonObjectRequest(HttpRequest httpRequest, HttpListener<JSONObject> listener, Object tag);

    /**
     * JsonArray请求
     */
    Request jsonArrayRequest(HttpRequest httpRequest, HttpListener<JSONArray> listener, Object tag);

    /**
     * Gson请求，可以映射Model
     *
     * @param tClass      映射的Model
     */
    <T> Request gsonRequest(Class<T> tClass, HttpRequest httpRequest, HttpListener<T> listener, Object tag);

    /**
     * Gson请求，可以映射Model
     *
     * @param typeToken   例如List<Model>
     */
    <T> Request gsonRequest(TypeToken<T> typeToken, HttpRequest httpRequest, HttpListener<T> listener, Object tag);

    /**
     * 取消请求
     *
     * @param tag
     */
    void cancelRequest(Object tag);
```

### 详细的使用方式
建议使用全局的Context。
```
public class DemoApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

}
```

注意在onDestroy里面取消请求
```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHttpClient = MMCHttpClient.getInstance(DemoApplication.getContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHttpClient.cancelRequest(this);
    }
```

**注:String，JsonObject，JsonArray，Gson请求都支持Gzip，只需要在请求的时候，在请求头里面添加gzip的请求头即可。关于gzip的解压缩，库里面已经做了判断和处理。**
```
.addHeader("Accept-Encoding", "gzip")
```


String请求

```
    public void httpStringReq(View view) {
        HttpRequest request = new HttpRequest.Builder(url)
                .setMethod(HttpRequest.Builder.Method.POST)
                .addParam("appkey", "")
                .addParam("channel", "")
                .build();
        mHttpClient.request(request, new HttpListener<String>() {
            @Override
            public void onSuccess(String result) {}

            @Override
            public void onError(VolleyError error) {}
        }, this);
    }
```

Gson的TypeToken的请求，这里呢返回的是一个JsonArray的数组，所以需要使用这种形式。
```
    public void httpGsonTypeTokenReq(View view) {
        final HttpRequest request = new HttpRequest.Builder
                (url)
                .setMethod(HttpRequest.Builder.Method.GET)
                .build();
        mHttpClient.gsonRequest(new TypeToken<List<TypeTokenModel>>() {
        }, request, new DefaultHttpListener<List<TypeTokenModel>>() {
            @Override
            public void onSuccess(List<TypeTokenModel> result) {}

            @Override
            public void onError(VolleyError error) {}
        }, this);
    }
```

Gson请求
```
        HttpRequest request = new HttpRequest.Builder(url)
                .setMethod(HttpRequest.Builder.Method.POST)
                .addParam("appkey", "")
                .addParam("channel", "")
                .build();
        mHttpClient.gsonRequest(GsonModel2.class, request, new DefaultHttpListener<GsonModel2>() {
            @Override
            public void onSuccess(GsonModel2 result) {}

            @Override
            public void onError(VolleyError error) {}
        }, this);
```

Gzip的jsonObject请求
```
    public void httpGzipJsonObjectReq(View view) {
        HttpRequest request = new HttpRequest.Builder(url)
                .setMethod(HttpRequest.Builder.Method.GET)
                .addHeader("Accept-Encoding", "gzip")
                .addParam("data", "")
                .addParam("sign", "")
                .build();
        mHttpClient.jsonObjectRequest(request, new DefaultHttpListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {}

            @Override
            public void onError(VolleyError error) {}
        }, this);
    }
```

### 混淆
```
-dontwarn com.squareup.okhttp.internal.huc.**
-keep class okio.** {*;}
Gson解析的Model不能混淆
```