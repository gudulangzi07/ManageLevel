package com.mmbuycar.managelevel.framework.network.builder;

import com.mmbuycar.managelevel.framework.network.utils.OkHttpUtils;
import com.mmbuycar.managelevel.framework.network.request.OtherRequest;
import com.mmbuycar.managelevel.framework.network.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
