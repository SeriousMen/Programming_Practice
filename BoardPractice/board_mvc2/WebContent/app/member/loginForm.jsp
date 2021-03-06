<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta>
      <title>로그인 페이지</title>
   </head>
   <body>
   <!--1.login 파라미터를 받았다면   -->
   <!--2.받은 login에 false가 담겨 있다면 (조건문안은 true여야하니까 앞에 not써줌)  -->
   <!--3.로그인 실패 시 alert창이 출력된다.(확실하게 하려고 if두개씀)  -->
   <c:if test="${not empty param.login}">
   	<c:if test ="${not param.login}">
   		<script> alert("아이디 또는 비밀번호를 다시 확인해주세요.");</script>
   	</c:if>
   </c:if>
      <form name="loginForm" action="${pageContext.request.contextPath}/member/MemberLoginOk.me" method="post">
         <center> <!--정상적으로 로그인으로 이동할 때는 login이라는 파라미터가 없고 실패했을 경우 컨트롤러에서 login에 false값을 담아주도록 한다. -->
            <table border="1" cellpadding="0" cellspacing="0" width="400px">
               <tr height="50px">
                  <td colspan="2" align=center>
                     <b><font size=5>로그인 페이지</font></b>
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">아이디</td>
                  <td align="center"><input type="text" name="memberId" style="width:280px;" /></td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">비밀번호</td>
                  <td align="center"><input type="password" name="memberPw"  style="width:280px;" /></td>
               </tr>
               <tr height="30px">
                  <td colspan="2" align=center>
                     <a href="javascript:loginForm.submit()">로그인</a>&nbsp;&nbsp;
                     <a href="${pageContext.request.contextPath}/member/MemberJoin.me">회원가입</a> 
                  </td>
               </tr>
            </table>
         </center>
      </form>
   </body>
</html>