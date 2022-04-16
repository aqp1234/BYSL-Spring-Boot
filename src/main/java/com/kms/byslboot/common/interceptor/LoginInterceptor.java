package com.kms.byslboot.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kms.byslboot.common.annotation.LoginRequired;
import com.kms.byslboot.member.exception.UnAuthenticatedException;
import com.kms.byslboot.member.service.LoginService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor{
	
	private final LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)) {
			Integer memberId = loginService.getLoginMemberID();
			if(memberId == null) {
				throw new UnAuthenticatedException("로그인이 필요합니다.");
			}
		}
		return true;
	}
}
