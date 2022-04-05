package com.joker.member.service;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.member.db.MemberDAO;
import com.joker.member.db.MemberDTO;

public class MemberJoin implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoin");
		// out 객체에서 사용할 타입
		response.setContentType("text/html;charset=utf-8");
		// out  객체 생성
		PrintWriter out=response.getWriter();
		// post 방식으로 넘어오는 데이터의 인코딩 타입
		request.setCharacterEncoding("utf-8");
		// 폼에서 넘어온 id값 저장
		String id = request.getParameter("id");
		// DAO 객체 생성
		MemberDAO manager = MemberDAO.getInstance();
		// 아이디 중복 검사
		int result = manager.idCheck(id);
		// 패스워드 정규식
		String regExp = "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^a-z0-9]).{8,}$";
		boolean result_pw = ((String)request.getParameter("passwd")).matches(regExp);
		
		if(result != 0){
			out.println("<script>");
			out.println("alert('이미 존재하는 id입니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}else if(!result_pw) {
	        out.println("<script>");
	        out.println("alert('숫자,영문,특수문자를 1개이상, 공백 없이 8자 이상 입력해주세요.');");
	        out.println("history.go(-1);");
	        out.println("</script>");
	        out.close();
	    }else{          
	        // 동일한 id가 없으면 dto객체를 저장하여 dao에 전달
	        MemberDTO member = new MemberDTO();
	        member.setId(id);
	        member.setPasswd(request.getParameter("passwd"));
	        member.setName(request.getParameter("name"));
	        member.setEmail(request.getParameter("email"));
	        member.setNickname(request.getParameter("nickname"));
	        manager.memberJoin(member);
	    }
	    // 포워딩 방식 및 경로 설정
	    ActionForward forward = new ActionForward();
	    forward.setRedirect(true);
	    forward.setPath("./LoginForm.do");
	    return forward;
	}
}
