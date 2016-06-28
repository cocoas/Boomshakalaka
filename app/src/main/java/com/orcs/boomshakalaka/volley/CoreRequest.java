package com.orcs.boomshakalaka.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orcs.boomshakalaka.utils.ALog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 自定义Request，通过GSON解析json格式的response。带缓存请求功能。
 *
 * @param <T>
 */
public class CoreRequest<T> extends Request<T> {

    private static final String PROTOCOL_CONTENT_TYPE = "application/json; charset=utf-8";

    public final Gson gson = new Gson();
    private Response.Listener mListenerer;

    private Object[] mParams;
    private Response.Listener<T> mListener;

    private JavaType returnJavaType;


    private ObjectMapper objectMapper = null;
    /**
     * 添加的头
     */
    private Map<String, String> mHeaders;
    /**
     * 获取cookie
     */
    private OnGetHeaderListener mOnGetHeaderListener;


    /**
     * extra参数
     */
    private String mExtra;

    /**
     * 初始化GSonRequest
     *
     * @param method        请求方式
     * @param url           请求地址
     * @param listener      处理响应的监听器
     * @param errorListener 处理错误信息的监听器
     */
    public CoreRequest(int method, String url, Object[] objs, Map<String, String> header, String extra,
                       Response.Listener listener, Response.ErrorListener errorListener
            , OnGetHeaderListener onGetHeaderListener) {
        super(method, url, errorListener);
        mOnGetHeaderListener = onGetHeaderListener;
        mExtra = extra;
        mParams = objs;
        mListener = listener;
        mHeaders = header;
        objectMapper = new ObjectMapper();
        //获取相应的类型
        if (listener != null) {
            java.lang.reflect.Method[] methods = listener.getClass().getDeclaredMethods();
            java.lang.reflect.Method onResponseMethod = null;
            for (java.lang.reflect.Method method1 : methods) {
                if (method1.getName().equals("onResponse") && !Modifier.isVolatile(method1.getModifiers())) {
                    onResponseMethod = method1;
                    break;
                }
            }
            if (onResponseMethod != null) {
                Type type = onResponseMethod.getGenericParameterTypes()[0];
                returnJavaType = objectMapper.getTypeFactory().constructType(type);
            }
        }

    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (mHeaders == null) {
            return super.getHeaders();
        }
        return mHeaders;
    }


    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }


    /**
     * 在此方法中处理response
     *
     * @param response 经过Gson解析后的response
     */
    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    public byte[] getBody() {
        String body = "[]";
        try {
            if (mParams != null) {

                try {
                    body = objectMapper.writeValueAsString(mParams);
                } catch (com.fasterxml.jackson.core.JsonProcessingException jsonProcessingException) {
                    jsonProcessingException.printStackTrace();
                }
                if (mExtra != null) {
                    body = body.substring(0, body.lastIndexOf("]")) + mExtra + "]";
                }
            }

            ALog.d("body................." + body);


            return this.mParams == null ? null : body.getBytes("utf-8");
        } catch (UnsupportedEncodingException var2) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[]{body, "utf-8"});
            return null;
        }
    }

    @Override
    public String getPostBodyContentType() {
        return String.format("application/json; charset=%s", new Object[]{"utf-8"});
    }



    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
//        switch (volleyError.networkResponse.statusCode) {
//            case 500: //如果错误代码为500
//
//
//                break;
//            case 404:  //如果错误代码为404
//
//
//                break;
//
//
//        }



        return super.parseNetworkError(volleyError);
    }



    /**
     * 解析网络返回的结果
     *
     * @param response 网络返回的response
     * @return 经过Gson解析后的response
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {


        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            ALog.d("resp" + json);
            mOnGetHeaderListener.onGetHeader(response.headers);
//            JSONArray jsonArray = new JSONArray(json);
            if (TextUtils.isEmpty(json)) {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            } else {

                T obj = objectMapper.readValue(response.data, returnJavaType);
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }


    }


    /**
     * 获取cookie的监听器
     */
    public interface OnGetHeaderListener {
        public void onGetHeader(Map<String, String> header);

    }


    @SuppressWarnings("unchecked")
    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

}