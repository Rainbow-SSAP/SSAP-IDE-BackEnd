package com.ssap.SSAPIDE.interceptor;

import com.ssap.SSAPIDE.session.SessionLoginConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("로그인 인증 체크 실행 {}", requestURI);

        HttpSession session = request.getSession(false);
        if ((session == null) || (session.getAttribute(SessionLoginConst.LOGIN_USER)) == null) {
            response.sendRedirect("/?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }
}
