package com.springcloud.microservicezuul.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyh
 * @Date 2019/6/30 21:23
 * @Description
 *      zuul代理请求发生错误的回调类,实现FallbackProvider接口;每一个服务都需要单独实现一个回调;服务的
 *      指定是在getRoute()方法中返回回调服务在配置文件中配置的serviceId
 * @Note
 */
@Component
public class FeignConsumerFallbackProvider implements FallbackProvider {

    /**
     * 指定服务
     */
    @Override
    public String getRoute() {
        return "microservice-feign-consumer";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            /**
             * 网关向 api 服务请求虽然失败,但是消费者客户端向网关发起的请求是成功的;
             * 不应该把 api 的 404,500 等问题抛给客户端;网关和 api 服务集群对于客户端来说是黑盒
             * 所以此处返回的状态码应该是200;
             *
             * @return 状态码枚举
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            /**
             * @return 状态码
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            /**
             * @return 状态码文本
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 返回响应体
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                // 用于封装JSON
                ObjectMapper mapper = new ObjectMapper();

                Map<String, Object> resultBody = new HashMap<>(8);
                resultBody.put("status", 200);
                resultBody.put("message", "网络异常");

                // 返回转成JSON字符串的字符流
                return new ByteArrayInputStream(mapper.writeValueAsString(resultBody).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                // 设置响应2编码格式;注意要和getBody中的内容编码一致
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
