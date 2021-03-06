package com.koreait.app.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;

//컨트롤러
public class MemberCheckIdOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//전달받은 데이터 중 한글이 있다면 깨지지 않도록 방지 
		req.setCharacterEncoding("UTF-8");
		
		String id = req.getParameter("id");
		MemberDAO m_dao = new MemberDAO();
		
		PrintWriter out = resp.getWriter();
		
		resp.setContentType("text/html;charset=utf-8");
		//Ajax에서 요청한 데이터를 DB에서 조회한 후 
		//text로 출력하여 응답해준다.
		
		if(m_dao.checkId(id)) {
			//not-ok
			out.println("not-ok");//해당 페이지로 이동하는 것이 아니라 출력된 화면은 나오지않음 다만 ajax에서 요청시 해당 값을 넘겨준다. 
			
		}else {
			//ok
			out.println("ok");
			
		}
		out.close();
		//페이지 이동을 하지 않는다.(그냥 결과 만 실시간으로 ajax한테 넘겨주는 목적이니까)
		return null;
	}
	
}
