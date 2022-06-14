package com.jny.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jny.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardservice;
	
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size=3, sort="id",direction = Sort.Direction.DESC) Pageable pageable) { //컨트롤러에서 세션을 어떻게 찾는지? @AuthenticationPrincipal PrincipalDetail principal
		model.addAttribute("boards", boardservice.boardList(pageable));
		return "index"; //viewResolver 작동
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardservice.boardDetail(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateFrom(@PathVariable int id, Model model) {
		model.addAttribute("board", boardservice.boardDetail(id));
		return "board/updateForm";
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
