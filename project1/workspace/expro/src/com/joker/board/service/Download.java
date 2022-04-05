package com.joker.board.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.board.db.BoardDAO;
import com.joker.board.db.BoardDTO;
import com.joker.member.service.Action;
import com.joker.member.service.ActionForward;

import java.io.File;

public class Download implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");	

		int no = Integer.parseInt(request.getParameter("no"));
		
		BoardDAO manager = BoardDAO.getInstance();
		BoardDTO content = manager.boardContent(no);
		String filename = content.getFile_name();
		System.out.println("filename: "+filename);
		String uploadFileName = request.getRealPath("/upload") +"/"+ filename;

		File downFile = new File(uploadFileName);
		if(downFile.exists() && downFile.isFile())		// 다운로드
		{
			try
			{
				long filesize = downFile.length();

				response.setContentType("application/x-msdownload");
				response.setContentLength((int)filesize);

				String strClient = request.getHeader("user-agent");
				if(strClient.indexOf("MSIE 5.5")!=-1)
				{
					response.setHeader("Content-Disposition", "filename=" + filename + ";" );
				}
				else 
				{
				    filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+","%20");
					response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";" );
				}

				response.setHeader("Content-Length", String.valueOf(filesize));
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "private");
				
				byte b[] = new byte[1024];
				BufferedInputStream fin = new BufferedInputStream(new FileInputStream(downFile));
				BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
				int read = 0;
				while((read=fin.read(b)) != -1)
				{
					outs.write(b, 0, read);
				}
				outs.flush();
				outs.close();
				fin.close();
			} catch(Exception e) {
				System.out.println("Download Exception : " + e.getMessage());
			}
		} else {
			System.out.println("Download Error : downFile Error [" + downFile + "]");
		}

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/boardContent.jsp");
	
		return forward;
	}
}
