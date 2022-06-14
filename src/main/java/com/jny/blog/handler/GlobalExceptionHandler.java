package com.jny.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.jny.blog.dto.ResponseDTO;

@ControllerAdvice //exception 발생 시 해당 클래스로 오게끔 처리
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDTO<String> handleArgumentException(Exception e) {
		return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
		//return "<h1>"+e.getMessage()+"</h1>";
	}
}
