<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>회원가입 페이지</title>
   </head>
   <body>
   
   		<!--주석처리 -->
   		<%--주석처리 --%> <!-- html주석은 콘솔창에서 소스코드에서 보이지만 jsp주석은 보이지 않는다. 그래서 el문표현이나 표현문을 html주석으로 감싸면 주석처리가 안될 때 jsp주석을 사용하자 -->  -->
   		<%-- ${}은 EL문으로서 자바 코드를 사용할 수 있다. pageContetxt는 JSP내장객체이므로 서블릿으로 넘어갈 때 웹 컨테이너가 자동으로 할당 --%>
      <form name="joinForm" action="${pageContext.request.contextPath}/member/MemberJoinOk.me" method="post">
         <center>	<!--center태그는 HTML5에서는 지원을 안하지만 현재 이 JSP파일의 DOCTYPE은 HTML 4 -->
            <table  border="1" cellpadding="0" cellspacing="0" width="25%">
               <tr height="50px">
                  <td colspan="2" align=center>
                     <b><font size=5>회원가입 페이지</font></b>
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">아이디</td>
                  <td><input type="text" name="memberId" style="width:280px;" /><%--name은 값을 넘길 때참조명이라 유의미하게 지정해주어야한다. --%>
                  <p id="idCheck_text"></p>	<%-- 아무런 값(내용)이 없는 p태그를 생성해서 id중복검사 결과를 출력할 것이다 --%>
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">비밀번호</td>
                  <td><input type="password" name="memberPw" style="width:280px;" /></td><%-- 입력받는 데이터의 종류에 어울리게 type을 설정해주어야한다. --%>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">이름</td>
                  <td><input type="text" name="memberName" style="width:280px;" /></td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">나이</td>
                  <td><input type="text" name="memberAge" style="width:50px; margin-left:5px;" />살</td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">성별</td>
                  <td>
                     <input type="radio" name="memberGender" value="남" checked/>남자<%--radio버튼이 있는 태그, 이처럼 type에는 여러 종류가 있으니 때에 맞게 활용할줄 알아야한다. --%>
                     <input type="radio" name="memberGender" value="여"/>여자
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">이메일</td>
                  <td><input type="text" name="memberEmail" style="width:280px;" /></td><%-- 이메일을 입력받는 곳이므로 email타입으로 만드는게 상황에 맞지만 정규식으로 양식을 제한할 수 있다. --%>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">우편번호</td>
                  <td>
                  <input type="text" name="memberZipcode" class="postcodify_postcode5" value="" readonly/>
                  <input type="button" id="postcodify_search_button" value="검색"><br /><%--주소값을 입력받는 곳은 readonly로 막아놓고 주소검색API를 통해 값을 검색할 것 --%>
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">주소</td>
                  <td>
                     <input type="text" name="memberAddress" class="postcodify_address" value="" style="width:280px;" readonly/><br />
                  </td><%--주소검색API가 주소도 가져오니 여기도 readonly --%>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">상세주소</td>
                  <td>
                     <input type="text" name="memberAddressDetail" class="postcodify_details" value="" style="width:280px;" /><br />
                  </td><%--여기는 따로입력 --%>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">참고항목</td>
                  <td>
                     <input type="text" name="memberAddressEtc" class="postcodify_extra_info" value="" style="width:280px;" readonly /><br />
                  </td><%--주소검색API가 무슨 동인지도 가져온다. --%>
               </tr>
               <tr height="40px">
                  <td colspan="2" align="center">
                     <a href="javascript:formSubmit()">회원가입</a>&nbsp;&nbsp;<%--유효성 검사하고 submit할꺼니까 자바스크립트를 실행하자 --%>
                     <a href="javascript:joinForm.reset()">다시작성</a>&nbsp;&nbsp;<%--reset()은 입력데이터 초기화를 위한 DOM메소드 --%>
                     <a href="${pageContext.request.contextPath}/member/MemberLogin.me">로그인</a><%--로그인시 전달 할 값 and 데이터연산이 필요하니 컨트롤러를 거치게 --%>
                  </td>
               </tr>
            </table>
         </center>
      </form>
   </body><%--아래와 같이 직접 라이브러리를 추가하지않고 태그로 참고하는 것을 CDN방식이라고 한다. --%>
<script src="//code.jquery.com/jquery-3.5.1.min.js"></script>	<!--제이쿼리 쓰려고  연결-->
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script><!--도로랑 우편주소 검색하려고 연결  -->
<script> var contextPath = "${pageContext.request.contextPath}";</script> <!--EL문은 .jsp에서만 사용가능하기 때문에   이렇게 .js에 변수로 담아서 넘겨준다.-->
<script src="${pageContext.request.contextPath}/app/member/join.js"></script> <!--외부js파일 적용 -->
<script> $(function() {$("#postcodify_search_button").postcodifyPopUp();}); </script><!--'검색'버튼을 누르면 주소검색팝업레이어가 열리도록  -->
</html>