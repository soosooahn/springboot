package com.koscom.springboot.config.auth.login;

import com.koscom.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){ // (1)
        // method의 파라미터 어노테이션이 @LoginUser
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) !=null;

        // class의 타입이 SessionUser 인지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        // 둘다 true인 경우우
        return isLoginUserAnnotation && isUserClass;
    }

    @Override // (2)
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return httpSession.getAttribute("user");
    }
}
