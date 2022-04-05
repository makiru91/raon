package com.joker.member.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joker.member.db.MemberDAO;
import com.joker.member.db.MemberDTO;

public class MemberUpdate implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberUpdate");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		MemberDTO member = new MemberDTO();
		// �Ѿ�� �����͸� member ��ü�� ����
		member.setId(request.getParameter("id"));
		member.setEmail(request.getParameter("email"));
		member.setName(request.getParameter("name"));
		member.setNickname(request.getParameter("nickname"));
		
		MemberDAO manager = MemberDAO.getInstance();
		// ���� ����
		int result = manager.updateMember(member);
		System.out.println(result);
		if(result == 0){ // ������ �ȉ��� ���
			out.print("<script>");
			out.print("alert('잘못된 정보입니다.');");
			out.print("history.go(-1);");
			out.print("</script>");
			out.close();
			return null;
		}else{ // ������ ���� ���
			HttpSession session = request.getSession();
			// session �缳��
			session.setAttribute("nickname", member.getNickname());
			request.setAttribute("result", result);
		}
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./MemberUpdateForm.do");
		
		return forward;
	}
}
