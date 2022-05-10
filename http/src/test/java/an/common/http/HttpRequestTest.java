package an.common.http;

import an.common.http.core.Method;
import an.common.http.core.Pair;
import an.common.http.entity.R;
import lombok.SneakyThrows;
import okhttp3.Call;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author andanyang
 * @since 2022/5/10 14:08
 */
class HttpRequestTest {
    HttpRequest httpRequest;

    @BeforeEach
    void init() {
        HttpConfig httpConfig = new HttpConfig();
        httpRequest = new HttpRequest(httpConfig);
    }

    @Test
    void get() throws IOException {

        String s1 = HttpUtil.get("https://www.hutool.cn/docs/#/http/Http%E8%AF%B7%E6%B1%82-HttpRequest",
                Pair.of("id", "介绍"),
                Pair.of("id", 12));
        System.out.println("s1 = " + s1);
        String s = httpRequest.get("https://www.hutool.cn/docs/#/http/Http%E8%AF%B7%E6%B1%82-HttpRequest", Pair.of("id", "介绍"));
        System.out.println("s = " + s);
    }

    @Test
    void post() {
        String post = HttpUtil.post("https://oapi.dingtalk.com/robot/send?access_token=37f5954ab60ea8b2e431ae9101b1289c138e85aa6eb6e3940c35ee13ff8b6335",
                "{\"msgtype\": \"text\",\"text\": {\"content\":\"【反馈提醒】我就是我, 是不一样的烟火\"}}");
        System.out.println("post = " + post);
    }

    @Test
    void postForm() {

        Map<String, Object> formParams = new HashMap<>(16);
        formParams.put("username", "admin");
        formParams.put("password", "admin123");
        String post = HttpUtil.postForm("http://192.168.1.13:9100/auth/login",
                formParams
        );
        System.out.println("post = " + post);
    }

    @Test
    void postForm2() {

        Map<String, Object> formParams = new HashMap<>(16);
        formParams.put("username", "admin");
        formParams.put("password", "admin123");
        Object post = HttpUtil.postForm("http://192.168.1.13:9100/auth/login",
                formParams,
                R.class
        );
        System.out.println("post = " + post);
    }

    @SneakyThrows
    @Test
    void getRouters() {
        Map<String, Object> headerParams = new HashMap<>(16);
        headerParams.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTIzNTA2NDAsInVzZXJJZCI6MX0.idSAdHULHlVKZapcREsV6pg7kDcda70D8vSyzl_ubiY");
        String url = "http://192.168.1.13:9100/system/menu/getRouters";
        Call call = httpRequest.buildCall(url, Method.GET, null, null, headerParams);
        Object execute = httpRequest.execute(call, R.class);
        System.out.println(execute);
    }

    @SneakyThrows
    @Test
    void getRouters2() {
        String url = "http://192.168.1.13:9100/system/menu/getRouters";

        Object authorization = HttpUtil.getBuilder(url).addHeader("Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTIzNTA2NDAsInVzZXJJZCI6MX0.idSAdHULHlVKZapcREsV6pg7kDcda70D8vSyzl_ubiY")
                .addHeader("1", 2)
                .execute(R.class);
        System.out.println("authorization = " + authorization);
    }

}