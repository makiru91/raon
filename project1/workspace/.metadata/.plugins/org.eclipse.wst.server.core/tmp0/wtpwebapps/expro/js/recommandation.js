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
		})
		
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
	    };
	    recCount(); // 처음 시작했을 때 실행되도록 해당 함수 호출
	})