package com.kjm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.kjm.vo.BoardVO;
import com.kjm.vo.CambodiaVO;
import com.kjm.vo.CommentVO;
import com.kjm.vo.CustomUserDetails;
import com.kjm.vo.UserVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject 
	private SqlSession session; 
	private static String namespace = "com.kjm.mapper.BoardMapper";
	@Override
	public void create(BoardVO vo) throws Exception {
		session.insert(namespace+".create", vo);
	}
	@Override
	public BoardVO read(int bno) throws Exception {
		return session.selectOne(namespace+".read", bno);
	}
	@Override
	public void update(BoardVO vo) throws Exception {
		session.update(namespace+".update", vo);
	}
	@Override
	public void delete(int bno) throws Exception {
		session.delete(namespace+".delete", bno);
	}
	@Override
	public Map<String,Object> selectFileInfo(int bno, int fileno)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("bno",bno);
		map.put("fileno", fileno);
		return session.selectOne(namespace+".selectFileInfo", map);
	}

	
	
	
	@Override
	public int listCount() throws Exception {
		return session.selectOne(namespace+".listCount");
	}
	@Override
	public List<BoardVO> listAll()throws Exception{
		return session.selectList(namespace+".listAll");
	}
	
	@Override
	public List<BoardVO> listPage(int start)throws Exception{
		return session.selectList(namespace+".listPage", start);
	}
	@Override
	public int searchListCount(String title)throws Exception{
		return session.selectOne(namespace+".searchListCount", title);
	}
	@Override
	public List<BoardVO> listSearch(Map<String, Object> map)throws Exception{
		return session.selectList(namespace+".listSearch", map);
	}
	public List<BoardVO> listUserWrite(String writer)throws Exception{
		return session.selectList(namespace+".listUserWrite",writer);
	}
	
	
	
	@Override
	public int listEnd()throws Exception{
		return session.selectOne(namespace+".listEnd");
	}
	@Override
	public int prevPage(int num)throws Exception{
		return session.selectOne(namespace+".prevPage", num);
	}
	@Override
	public int nextPage(int num)throws Exception{
		return session.selectOne(namespace+".nextPage", num);
	}
	@Override
	public void upViewcnt(int bno)throws Exception{
		session.update(namespace+".upViewcnt", bno);
	}
	@Override
	public int listStart()throws Exception{
		return session.selectOne(namespace+".listStart");
		
	}
	
	
	
	
	@Override
	public int commentWrite(CommentVO vo)throws Exception{
		return session.insert(namespace+".commentWrite", vo);
	}
	@Override
	public void commentDelete(int bno)throws Exception{
		session.delete(namespace+".commentDelete",bno);
	}
	@Override
	public List<CommentVO> commentList(int bno)throws Exception{
		return session.selectList(namespace+".commentList", bno);
	}
	@Override
	public void commentDeleteVO(CommentVO vo)throws Exception{
		session.delete(namespace+".commentDeleteVO",vo);
	}
	
	
	
	
	@Override
	public UserVO logIn(String writer) throws Exception{
		return session.selectOne(namespace+".logIn",writer);
	}
	@Override
	public void signUp(UserVO uvo)throws Exception{
		session.insert(namespace+".signUp",uvo);
	}

	@Override
	public String findID(String mail) throws Exception{
		return session.selectOne(namespace+".findID", mail);
	}
	@Override
	public UserVO checkUserInfo(UserVO uvo) throws Exception{
		return session.selectOne(namespace+".checkUserInfo", uvo);
	}
	@Override
	public void modifyUser(UserVO uvo) throws Exception{
		session.update(namespace+".modifyUser", uvo);
	}
	@Override
	public void modifyUser2(UserVO uvo) throws Exception{
		session.update(namespace+".modifyUser2", uvo);
	}
	@Override
	public int idCount(String writer)throws Exception{
		return session.selectOne(namespace+".idCount",writer);
	}
	@Override
	public List<UserVO> userList()throws Exception{
		return session.selectList(namespace+".userList");
	}
	@Override
	public void userDelete(String writer)throws Exception{
		session.delete(namespace+".userDelete",writer);
	}
	@Override
	public void expUp(UserVO uvo) throws Exception {
		session.update(namespace+".expUp", uvo);
		if(uvo.getExp()==20) {
			authUp(uvo);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
			updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER2"));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		
	}
	@Override
	public void authUp(UserVO uvo)throws Exception{
		session.update(namespace+".authUp", uvo);
	}
	
	
	
	
	
	
	
	@Override
	public CustomUserDetails getUserById(String writer) {
		CustomUserDetails users = session.selectOne(namespace+".getUserById", writer);
		return users;
	}
	
	
	
	
	
	@Override
	public void insertFile(Map<String, Object> map) {
	    session.insert(namespace+".insertFile",map);
	}
	@Override
	public List<Map<String,Object>> readFile(int bno)throws Exception{
		return session.selectList(namespace+".readFile",bno);
	}
	@Override
	public void deleteFile(Map<String, Object> map) {
		session.delete(namespace+".deleteFile",map);
	}
	@Override
	public List<Map<String,Object>> fileList(int bno)throws Exception{
		return session.selectList(namespace+".fileList",bno);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void registCambodia(CambodiaVO camvo) {
		session.insert(namespace+".registCambodia",camvo);
	}
	@Override
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo){
		return session.selectList(namespace+".getCambodiaYear",camvo);
	}
	@Override
	public void updateCam(CambodiaVO camvo) {
		session.update(namespace+".updateCam",camvo);
	}
	@Override
	public void registCambodiaList(List<CambodiaVO> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("camList",list);
		session.insert(namespace+".registCambodiaList",map);
	}

}