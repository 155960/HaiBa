package com.zengqiang.future.filter;


import com.alibaba.fastjson.JSON;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //不能重复获得printwriter
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        //登录页不做token验证
        String path=request.getRequestURL().toString();
        if(path.indexOf("/login")>-1||path.indexOf("/register")>-1){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer=null;
        String token=request.getHeader("Authorization");
        boolean result=false;
        try {
            result=TokenUtil.authenticate(token);
            if(result){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
            else{
                writer=response.getWriter();
                writer.append(JSON.toJSONString(ServerResponse.createByErrorMessage("token错误，重新登录")));
                return;
            }
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            writer=response.getWriter();
            writer.append(JSON.toJSONString(ServerResponse.createByErrorMessage("token过期，重新登录")));
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            writer=response.getWriter();
            writer.append(JSON.toJSONString(ServerResponse.createByErrorMessage("token未知错误，重新登录")));
            return;
        }finally {
            if(writer!=null){
                writer.flush();
                writer.close();
            }

        }

    }

    @Override
    public void destroy() {

    }
}
