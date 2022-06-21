package com.jny.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jny.blog.auth.PrincipalDetailService;



@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // security 필터가 등록이 됨 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다라는 뜻
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소를 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	/*
	 * @Bean public AuthenticationManager authenticationManagerBean() throws
	 * Exception{ return authenticationManagerBean(); }
	 */
	
	
	@Bean // ioc가 됨
	public BCryptPasswordEncoder encodePWD() {
		// String enPassword = new BCryptPasswordEncoder().encode("1234");
		return new BCryptPasswordEncoder();
	}

	// 시큐리티가 대신 로그인을 할 떄 password 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암화화해서 db에 있는 해쉬랑 비교 가능
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트 시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**","/dummy/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 로그인을 가로채서 대신 로그인
				.defaultSuccessUrl("/");
		return http.build();
	}
}
