package com.hjy.myokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hjy.myokhttp.entity.Translation;
import com.hjy.myokhttp.entity.TranslationEn;
import com.hjy.myokhttp.parmas.retrofit.GetRequestInterface;
import com.hjy.myokhttp.parmas.retrofit.PostRequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Activity extends AppCompatActivity {

    private static final String TAG = "Retrofit2Activity";

    private TextView mContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        mContentTextView = findViewById(R.id.tvContent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retrofit2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGet:
                get();
                break;
            case R.id.menuPost:
                post();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 网络请求
     */
    private void get() {
        //步骤1: 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")//设置网络请求url
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
                .build();

        //步骤2: 创建 网络请求接口 的实例
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        //步骤3: 对 发送请求 进行封装
        Call<Translation> call = request.getCall();
        //步骤4: 发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                mContentTextView.setText(response.body().show());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });
    }

    private void post() {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")    // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())     //设置使用Gson解析(记得加入依赖)
                .build();
        //创建 网络请求接口 的实例
        PostRequestInterface request = retrofit.create(PostRequestInterface.class);
        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<TranslationEn> call = request.getCall("I love you");
        //发送网络请求(异步)
        call.enqueue(new Callback<TranslationEn>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<TranslationEn> call, Response<TranslationEn> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                mContentTextView.setText(response.body().translateResult.get(0).get(0).tgt);
            }
            //请求失败时回调
            @Override
            public void onFailure(Call<TranslationEn> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.getMessage());
            }
        });
    }
}
