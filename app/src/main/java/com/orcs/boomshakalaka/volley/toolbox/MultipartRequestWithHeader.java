package com.orcs.boomshakalaka.volley.toolbox;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orcs.boomshakalaka.utils.ALog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支持头的添加和保存 返回数据为Gson对象
 */
public class MultipartRequestWithHeader<T> extends Request<T> implements Mutipart {


    private Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 失败监听器
     */
    private Response.ErrorListener mErrorListener;
    /**
     * 成功监听器
     */
    private Response.Listener<T> mListener;
    /**
     * 上传参数
     */
    private Object[] mParams;
    /**
     * 上传文件
     */
    private List<FormFile> mFiles;
    /**
     * 分割线
     */
    private Gson gson = new Gson();

    /**
     * 添加的头
     */
    private Map<String, String> mHeaders;

    /**
     * 获取cookie
     */
    private OnGetHeaderListener mOnGetHeaderListener;

    private JavaType returnJavaType;

    private ObjectMapper objectMapper = null;


    /**
     * @param url           网址
     * @param listener      监听器
     * @param objs          参数
     * @param files         文件列表
     * @param errorListener 错误监听
     * @param headers       头
     */
    public MultipartRequestWithHeader(String url, Response.Listener<T> listener, Object[] objs,
                                      List<FormFile> files, Response.ErrorListener errorListener,
                                      Map<String, String> headers, OnGetHeaderListener onGetHeaderListener) {
        super(Method.POST, url, errorListener);
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
        this.mListener = listener;
        this.mErrorListener = errorListener;
        mOnGetHeaderListener = onGetHeaderListener;
        //添加头
        if (headers != null) {
            this.mHeaders = new HashMap<>(headers);
        } else {
            this.mHeaders = new HashMap<>();
        }
        this.mFiles = files;
        this.mParams = objs;

    }

    @Override
    public Map<String, String> getHeaders() {
        mHeaders.put("Charset", "UTF-8");
        //Keep-Alive
        mHeaders.put("Connection", "Keep-Alive");

//        mHeaders.put("Cookie", App.COOKIE);
        return mHeaders;
    }


    @Override
    public byte[] getBody() {
        String body = "[]";
        try {
            if (mParams != null) {
                try {
                    body = objectMapper.writeValueAsString(mParams);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            return this.mParams == null ? null : body.getBytes("utf-8");
        } catch (UnsupportedEncodingException var2) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[]{body, "utf-8"});
            return null;
        }
    }

    /**
     * 上传数据时调用
     *
     * @param out 数据输出流
     */
    public void handRequest(OutputStream out) {
        DataOutputStream dos = (DataOutputStream) out;
        try {
            //发送文件数据
            if (mFiles != null) {
                int position = 0;
                for (FormFile file : mFiles) {
                    // 发送文件数据
                    StringBuilder split = new StringBuilder();
                    split.append("--");
                    split.append(BOUNDARY);
                    split.append("\r\n");
                    split.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilname() + "\"\r\n");
                    split.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
                    dos.write(split.toString().getBytes());
                    if (file.getInStream() != null) {
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        int count = 0;
                        while ((len = file.getInStream().read(buffer)) != -1) {
                            dos.write(buffer, 0, len);
                            count += len;
                            final FormFile file1 = file;
                            final int count1 = count;
                            final int position1 = position;
                            if (mListener != null) {
                                //上传数据监听
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO: 2016/3/20 进度监听
                                    }
                                });
                            }
                        }
                        file.getInStream().close();
                    } else {
                        dos.write(file.getData(), 0, file.getData().length);
                    }
                    dos.write("\r\n".getBytes());
                    position++;
                }
                dos.write(("--" + BOUNDARY + "--\r\n").getBytes());
                dos.flush();
            }
        } catch (IOException e) {
            // TODO: 2016/3/20 错误监听
            try {
                dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        ALog.i("Volley get Response......" + new String(response.data));

        //拿到头
        // TODO: 2016/3/20 拿到头的监听
        Map<String, String> headers = response.headers;
        mOnGetHeaderListener.onGetHeader(headers);
        String json = "";
        try {

            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T obj = objectMapper.readValue(response.data, returnJavaType);
            return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
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
                mListener.onResponse(response);
            }
        });
    }

    @Override
    public void deliverError(final VolleyError error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mErrorListener.onErrorResponse(error);
            }
        });
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
