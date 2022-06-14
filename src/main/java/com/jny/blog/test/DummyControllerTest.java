package com.jny.blog.test;


import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jny.blog.model.RoleType;
import com.jny.blog.model.User;
import com.jny.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getRole());
		System.out.println(user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료";
	}
	
	//{id}주소로 파라미터를 전달받을 수 있음
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저가 없습니다 id: "+id);
			}
		});
		//user 객체 =자바 오브젝트 -> 변환 (json)
		// messageConverter가 응답시에 자동 작동
		// 자바 오브젝트를 리턴하게 되면 jackson 라이브러리를 호출해서 json으로 변환해서 브라우저에 띄움
		return user;
	}
	
	@GetMapping("/dummy/user")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user/page")
	public List<User> pageList(@PageableDefault(size=2, sort="id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser =userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	@Transactional //함수 종료 시에 자동 commit , 변경 감지 -> db수정 : 더티체킹
	@PutMapping("dummy/user/{id}")
	public User updateUser (@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id:"+id);
		System.out.println("password:"+requestUser.getPassword());
		System.out.println("email:"+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정 실패");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		
		//save 함수는 id를 전달하지 않으면 insert를 해주고
		//id를 전달할 때 해당 id에 대한 데이터가 있으면 update를 하고
		//id를 전달할 때 해당 id에 대한 데이터가 없으면 insert를 함
		//userRepository.save(user);
		
		//더티체킹
		
		return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제 실패. ";
		}
		
		return "삭제 완료. id:"+id;
	}
	
	
}
