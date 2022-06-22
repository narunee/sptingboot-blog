package com.jny.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jny.blog.dto.ReplySaveRequestDTO;
import com.jny.blog.model.Board;
import com.jny.blog.model.Reply;
import com.jny.blog.model.User;
import com.jny.blog.repository.BoardRepository;
import com.jny.blog.repository.ReplyRepository;
import com.jny.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void write(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board boardDetail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패");
		});
	}

	@Transactional
	public void delete(int id) {
		System.out.println("글삭제"+id);
		boardRepository.deleteById(id);
	}

	@Transactional
	public void update(int id, Board requestboard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패");
				}); //영속화 완료
		board.setTitle(requestboard.getTitle());
		board.setContent(requestboard.getContent());
		//해당함수 종료 시에( service가 종료될 때 ) 트랜잭션이 종료
		//이 때 더티체킹 -> 자동 업데이트 flush
	}

	@Transactional
	public void writeReply(ReplySaveRequestDTO replySaveRequestDTO) {
		replyRepository.mSave(replySaveRequestDTO.getUserid(),replySaveRequestDTO.getBoardid(),replySaveRequestDTO.getContent());
		/*User user = userRepository.findById(replySaveRequestDTO.getUserid()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 작성 실패: 유저 아이디 x");
		});	
		
		Board board=boardRepository.findById(replySaveRequestDTO.getBoardid()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 작성 실패: 게시글 아이디 x");
		});
		
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDTO.getContent())
				.build();
		
		replyRepository.save(reply); */
	}

	@Transactional
	public void deleteReply(int replyid) {
		replyRepository.deleteById(replyid);
		
	}
}
