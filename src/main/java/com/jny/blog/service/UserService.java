package com.jny.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.jny.blog.model.RoleType;
import com.jny.blog.model.User;
import com.jny.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;


	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword(); // password의 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬 암호
		user.setPassword(encPassword); //해쉬화된 암호 저장
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void updateUser(User user) {
		//수정 시에 영속성 컨텍스에 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		//select를 해서 User 오브젝트를 db로부터 가져옴 (영속화를 하기 위함) -> 영속화된 오브젝트를 변경하면 자동으로 db에 update문을 날려줌
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		
		//회원 수정 함수 종료 시 = 서비스 종료 = 트랜잭션 종료 = commit 자동 실행
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹을 통해 update문을 날려줌
		
	}
	
	/*
	 * @Transactional(readOnly = true) //select 시 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 (정합성
	 * 유지시킴) public User login(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); }
	 */
}
