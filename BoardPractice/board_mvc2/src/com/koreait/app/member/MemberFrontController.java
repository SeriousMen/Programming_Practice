package com.koreait.app.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;
//서블릿파일(프론트컨트롤러)
//만약 요청한 응답 페이지가 '흰색 화면'이 나온다면 문법오류 혹은 페이지 이동 오류일 가능성이 있다.
public class MemberFrontController extends HttpServlet {

	/**
	 * JVM의 메모리에서만 머물러 있던 객체를 시스템이 종료되거나 네트워크로 통신을 할 때에도 그대로 사용할 수 있도록
	 * 영구화 목적으로 직렬화를 사용한다. 직렬화 된 객체는 byte형태로 변환되어 있으며, 다시 사용하고 싶을 때에는 역직렬화를 통해서 객체로 변환된다.
	 * 이 때 SUID(SerialVersionUID)라는 값을 고정시켜 구분점으로 사용해서 사용자가 원하는 객체가 맞는지 판단하게 된다.
	 * 자주 변경되는 클래스 객체에는 사용하지 않는 것이 좋다.
	 * 
	 * 해당 객체를 우리가 직접 메모리에 할당하지않더라도 그냥 byte형태로 고정시키는 것 (직렬화) -->영구화 목적 
	 * 여러가지 있을 텐데 뭘로 구분해? UID로 구분해 
	 * 다시 쓸 때 풀어서 데이터형태로 쓰는 것(역 직렬화)
	 *  
	 */
	private static final long serialVersionUID = 1L;//직렬화

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청된 방식에 따라 doGet 또는 doPost가 실행될 때 모두 같은 로직이 실행되어야 하므로 doProcess메소드 선언 후 사용가능하다.
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	//비지니스 핵심 로직을 모아둔 메소드
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		//클라이언트가 요청한 전체 URI , 분기처리 여기서
		String requestURI = req.getRequestURI(); 
		String contextPath = req.getContextPath(); //도메인부터 계속 반복되는 url(ex , www.naver.com)을 컨텍스트패스라고한다.(사용자의 요청을 구분하기 위해서는 불필요한 부분)소스파일에서는 프로젝트명
		String command = requestURI.substring(contextPath.length()); //컨텍스트패스를 잘라준다.(사용자의 요청이 무엇인지 판단할 수 있는 부분을 담는다.)
		
		// 컨트롤러에서 응답할 정보를 담아줄 객체 
		ActionForward forward = null;
//		요청에 따른 분기처리를 해준다 if~else if, else 말고 switch문을 사용하는 것이 더 깔끔하지만 그냥 여기서는 이렇게 (switch는 반드시 case마다 break해줘야함)
		if(command.equals("/member/MemberJoinOk.me")) { // /member/를 붙여주는 이유는 사용자의 요청이 어떤 소속인지 가독성을 높이기 위해서이다
			try {
				// 사용자의 요청에 맞는 응답을 하기위해서 일치하는 컨트롤러를 할당하고 그안에 있는 execute메소드를 사용해준다. 그리고 리턴 값을 forward에 담아서 일괄처리한다.
			forward = new MemberJoinOkAction().execute(req, resp);
			} catch (Exception e) {e.printStackTrace();}//이렇게 하면 오류의 원인을 출력해준다.
		}else if(command.equals("/member/MemberJoin.me")) {	//이 다음 부터는 별겨없다 데이터 연산에 따른 기능(jsp페이지 혹은 데이터 처리 종류)마다 분기처리를 해준다
			forward = new ActionForward();//여기서 forward를 초기화하는 이유는 이곳에서 주소를 설정하기 위해서이다.
			forward.setRedirect(false); 
			forward.setPath("/app/member/joinForm.jsp");
			
		}else if(command.equals("/member/MemberLogin.me")) {
			//전달받은 login 값을 가져온다.
			String login = req.getParameter("login");
			forward = new ActionForward();
			forward.setRedirect(false); //아래의 주소에 get방식으로 값을 담아서 보내준다.																
			forward.setPath("/app/member/loginForm.jsp"+ (login !=null? "?login=false" :"")); //건내받은 login이 값이 있으면 값에 false들어간다.
			
		 }else if(command.equals("/member/MemberLoginOk.me")) {
			 try {
				System.out.println("MemberLoginOk.me들어옴"); //프로그램 실행 중에 오류가 있다면 이렇게 출력문을 통해 어느 부분까지 되고 어느 부분에서 막히는지 알아보는 게 좋다.
															//*출시하거나 다 끝났다면 내가 작성한 출력문은 지워주는 것이 깔끔하다고는 하는데... 
				forward = new MemberLoginOkAction().execute(req, resp);
			} catch (Exception e) {;}

		}else if(command.equals("/member/MemberCheckIdOk.me")) {
			try {
				forward = new MemberCheckIdOkAction().execute(req, resp);
			} catch (Exception e) {;}
		}else if(command.equals("/member/MemberLogout.me")) {
			try {
				forward = new MemberLogoutAction().execute(req, resp);
			} catch (Exception e) {;}
		}else{
			//만약 위의 모든 분기에 들어가지 않았다면, 요청한 URI가 잘못된 것이다.(뷰에서 전송한 주소)
			//따라서 컨트롤러를 호출할 필요 없이 직접 404 오류 안내 페이지로 이동시켜준다.
			forward = new ActionForward();
			forward.setRedirect(false); // 뭐가 들어가도 상관없으니까 그중에 성능 좋은 걸로 하기 위해 false
			forward.setPath("/app/error/404.jsp");
		}
		
		//위의 분기처리가 끝난 후 응답을 일괄처리해준다.
		if(forward != null) {// 응답할게 있다면 
			if(forward.isRedirect()) {
				//redirect방식으로 새로운 세션을 열어 이전 request 객체 초기화 후 응답
				resp.sendRedirect(forward.getPath()); // 어디로 이동할지 path가 알고 있으니까
			}else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);	//forward 방식은 request객체와 response객체를 가지고 가야하니까 				
			}
		}
			
	}
}
