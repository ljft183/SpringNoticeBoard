package com.kjm.controller.userPage;

import java.io.File;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kjm.service.BoardService;
import com.kjm.vo.BoardVO;
import com.kjm.vo.CommentVO;
import com.kjm.vo.UserVO;


@Controller
public class NoticeController {
	
	@Inject
	private BoardService service;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	// ------- 게시판 기본 기능
	
	//게시판 메인 화면 호출
	@RequestMapping(value="/up/NoticeBoard.do")	
	public ModelAndView NoticeBoard(@RequestParam(value = "detailError", required = false) String detailError)throws Exception {							// Call NoticeBoard, return All Post'Count for Start Setting
		ModelAndView mv = new ModelAndView();

		if (detailError != null) {	//존재하지 않는 게시물 찾을 시에 호출
			mv.setViewName("/sboard/up/detailError");
			return mv;
		}
		else {
			int noticeCount = service.listCount();
			mv.addObject("noticeCount", Integer.toString(noticeCount));
			mv.setViewName("/sboard/up/noticeBoard");	
			return mv;
		}
	}
	
	// 게시글 목록 리스트 반환
	@RequestMapping(value="/up/NoticeList.do")			
	public ModelAndView NoticeList(HttpServletRequest req)throws Exception{
		int pageNum = Integer.parseInt((String)req.getParameter("pageNo"));
		int listStart = (pageNum*10) - 10;
		
		String search = (String)req.getParameter("searchText");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("listStart", listStart);
	
		
		List<BoardVO> list = new ArrayList<>();
		if("".equals(search))
			list = service.listPage(listStart);
		else 
			list = service.listSearch(map);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("jsonView");
		return mv;			
	}
	
	// 게시글 상세 정보 페이지 호출
	@RequestMapping(value="/up/NoticeBoardDetail.do", method=RequestMethod.GET)	
	public String addList(HttpServletRequest req, Model model)throws Exception{
		int getPageNum = Integer.parseInt((String)req.getParameter("bno"));
		service.upViewcnt(getPageNum);
		Map<String, Object> map = service.read(getPageNum);
		BoardVO vo = (BoardVO) map.get("vo");


		if(vo==null) {	//게시글 존재하지 않을 경우
			return "redirect:/up/NoticeBoard.do?detailError";
		}
		
		else {
			model.addAttribute("vo", vo);
			model.addAttribute("fileList", map.get("fileList"));

			return "/sboard/up/noticeBoardDetail";
		}
	}
	
	// 게시글 삭제
	@RequestMapping(value="/up/NoticeDelete.do")	
	public String noticeDelete(@RequestParam int bno)throws Exception{
		service.remove(bno);
		return "redirect:/up/NoticeBoard.do";
	}
	
	//게시글 작성페이지 호출
	@RequestMapping(value="/up/NoticeWrite.do")									
	public ModelAndView noticeWrite()throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sboard/up/writePost");
	
