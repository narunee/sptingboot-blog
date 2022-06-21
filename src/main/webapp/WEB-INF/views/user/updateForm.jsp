<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form>
		<input type="hidden" id="id" value="${principal.user.id }">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input type="username" class="form-control" placeholder="Enter username" id="username" value="${principal.user.username }" readonly="readonly">
		</div>
		
		<c:if test="${empty principal.user.oauth}">
		<div class="form-group">
			<label for="pwd">Password:</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password" >
		</div>
		</c:if>
		
		<div class="form-group">
			<label for="email">Email address:</label> 
			<input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email }" readonly="readonly">
		</div>
		
		<!-- <div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox"> Remember me
			</label>
		</div> -->
		
	</form>
	<button id="btn-update" class="btn btn-primary">회원수정 완료</button>

</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>


