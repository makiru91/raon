<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>${ section }-${ content.board_subject }</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$(function(){
		// 추천버튼 클릭시(추천 추가 또는 추천 제거)
		$("#rec_update").click(function(){
			$.ajax({
				url: "/expro/RecUpdate.do",
                type: "POST",
                data: {
                    no: '${content.board_no}',
                    id: '${id}'
                },
                success: function () {
			        recCount();
                },
			})
		});
		
		$("#boardDelete").click(function(){
			var result = confirm("게시글을 삭제하시겠습니까?");
			if(result){
				location.href = '/expro/BoardDelete.do?section=${param.section}&num=${param.num}&no=${content.board_no}';
			}
		});
		
		// 게시글 추천수
	    function recCount() {
			$.ajax({
				url: "/expro/RecCount.do",
                type: "POST",
                data: {
                    no: '${content.board_no}'
                },
                success: function (count) {
                	$(".rec_count").html(count);
                },
			})
	    });
	    recCount(); // 처음 시작했을 때 실행되도록 해당 함수 호출
	    
		 // 로그인 클릭
	    $(".newLogin").click(function(){
			var ww=400;    //띄울 창의 넓이
			var wh=250;    //띄울 창의 높이
			
			// 중앙 좌표
			var top=(screen.availHeight-wh)/2;
			var left=(screen.availWidth-ww)/2;
			// 새창 띄움
			window.open("/expro/NewLoginForm.do", "window", "width="+ww+", height="+wh+", top="+top+", left="+left+", toolbar=no, menubar=no, scrollbars=no, resizable=no");
	    });
	    
	    // 댓글 입력
	    $("#reply_btn").click(function(){
	    	if($("#reply_content").val().trim() === ""){
	    		alert("댓글을 입력하세요.");
	    		$("#reply_content").val("").focus();
	    	}else{
	    		$.ajax({
	    			url: "/expro/ReplyWriteAction.do",
	                type: "POST",
	                data: {
	                    no : $("#no").val(),
	                    id : $("#id").val(),
	                    reply_content : $("#reply_content").val()
	                },
	                success: function () {
	                	alert("댓글 등록 완료");
	                	$("#reply_content").val("");
	                	getReply();
	                }
	    		})
	    	}
	    });
	    
	    // 댓글 리스트
	    function getReply(){
	    	$.ajax({
    			url: "/expro/GetReply.do", // 요청 url
                type: "POST", // post 방식
                data: {
                	board_no : ${ content.board_no } // board_no의 값을 넘겨줌
                },
                success: function (json) { // 성공하였을 경우
                	json = json.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
                	$("#replyList").text(""); // 댓글리스트 영역 초기화
                	var obj = JSON.parse(json); // service 클래스로 부터 전달된 문자열 파싱
                	var replyList = obj.replyList; // replyList는 전달된 json의 키값을 의미
                	var output = ""; // 댓글 목록을 누적하여 보여주기 위한 변수
                	for (var i = 0; i < replyList.length; i++) { // 반복문을 통해 output에 누적
   	                    output += "<div class='w3-border w3-padding'>";
    	                for (var j = 0; j < replyList[i].length; j++) {
    	                    var reply = replyList[i][j];
    	                    if(j === 0){
    	     					output += "<i class='fa fa-user'></i>&nbsp;&nbsp;" + reply.id + "&nbsp;&nbsp;";
    	                    }else if(j === 1){
    	     					output += "&nbsp;&nbsp;<i class='fa fa-calendar'></i>&nbsp;&nbsp;" + reply.reply_date;
    	                    }else if(j === 2){
    	     					output += "<pre>" + reply.reply_content + "</pre></div>";
    	                    }
    	                };
    	        	};
   	              	$("#replyList").html(output); // replyList 영역에 output 출력
   	              	$(".reply_count").html(i);
                }
	    	})
	    }
	    getReply(); // 해당 페이지 실행 시 해당 함수 호출
	});
}
	function onDownload(no) {
    	var o = document.getElementById("ifrm_filedown");
    	o.src = "/expro/Download.do?no="+no;
    }
