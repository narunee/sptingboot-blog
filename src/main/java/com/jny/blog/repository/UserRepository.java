package com.jny.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jny.blog.model.User;

//DAO
//자동으로 bean 등록이 된다
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//select * from user where username = ?1;
	Optional<User> findByUsername (String username);
}

//JPA Naming 쿼리
	//select * from user where username=? and password=?; => 쿼리가 동작하게 됨
	//User findByUsernameAndPassword(String username, String password);
	
	
//	@Query(value = "select * from user where username=?1 and password=?2" ,nativeQuery = true)
//	User login(String username, String password);
