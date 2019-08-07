package com.crow.filter;

import com.crow.utils.JwtUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @ClassName:XflameFilter
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 9:38 2019/8/7
 * @Version: V1.0
 */
@WebFilter
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("Authorization");
        if(!JwtUtil.verify(token)){
            this.response401(servletRequest, servletResponse, "token过期");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletRequest req, ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        //之后构造错误返回的code和status
        try {
            out = httpServletResponse.getWriter();
//            String data = JsonUtil.toJson(new Result(false, HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + msg, null));  写入登录失败的返回信息
//            out.append(data);
        } catch (IOException e) {
//            LOGGER.error("直接返回Response信息出现IOException异常:" + e.getMessage());
//            throw new CustomException("直接返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
