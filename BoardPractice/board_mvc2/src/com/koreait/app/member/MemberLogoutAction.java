package com.koreait.app.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;

public class MemberLogoutAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();//세션반환하고
		ActionForward forward = new ActionForward();
		
		
		//존재하는 세션삭제
		session.invalidate(); // 이것 전체 삭제하는 거고 만약 어느 세션은 삭제 시켜야하고 어느 세션은 유지시켜야된다면 removeAttribute(세션이름) 사용해라
		
		forward.setRedirect(true);
		forward.setPath(req.getContextPath()+"/board/BoardList.bo");
		
		return forward;
	}
}
