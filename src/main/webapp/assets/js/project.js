// <script>
// $(function () {
$(document).ready(function() {
	var process = $('.process_wrap')

	$('.process_wrap').slick({
		arrow: true,
		dots: false,
		slidesToShow: 1,
		slidesToScroll: 1,
		//variableWidth: true,
		centerMode: true,
		speed: 1500,
		infinite: true,
		autoplay: false,
		autoPlaySpeed: 2500,
		adaptiveHeight: true,
		//fade: true,
		//  initialSlide : 0,
		// slide: '.process_wrap',
		responsive: [{
			breakpoint: 1024,
			settings: {
				variableWidth: true,
			}
		}
		]
	})


	var Dots = $('.process_dots > li')
	var ProgressBar = $('.process_dots .dots_bar .bar_fill')
	var active = $('.process_dots > li.slick-active');
	var active_i = active.index();


	// 모션 초기화 
	Dots.eq(0).addClass('current')
	ProgressBar.css('width', (100 / 7) * 0 + '%')

	process.on('beforeChange', function(e, s, c, n) {

		Dots.each(function(index, value) {
			if (index < n) {
				$(value).addClass('active')
			} else {
				$(value).removeClass('active')
			}
		})


		ProgressBar.css({
			width: (100 / 7) * n + '%'
		})
		Dots.removeClass('current').eq(n).addClass('current')

	})

	process.on('afterChange', function(e, s, c) {
		//  Dots.removeClass('current').eq(c).addClass('current')
		//  ProgressBar.css({
		//  width: (100/7) * c + '%'
		//  })
	})


	Dots.on('click', function() {
		var idx = $(this).index();
		process.slick('slickGoTo', idx)
	})

	$("#news-slider").owlCarousel({
		items: 4,
		itemsDesktop: [1199, 3],
		itemsDesktopSmall: [980, 2],
		itemsMobile: [600, 1],
		navigation: true,
		navigationText: ["", ""],
		pagination: true,
		autoPlay: true,
	});
})
// </script>