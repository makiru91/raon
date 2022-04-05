package com.joker.member.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joker.member.db.MemberDAO;

public class Withdrawal implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Withdrawal");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("id");
		String passwd = request.getParameter("passwd");
		
		MemberDAO manager = MemberDAO.getInstance();
		
		int result = 0;
		
		if(!passwd.equals(manager.pwCheck(id))){ // �Է��� ��й�ȣ�� ����� ��й�ȣ�� �ٸ����
			out.print("<script>");
			out.print("alert('비밀번호가 맞지 않습니다..');");
			out.print("history.go(-1);");
			out.print("</script>");
			out.close();
			return null;
		}else{ // ��й�ȣ�� �����ϸ�
			// ȸ�� Ż��
			result = manager.memDelete(id);
			request.setAttribute("result", result);
			session.invalidate(); // ���� ����
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./WithdrawalForm.do");
		
		return forward;
	}
	
}
