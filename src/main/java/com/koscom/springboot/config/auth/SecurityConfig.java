package com.koscom.springboot.config.auth;

import com.koscom.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // (1) Security 활성화 시킴
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // (2)
                .and()
                    .authorizeRequests() // (3)
                        .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // (4) 인가 : api ~  USER 이어야 사용 가능
                        .anyRequest().authenticated() // (5) 인증 : 그 외는 모두 인증 절차를 거친다
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // (6) 로그아웃 성공
                .and()
                    .oauth2Login() // (7)
                        .userInfoEndpoint() // (8)
                            .userService(customOAuth2UserService); // (9) 로그인 후 어떤 서비스 실행 ?
    }

    // 테스트에서 사용할 경우 security 무시 (17Line 참조)
    @Override
    public void configure(WebSecurity web) throws Exception{
        if(!securityEnabled){
            web.ignoring().antMatchers("/**");
        }
    }
}
