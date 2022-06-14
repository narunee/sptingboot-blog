package com.jny.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity//User 클래스가 mysql에 테이블이 생성됨
//@DynamicInsert //insert할 때 null인 필드 제외
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 db의 넘버링 전략을 따라감
	private int id;
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(nullable = false, length = 100) //hash로 비밀번호 암호화
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋음
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
}
