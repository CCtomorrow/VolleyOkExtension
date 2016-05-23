package com.yong.volleyokextension;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.yong.volleyok.HttpClient;
import com.yong.volleyok.HttpListener;
import com.yong.volleyok.HttpRequest;
import com.yong.volleyokextension.util.HttpUtil;

public class MainActivity extends AppCompatActivity {

    private HttpClient mHttpClient;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = (TextView) findViewById(R.id.result);
        mHttpClient = HttpUtil.getHttpClient();
        HttpRequest request = new HttpRequest.Builder("http://www.mocky.io/v2/571b3c270f00001a0faddfcc")
                .setMethod(Request.Method.GET)
                .build();
        mHttpClient.request(request, new HttpListener<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", result);
                mResult.setText(result);
            }

            @Override
            public void onError(VolleyError error) {
                mResult.setText(error.getMessage());
            }
        }, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHttpClient.cancelRequest(this);
    }
}
