<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MVC 게시판</title>
	</head>
	<body><%--담긴거 여기서 또 담아야지 --%>
		<c:set var="b_vo" value="${b_vo}"/>
		<c:set var="replies" value="${replies}"/>
		<c:set var="files" value="${files}"/>
		<center>
		<c:choose>
			<c:when test="${session_id eq null}">
			<script>
				alert("로그인 후 이용해주세요");
				location.replace("${pageContext.request.contextPath}/member/MemberLogin.me");
			</script>
			</c:when>
			<c:otherwise>
			<table border="0" cellpadding="0" cellspacing="0" width="900px" >
				<tr align="right" valign="middle">
					<td>
						${session_id} 님 환영합니다.
						<a href="${pageContext.request.contextPath}/member/MemberLogout.me">로그아웃</a>
					</td>
				</tr>
			</table>
			</c:otherwise>
			</c:choose>
			<br />
			<br />
		
			<table width="900px" border="0" cellpadding="0" cellspacing="0">
				<tr align="center" valign="middle">
					<td><h3>MVC 게시판</h3></td>
				</tr>
			</table>
				
			<table width="900px" border="1" cellpadding="0" cellspacing="0">
				<tr height="30px">
					<td align="center" width="150px">제 목</td>
					<td style="padding-left:10px;">${b_vo.getBoardTitle()}</td>
				</tr>
				
				<tr height="30px">
					<td  align="center" width="150px">아이디</td>
					<td style="padding-left:10px;">${b_vo.getBoardId()}</td>
				</tr>
				
				<tr height="200px">
					<td align="center" width="150px">내 용</td>
					<td valign="top" style="padding-top:10px; padding-left:10px;">${b_vo.getBoardContent()}</td>
				</tr>
				
				<c:if test="${files != null}"> <%--파일이 없는데 이 태그를 보일 필요는 없지 --%>
					<tr height="30px">
						<td align="center">첨부파일</td>
						<td>
							<c:forEach var="file" items="${files}"> <%--파일 다운받으려면 해당 파일네임을 넘겨줘서 컨트롤러에서 처리해줘야지 --%>
								<a href="${pageContext.request.contextPath}/board/FileDownload.bo?fileName=${file.getFileName()}">${file.getFileName()}</a>
							</c:forEach>
						</td>
					</tr>
				</c:if>
			</table>
			<table width="900px" border="0" cellpadding="0" cellspacing="0">
				<tr align="right" valign="middle">
					<td>
						<c:if test="${b_vo.getBoardId() eq session_id}">
							<a href="${pageContext.request.contextPath}/board/BoardModify.bo?boardNum=${b_vo.getBoardNum()}&page=${page}">[수정]</a>
							<a href="javascript:deleteBoard()">[삭제]</a>
						</c:if>
						<a href="${pageContext.request.contextPath}/board/BoardList.bo?page=${page}">[목록]</a>
					</td>
				</tr>
			</table>
			<!--삭제 버튼을 누르면 필요한 데이터를 가지고 컨트롤러로 향할 수 있게 hidden으로 선언해준다.(form)그 자체는 공간을 차지하지 않는다.(감싸는 역할)  -->
			<form name="deleteBoard" method="post" action="${pageContext.request.contextPath}/board/BoardDeleteOk.bo">
				<input type="hidden" name="boardNum" value="${b_vo.getBoardNum()}">
				<input type="hidden" name="page" value="${page}">
			</form>
			<!-- 댓글 --> <!-- table을 id를 줘서 선언하고 댓글이 달릴 때마다 AJAX로 이동없이 그 줄을 가져와 준다.  -->
			<table id="replyTable"></table>
		</center>
	</body>
	<script src="//code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script> <!--textarea 자동크기조절API -->
	<script>
		var boardForm = document.deleteBoard;
		function deleteBoard(){
			boardForm.submit();
		}
	</script>
	<script>
		check = false;
		
		$(document).ready(function(){getList();}); // 웹페이지가 화면에 보이기 전에 실행한다.(DOM이 준비됬을 때) --> 댓글 List를 가져온다.
													
		//자주 쓸 것은 재사용하기 편하게 
		var pageContext = "${pageContext.request.contextPath}";
		var boardNum = "${b_vo.getBoardNum()}";
			
		//댓글 추가(Ajax로 실시간으로 등록)
 		function insertReply(){
			var replyContent = $("textarea[name='replyContent']").val(); 
			$.ajax({
				url : pageContext + "/board/BoardReplyOk.bo",
				type : "post",
				data : {"replyContent" : replyContent, "boardNum" : boardNum},
				dataType : "text",
				success : function(result){
					alert(result);
					getList(); // 가져온 다음 다시 댓글 보기를 실행시켜줘서 뿌려준다.
				}
			});
		}
		
		//댓글 목록(해당 게시글 번호를 넘겨서 목록을 뿌려준다.)
		function getList(){
			$.ajax({
				url : pageContext + "/board/BoardReplyList.bo?boardNum=" + boardNum,
				type : "get",
//				dataType : "text",
				dataType : "json", //제이슨형식의 데이터를 반환받겠다.
//				success : function(result){
//					var replys = JSON.parse(result);
//					console.log(replys);
//				success : function(replys){
//					console.log(replys);
				success : showList // 성공시 showList(댓글 목록을 가져와라) ajax는 따로 리턴 값을 표시하지 않아도 success시 함수를 썻기 때문에 값이 들어간다.
			});
		}
		
 		function showList(replys){
//			console.log(replys);
			var insertReply_HTML = "<tr height='200px'>	<td align='center' width='80px'><div align='center'>댓 글</div></td>"
			+ "<td style='padding-left:10px'><textarea name='replyContent' style='width:750px; height:85px; resize:none;''></textarea>"
			+ "<a href='javascript:insertReply()''>[등록]</a></td></tr>";
			
			var text = insertReply_HTML;
			
			if(replys != null && replys.length != 0){ //리턴을 받은 값이 비어있지 않다면 
				$.each(replys, function(index, reply){
					text += "<tr><td align='center' width='150px'>" + reply.memberId + "</td>";
					text += "<td valign='top' style='padding-left:10px;'>";
					text += "<textarea name='content" + (index + 1) + "' id='" + (index + 1) + "' class='re' style='width:750px; height:85px; resize:none;' readonly>" + reply.replyContent + "</textarea>";
						//여기서 index에 +1안하고 그냥 index해도 문제가 없을 것 같음 
					if("${session_id}" == reply.memberId){ //작성자라면 해당 댓글을 수정 삭제 할 수 있게
							text += "<a id='ready" + (index + 1) + "' href='javascript:updateReply(" + (index + 1) + ")'>[수정]</a>";
							text += "<a id='ok"+ (index + 1) + "' href='javascript:updateOkReply(" + reply.replyNum + ", " + (index + 1) + ")' style='display:none;'>[수정 완료]</a>";
							text += "<a href='javascript:deleteReply(" + reply.replyNum + ")'>[삭제]</a>"
					}
					text += "</td></tr>";
				});
			}else{
				text += "<tr align='center'><td align='center' width='150px' colspan='2'>댓글이 없습니다.</td></tr>";
			}
			
			$("#replyTable").html(text);
			autosize($("textarea.re"));
		} 
 		
 		//삭제 Ajax
 		function deleteReply(replyNum){ //삭제하고자하는 댓글의 번호를 받아서 넘겨주고 컨트롤러에서 연산처리를 해준다.
			$.ajax({
				url : pageContext + "/board/BoardReplyDeleteOk.bo",
				type : "post",
				data : {"replyNum" : replyNum},
				dataType : "text",
				success : function(result){
					alert(result);
					getList(); //삭제를 하고 다시 댓글 목록을 불러와야하니까 
				}
			});
		}
 		
		//댓글 수정
 		function updateReply(num){//수정 버튼을 눌렀을 때 일어나는 이벤트
			if(!check){
				var textarea = $("textarea#" + num);
				var a_ready = $("a#ready" + num);
				var a_ok = $("a#ok" + num);
		
				textarea.removeAttr("readonly");
				a_ready.hide();
				a_ok.show();
				check = true;
			}else{
				alert("수정 중인 댓글이 있습니다.");
			}
		}
		
		//댓글 수정완료 
		function updateOkReply(replyNum, seq){
			var content = $("textarea#"+seq).val();
			console.log(content);
			$.ajax({
				url : pageContext + "/board/BoardReplyModifyOk.bo",
				type : "post",
				data : {"replyNum" : replyNum, "content" : content},
				dataType : "text",
				success : function(result){
					alert(result);
					check = false; // 새로고침이되는게 아니니까 
					getList();
					
				}
			
				});
			
		
		}
		
		
	</script>
</html>




















