package com.jny.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jny.blog.model.KakaoProfile;
import com.jny.blog.model.OAuthToken;
import com.jny.blog.model.User;
import com.jny.blog.service.UserService;

//인증 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp 허용
//static 이하 있는 파일 (/js/**,/css/**,/image/** ) 허용

@Controller
public class UserController {
	
	@Value("${cos.key")
	private String cosKey;
	
	/*
	 * @Autowired private AuthenticationManager authenticationManager;
	 */
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // 데이터를 리턴해주는 컨트롤러 함수

		// post 방식으로 key=value 데이터를 요청 (카카오에게)
		RestTemplate rt = new RestTemplate();

		// httpheader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// httpbody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "a8cea254e7916b1c7de1393affba3dd3");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// httpheader와 httpbody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// http 요청하기 - post 방식으로 - 그리고 response 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// System.out.println(oauthToken.getAccess_token());

		
		// 토큰을 통한 사용자 정보 조회 (post)
		RestTemplate rt2 = new RestTemplate();

		// httpheader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// httpheader와 httpbody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// http 요청하기 - post 방식으로 - 그리고 response 변수의 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST,
				kakaoProfileRequest, 
				String.class
		);
		
		//사용자 정보 받아오기
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디:"+kakaoProfile.getId());
		System.out.println("카카오 이메일:"+kakaoProfile.getKakao_account().getEmail());

		System.out.println("블로그 서버 유저 네임: "+ kakaoProfile.getKakao_account().getEmail()+"_"+ kakaoProfile.getId());
		System.out.println("블로그 서버 이메일: "+kakaoProfile.getKakao_account().getEmail());
		//UUID garbagePassword = UUID.randomUUID(); //임시 패스워드 , UUID란 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		System.out.println("블로그 서버 패스워드: "+ cosKey);
		
		User KakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+ kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
				
		//회원가입 여부 확인
		System.out.println(KakaoUser.getUsername());
		User originUser= userService.findUser(KakaoUser.getUsername());
		if(originUser.getUsername()==null) {
			System.out.println("기존회원이 아님 -> 회원가입");
			//회원가입
			userService.join(KakaoUser);
		}
		
		System.out.println("자동 로그인 진행");
		//로그인 처리
		/*
		 * Authentication authentication = authenticationManager.authenticate(new
		 * UsernamePasswordAuthenticationToken(KakaoUser.getUsername(),
		 * KakaoUser.getPassword()));
		 * SecurityContextHolder.getContext().setAuthentication(authentication);
		 */
		
		
		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() { // @AuthenticationPrincipal PrincipalDetail principal
		return "user/updateForm";
	}
}
