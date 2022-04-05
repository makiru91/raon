package com.joker.reply.db;

import java.util.Date;

public class ReplyDTO {
	private int reply_no;
	private int board_no;
	private String id;
	private Date reply_date;
	private String reply_content;

	public int getReply_no() {
		return reply_no;
	}

	public int getBoard_no() {
		return board_no;
	}

	public String getId() {
		return id;
	}

	public Date getReply_date() {
		return reply_date;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_no(int reply_no) {
		this.reply_no = reply_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

}
