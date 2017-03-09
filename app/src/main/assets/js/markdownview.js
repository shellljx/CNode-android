$(document).ready(function() {

    //给无http前缀的图片链接加上http
    $("img").each(function(){
    if($(this).attr('src').indexOf('http')<0){
    $(this).attr('src','https:'+$(this).attr('src'));
    }
    });

    //js调用android本地代码显示大图
    $("img").each(function(){
        $(this).click(function(){
            window.cnode.showImageDetail($(this).attr('src'));
        });
    });

	$('abbr').each(function() {
		$(this).data('title', $(this).attr('title'));
		$(this).removeAttr('title');
	});

	$('abbr').mouseover(function() {
	  $('#tooltip').css('display', 'inline-block');
		$('#tooltip').html($(this).data('title'));
    $('#tooltip').css('margin-left', -$('#tooltip').outerWidth() / 2);
	});

	$('abbr').click(function() {
		$(this).mouseover();
		$('#tooltip').animate({opacity: 0.9},{duration: 2000, complete: function() {
			$('#tooltip').fadeOut(1000);
		}});

	});

	$('abbr').mouseout(function() {
		$('#tooltip').css('display', 'none');
	});

});