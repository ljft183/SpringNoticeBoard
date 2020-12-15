package com.kjm.dao;

import java.util.List;
import java.util.Map;

import com.kjm.vo.BoardVO;
import com.kjm.vo.CambodiaVO;
import com.kjm.vo.CommentVO;
import com.kjm.vo.CustomUserDetails;
import com.kjm.vo.UserVO;


public interface BoardDAO {
	public void create(BoardVO vo) throws Exception; 
	public BoardVO read(int bno) throws Exception; 
	public void update(BoardVO vo) throws Exception; 
	public void delete(int bno) throws Exception; 
	public Map<String,Object> selectFileInfo(int bno, int fileno)throws Exception;
	
	
	
	
	public int listCount()throws Exception;
	public List<BoardVO> listAll()throws Exception;
	public List<BoardVO> listPage(int start)throws Exception;
	public int searchListCount(String title)throws Exception;
	public List<BoardVO> listSearch(Map<String, Object> map)throws Exception;	
	public List<BoardVO> listUserWrite(String writer)throws Exception;
	
	
	
	public int listStart()throws Exception;
	public int listEnd()throws Exception;
	public int prevPage(int num)throws Exception;
	public int nextPage(int num)throws Exception;
	public void upViewcnt(int bno)throws Exception;
	
	
	
	
	public int commentWrite(CommentVO vo)throws Exception;
	public void commentDelete(int bno)throws Exception;
	public List<CommentVO> commentList(int bno)throws Exception;
	public void commentDeleteVO(CommentVO vo)throws Exception;
	
	
	
	public UserVO logIn(String writer) throws Exception;
	public void signUp(UserVO uvo)throws Exception;
	public String findID(String mail) throws Exception;
	public UserVO checkUserInfo(UserVO uvo) throws Exception;
	public void modifyUser(UserVO uvo) throws Exception;
	public void modifyUser2(UserVO uvo) throws Exception;
	public int idCount(String writer)throws Exception;
	public List<UserVO> userList()throws Exception;
	public void userDelete(String writer)throws Exception;
	public void expUp(UserVO uvo)throws Exception;
	public void authUp(UserVO uvo)throws Exception;
	
	
	public CustomUserDetails getUserById(String writer);
	
	
	
	public void insertFile(Map<String, Object> map);
	public List<Map<String,Object>> readFile(int bno)throws Exception;
	public void deleteFile(Map<String, Object> map);
	public List<Map<String,Object>> fileList(int bno)throws Exception;






	public void registCambodia(CambodiaVO camvo);
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo);
	public void updateCam(CambodiaVO camvo);
	public void registCambodiaList(List<CambodiaVO> list);






}