package com.kjm.vo;

import java.sql.Date;

public class CommentVO {
	private int bno;
	private int commentno;
	private String comment;
	private String writer;
	
	private Date regdate;

	public CommentVO() {
		super();
	}
	
	public CommentVO(int bno, int commentno, String comment, String writer, Date regdate) {
		super();
		this.bno = bno;
		this.commentno = commentno;
		this.comment = comment;
		this.writer = writer;
		this.regdate = regdate;
		
	}
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getCommentno() {
		return commentno;
	}
	public void setCommentno(int commentno) {
		this.commentno = commentno;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", commentno=" + commentno + ", comment=" + comment + ", writer=" + writer + ", regdate="
				+ regdate+ "]";
	}
}