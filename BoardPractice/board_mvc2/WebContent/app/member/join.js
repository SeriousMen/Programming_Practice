/**
 * 회원가입
 */

/**
 * 전역 변수: 함수 밖에서 선언되거나 함수 안에서 var 키워드 없이 선언.
   지역 변수: 함수 안에서 var 키워드로 선언. 선언된 함수 내에서만 사용 가능.

 */
	var check = false; // 이것은 전역 변수 (전체 유효성 검사를 위한 flag)
	
	function formSubmit(){
		var form = document.joinForm; //joinForm를 가져와서 form변수에 담는다.
		
		//아이디 검사
		if(form.memberId.value =="" || !check){ //입력안했거나 check가 true면(!check가 true라는 건 곧 check가 false라는 의미기에 유효성 검사를 충족 못시킨것)
			alert("아이디를 확인해주세요.");
			form.memberId.focus();	//해당 입력창으로 이동하고
			return false; //여기서 끊고 호출된 함수를 탈출(그만 실행) 
		}
		
		form.submit(); //아니라면 submit해라
		
	}
	// 외부에서 사용자가 입력한 id값을 입력받는다.
	function checkId(id){//키를 입력할 때 마다 검사하기 위해//dom으로 안하고 전달받아서 하는 이유
		check = false;//사용자가 id를 입력할 때 마다 Flag를 false로 바꿔준다. (검사하기위해)
		
		if(id ==""){ //id값 비였으면 
			$("#idCheck_text").text("아이디를 작성해주세요.");
		}else{
			$.ajax({//제이쿼리에서는 Ajax를 이렇게 쓴다.jQuery Ajax는 JSON타입으로 필요한 요소를 전달하고 {}안에는 Json을 여러개 전달 받으려고
				url:contextPath+"/member/MemberCheckIdOk.me?id="+id,	//get방식으로 전달, 외부.js에서 EL문을 쓸 수 없다.따라서 EL문의 값을.jsp에서 변수에 담아서 써준다..
				type:"get",//method 어떤방식으로 데이터를 보낼 지 
				dataType:"text", //전달'받을' 데이터 타입, 성공시 text의 값이 아래 result에 들어간다.
				success:function(result){ //요청 성공 시 응답 값을 result로 받는다.
					if(result.trim() =="ok"){	//trim은 문자열 좌우공백을 제거해주는 함수
						check =true;//MemberCheckIdOkAction에서 ok, not-ok 값가져와서 아래로 분기처리
						$("#idCheck_text").text("사용할 수 있는 아이디입니다..");
					}else{
						$("#idCheck_text").text("중복된 아이디입니다.");
					}
				},
				error:function(){ // 통신 오류시
					console.log("오류");
				}
			}); 
		}
	}
	
	
//키 이벤트 함수
	$("input[name='memberId']").keyup(function(event){ //누르는게 너무 빨라서 keydown이 아닌 keyup(키입력 후) 으로 
		var id = $("input[name='memberId']").val();
		checkId(id);	//키를 입력할 때마다 위에 선언된 중복검사메소드를 실행한다.
	});