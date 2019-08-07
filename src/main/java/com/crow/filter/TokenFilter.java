package com.crow.filter;

import com.alibaba.fastjson.JSON;
import com.crow.result.ResultInfo;
import com.crow.utils.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @ClassName:XflameFilter
 * @Author: wuy2 wangyq1
 * @Description: DONE
 * @Date: Created in 9:38 2019/8/7
 * @Version: V1.0
 */
@WebFilter(urlPatterns = "/*")
@Order(3)
public class TokenFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS =
            Collections.unmodifiableSet(
                    new HashSet<>(
                            Arrays.asList(
                                    "/wsnews/login"
                            )
                    )
            );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // doFilterByToken(servletRequest,servletResponse,filterChain);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**带验证token的过滤处理**/
    private void doFilterByToken(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");


        ResultInfo resultInfo=new ResultInfo();
        String token = request.getHeader("Authorization");
        boolean isValidate=false;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        String method=request.getMethod();
        if(method.equals("OPTIONS") || ALLOWED_PATHS.contains(path)){
            // 预请求方法和允许的路径直接通过
            // esponse.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request,response);
        }else{
            if(token!=null && !token.isEmpty() && JwtUtil.verify(token)){
                resultInfo.setSuccess(true);
                resultInfo.setMsg("用户token验证授权通过");
                isValidate=true;
            }else{
                resultInfo.setMsg("用户token验证授权失败，请求头的Authorization中没有token信息或token信息无效");
            }
        }

        if(!isValidate){
            response401(servletRequest, servletResponse, resultInfo);
        }else{
            filterChain.doFilter(request, response);
        }
    }

    /**无需转发，直接返回Response信息**/
    private void response401(ServletRequest request, ServletResponse response, ResultInfo resultInfo) throws UnsupportedEncodingException,IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        PrintWriter printWriter = null;
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(response.getOutputStream(),
                    "UTF-8");
            printWriter = new PrintWriter(outputStreamWriter, true);
            String jsonStr = JSON.toJSONString(resultInfo);
            printWriter.write(jsonStr);
            printWriter.flush();
            printWriter.close();
            outputStreamWriter.close();
        } catch (UnsupportedEncodingException e) {
            throw e;
            //logger.error("过滤器返回信息失败:" + e.getMessage(), e);
        } catch (IOException e) {
            throw e;
            //logger.error("过滤器返回信息失败:" + e.getMessage(), e);
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
            if (null != outputStreamWriter) {
                outputStreamWriter.close();
            }
        }
    }

}
