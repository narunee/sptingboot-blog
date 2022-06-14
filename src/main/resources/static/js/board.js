let index={
	init: function (){
		$("#btn-save").on("click",()=>{//function(){}대신 ()=>{}를 사용한 이유: this를 바인딩 하기 위해서
			this.save();
		});
		
		$("#btn-delete").on("click",()=>{//function(){}대신 ()=>{}를 사용한 이유: this를 바인딩 하기 위해서
			this.deleteById();
		});
		
		$("#btn-update").on("click",()=>{//function(){}대신 ()=>{}를 사용한 이유: this를 바인딩 하기 위해서
			this.update();
		});
	},
	
	save: function(){
		//alert('save함수호출');
		let data={
			title:$("#title").val(),
			content:$("#content").val()
		};
		
		//console.log(data);
		
		//ajax 통신으로 데이터를 json으로 변경하여 insert 요청
		$.ajax({
			type:"post",
			url:"/api/board",
			data: JSON.stringify(data), //http body 데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mime)
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 오는데 생긴게 json이라면 자바스크립트 오브젝트로 변경
		}).done(function(resp){
			//console.log(resp);
			alert("글쓰기 완료");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	deleteById: function(){
		let id = $("#id").text();
		
		$.ajax({
			type:"delete",
			url:"/api/board/"+id ,
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 오는데 생긴게 json이라면 자바스크립트 오브젝트로 변경
		}).done(function(resp){
			alert("삭제 완료");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	update: function(){
		
		let id= $("#id").val();
		
		let data={
			title:$("#title").val(),
			content:$("#content").val()
			
		};
		/*console.log(id);
		console.log(data);*/
		$.ajax({
			type:"put",
			url:"/api/board/"+id,
			data: JSON.stringify(data), //http body 데이터
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mime)
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 오는데 생긴게 json이라면 자바스크립트 오브젝트로 변경
		}).done(function(resp){
			console.log(resp);
			alert("글 수정 완료");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
	
}

index.init();