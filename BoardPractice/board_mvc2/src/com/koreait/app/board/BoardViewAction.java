package com.koreait.app.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.BoardReplyVO;
import com.koreait.app.board.vo.BoardVO;
import com.koreait.app.board.vo.FilesVO;

public class BoardViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		
		BoardDAO b_dao = new BoardDAO();
		BoardDAO r_dao = new BoardDAO();
		
		BoardVO b_vo = null;
		FilesDAO f_dao = new FilesDAO();
		ActionForward forward = null;
		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		
		int page = Integer.parseInt(req.getParameter("page"));
		
		b_vo = b_dao.getDetail(boardNum); //클릭한 해당 게시물번호를 담아서 그 게시물을 가져온다.
		
		List<FilesVO> fileList = f_dao.getFileList(boardNum); //해당 게시물 번호에 담겨있는 파일리스트들을 담아준다.
		List<BoardReplyVO> replyList = r_dao.getReplyList(boardNum); // 해당 게시물 번호에 담겨있는 댓글들을 담아준다.
		
		if(b_vo != null) { //만약 존재하는 게시물이라면 
			b_dao.updateReadCount(boardNum); //조횟수를 올려주고 댓글,내용물,페이지,파일을 담아서 보내줘라
			req.setAttribute("replies", replyList); 
			req.setAttribute("b_vo", b_vo);
			req.setAttribute("page", page);
			if(fileList != null) {
				req.setAttribute("files", fileList);
			}
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/board/boardView.jsp");
		}
		return forward;
	}
}

















