<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>MVC 게시판</title>
   </head>
   <body>
      <c:if test="${session_id eq null}">
         <script>
            alert("로그인 후 이용해주세요");
            location.replace("${pageContext.request.contextPath}/member/MemberLogin.me");
         </script>
      </c:if>
      <!--jstl사용해서 컨트롤러에서 담은 데이터를 다담아주기 .java에서의 키는 EL문을 통해서 가져오는것 같다. -->
      <c:set var = "list" value="${boardList}"/>
      <c:set var = "totalCnt" value="${totalCnt}"/>
      <c:set var = "startPage" value="${startPage}"/>
      <c:set var = "endPage" value="${endPage}"/>
      <c:set var = "nowPage" value="${nowPage}"/>
      <c:set var = "realEndPage" value="${realEndPage}"/>
      
      
      <center>
         <table border="0" cellpadding="0" cellspacing="0" width="900px" >
            <tr align="right" valign="middle">
               <td>
                  ${session_id}님 환영합니다.<%--세션은 유지되니까--%>
                  <a href="${pageContext.request.contextPath}/member/MemberLogout.me">로그아웃</a>
               </td>
            </tr>
         </table>
         <br />
         <br />
         
         <!-- 게시판 리스트 -->
         <table width="900px" border="0" cellpadding="0" cellspacing="0">
            <tr align="center" valign="middle">
               <td><h3>MVC 게시판</h3></td>
            </tr>
            <tr align="right" valign="middle">
               <td>글 개수 : <c:out value="${totalCnt}"/>개</td>
            </tr>
         </table>
         
         <table border="1" cellpadding="0" cellspacing="0" width="900px" >
            <tr align="center" valign="middle">
               <td width="8%" height="26">
                  <div align="center">번호</div>
               </td>
               <td width="50%">
                  <div align="center">제목</div>
               </td>
               <td width="14%">
                  <div align="center">작성자</div>
               </td>
               <td width="17%">
                  <div align="center">날짜</div>
               </td>
               <td width="11%">
                  <div align="center">조회수</div>
               </td>
            </tr>
            <c:choose>
            <c:when test="${list !=null and fn:length(list)>0}">
            <c:forEach var ="b_bean" items="${list}"><%--페이제에 나오는 값을 list에 담았으니 한 줄씩 뽑아줘야지 한행씩 반복해서 전체 페이지를 만든다. --%>
            <tr align="center" valign="middle" onmouseover="this.style.backgroudColor='F8F8F8'" onmouseout="this.style.backgroundColor='FFFFFF'">
                        <td height="23" style="font-family:Tahoma; font-size:10pt;">
                           ${b_bean.getBoardNum()}
                        </td>
                        <td style="font-family:Tahoma; font-size:10pt;">
                        <div align ="left">
                        	<a href="${pageContext.request.contextPath}/board/BoardView.bo?boardNum=${b_bean.getBoardNum()}&page=${nowPage}">
                        	${b_bean.getBoardTitle()}</a>
                        	</div>
                        </td>
                        <td>
                        	<div align="center">
                        		${b_bean.getBoardId()}
                        	</div>
                        </td>
                        <td>
                        	<div align="center">
                        		${b_bean.getBoardDate()}
                        	</div>
                        </td>
                       <td>
	               			<div align="center">
	               				${b_bean.getReadCount()}	
	               			</div>
	               		</td>
                        <!--모델 객체(DTO)로 데이터로 전달하고 그게 안되면 파라미터로 전달한다.  -->
                        
                     </tr>
            </c:forEach>
            </c:when>
            <c:otherwise>
            <tr>
            	<td colspan="5" align="center">등록된 게시물이 없습니다.</td>
             </tr>
            </c:otherwise>
            </c:choose>
         </table>
         <br/>
         <table border="0" cellpadding="0" cellspacing="0" witdh="900px">
         	<tr align ="center" valign="middle">
         		<td>		<%--페이징 처리 --%>
         		<c:if test="${nowPage>1}"> <%--2페이지 이상일시만 나와야하니까 --%>
         			<a href ="${pageContext.request.contextPath}/board/BoardList.bo?page=${nowPage-1}">[이전]</a>
         		</c:if>
         		<c:forEach var = "i" begin="${startPage}" end="${endPage}">
         			<c:choose>
         				<c:when test ="${i eq nowPage}" > 
         					[${i}]&nbsp;
         				</c:when>
         				<c:otherwise> <%--현재 페이지가 아닌건 선택해서 전달할 수 있게 연결해준다. --%>
         					<a href ="${pageContext.request.contextPath}/board/BoardList.bo?page=${i}"> ${i}&nbsp;</a>
         				</c:otherwise>
         			</c:choose>
         		</c:forEach>
         		<c:if test= "${realEndPage != nowPage }"><%--다음 페이지가 더남아있다면 --%>
         			<a href = "${pageContext.request.contextPath}/board/BoardList.bo?page=${nowPage+1}">[다음]</a>
         		</c:if>
         		</td>
         	</tr>
         </table>
         <table border="0" cellpadding="0" cellspacing="0" width="900px">
            <tr align="right" valign="middle">
               <td><a href="${pageContext.request.contextPath}/board/BoardWrite.bo?page=${nowPage}">[글쓰기]</a></td>
            </tr>
         </table>
      </center>
   </body>
</html>