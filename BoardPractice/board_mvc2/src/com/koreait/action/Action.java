package com.koreait.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 모든 컨트롤러에서 구현해야 할 execute메소드를 추상 메소드로 선언해 놓는다.
// 재사용 목적, 개발 속도 상승 
//리턴 타입을 정해놓고 컨트롤러에서 정보를 전달할 때 해당 리턴 값에 담겨있는 메뉴얼에 정보를 담고 전달한다.
public interface Action {
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
