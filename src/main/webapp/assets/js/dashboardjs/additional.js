$(document).ready(function() {
	// 이전 페이지로 이동
	$('.pagination .fa-chevron-left').on('click', function() {
		const currentPage = parseInt($('.pagination .active').text());
		if (currentPage > 1) {
			navigateToPage(currentPage - 1);
		}
	});

	// 다음 페이지로 이동
	$('.pagination .fa-chevron-right').on('click', function() {
		const currentPage = parseInt($('.pagination .active').text());
		const totalPages = parseInt($('.pagination li:not(.prev):not(.next)').length);
		if (currentPage < totalPages) {
			navigateToPage(currentPage + 1);
		}
	});

	// 특정 페이지로 이동
	$('.pagination li:not(.prev):not(.next)').on('click', function() {
		const pageNumber = parseInt($(this).text());
		navigateToPage(pageNumber);
	});

	// 페이지 이동 함수
	function navigateToPage(pageNumber) {
		// 페이지 이동에 필요한 로직을 작성합니다.
		// 예를 들어, AJAX 요청을 통해 해당 페이지의 콘텐츠를 가져오거나, URL을 업데이트하거나, 페이지를 다시로드할 수 있습니다.
		// 이 예시에서는 페이지 번호를 활성화하고, 해당 페이지 번호를 출력하도록 설정합니다.

		// 현재 활성화된 페이지 번호의 클래스를 제거합니다.
		$('.pagination .active').removeClass('active');

		// 선택한 페이지 번호를 활성화합니다.
		$('.pagination li:not(.prev):not(.next)').eq(pageNumber - 1).addClass('active');

		// 페이지 번호를 출력합니다. (이 부분을 필요에 맞게 수정할 수 있습니다.)
		console.log('이동한 페이지:', pageNumber);
		// 페이지 이동에 따라 다른 작업을 수행할 수 있습니다.
	}

	$(".userinfo").click(function() {
		$('#userviewmodal').modal('show');

	})
	$("#usermodify").click(function() {
		$('#usermodifymodal').modal('show');

	})
	$(".userdelete").click(function() {
			console.log('클릭')
	})
	
	var userDeleteButton = document.getElementsByClassName("userdelete");

	// userinfo 버튼 클릭 이벤트 리스너 등록
	for (var i = 0; i < userDeleteButton.length; i++) {
		userDeleteButton[i].addEventListener("click", function() {
			// 클릭된 버튼의 부모 요소인 <td> 선택
			var parentTd = this.parentNode;

			// <td> 요소에서 <tr> 요소를 찾아 선택
			var parentTr = parentTd.parentNode;

			// <tr> 요소 내의 모든 span 요소 선택
			var spanElements = parentTr.getElementsByTagName("span");

			// span 요소 안의 값을 가져오기
			var value = spanElements[0].innerText;
			swal({
				title: "계정을 삭제하시겟습니까?",
				text: "확인을 누르시면 계정이 삭제되고 복구하실 수 없습니다.",
				icon: "warning",
				buttons: true,
				dangerMode: true,
			})
				.then((willDelete) => {
					if (willDelete) {
						$.ajax({
							url: "/getUserDelete.do",
							type: "GET",
							data: { value: value },
							success: function(result) {
							},
							error: function(xhr, status, error) {

								console.error(error);
							}
						});
						swal("계정이 삭제되었습니다!", {
							icon: "success",
						});
					} else {
						swal("계정이 삭제되지 않앗습니다.");
					}
				})
		})
	}
})

function commaSeparateNumber(val) {
    while (/(d+)(d{3})/.test(val.toString())) {
        val = val.toString().replace(/(d)(?=(ddd)+(?!d))/g, "$1,");
    }
    return val;
} 
