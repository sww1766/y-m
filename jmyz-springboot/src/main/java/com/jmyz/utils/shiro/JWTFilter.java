package com.jmyz.utils.shiro;

import com.jmyz.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 获取token
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(TokenUtils.AUTHORIZATION);
        return StringUtils.isNotBlank(authorization);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(TokenUtils.AUTHORIZATION);

        JWTToken token = new JWTToken(authorization);
        Subject subject = getSubject(request, response);
        subject.login(token);
        // 如果没有抛出异常则登入成功，返回true
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //header中带有Authorization
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                log.error("token验证失败：{}",e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        //不带token，则在controller中添加@RequiresRoles等注解
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
