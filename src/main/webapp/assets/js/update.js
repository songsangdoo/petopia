$(document).ready(function() { 
	
	let index =[];
	for(i=0; i < $('.count').length; i++){		
		index = index.concat($('.count')[i].innerText);
	}
		
	for(i=0; i < $('.count').length; i++){	
		let count = $('.count').get(i);
		$({ someValue: 0 }).animate({ someValue: Math.floor(index[i]) }, {
		    duration: 1000,
	    	easing: 'swing', // can be anything
	   		step: function () { // called on every step
	       		 // Update the element's text with rounded-up value:
				count.innerText = commaSeparateNumber(Math.round(this.someValue));
		    }
		});
	}
	

	function commaSeparateNumber(val) {
	    while (/(d+)(d{3})/.test(val.toString())) {
	        val = val.toString().replace(/(d)(?=(ddd)+(?!d))/g, "$1,");
    	}
    	return val;
	} 
});