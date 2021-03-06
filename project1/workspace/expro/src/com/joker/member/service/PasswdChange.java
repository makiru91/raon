package com.joker.member.service;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.member.db.MemberDAO;
import com.joker.member.db.MemberDTO;

public class PasswdChange implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		String oldPasswd = request.getParameter("oldPasswd");
		String id = request.getParameter("id");
		
		MemberDAO manager = MemberDAO.getInstance();
		// db�� ����� ���� ��й�ȣ�� ������
		String checkPasswd = manager.pwCheck(id);
	
		String regExp = "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^a-z0-9]).{8,}$";
		boolean result_pw = ((String)request.getParameter("passwd")).matches(regExp);

		if(!oldPasswd.equals(checkPasswd)){
			out.print("<script>");
			out.print("alert('비밀번호가 맞지 않습니다.');");
			out.print("history.go(-1);");
			out.print("</script>");
			out.close();
		}else if(!result_pw) {
	        out.println("<script>");
	 		out.println("alert('숫자,영문,특수문자를 1개이상, 공백 없이 8자 이상 입력해주세요.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}else{ 
			MemberDTO member = new MemberDTO();
			member.setId(id);
			member.setPasswd(request.getParameter("passwd"));
			int result = manager.pwChange(member);
			request.setAttribute("result", result);
		}
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./Index.do");
		
		return forward;
	}
}
