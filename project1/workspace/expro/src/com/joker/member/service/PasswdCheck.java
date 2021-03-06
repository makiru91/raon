package com.joker.member.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.member.db.MemberDAO;

public class PasswdCheck implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		String realPasswd = request.getParameter("realPasswd");
		String id = request.getParameter("id");
		
		MemberDAO manager = MemberDAO.getInstance();
		// db�� ����� ���� ��й�ȣ�� ������
		String checkPasswd = manager.pwCheck(id);
		
		// �Է��� ��й�ȣ�� ��ġ�ϴ��� �˻�
		if(!realPasswd.equals(checkPasswd)){
			out.print("<script>");
			out.print("alert('비밀번호가 다릅니다.');");
			out.print("history.go(-1);");
			out.print("</script>");
			out.close();
			
			ActionForward forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./Index.do");
			
			return forward;
		}else{ 
			ActionForward forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./MemberUpdateForm.do");
			
			return forward;
		}
	}
}
