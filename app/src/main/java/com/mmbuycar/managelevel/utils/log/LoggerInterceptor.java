package com.mmbuycar.managelevel.utils.log;

import android.text.TextUtils;

import com.mmbuycar.managelevel.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            LogManager.getLogger().e(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogManager.getLogger().e(tag, "url : " + clone.request().url());
            LogManager.getLogger().e(tag, "code : " + clone.code());
            LogManager.getLogger().e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                LogManager.getLogger().e(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogManager.getLogger().e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LogManager.getLogger().e(tag, "responseBody's content : " + resp);
                            /*********************本地log文件*****************************/
                            File file = new File(Constants.FILE_PATH_LOG);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            File file2 = new File(Constants.FILE_PATH_LOG, Constants.LOG_NAME);
                            FileOutputStream outStream = new FileOutputStream(file2);
                            outStream.write(resp.getBytes());
                            outStream.flush();
                            outStream.close();
                            /*********************本地log文件*****************************/
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            LogManager.getLogger().e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            LogManager.getLogger().e(tag, "========response'log=======end");
        } catch (Exception e) {
            LogManager.getLogger().e(tag, "=========Exception========", e);
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogManager.getLogger().e(tag, "========request'log=======");
            LogManager.getLogger().e(tag, "method : " + request.method());
            LogManager.getLogger().e(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                LogManager.getLogger().e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogManager.getLogger().e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogManager.getLogger().e(tag, "requestBody's content : " + bodyToString(request));
                    }
                }
            }
            LogManager.getLogger().e(tag, "========request'log=======end");
        } catch (Exception e) {
            LogManager.getLogger().e(tag, "=========Exception========", e);
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
