package com.kjm.controller.userPage;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.service.BoardService;
import com.kjm.vo.BoardVO;


@RestController
public class NoticeRestController {
	@Inject
	private BoardService service;
	
	// 이전 게시물 호출
	@RequestMapping(value="/up/prevPage.do")				
	public BoardVO prevPage(HttpServletRequest req)throws Exception{
		BoardVO vo = new BoardVO();
		int pageno = Integer.parseInt((String)req.getParameter("pageNum"));		
		if(pageno==service.listStart()) {
			vo.setBno(pageno);
			return vo;
		}
		else {
			int bno=service.prevPage(pageno);
			vo.setBno(bno);
			return vo;
		}
	}
	
	// 다음 게시물 호출
	@RequestMapping(value="/up/nextPage.do")				
	public BoardVO nextPage(HttpServletRequest req)throws Exception{
		int pageno = Integer.parseInt((String)req.getParameter("pageNum"));		
		BoardVO vo = new BoardVO();
		if(pageno==service.listEnd()) {
			vo.setBno(pageno);
			return vo;
		}
		else {
			int bno=service.nextPage(pageno);
			vo.setBno(bno);
			return vo;
		}
	}

	// 검색된 게시글의 수 
	@RequestMapping(value="/up/getSearchCount.do")			
	public Map<String, Object> getSearchCount(HttpServletRequest req)throws Exception{
		String searchTitle = (String)req.getParameter("searchTitle");
		Map<String, Object> map = new HashMap<String, Object>();
		int searchCount = service.searchListCount(searchTitle);
		map.put("searchCount", searchCount);
		return map;
	}
		
}
