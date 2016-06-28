package com.orcs.boomshakalaka.volley;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnshituo.manager.App;
import com.hnshituo.manager.volley.toolbox.FormFile;
import com.hnshituo.manager.volley.toolbox.MultipartHurlStack;
import com.hnshituo.manager.volley.toolbox.MultipartRequestWithHeader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hui on 2016/3/21.
 */
public class VolleyRequestUtils {

    private Context mContext;
    private static VolleyRequestUtils mVolleyRequestUtils = null;
    private RequestQueue mRequestQueue;

    private VolleyRequestUtils(Context context) {
        mContext = context.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(mRequestQueue, new VolleyImageCacheImpl(mContext));
    }


    /**
     * 图片加载工具，自定义缓存机制
     */
    private ImageLoader mImageLoader;


    public static VolleyRequestUtils getDefaute(Context context) {
        if (mVolleyRequestUtils == null) {
            mVolleyRequestUtils = new VolleyRequestUtils(context);
        }
        return mVolleyRequestUtils;
    }
    /**
     * 发送get方式的GsonRequest请求,默认缓存请求结果
     *
     * @param url         请求地址
     * @param params      GET请求参数，拼接在URL后面。可以为null

     * @param listener    处理响应的监听器
     */
    public Request<?> get(String url, HttpParams params, Response.Listener listener, Response.ErrorListener errorListener) {
        url = url + params.toGetParams();//如果是get请求，则把参数拼在url后面
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue = Volley.newRequestQueue(mContext);
        mRequestQueue.add(stringRequest);
        return stringRequest;
    }


    /**
     * 请求网络图片并设置给ImageView
     *
     * @param view       The imageView
     * @param requestUrl The URL of the image to be loaded.
     */
    public void display(ImageView view, String requestUrl) {
        display(view, requestUrl, 0, 0);
    }

    /**
     * 请求网络图片并设置给ImageView，可以设置默认显示图片和加载错误显示图片
     *
     * @param view              The imageView
     * @param defaultImageResId Default image resource ID to use, or 0 if it doesn't exist.
     * @param errorImageResId   Error image resource ID to use, or 0 if it doesn't exist.
     */
    public void display(ImageView view, String requestUrl, int defaultImageResId, int errorImageResId) {
        mImageLoader.get(requestUrl, ImageLoader.getImageListener(view, defaultImageResId, errorImageResId));

    }


    public void displayThumb(ImageView view, String requestUrl, int maxWidth, int maxHeigh, ImageView.ScaleType scaleType) {
        mImageLoader.get(requestUrl, ImageLoader.getImageListener(view, 0, 0), maxWidth, maxHeigh, scaleType);

    }


    /**
     * @param url                 请求地址
     * @param objs                请求参数
     * @param header              需要添加头参数
     * @param listener            成功的监听
     * @param errorListener       失败的监听
     * @param onGetHeaderListener 获取header的监听器
     * @param extra               body的额外参数
     * @return
     */

    public Request<?> post(String url, Object[] objs, Map<String, String> header, String extra,
                           Response.Listener listener, Response.ErrorListener errorListener,
                           CoreRequest.OnGetHeaderListener onGetHeaderListener, Context context) {

        if (header == null) {
            header = new HashMap<>();
            header.put("Cookie", App.COOKIE);
            header.put("Charset", "UTF-8");
            header.put("Connection", "Keep-Alive");
            header.put("Content-Type","application/json; charset=utf-8");
        }



        CoreRequest request = new CoreRequest(Request.Method.POST, url, objs, header, extra, listener, errorListener
                , onGetHeaderListener);

        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue = Volley.newRequestQueue(mContext);
        mRequestQueue.add(request);

        return request;
    }

    /**
     * 将请求添加到队列
     *
     * @param request
     * @return
     */
    private Request<?> addRequest(MultipartRequestWithHeader request, Context context) {

        File cacheDir = new File(context.getCacheDir(), "volley");

        String userAgent = "volley/0";

        try {
            String network = context.getPackageName();
            PackageInfo queue = context.getPackageManager().getPackageInfo(network, 0);
            userAgent = network + "/" + queue.versionCode;
        } catch (PackageManager.NameNotFoundException var6) {

        }
        BasicNetwork network1 = new BasicNetwork(new MultipartHurlStack());
        mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), network1);
        mRequestQueue.add(request);
        mRequestQueue.start();
        return request;

    }

    /**
     * @param url                 地址值
     * @param objs                传入参数
     * @param files               上传文件
     * @param header              请求头
     * @param listener            成功的监听
     * @param errorListener       失败的监听
     * @param onGetHeaderListener 获取头的监听
     * @param context             获取context
     * @return
     */

    public Request<?> upload(String url, Object[] objs, List<FormFile> files, Map<String, String> header,
                             Response.Listener listener, Response.ErrorListener errorListener,
                             MultipartRequestWithHeader.OnGetHeaderListener onGetHeaderListener, Context context) {


        if (header == null) {
            header = new HashMap<>();
            header.put("Cookie", App.COOKIE);
            header.put("Charset", "UTF-8");
            header.put("Connection", "Keep-Alive");
        }
        MultipartRequestWithHeader multipartRequestWithHeader = new MultipartRequestWithHeader(url, listener, objs,
                files, errorListener, header, onGetHeaderListener);

        multipartRequestWithHeader.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addRequest(multipartRequestWithHeader, context);
        return multipartRequestWithHeader;
    }

    public void cancelRequest(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);//从请求队列中取消对应的任务
        }
    }


}

