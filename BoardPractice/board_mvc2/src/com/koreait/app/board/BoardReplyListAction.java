package com.koreait.app.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.vo.BoardReplyVO;
//Ajax용
public class BoardReplyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		
		PrintWriter out = resp.getWriter();
		BoardDAO r_dao = new BoardDAO();
		
		JSONArray arReply = new JSONArray(); //제이슨 타입의 배열을 선언해준다.
		
		List<BoardReplyVO> replys = r_dao.getReplyList(boardNum);	//해당 게시글의 댓글들을 다 담아준다.
		
		for(BoardReplyVO r_vo :replys) { // 담아 준 댓글들을 키:벨류의 형태로 제이슨타입으로 하나씩 
			JSONObject reply = new JSONObject();
			reply.put("replyNum", r_vo.getReplyNum());
			reply.put("memberId", r_vo.getMemberId());
			reply.put("replyContent", r_vo.getReplyContent());
			arReply.add(reply);
		}
		out.println(arReply.toJSONString());
		out.close();
		return null;
		
	}
}
