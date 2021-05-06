package com.koreait.app.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;
import com.koreait.app.member.vo.MemberVO;

//컨트롤러(비즈니스로직을 담는다.)
public class MemberJoinOkAction implements Action{//뒤에 OK가 붙은건 연산이 필요한것 없는 것은 단순 페이지 이동

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8"); 
		ActionForward forward =null;
		
		MemberDAO m_dao = new MemberDAO();
		MemberVO m_vo = new MemberVO();
		
		
		
		//사용자가 form태그에서 입력한 정보를들 req로 받고name(키) 모델객체에 하나씩 채워 넣는다. DAO랑 member.xml에서 parameterType memberVO로 햿으니
		m_vo.setMemberId(req.getParameter("memberId"));
		m_vo.setMemberPw(req.getParameter("memberPw"));
		m_vo.setMemberName(req.getParameter("memberName"));
		m_vo.setMemberAge(Integer.parseInt(req.getParameter("memberAge")));
		m_vo.setMemberGender(req.getParameter("memberGender"));
		m_vo.setMemberEmail(req.getParameter("memberEmail"));
		m_vo.setMemberZipcode(req.getParameter("memberZipcode"));
		m_vo.setMemberAddress(req.getParameter("memberAddress"));
		m_vo.setMemberAddressDetail(req.getParameter("memberAddressDetail"));
		m_vo.setMemberAddressEtc(req.getParameter("memberAddressEtc"));
		
		//DB에서 INSERT실패 시
		if(!m_dao.join(m_vo)) {
			//직접 HTML문서로 응답한다.
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html;charset=utf-8");
			out.println("<script>alert('서버가 불안정합니다. 잠시 후 다시 시도해세요.');</script>");
			out.close();
		}else {
			//DB에서 INSERT 성공 시
			forward = new ActionForward();
			
			//이동할 페이지 정보를 담아서 리턴한다.
			forward.setRedirect(false); //뭘 써야될지 모를 땐 forward
			forward.setPath("/member/MemberLogin.me"); //바로 웹페이지로 이동하지않고 컨트롤러 거쳐서 jsp로 
			
		}
		//alert창 띄우고 페이지 이동 : 오류, 절대 불가능 -->컨트롤러에서 응답은 한번만 가능하기 때문에 
		// out.close; out.flush한 이후에는 forward가 return 안된다는 점 주의 그래서 위에는 요청이 실패할 경우에 PrintWriter를 해줬다. 
		return forward;
	}

}
