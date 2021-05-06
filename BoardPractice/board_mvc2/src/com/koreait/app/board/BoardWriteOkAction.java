package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.BoardVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteOkAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		req.setCharacterEncoding("UTF-8"); 파일첨부때는 쓸 필요가 없음
		
		BoardVO b_vo = new BoardVO();
		BoardDAO b_dao = new BoardDAO();
		FilesDAO f_dao = new FilesDAO();
		ActionForward forward = null;
		
		String saveFolder ="C:\\0900_gb_cch\\jsp\\workspace\\board_mvc2\\WebContent\\app\\upload"; //절대경로 설정
				
		int fileSize = 5*1024*1024; //용량 제한 5M(메가)
		
		MultipartRequest multi = null; //req객체 말고 multi에 담아서 보내준다.(text형태의 자료말고 file타입의 자료도 받았으니 multipart/form받을떄
		
		//DefaultFileRenamePolicy : 파일 업로드 및 다운로드 정책( 같은 이름이 존재하면 자동으로 이름이 변경되도록 한다.) 
		multi = new MultipartRequest(req, saveFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
									
		b_vo.setBoardTitle(multi.getParameter("boardTitle"));
		b_vo.setBoardId(multi.getParameter("boardId"));
		b_vo.setBoardContent(multi.getParameter("boardContent"));
		
		if(b_dao.insertBoard(b_vo)) { //파일첨부는 DAO에서 동적데이터 처리가 중요하다.
			if(f_dao.insertFiles(b_dao.getBoardNum(), multi)) {
				
				//첨부파일 추가
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath(req.getContextPath() + "/board/BoardList.bo");
			}
		}
		return forward;
	}
}


















