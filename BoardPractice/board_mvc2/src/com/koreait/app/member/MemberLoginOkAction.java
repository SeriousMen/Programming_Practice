package com.koreait.app.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;

public class MemberLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession(); //로그인한 상태를 유지시켜줘야되니까 session을 생성해준다.
		
			//getSession(true or false) 디폴트가 true 
			// true : 서버에 생성된 세션이 있다면 세연을 반환하고 없다면 새 세션을 생성해서 반환한다.
			// false : 이미 생성된 세션이 있을 때 그 세션을 반환하고 없으면 null을 반환한다.그냥 isNew()쓰면됨 
		ActionForward forward = new ActionForward();
		
		MemberDAO m_dao = new MemberDAO();
		System.out.println("dao실행");
		String id = req.getParameter("memberId");
		String pw = req.getParameter("memberPw");
		System.out.println("MemberLoginOkAction들어옴");
		if(m_dao.login(id, pw)) { //매개변수 타입에 맞게 하는건 너무 당연해
			System.out.println("로그인성공");
			//로그인 성공 시 
			session.setAttribute("session_id", id); //"session_id"에 id값을 담아라 (세션이 유지되는 동안 저장된다.)
			forward.setRedirect(true); //로그인 성공시 redirect 어처피 세션에 담을 것(로그인 값만 담으면되니까?)
			forward.setPath(req.getContextPath()+"/board/BoardList.bo");//성공시 게시판쪽 프론트 컨트롤러에서 분기처리해라
		}else {//로그인 실패시
			System.out.println("로그인실패");
			forward.setRedirect(false);
			//로그인 실패시 경고창을 출력해주기 위해서 login 파라미터를 같이 전송해준다.
			forward.setPath("/member/MemberLogin.me?login=false"); //web.xml갔다가 프론트 컨트롤러로 (거기서 참고해서 다시 프론트컨트롤러에가서 매개변수의 값을 전달하라는것)
		}
		return forward;
	}

}
