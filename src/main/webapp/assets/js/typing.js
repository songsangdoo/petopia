(function($) {
	"use strict";
	
	// 타이핑 효과 출력  JS 
	// typed-text-output 클래스로 출력 가능
	if ($('.typed-text-output').length == 1) {
		var typed_strings = $('.typed-text').text();
		var typed = new Typed('.typed-text-output', {
			strings: typed_strings.split(', '),
			typeSpeed: 100,
			backSpeed: 20,
			smartBackspace: false,
			loop: true
		});
	}
})(jQuery)