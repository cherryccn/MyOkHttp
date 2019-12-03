package com.hjy.myokhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class OkHttpActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpActivity-xx";

    private TextView mContentTextView;

    private final OkHttpClient mClient = new OkHttpClient();
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentTextView = findViewById(R.id.tvContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_okhttp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGet:
                get();
                break;
            case R.id.menuResponse:
                response();
                break;
            case R.id.menuPost:
                post();
                break;
            case R.id.menuRetrofit:
                startActivity(new Intent(OkHttpActivity.this, Retrofit2Activity.class));
                break;
            case R.id.menuRxJava:
                startActivity(new Intent(OkHttpActivity.this, RxJavaActivity.class));
                break;
            case R.id.menu_Retrofit_RxJava:
                startActivity(new Intent(OkHttpActivity.this, Retrofit_RxjavaActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get请求
     */
    private void get() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url("http://fanyi.youdao.com/");
                Request request = builder.build();
                Call call = mClient.newCall(request);
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            final String resultStr = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mContentTextView.setText(resultStr);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }

    /**
     * post请求
     */
    private void post() {
        Request.Builder builder = new Request.Builder();
        builder.url("http://fanyi.youdao.com/");
        builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, "Hello world github/linguist#1 **cool**, and #1!"));
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String content = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContentTextView.setText(content);
                        }
                    });
                }
            }
        });
    }

    private void response() {
        Request.Builder builder = new Request.Builder();
        builder.url("https://raw.githubusercontent.com/square/okhttp/master/README.md");
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], e = [" + e + "]");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                int code = response.code();
                Headers headers = response.headers();
                assert response.body() != null;
                String content = response.body().string();
                final StringBuilder buf = new StringBuilder();
                buf.append("code: " + code);
                buf.append("\nHeaders: \n" + headers);
                buf.append("\nbody: \n" + content);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContentTextView.setText(buf.toString());
                    }
                });
            }
        });
    }
}
