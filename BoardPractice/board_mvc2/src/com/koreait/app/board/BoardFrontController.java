package com.koreait.app.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;

//만약 요청한 응답 페이지가 흰색 화면이 나온다면 문법오류 혹은 페이지 이동 오류일 가능성이 있다.
public class BoardFrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		//클라이언트가 요청한 전체 URI , 분기처리 여기서
		String requestURI = req.getRequestURI(); 
		String contextPath = req.getContextPath(); //도메인부터 계속 반복되는 url(ex , www.naver.com)을 컨텍스트패스라고한다.(사용자의 요청을 구분하기 위해서는 불필요한 부분)
		String command = requestURI.substring(contextPath.length()); //컨텍스트패스를 잘라준다.(사용자의 요청이 무엇인지 판단할 수 있는 부분을 담는다.)
		
		// 컨트롤러에서 응답할 정보를 담아줄 객체 
		ActionForward forward = null;
		
		// 여기서는 switch문으로 분기처러해줄께
		switch(command) {
		case "/board/BoardList.bo": //login컨트롤러에서 자연스럽게 성공시 여기로 넘겨줬지 여기도 member프론트 컨트롤러와 다를 것 없이 흐름에 맞게 분기처리,데이터처리,주소처리를 해준다.
			try {
				forward = new BoardListOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break; //멈추고 빠져나가야 하니까 break를 꼭해줘... 기본
		case "/board/BoardWriteOk.bo":
			try {
				forward = new BoardWriteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardView.bo":
			try {
				forward = new BoardViewAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardModify.bo":
			try {
				forward = new BoardModifyAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardModifyOk.bo":
			try {
				forward = new BoardModifyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/FileDownload.bo":
			try {
				forward = new FileDownloadAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardDeleteOk.bo":
			try {
				forward = new BoardDeleteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardWrite.bo":
			forward = new ActionForward();
			forward.setRedirect(false);
			req.setAttribute("page",req.getParameter("page"));
			forward.setPath("/app/board/boardWrite.jsp");
		
			break;
		case "/board/BoardReplyOk.bo":
			System.out.println("BoardReplyOk.bo들어옴");
			try {
				forward = new BoardReplyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardReplyList.bo":
			System.out.println("BoardReplyList.bo들어옴");
			try {
				forward = new BoardReplyListAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardReplyDeleteOk.bo":
			System.out.println("BoardReplyDeleteOk.bo들어옴");
			try {
				forward = new BoardReplyDeleteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "/board/BoardReplyModifyOk.bo":
			System.out.println("BoardReplyModifyOk.bo들어옴");
			try {
				forward = new BoardReplyModifyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		default:
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/error/404.jsp");
		}
		
		//일괄처리
		if(forward != null) {
			if(forward.isRedirect()) {
				
				resp.sendRedirect(forward.getPath()); 
			}else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);			
			}
		}
	}
}
