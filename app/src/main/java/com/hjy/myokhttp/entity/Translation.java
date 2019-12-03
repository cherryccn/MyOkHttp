package com.hjy.myokhttp.entity;

public class Translation {

    private int status;     //请求成功时取1

    private Content content;//内容信息

    private static class Content {
        private String from;    //原文内容类型
        private String to;      //译文内容类型
        private String vendor;  //来源平台
        private String out;     //译文内容
        private int errNo;      //请求成功时取0
    }

    //定义 输出返回数据 的方法
    public String show() {
        return "Retrofit翻译结果：Translation:{" +
                "status=" + status +
                ", content:{" +
                "from='" + content.from + '\'' +
                ", to='" + content.to + '\'' +
                ", vendor='" + content.vendor + '\'' +
                ", out='" + content.out + '\'' +
                ", errNo=" + content.errNo +
                '}';
    }

    public String show2() {
        return "Retrofit_Rxjava翻译结果：Translation:{" +
                "status=" + status +
                ", content:{" +
                "from='" + content.from + '\'' +
                ", to='" + content.to + '\'' +
                ", vendor='" + content.vendor + '\'' +
                ", out='" + content.out + '\'' +
                ", errNo=" + content.errNo +
                '}';
    }

}
