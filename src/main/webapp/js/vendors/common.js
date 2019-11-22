


$(document).ready(function() {
  function setHeight() {
    windowHeight = ($(window).innerHeight()-145);
    $('.leftres').css('min-height', windowHeight);
  };
  setHeight();
  $(window).resize(function() {
    setHeight();
  });
});	

$(document).ready(function() {
  function setHeight() {
    windowHeight = ($(window).innerHeight()-150);
	$('.resadd').css('height', windowHeight);
  };
  setHeight();
  $(window).resize(function() {
    setHeight();
  });
});	

$(document).ready(function() {
  function setHeight() {
    windowHeight = ($(window).innerHeight()-5);
	$('.bglogin').css('height', windowHeight);
  };
  setHeight();
  $(window).resize(function() {
    setHeight();
  });
});

$(document).ready(function () {
	$('#showsearch').click(function () {
		$('.showsearchbox').slideToggle("fast");
	});
});