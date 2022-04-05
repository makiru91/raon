package com.joker.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joker.member.db.MemberDAO;
import com.joker.member.db.MemberDTO;

public class MemberUpdateForm implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberUpdateForm");
		
		HttpSession session = request.getSession();
		// session이 공유되어 있는 id를 가져옴
		String id = (String) session.getAttribute("id");

		MemberDAO manager = MemberDAO.getInstance();
		
		MemberDTO member = manager.getMember(id);
		
		request.setAttribute("member", member);
		
		ActionForward forward = new ActionForward();

		forward.setRedirect(false);
		forward.setPath("./member/memberUpdateForm.jsp");
		
		return forward;
	}
}
