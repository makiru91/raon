package com.joker.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.joker.board.db.BoardDAO;
import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;

public class BoardDelete implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 폼에서 넘어온 값을 받음
		int no = Integer.parseInt(request.getParameter("no"));
		String section = request.getParameter("section");
		String num = request.getParameter("num");
		
		// 해당 게시글 삭제
		BoardDAO manager = BoardDAO.getInstance();
		manager.boardDelete(no);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("/expro/BoardList.do?section="+section+"&num="+num);
		
		return forward;
	}
}