</script>
</head>
<body>
<iframe id="ifrm_filedown"  style="position:absolute; z-index:1;visibility : hidden;"></iframe>

	<div>
		<%@ include file="/home/sidebar.jsp"%>
	</div>
	<div class="w3-main w3-margin-bottom" style="margin-left: 340px; margin-right: 40px; width: 60%;">
		<div class="w3-center w3-text-white w3-round">
			<h3 style="text-transform: uppercase">${ section } </h3>
		</div>
		<div class="w3-bar">
			<c:if test="${ sel == null && find == null }">
				<button type="button" class="w3-bar-item w3-button w3-border" onclick="location.href='/expro/BoardList.do?section=${ section }&num=${ num }'">
					<i class="fa fa-bars"></i> 글 목록
				</button>
			</c:if>
			<c:if test="${ sel != null && find != null }">
				<button type="button" class="w3-bar-item w3-button w3-border" onclick="location.href='/expro/BoardList.do?section=${ section }&num=${ num }&sel=${ sel }&find=${ find }'">
					<i class="fa fa-bars"></i> 글 목록
				</button>
			</c:if>
			<!-- 로그인 하였을때 -->
			<c:if test="${ id != null }">
				<button type="button" class="w3-bar-item w3-button w3-border" onClick="location.href='/expro/BoardWriteForm.do?section=${ section }'">
					<i class="fa fa-pencil-square-o"></i> 새 글 쓰기
				</button>
			</c:if>
			<!--작성자 일때  -->
			<c:if test="${ content.id == id }">
				<button type="button" class="w3-bar-item w3-button w3-border" onClick="location.href='/expro/BoardUpdateForm.do?section=${ section }&no=${ content.board_no }&num=${ num }'">
					<i class="fa fa-exchange"></i> 글 수정
				</button>
				<button type="button" class="w3-bar-item w3-button w3-border" id="boardDelete">
					<i class="fa fa-remove"></i> 글 삭제
				</button>
			</c:if>
		</div>
		<!-- 게시글 내용(작성자, 작성일, 조회수, 번호, 제목, 내용) -->
		<div class="w3-article">
			<div class="w3-border w3-padding">
				<i class="fa fa-user"></i> <a href="#">${ content.id }</a><br /> 
				<i class="fa fa-calendar"></i> <fmt:formatDate value="${ content.write_date }" pattern="yy-MM-dd" />
				<div class="w3-right">
					<span><i class="fa fa-eye"></i> ${ content.readcount }</span>&nbsp;
					<i class="fa fa-heart" style="color:red"></i>&nbsp;<span class="rec_count"></span>
					<i class="fa fa-commenting-o"></i> <span class="reply_count"></span>
				</div>
			</div>
			<div class="w3-border w3-padding">
				#${ content.board_no } <span class="w3-center w3-xlarge w3-text-blue">${ content.board_subject }</span>
			</div>
			<article class="w3-border w3-large w3-padding">${ content.board_content }</article>
		</div>
        <!-- 글내용에서 첨부파일 이름 출력 -->
			<div class="w3-border w3-padding">
				<iframe id="ifrm_filedown"  style="position:absolute; z-index:1;visibility : hidden;"></iframe>
				<a href="#" onclick="onDownload('${content.board_no}')">${content.file_name}</a>
			</div>
		<!-- 추천 기능 -->
		<div>
			<div class="w3-border w3-center w3-padding">
				<c:if test="${ id == null }">
					추천 기능은 <button type="button" id="newLogin" class="newLogin"><b class="w3-text-blue">로그인</b></button> 후 사용 가능합니다.<br />
					<i class="fa fa-heart" style="font-size:16px;color:red"></i>
					&nbsp;<span class="rec_count"></span>					
				</c:if>
				<c:if test="${ id != null }">
					<button class="w3-button w3-black w3-round" id="rec_update">
						<i class="fa fa-heart" style="font-size:16px;color:red"></i>
						&nbsp;<span class="rec_count"></span>
					</button> 
				</c:if>
				
			</div>
		</div>
		
		<!-- 댓글 기능 -->
		<div>
			<div class="w3-border w3-padding">댓글</div>
			<div class="w3-border w3-padding">
				<c:if test="${ id == null }">
					<textarea rows="5" cols="50" class="w3-input w3-border newLogin" readonly>로그인 후 댓글 달기</textarea>
				</c:if>
				<c:if test="${ id != null }">
					<i class="fa fa-user w3-padding-16"></i> ${ content.id }
					<form>
						<input type="hidden" name="no" id="no" value="${ content.board_no }"> 
						<input type="hidden" name="id" id="id" value="${ id }">
						<textarea rows="5" cols="50" class="w3-input w3-border" placeholder="댓글 작성" name="reply_content" id="reply_content"></textarea>
						<input type="button" class="w3-button w3-border" id="reply_btn" value="댓글 등록">
					</form>
				</c:if>
			</div>
			<div>
				<div class="w3-border w3-padding">댓글목록(<i class="fa fa-commenting-o"></i> <span class="reply_count"></span>)</div>
				<div id="replyList"></div>
			</div>
		</div>
	</div>
</body>
</html>