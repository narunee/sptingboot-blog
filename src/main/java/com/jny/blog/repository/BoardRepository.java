package com.jny.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jny.blog.model.Board;

//DAO
//자동으로 bean 등록이 된다
//@Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}

