package com.koreait.app.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;

public class BoardReplyDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		BoardDAO r_dao = new BoardDAO();
		
		
		
		int replyNum=Integer.parseInt(req.getParameter("replyNum"));
		
		
		if(r_dao.deleteReply(replyNum)) {
			
			out.println("삭제성공");
		}else {
			out.println("삭제 실패");
		}
			out.close();
		//forward.setRedirect(true);
		//forward.setPath(req.getContextPath()+"/board/BoardView.bo?page="+page+"&boardNum="+boardNum);
		
		//return forward;
		return null;
		
		
	}
}
