package com.kjm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kjm.vo.BoardVO;
import com.kjm.vo.CambodiaVO;
import com.kjm.vo.CommentVO;
import com.kjm.vo.UserVO;



public interface BoardService {
	
	//ADMIN
	public void registAdmin(BoardVO vo)throws Exception;
	public void modifyAdmin(BoardVO vo)throws Exception;
	
	
	
	
	//CRUD
	public void regist(BoardVO board, HttpServletRequest req) throws Exception; // 게시글 등록
	public Map<String,Object> read(int bno) throws Exception; // 게시글 조회
	public BoardVO getBoard(int bno)throws Exception;
	public void modify(BoardVO board, HttpServletRequest req) throws Exception; // 게시글 수정
	public void remove(int bno) throws Exception; // 게시글 삭제
	public Map<String,Object> selectFileInfo(int bno, int fileno)throws Exception;

	
	//paging & search
	public int listCount()throws Exception; // 게시글 총 갯수
	public List<BoardVO> listAll()throws Exception; // 모든 게시물 조회
	public List<BoardVO> listSearch(Map<String, Object> map)throws Exception; // 검색 대상의 게시물 페이지 단위 나눠서 조회
	public List<BoardVO> listPage(int start)throws Exception; // 모든 게시물 페이지 단위 나눠서 조회
	public int searchListCount(String title)throws Exception; // 검색 대상의 게시물 갯수
	
	//board detail
	public int listStart()throws Exception; // 처음 게시물의 번호
	public int listEnd()throws Exception; // 마지막 게시물의 번호
	public int prevPage(int num)throws Exception; // 현재 보고있는 페이지의 이전 글 조회
	public int nextPage(int num)throws Exception; // 현재 보고있는 페이지의 다음 글 조회
	public void upViewcnt(int bno)throws Exception; // 조회수 증가
	
	//board Comment
	public int commentWrite(CommentVO vo)throws Exception;
	public List<CommentVO> commentList(int bno)throws Exception;
	public void commentDeleteVO(CommentVO vo)throws Exception;
	
	//user
	public UserVO logIn(String writer) throws Exception;//로그인
	public void signUp(UserVO uvo)throws Exception;//회원가입 
	public String findID(String mail) throws Exception;//mail통해 id조회
	public UserVO checkUserInfo(UserVO uvo) throws Exception;//mail,id 이용하여 유저 정보 조회
	public void modifyUser(UserVO uvo) throws Exception;//user 정보 수정
	public void modifyUser2(UserVO uvo) throws Exception;//user 정보 수정
	public int idCount(String writer)throws Exception;
	public List<UserVO> userList()throws Exception;
	public void userDelete(String write)throws Exception;
	public void expUp(UserVO uvo)throws Exception;
	
	
	//file
	public List<Map<String,Object>> readFile(int bno)throws Exception;
	
	
	
	public void registCambodia(CambodiaVO camvo);
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo);
	public void updateCam(CambodiaVO camvo);
	public void registCambodiaList(List<CambodiaVO> list);
}