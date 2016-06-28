package com.orcs.boomshakalaka.volley;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hnshituo.manager.bean.FormFile;
import com.hnshituo.manager.utils.ALog;
import com.hnshituo.manager.utils.FileCopyUtils;
import com.hnshituo.manager.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hui on 2016/3/16.
 */
public class RequestUpload<T> extends Request<T> {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Gson gson = new Gson();

    /**
     * 监听器
     */
    private Response.Listener<T> mListener;
    /**
     * 上传参数
     */
    private Map<String, String> mParams;
    /**
     * 上传文件
     */
    private List<List<FormFile>> mFileGroups;
    /**
     * 返回参数类
     */
    private Class<? extends T> mClazz;
    /**
     * 添加的头
     */
    private Map<String, String> mHeaders;

    private Context mContext;

    /**
     * 初始化
     *
     * @param method   请求方式
     * @param url      请求地址
     * @param params   请求参数，可以为null
     * @param clazz    Clazz类型，用于GSON解析json字符串封装数据
     * @param listener 处理响应的监听器
     * @param listener 监听器
     */
    public RequestUpload(int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener, Map<String, String> params,
                         Class<? extends T> clazz, Map<String, String> Headers,Context context) {
        super(method, url, errorListener);
        mClazz = clazz;
        mParams = params;
        mListener = listener;
        mHeaders = Headers;
        mContext = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
            if (mHeaders == null) {
            return new HashMap<>();
        }
        return mHeaders;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mParams != null && !mParams.isEmpty()) {
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                    encodedParams.append('&');
                }
                return encodedParams.toString().getBytes("utf-8");
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + "utf-8", uee);
            }
        }
        return null;

    }

    @Override
    protected Response<T> parseNetworkResponse (NetworkResponse networkResponse){
                ALog.i("Volley get Response......" + new String(networkResponse.data));

        try {
            String json = new String(
                    networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            ALog.d("" + json);

            T result = gson.fromJson(json, mClazz);//按正常响应解析

                //如果解析成功，并且需要缓存则将json字符串缓存到本地
                ALog.i("Save response to local!");
                FileCopyUtils.copy(networkResponse.data, new File(mContext.getCacheDir(), "" + MD5Utils.encode(getUrl())));

            return Response.success(
                    result,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(final T response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                mListener.onSuccess(response);
                mListener.onResponse(response);
            }
        });

    }

    @Override
    public void deliverError(final VolleyError error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                mListener.onError(error);
//                mListener.onResponse(response);
            }
        });
    }
}
