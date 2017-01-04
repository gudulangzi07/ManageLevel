package com.mmbuycar.managelevel.framework.network;

import com.mmbuycar.managelevel.SoftApplication;
import com.mmbuycar.managelevel.framework.network.builder.PostFormBuilder;
import com.mmbuycar.managelevel.framework.network.callback.Callback;
import com.mmbuycar.managelevel.framework.network.utils.OkHttpUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: RequestNetWork
 * @author: 张京伟
 * @date: 2016/12/27 16:59
 * @Description:
 * @version: 1.0
 */
public class RequestNetWork {

    private static RequestNetWork requestNetWork = null;

    public static synchronized RequestNetWork getInstance() {
        if (null == requestNetWork) {
            synchronized (RequestNetWork.class) {
                if (null == requestNetWork)
                    requestNetWork = new RequestNetWork();
            }
        }

        return requestNetWork;
    }

    public RequestNetWork() {
    }

    /**
     * 普通网络请求
     *
     * @param request  网络请求数据
     * @param callback 网络请求返回值
     */
    public void getNetWorkDate(Request request, Callback callback) {
        OkHttpUtils.getInstance().post()
                .url(SoftApplication.softApplication.getAppInfo().serverAddress + request.getServerInterfaceDefinition().getOpt())
                .params(request.getParamsMap())
                .headers(request.getHeadersMap())
                .build()
                .execute(callback);
    }

    /**
     * 上传图片网络请求
     *
     * @param request  网络请求数据
     * @param callback 网络请求返回值
     */
    public void getImgNetWorkDate(Request request, Callback callback) {
        PostFormBuilder params = OkHttpUtils.post()
                .url(SoftApplication.softApplication.getAppInfo().serverAddress + request.getServerInterfaceDefinition().getOpt())
                .params(request.getParamsMap())
                .headers(request.getHeadersMap());

        if (request.getFilesMap() != null && request.getFilesMap().size() > 0) {
            Map<String, Map<String, File>> filesMap = request.getFilesMap();
            Iterator iterator = filesMap.keySet().iterator();
            while (iterator.hasNext()) {
                String fileKey = iterator.next().toString();
                params.addFiles(fileKey, filesMap.get(fileKey));
            }
        }

        params.build().execute(callback);
    }
}
