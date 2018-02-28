package com.goldmantis.wb.viewdemo.utils;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 00027450 on 2018/1/11.
 */

public class httpUtil {
    OkHttpClient client = new OkHttpClient();
    MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
    RequestBody body = RequestBody.create(mediaType,"");
    Request request = new Request.Builder().url("").post(body).build();
    Response response = client.newCall(request).execute();

    public httpUtil() throws IOException {
    }
}
