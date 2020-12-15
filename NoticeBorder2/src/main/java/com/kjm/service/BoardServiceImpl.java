package com.kjm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjm.dao.BoardDAO;
import com.kjm.util.fileUtil;
import com.kjm.vo.BoardVO;
import com.kjm.vo.CambodiaVO;
import com.kjm.vo.CommentVO;
import com.kjm.vo.UserVO;


@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	private BoardDAO dao;
	@Autowired
	fileUtil fileutil;
	
	@Override
	public void registAdmin(BoardVO vo)throws Exception{
		dao.create(vo);
	}
	
	@Override
	public void modifyAdmin(BoardVO vo)throws Exception{
		dao.update(vo);
	}
	

	
	@Override
	public void regist(BoardVO board, HttpServletRequest req) throws Exception {
		dao.create(board);

		
		List<Map<String, Object>> list = fileutil.parseInsertFileInfo(board, req);
		Map<String,Object> tempMap = null;
	    for(int i = 0; i<list.size();i++) {
	        tempMap = list.get(i);
	        dao.insertFile(tempMap);
	    }
	}
	
	@Override
	public Map<String,Object> read(int bno) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("vo", dao.read(bno));
		
		
		List<Map<String,Object>> list = dao.readFile(bno);
		if(list.size()>0)
			resultMap.put("fileList",dao.readFile(bno));
		return resultMap;
	}
	@Override
	public BoardVO getBoard(int bno)throws Exception{
		return dao.read(bno);
	}
	@Override
	public void modify(BoardVO board, HttpServletRequest req) throws Exception {
		dao.update(board);
		
		String deleteFileList = (String)req.getParameter("deleteFileList");
		
		if(deleteFileList != "" && !"".equals(deleteFileList)) {
			String[] strArray = deleteFileList.split(",");
			int removeFileNo;
			for(int i =0; i<strArray.length; i++) {
				removeFileNo = Integer.parseInt(strArray[i]);
				Map<String,Object> map= dao.selectFileInfo(board.getBno(), removeFileNo);
				fileutil.deleteFile(map);
				dao.deleteFile(map);
			}
		}
		

		
		List<Map<String, Object>> list = fileutil.parseInsertFileInfo(board, req);
		
		Map<String,Object> tempMap = null;
	    for(int i = 0; i<list.size();i++) {
	        tempMap = list.get(i);
	        dao.insertFile(tempMap);
	    }
	}
	@Override
	public void remove(int bno) throws Exception {
		List<Map<String,Object>> list = dao.fileList(bno);
		for(int i =0; i<list.size();i++) {
			Map<String,Object> fileMap = list.get(i);
			int fileno = (int) fileMap.get("fileno");
			Map<String,Object> map= dao.selectFileInfo(bno, fileno);
			fileutil.deleteFile(map);
			dao.deleteFile(map);
		}
		dao.commentDelete(bno);
		dao.delete(bno);
	}
	@Override
	public Map<String,Object> selectFileInfo(int bno, int fileno)throws Exception{
		return dao.selectFileInfo(bno,fileno);
	}

	
	// ---------------------------------------------------
	
	
	@Override
	public int listCount()throws Exception{
		return dao.listCount();
	}
	
	@Override
	public List<BoardVO> listAll()throws Exception{
		return dao.listAll();
	}
	
	@Override
	public List<BoardVO> listPage(int start)throws Exception{
		return dao.listPage(start);
	}
	@Override
	public int listStart()throws Exception{
		return dao.listStart();
	}
	@Override
	public int searchListCount(String title)throws Exception{
		return dao.searchListCount(title);
	}	
	
	
	// ---------------------------------------------------	
	
	
	@Override
	public int listEnd()throws Exception{
		return dao.listEnd();

	}
	@Override
	public int prevPage(int num)throws Exception{
		return dao.prevPage(num);
	}
	@Override
	public int nextPage(int num)throws Exception{
		return dao.nextPage(num);
	}
	@Override
	public void upViewcnt(int bno)throws Exception{
		dao.upViewcnt(bno);
	}
	@Override
	public List<BoardVO> listSearch(Map<String, Object> map)throws Exception{
		return dao.listSearch(map);
	}

	
	// ---------------------------------------------------	
	
	
	@Override
	public int commentWrite(CommentVO vo)throws Exception{
		return dao.commentWrite(vo);
	}
	@Override
	public List<CommentVO> commentList(int bno)throws Exception{
		return dao.commentList(bno);
	}
	@Override
	public void commentDeleteVO(CommentVO vo)throws Exception{
		dao.commentDeleteVO(vo);
	}
	

	// ---------------------------------------------------	
	
	
	
	@Override
	public UserVO logIn(String writer) throws Exception{
		return dao.logIn(writer);
	}
	@Override
	public void signUp(UserVO uvo)throws Exception{
		dao.signUp(uvo);
	}
	
	@Override
	public String findID(String mail) throws Exception{
		return dao.findID(mail);
	}
	
	@Override
	public UserVO checkUserInfo(UserVO uvo) throws Exception{
		return dao.checkUserInfo(uvo);
	}
	@Override
	public void modifyUser(UserVO uvo) throws Exception{
	dao.modifyUser(uvo);
	}
	@Override
	public void modifyUser2(UserVO uvo) throws Exception{
	dao.modifyUser2(uvo);
	}
	@Override
	public int idCount(String writer)throws Exception{
		return dao.idCount(writer);
	}
	@Override
	public List<UserVO> userList()throws Exception{
		return dao.userList();
	}
	@Override
	public void userDelete(String writer)throws Exception{
		List<BoardVO> list = dao.listUserWrite(writer);
		for(int i=0;i<list.size();i++) {
			BoardVO vo = list.get(i);
			remove(vo.getBno());
		}
		dao.userDelete(writer);
	}
	@Override
	public void expUp(UserVO uvo)throws Exception{
		dao.expUp(uvo);
	}
	
	
	
	//================================
	
	
	
	
	@Override
	public List<Map<String,Object>> readFile(int bno)throws Exception{
		return dao.readFile(bno);
	}
	
	
	
	
	
	
	
	@Override
	public void registCambodia(CambodiaVO camvo) {
		dao.registCambodia(camvo);
	}
	@Override
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo){
		return dao.getCambodiaYear(camvo);
		
	}
	
	@Override
	public void updateCam(CambodiaVO camvo) {
		dao.updateCam(camvo);
	}
	@Override
	public void registCambodiaList(List<CambodiaVO> list) {
		dao.registCambodiaList(list);
	}
	
	
}