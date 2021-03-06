package com.joker.board.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.board.db.BoardDAO;
import com.joker.board.db.BoardDTO;
import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;

public class BoardUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		// ����form���� �Ѿ�� �� ����
		int num = Integer.parseInt(request.getParameter("num"));
		String section = request.getParameter("section");
		
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		String board_subject = request.getParameter("board_subject");
		String board_content = request.getParameter("board_content");
		
		// Map ��ü�� db �۾��� ���� �� ����
		Map<String, Object> m = new HashMap<>();
		m.put("board_no", board_no);
		m.put("board_subject", board_subject);
		m.put("board_content", board_content);
		
		BoardDAO manager = BoardDAO.getInstance();
		manager.boardUpdate(m);
		
		// ���� �Ϸ� �� �̵��� ������ ����
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("/expro/BoardContent.do?section="+section+"&num="+num+"&no="+board_no);
		
		return forward;
	}
	
}
