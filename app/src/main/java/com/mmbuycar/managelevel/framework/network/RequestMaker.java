package com.mmbuycar.managelevel.framework.network;

import com.alibaba.fastjson.JSONObject;
import com.mmbuycar.managelevel.framework.network.callback.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: RequestMaker
 * @author: 张京伟
 * @date: 2016/12/27 17:23
 * @Description:
 * @version: 1.0
 */
public class RequestMaker {
    private static final String PARAM_AUTH = "auth";
    private static final String PARAM_INFO = "info";
    private static final String PARAM_OPT = "opt";

    private RequestMaker() {

    }

    private static RequestMaker requestMaker = null;

    /**
     * 得到JsonMaker的实例
     *
     * @return
     */
    public static RequestMaker getInstance() {
        if (requestMaker == null) {
            synchronized (RequestMaker.class) {
                if (null == requestMaker)
                    requestMaker = new RequestMaker();
            }
        }
        return requestMaker;
    }

    /**
     * 拼接报文信息，发送信息到服务器请求网络
     *
     * @param hashMap    需要拼接的报文信息
     * @param jsonParser 请求返回的数据
     * @param sid        请求的接口
     * @return
     */
    public Request getRequest(HashMap<String, Object> hashMap, Callback<?> jsonParser, ServerInterfaceDefinition sid) {
        Request request = null;
        try {
            JSONObject jsonObject = new JSONObject();
            Set<Map.Entry<String, Object>> set = hashMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                jsonObject.put(entry.getKey(), entry.getValue());
            }

            String paramString = jsonObject.toJSONString();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put(PARAM_INFO, paramString);
            paramsMap.put(PARAM_AUTH, "e10adc3949ba59abbe56e057f20f883e");
            request = new Request(sid, paramsMap, jsonParser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
