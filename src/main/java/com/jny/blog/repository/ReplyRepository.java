package com.jny.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jny.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	@Modifying
	@Query(value="INSERT INTO reply(userid, boardid,content, createDate) VALUES (?1,?2,?3,now())", nativeQuery = true)
	int mSave(int userid, int boardid, String content);
}
