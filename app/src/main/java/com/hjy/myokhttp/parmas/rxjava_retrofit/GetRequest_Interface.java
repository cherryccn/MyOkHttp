package com.hjy.myokhttp.parmas.rxjava_retrofit;

import com.hjy.myokhttp.entity.Translation;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 创建 用于描述网络请求 的接口
 * 采用 注解 + Observable<...>接口描述 网络请求参数
 */

public interface GetRequest_Interface {

    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // 采用Observable<...>接口
    // getCall()是接受网络请求数据的方法
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();



}
