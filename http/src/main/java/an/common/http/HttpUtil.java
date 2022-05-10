package an.common.http;

import an.common.http.core.Method;
import an.common.http.core.Pair;
import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author andanyang
 * @since 2022/5/10 14:31
 */
public class HttpUtil {
    /**
     * 单列
     */
    private static volatile HttpRequest SINGLETON_REQUEST = null;

    private HttpUtil() {

    }

    public static HttpRequest getHttpRequest() {
        if (null == SINGLETON_REQUEST) {

            synchronized (HttpUtil.class) {
                if (null == SINGLETON_REQUEST) {
                    SINGLETON_REQUEST = new HttpRequest();
                }
            }
        }
        return SINGLETON_REQUEST;
    }

    public static void setHttpRequest(HttpRequest httpRequest) {
        SINGLETON_REQUEST = httpRequest;
    }

    public static String get(String path, Pair<?>... queryParams) throws IOException {

        Call call = getHttpRequest().buildCall(path, Method.GET, queryParams, null, null, null, null);
        ResponseBody body = call.execute().body();

        assert body != null;
        return body.string();
    }

    public static <T> T get(String path, Class<T> tClass, Pair<?>... queryParams) throws IOException {

        Call call = getHttpRequest().buildCall(path, Method.GET, queryParams, null, null, null, null);
        ResponseBody body = call.execute().body();

        return JSON.parseObject(body.byteStream(), tClass);
    }

    public static String get(String path, Map<String, Object> queryParams) throws IOException {

        Call call = getHttpRequest().buildCall(path, Method.GET, null, queryParams, null, null, null);
        ResponseBody body = call.execute().body();

        assert body != null;
        return body.string();
    }


    public static HttpRequestBuilder postBuilder(String url) {
        return new HttpRequestBuilder(url, Method.POST);
    }

    public static HttpRequestBuilder getBuilder(String url) {
        return new HttpRequestBuilder(url, Method.GET);
    }

    public static <T> T post(String url, Object body, Type returnType) {
        return getHttpRequest().post(url, body, returnType);
    }

    public static String post(String url, Object body) {
        return (String) getHttpRequest().post(url, body, String.class);
    }

    public static <T> T postForm(String url, Map<String, Object> formParams, Type returnType) {
        return getHttpRequest().postForm(url, formParams, returnType);
    }

    public static String postForm(String url, Map<String, Object> formParams) {
        return (String) getHttpRequest().postForm(url, formParams, String.class);
    }
}
