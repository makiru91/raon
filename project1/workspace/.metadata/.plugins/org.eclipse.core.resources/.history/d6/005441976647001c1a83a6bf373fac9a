package com.joker.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.board.db.BoardDAO;
import com.joker.board.db.BoardDTO;
import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;

public class BoardUpdateForm implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		BoardDAO manager = BoardDAO.getInstance();
		BoardDTO content = manager.boardContent(Integer.parseInt(request.getParameter("no")));
		
		request.setAttribute("content", content);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/boardUpdateForm.jsp");
		
		return forward;
	}
}
