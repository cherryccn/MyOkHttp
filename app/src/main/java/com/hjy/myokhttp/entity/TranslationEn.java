package com.hjy.myokhttp.entity;

import java.util.List;

public class TranslationEn {

    private String type;        //翻译类型  。2之前的表示原文类型  2之后表示译文类型
    private int errorCode;      //0表示成功
    private int elapsedTime;
    public List<List<TranslateResultBean>> translateResult; //译文结果

    public static class TranslateResultBean {
        /**
         * src : merry me
         * tgt : 我快乐
         */

        public String src;      //原文
        public String tgt;      //译文

    }

    //定义 输出返回数据 的方法
    public String show() {
        return "TranslationEn:{" +
                "type='" + type + '\'' +
                ", errorCode=" + errorCode +
                ", elapsedTime=" + elapsedTime +
                ", translateResult=" + translateResult +
                '}';
    }
}
