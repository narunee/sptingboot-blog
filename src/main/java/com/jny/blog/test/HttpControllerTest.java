package com.jny.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController //사용자가 요청 -> 응답(data)
public class HttpControllerTest {

	private static final String TAG="HttpControllerTest:";
	
	@GetMapping("http/lombok")
	public String lombokTest() {
		//Member member = new Member(1,"iidd","1234","email");
		Member member = Member.builder().username("idididid").password("12345").email("email").build();
		System.out.println(TAG+"getter: "+member.getId());
		member.setId(2);
		System.out.println(TAG+"setter: "+member.getId());
		return "lombok test end";
	}
	
	@GetMapping("http/get") //인터넷 브라우저 요청은 get 방식만 가능
	public String getTest(Member member) {
		return "get 요청 "+ member.getId();
	}
	
	@PostMapping("http/post")
	public String postTest(@RequestBody Member member) {
		return "post " + member.getId() + member.getUsername()+member.getPassword()  + member.getEmail();
		//return "post " +text;
	}
	
	@PutMapping("http/put")
	public String putTest(@RequestBody Member member) {
		return "put " + member.getId() + member.getUsername()+member.getPassword()  + member.getEmail();
	}
	
	@DeleteMapping("http/delete")
	public String deleteTest() {
		return "delete";
	}
}
