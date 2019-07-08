package com.springcloud.microservicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Zyh
 * @Date 2019/6/30 21:46
 * @Description
 *      网关提供的过滤器,可以通过过滤器扩展实现其他功能
 * @Note
 */
@Component
@Slf4j
public class LoginFilter extends ZuulFilter {

    /**
     * 配置过滤类型，有四种不同生命周期的过滤器类型
     * 1. pre：请求到网关之前
     * 2. routing：请求发送给指定服务时
     * 3. post：服务响应完之后
     * 4. error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 配置过滤顺序;数字越小越靠前,包含负数
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 配置是否启用过滤器：true/需要，false/不需要
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑实现
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文,这是zuul封装的请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = context.getRequest();
        // 打印日志,其中{}和%s一样,都属于占位符
        log.info("{} >>> {}", request.getMethod(), request.getRequestURL().toString());

        // 模拟token验证
        String token = request.getParameter("token");
        if (token == null) {
            log.warn("Token is empty");
            // 表示zuul不继续路由
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                // 解决输流中文乱码格式
                HttpServletResponse response = context.getResponse();
                response.setContentType("text/html;charset=utf-8");
                // 输出流直接返回给前端
                context.getResponse().getWriter().write("非法请求");
            } catch (IOException e) {

            }
        } else {
            log.info("OK");
        }
        return null;
    }
}
