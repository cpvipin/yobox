$(document).ready(function(){

	//Custom Modal
	$('.open-modalbox').click(function(){
		$(this).addClass('current');
		$('#loginModal').addClass('open');
		$('body').addClass('modalbox-opened');
	});
	$('.close-modalbox').click(function(){
		$('.open-modalbox').removeClass('current');
		$('.modal-container').removeClass('open');
		$('body').removeClass('modalbox-opened');
	});


	//
	$('.data-color').each(function() {
	  $(this).parent().find('label').css('background', $(this).attr('data-color'));
	});

	//
	$('.carousel').carousel({
	  //interval: 5000,
	  wrap: false
	});
	
	$('#carousel-example-generic').on('slid.bs.carousel', checkitem);

	function checkitem()                        // check function
	{
var $this = $('#carousel-example-generic');
if ($('.carousel-inner .item:last').hasClass('active')) {
    $this.children('.question-btn').children('.btn-next').hide();
    $this.children('.question-btn').children('#submitBut').show();
}
else
	{
	$this.children('.question-btn').children('.btn-next').show();
    $this.children('.question-btn').children('#submitBut').hide();
	}
	}


});