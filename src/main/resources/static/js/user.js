let index={
	init: function (){
		$("#btn-save").on("click",()=>{//function(){}대신 ()=>{}를 사용한 이유: this를 바인딩 하기 위해서
			this.save();
		});
		
		$("#btn-update").on("click",()=>{
			this.update();
		});
		
	},
	
	save: function(){
		//alert('save함수호출');
		let data={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
		//console.log(data);
		
		//ajax 통신으로 데이터를 json으로 변경하여 insert 요청
		$.ajax({
			type:"post",
			url:"/auth/joinProc",
			data: JSON.stringify(data), //http body 데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mime)
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 오는데 생긴게 json이라면 자바스크립트 오브젝트로 변경
		}).done(function(resp){
			//console.log(resp);
			alert("회원가입 완료");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	update: function(){
		let data={
			id:$("#id").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
		//console.log(data);
		
		$.ajax({
			type:"put",
			url:"/user",
			data: JSON.stringify(data), //http body 데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mime)
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 오는데 생긴게 json이라면 자바스크립트 오브젝트로 변경
		}).done(function(resp){
			//console.log(resp);
			alert("회원수정 완료");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
	
}

index.init();