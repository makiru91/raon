package com.joker.board.service;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.board.db.BoardDAO;
import com.joker.board.db.BoardDTO;
import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;



public class BoardWrite implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		//���� ���ε�(�߰���)
		 MultipartRequest multi = null;	
		 int sizeLimit = 10 * 1024 * 1024 ; // 10�ް��Դϴ�.
		 String savePath = request.getRealPath("/upload");    // ������ ���ε�� ���� tomcat ������ WebContent ����
		 try{
		 multi=new MultipartRequest(request, savePath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy()); 
		  }catch (Exception e) {
		 	e.printStackTrace();
		  } 
		 String filename = multi.getFilesystemName("filename");
		 
		// ������ �Ѿ�� �����͸� ����
		String section = multi.getParameter("section");
		String id = multi.getParameter("id");
		String board_subject = multi.getParameter("board_subject");
		String board_content = multi.getParameter("board_content");
		String ip = request.getRemoteAddr();
	
		// dto ��ü�� ������ ����
		BoardDTO dto = new BoardDTO();
		dto.setSection(section);
		dto.setId(id);
		dto.setBoard_subject(board_subject);
		dto.setBoard_content(board_content);
		dto.setIp(ip);
			//파일 이름 가져오기
		dto.setFile_name(filename);
		
		// dao ��ü ���� �� db ������ ���� �޼��� ȣ��
		BoardDAO manager = BoardDAO.getInstance();
		manager.boardWrite(dto);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("/expro/BoardList.do?section="+section);
	
		return forward;
	}
	
}
