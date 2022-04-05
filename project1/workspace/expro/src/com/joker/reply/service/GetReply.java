package com.joker.reply.service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;
import com.joker.reply.db.ReplyDAO;
import com.joker.reply.db.ReplyDTO;

public class GetReply implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		ReplyDAO manager = ReplyDAO.getInstance();
		// 댓글 목록을 db로 부터 구해옴
		List<ReplyDTO> list = manager.getReply(Integer.parseInt(request.getParameter("board_no")));
		
		// 구해온 댓글 목록을 반복문을 이용하여 json 형식으로 문자열 변수(json)에 누적 저장
		String json = "{\"replyList\":["; // replyList는 키값이 됨
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).getId();
			String content = list.get(i).getReply_content();
			Date date = list.get(i).getReply_date();
			SimpleDateFormat df = new SimpleDateFormat("YY-MM-dd");
			int reply_no = list.get(i).getReply_no();
			
			json += "[{\"id\":\"" + id + "\"},";
			json += "{\"reply_date\":\"" + df.format(date) + "\"},";
			json += "{\"reply_content\":\"" + content + "\"},";
			json += "{\"reply_no\":\"" + reply_no + "\"}]";
			
			if (i != list.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌
		out.print(json);
		
		return null;
	}	
}