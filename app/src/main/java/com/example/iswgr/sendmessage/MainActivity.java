package com.example.iswgr.sendmessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //发送信息
        sendMessage();
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        ByteArrayEntity entity = null;
        try {
            jsonObject.put("from", "postmaster@pg.abug.xyz");
            jsonObject.put("to", "isgorry@gmail.com");
            jsonObject.put("subject", "hello");
            jsonObject.put("text", "Hello World");
            Log.e("tag",jsonObject.toString());
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("utf-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        client.setBasicAuth("api", "key-e57dc67716cd04d846b7589cdd8ebcfe");
        client.post(this, "https://api.mailgun.net/v3/pg.abug.xyz/messages", entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                if (responseString != null) {
                    Log.e("tag", responseString);
                }
                Log.e("tag", statusCode + "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();
            }
        });
    }
}