		return mv;
	}
	
	//게시글 작성 수행
	@RequestMapping(value="/up/PostWrite.do")										
	public String postWrite(BoardVO vo, HttpServletRequest req)throws Exception{
		service.regist(vo, req);
		UserVO uvo = service.logIn(vo.getWriter());
		service.expUp(uvo);
		//HttpSession session = req.getSession();
		
		//session.setAttribute("Authorities", "ROLE_ADMIN");
		return "redirect:/up/NoticeBoard.do";
	}
	
	// 게시글 수정 페이지 호출
	@RequestMapping(value="/up/NoticeUpdate.do")									
	public ModelAndView noticeUpdate(@RequestParam int bno)throws Exception{
		BoardVO vo = service.getBoard(bno);
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> list = service.readFile(bno);
		mv.addObject("vo", vo);
		mv.addObject("list", list);
		mv.setViewName("/sboard/up/updatePost");
		return mv;
	}
	
	// 게시글 수정 수행
	@RequestMapping(value="/up/PostUpdate.do")										
	public String postUpdate(BoardVO vo,HttpServletRequest req)throws Exception{
		service.modify(vo, req);
		return "redirect:/up/NoticeBoard.do";
	}
	
	// ------- 게시글 추가 기능
	
	//댓글 리스트 반환
	@RequestMapping(value="/up/getCommentList.do")			
	public ModelAndView getCommentList(HttpServletRequest req)throws Exception{
		ModelAndView mv = new ModelAndView();
		int bno = Integer.parseInt((String)req.getParameter("pageNum"));
		List<CommentVO> list = service.commentList(bno);
		
		mv.addObject("list", list);
		mv.setViewName("jsonView");
		return mv;
	}
	
	// 댓글 작성
	@RequestMapping(value="/up/commentWrite.do")										
	public ModelAndView commentWrite(HttpServletRequest req)throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String pageNum = (String)req.getParameter("pageNum");
		if(pageNum != "" && !"".equals(pageNum)) {
			int bno = Integer.parseInt(pageNum);
			
			String comment = (String)req.getParameter("comment");
			String writer = (String)req.getParameter("writer");
			CommentVO vo = new CommentVO();

			
			vo.setBno(bno);
			vo.setComment(comment);
			vo.setWriter(writer);
			int writeCnt = service.commentWrite(vo);
			if(writeCnt == 0) {
				returnMap.put("isSuccess", "N");
			} else {
				returnMap.put("isSuccess", "Y");
				UserVO uvo = service.logIn(writer);
				service.expUp(uvo);
			}
		}else {
			returnMap.put("isSuccess", "N");
		}
		mv.addObject("result", returnMap);
		mv.setViewName("jsonView");
		return mv;
	}
	
	// 댓글 삭제
	@RequestMapping(value="/up/commentDelete.do")			
	public ModelAndView deleteComment(CommentVO vo)throws Exception{
			ModelAndView mv = new ModelAndView();
			
			service.commentDeleteVO(vo);
			mv.setViewName("jsonView");
			return mv;
	}
	
	//게시글의 파일 부분
    @RequestMapping(value="/up/fileDownload.do")	
    public void downloadFile(HttpServletRequest req, HttpServletResponse response) throws Exception{
    	int bno = Integer.parseInt((String)req.getParameter("bno"));
    	int fileno = Integer.parseInt((String)req.getParameter("fileno"));
        Map<String,Object> map = service.selectFileInfo(bno,fileno);
        String original_File_Name = (String)map.get("filename");
        String stored_File_Name = (String)map.get("uuid");
         
        byte[] fileByte = FileUtils.readFileToByteArray(new File("C:\\Temp\\file\\"+stored_File_Name));
         
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(original_File_Name,"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
          
        response.getOutputStream().flush();
        response.getOutputStream().close();
 
    }	
 	
	// ------- 현재 사용자 관련
	
	//회원 정보 수정을 위한 비밀번호 확인 페이지 호출
	@RequestMapping(value="/up/CheckPass.do")									
	public String checkPass()throws Exception{
		return "/sboard/up/checkPassword";

	}
  
	//유저 정보 수정
	@RequestMapping(value="/up/userModify.do")		
	public String userModify(UserVO vo)throws Exception{
		if(vo.getPassword()==null||vo.getPassword().equals("")) {
			return "redirect:/up/NoticeBoard.do";
		}
		else {
			String endcodedPassword = bcryptPasswordEncoder.encode(vo.getPassword());
			vo.setPassword(endcodedPassword);
			service.modifyUser(vo);
			return "redirect:/up/NoticeBoard.do";
		}

	}
		
	//마이페이지 호출 (유저정보)
	@RequestMapping(value="/up/myInfo.do")										
	public ModelAndView myInfo(Principal principal)throws Exception{
		ModelAndView mv = new ModelAndView();
		String username = principal.getName();
	
		UserVO uvo = service.logIn(username);

			mv.addObject("uvo", uvo);
			mv.setViewName("/sboard/up/userInfo");
			return mv;
	}
		
	// 비밀번호 확인 후 유저정보 수정 페이지 호출 mvc:
	@RequestMapping(value="/up/myPage.do")										
	public ModelAndView myPage(@RequestParam String password,Principal principal)throws Exception{
		ModelAndView mv = new ModelAndView();
		String username = principal.getName();
	
		UserVO uvo = service.logIn(username);
		if(bcryptPasswordEncoder.matches(password, uvo.getPassword())) {
			mv.addObject("uvo", uvo);
			mv.setViewName("/sboard/up/userPage");
			return mv;
		}
		else {
			mv.setViewName("/sboard/up/checkError");
			return mv;
		}
	}
	
    // ------- 보류
    
	// 페이지 호출 에러 처리 -- 보류
	@RequestMapping(value="/up/error.do")		
	public ModelAndView urlError()throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sboard/up/detailError");
		return mv;
	}
		
}
