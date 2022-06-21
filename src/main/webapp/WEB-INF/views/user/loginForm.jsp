<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input type="username" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=a8cea254e7916b1c7de1393affba3dd3&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="40px" src="/image/kakao_login_btn.png"></a>
	</form>
</div>

<%@ include file="../layout/footer.jsp"%>


