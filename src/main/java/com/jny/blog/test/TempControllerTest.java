package com.jny.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	@GetMapping("temp/home")
	public String tempHome() {
		System.out.println("tempHome");
		//파일 return 기본 경로: src/main/resources/static
		//리턴명: /home.html 로 설정
		return "/home.html";
	}
	
	@GetMapping("temp/jsp")
	public String tempJsp() {
		return "test";
	}
}
