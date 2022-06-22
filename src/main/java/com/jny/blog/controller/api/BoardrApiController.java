package com.jny.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jny.blog.auth.PrincipalDetail;
import com.jny.blog.dto.ReplySaveRequestDTO;
import com.jny.blog.dto.ResponseDTO;
import com.jny.blog.model.Board;
import com.jny.blog.model.Reply;
import com.jny.blog.repository.ReplyRepository;
import com.jny.blog.service.BoardService;

@RestController
public class BoardrApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.write(board,principal.getUser());
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 json으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDTO<Integer> deleteById (@PathVariable int id){
		boardService.delete(id);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDTO<Integer> update (@PathVariable int id, @RequestBody Board board){
		boardService.update(id,board);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	//데이터를 만들 때 컨트롤러에서 dto를 만들어서 받는 것이 좋음
	@PostMapping("/api/board/{id}/reply")
	public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDTO replySaveRequestDTO) {
		boardService.writeReply(replySaveRequestDTO);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 json으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/board/{boardid}/reply/{replyid}")
	public ResponseDTO<Integer> replyDelete(@PathVariable int replyid){
		boardService.deleteReply(replyid);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
}
