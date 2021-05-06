package com.koreait.app.board;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.FilesVO;

public class BoardDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	req.setCharacterEncoding("UTF-8");
		
		BoardDAO b_dao = new BoardDAO();
		ActionForward forward = new ActionForward();
		
		//리눅스 서버로 출시 후 서비스 제공 시 경로는 아래와 같이 사용하여 작업한다.
		//req.getServletContext().getRealPath("/") 
		FilesDAO f_dao = new FilesDAO();
		String saveFolder = "C:\\0900_gb_cch\\jsp\\workspace\\board_mvc2\\WebContent\\app\\upload";
		
		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		int page = Integer.parseInt(req.getParameter("page"));
		
//		req.setAttribute("b_vo", b_dao.deleteBoard(boardNum));
		
		//파일 삭제(2개 로직 완성)
		//'업로드 경로에 있는' 파일 삭제
		for(FilesVO file :f_dao.getFileList(boardNum)) {
			File f = new File(saveFolder, file.getFileName());
			if(f.exists()) {
				f.delete();
			}
		}
		//DB삭제
		f_dao.deleteFile(boardNum);
		
		b_dao.deleteBoard(boardNum);
		//req.setAttribute("page", page);
		
		System.out.println("딜리트 컨트롤러 들어옴");
		forward.setRedirect(true);
//		forward.setPath("/app/board/boardList.jsp");
		forward.setPath(req.getContextPath()+"/board/BoardList.bo?page="+page);	
		
		return forward;
	}
}
