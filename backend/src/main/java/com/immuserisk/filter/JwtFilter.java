
package com.immuserisk.filter;

import com.immuserisk.utils.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtFilter extends BasicHttpAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        String path = req.getRequestURI();
        logger.debug("检查是否有Token，路径：{}，Token：{}", path, token != null ? "存在" : "不存在");
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        String path = req.getRequestURI();

        logger.info("执行登录验证，路径：{}，Token：{}", path, token);

        try {
            // 验证token
            boolean valid = JwtUtil.validateToken(token);
            if (!valid) {
                logger.warn("Token验证失败，路径：{}", path);
                return false;
            }

            // 判断请求路径类型
            if (path.startsWith("/admin/")) {
                // 管理员接口
                if (JwtUtil.isUserToken(token)) {
                    logger.warn("用户Token尝试访问管理员接口，路径：{}", path);
                    return false;
                }
            } else if (path.startsWith("/user/")) {
                // 用户接口
                if (!JwtUtil.isUserToken(token)) {
                    logger.warn("管理员Token尝试访问用户接口，路径：{}", path);
                    return false;
                }
            }

            logger.info("Token验证成功，路径：{}", path);
            return true;
        } catch (Exception e) {
            logger.error("Token验证异常，路径：{}，异常：{}", path, e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        logger.debug("检查访问权限，路径：{}", path);

        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                logger.error("访问权限检查异常，路径：{}，异常：{}", path, e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String origin = httpServletRequest.getHeader("Origin");
        String path = httpServletRequest.getRequestURI();
        logger.debug("处理预检请求，路径：{}，来源：{}", path, origin);

        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            logger.debug("OPTIONS请求，直接返回OK，路径：{}", path);
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

} 
