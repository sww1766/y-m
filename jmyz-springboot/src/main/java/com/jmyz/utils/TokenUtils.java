package com.jmyz.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description token相关工具类
 */
public class TokenUtils {

  /**
   * 全局账号token
   */
  public static final String AUTHORIZATION = "Authorization";

  /**
   * 取得请求的token
   */
  public static String getRequestToken(HttpServletRequest request) {

    String token = request.getHeader(AUTHORIZATION);

    if (StringUtils.isBlank(token)) {
      token = CookieUtils.getCookie(request, AUTHORIZATION);
    }

    if (StringUtils.isBlank(token)) {
      token = request.getParameter(AUTHORIZATION);
    }

    return token;
  }
}
