package com.orcs.boomshakalaka.volley.toolbox;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ExtraReqUtils {

    /**
     * 获取"","",""这样的结构
     * @param arguments
     * @return
     */
    public static String getExtra(String[] arguments ) {
        String result = "";
        if (arguments.length > 0) {
           for (int i = 0 ; i < arguments.length;i++) {
               if (i != arguments.length-1) {//如果不是最后一个,后面加","

                   result += "\""+arguments[i]+"\""+",";
               }else {
                   result += "\""+arguments[i]+"\"";
               }


           }
        }

        return result;
    }
}
