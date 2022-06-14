package com.jny.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jny.blog.auth.PrincipalDetail;
import com.jny.blog.dto.ResponseDTO;
import com.jny.blog.model.RoleType;
import com.jny.blog.model.User;
import com.jny.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) { //@RequestBody -> json데이터 받기 위함, 없으면 key=value 형태로 받음 MINE: x-www-form-urlencoded
		System.out.println("UserApiController save 호출");
		userService.join(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 json으로 변환해서 리턴
	}
	
	@PutMapping("/user")
	public ResponseDTO<Integer> update (@RequestBody User user){
		System.out.println("UserApiController update 호출");
		userService.updateUser(user);
		//트랜잭션 종료 되기 때문에 db값은 변경되지만 세션 값은 변경되지 않은 상태
		//세션 값 직접 변경
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	/*
	 * @PostMapping("/api/user/login") public ResponseDTO<Integer> login
	 * (@RequestBody User user, HttpSession session){ System.out.println("UserApiController save 호출");
	 * User principal = userService.login(user); //principal (접근주체) if(principal !=
	 * null) { session.setAttribute("principal", principal); } return new
	 * ResponseDTO<Integer>(HttpStatus.OK.value(),1); }
	 */
}
