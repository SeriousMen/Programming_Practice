package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;

public class BoardListOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		BoardDAO b_dao = new BoardDAO();
		ActionForward forward = new ActionForward();
		String temp = req.getParameter("page"); // 웹페이지에서 페이지를 클릭할 때마다 그 해당 페이지에 맞는 데이터를 불러와서 보여주기위해 (페이징처리:보여줄 데이터만 가져온다.)
		
		// 요청한 페이지가 없다면 default로 1페이지를 응답해주고
		// 요청한 페이지가 있다면 해당 페이지로 응답해준다.
		int page = temp == null? 1 : Integer.parseInt(temp);
		
		// 한 페이지 당 ?개의 개시글이 보이도록 설정 
		int boardSize = 10;
		//한 화면에 선택할 페이지의 수 
		int pageSize = 10;
		
		// 한 페이지에서 가장 마지막  행 번호 
		int endRow = page*boardSize; // 딱 나눠 떨어지지않아도 그안에 있는 내용만 나오면 되기 때문에 따로 연산처리를 안해줘도 된다.(보통 최신글 쪽은 다채
		
		// 한 페이지에서 가장 첫번째 행 번호
		int startRow = endRow -(boardSize -1);  //9라고 안하고 pageSize-1을 해줘야 유지보수가 용이해진다.(이게 바로 객체지향적인 개발이 아닐까)
		
		// 페이지 번호(가로)
		int startPage = ((page -1)/ pageSize)*pageSize +1; //1~10 (-1하니까) 0~9 (나누면) ->0 그다음 1, 2 이런순이라 곱해주기
		int endPage = startPage + (pageSize -1); //10개의 페이지가 보이도록 
		
		int totalCnt = b_dao.getBoardCnt(); //등록된 게시물의 수 
		int realEndPage = (totalCnt-1)/pageSize +1;
		
		endPage = endPage >realEndPage ? realEndPage : endPage; //10페이지를 못채울만한 게시물 수일 때는 realEndPage가 진짜 endPage 그이상일 시는 그냥 endPage
		
		//MAP 방식
		//requestScope( 컨틀롤러에서 데이터를 담아서 요청한다.)
		req.setAttribute("totalCnt", totalCnt);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("nowPage", page);
		req.setAttribute("realEndPage", realEndPage);
		req.setAttribute("boardList", b_dao.getBoardList(startRow, endRow)); //출력될 화면에 맞게 데이터를 가져온다.
		
		//forward를 사용할 때 : req객페에 데이터를 담아서 전달해야 할 때(Forward일 때 기존의 객체가 유지되서 전달된다.)
		//redirect를 사용할 때 : 전달할 req 데이터가 없고 값을 초기화하고 싶을 때
		forward.setRedirect(false);
		forward.setPath("/app/board/boardList.jsp"); //req객체를 여기로 전달하고 이동해 
		
		
		return forward;
	}
}
