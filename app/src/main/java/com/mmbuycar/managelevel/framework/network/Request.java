package com.mmbuycar.managelevel.framework.network;

import com.mmbuycar.managelevel.framework.network.callback.Callback;

import java.io.File;
import java.util.Map;

/**
 * @ClassName: Request
 * @author: 张京伟
 * @date: 2016/12/27 17:39
 * @Description:
 * @version: 1.0
 */
public class Request {
    private ServerInterfaceDefinition serverInterfaceDefinition;
    private Map<String, String> paramsMap;//网络请求数据
    private Map<String, String> headersMap;//网络请求header
    private Map<String, Map<String, File>> filesMap;//流文件数组
    private Callback<?> jsonParser;

    public Request(ServerInterfaceDefinition serverInterfaceDefinition,
                   Map<String, String> paramsMap, Callback<?> jsonParser) {
        super();
        this.serverInterfaceDefinition = serverInterfaceDefinition;
        this.paramsMap = paramsMap;
        this.jsonParser = jsonParser;
    }

    public Request(ServerInterfaceDefinition serverInterfaceDefinition,
                   Map<String, String> paramsMap, Map<String, String> headersMap, Callback<?> jsonParser) {
        super();
        this.serverInterfaceDefinition = serverInterfaceDefinition;
        this.paramsMap = paramsMap;
        this.headersMap = headersMap;
        this.jsonParser = jsonParser;
    }

    public Request(ServerInterfaceDefinition serverInterfaceDefinition,
                   Map<String, String> paramsMap, Map<String, String> headersMap, Map<String, Map<String, File>> filesMap, Callback<?> jsonParser) {
        super();
        this.serverInterfaceDefinition = serverInterfaceDefinition;
        this.paramsMap = paramsMap;
        this.headersMap = headersMap;
        this.filesMap = filesMap;
        this.jsonParser = jsonParser;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public ServerInterfaceDefinition getServerInterfaceDefinition() {
        return serverInterfaceDefinition;
    }

    public void setServerInterfaceDefinition(
            ServerInterfaceDefinition serverInterfaceDefinition) {
        this.serverInterfaceDefinition = serverInterfaceDefinition;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public Map<String, Map<String, File>> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(Map<String, Map<String, File>> filesMap) {
        this.filesMap = filesMap;
    }

    public Callback<?> getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(Callback<?> jsonParser) {
        this.jsonParser = jsonParser;
    }
}
