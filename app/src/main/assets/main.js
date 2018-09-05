var originFontSize;

$(function(){
        $('.headline').remove();
        $('.meta').remove();
        $('.view-more').remove();

		var img=$('.content img');
		var imgSrc=new Array();

		for(var i=0;i<img.length;++i){	
			var theImg=img[i];
			theImg.id=new String(i);

			imgSrc[i]=img[i].src;

			theImg.addEventListener('click',function(e){
				var target=e.target;

				window.AndroidNative.toast(target.id);
			},false);
		}


		var src=$('.avatar').attr('src');
		var author=$('.author').html();
		var bio=$('.bio').html();
		$('.question-title').click(function(){
			window.AndroidNative.toast(src+'\n'+author+'\n'+bio);
		});

		originFontSize=Number($('p').css('font-size').replace('px',''));
})

function bigger(){
    var initSizeP=$('p').css('font-size').replace('px','');
    var initLineHeight=$('p').css('line-height').replace('px','');
    var initSizeH2=$('h2').css('font-size').replace('px','');

    var temp1=Number(initSizeP);
    var temp2=Number(initSizeH2);
    var lineHeight=Number(initLineHeight);

    var factor=lineHeight/temp1;

    temp1+=3;
    temp2+=3;

    lineHeight=Math.floor(temp1*factor);
    $('p').css({'font-size':temp1+'px',
                'line-height':lineHeight+'px'
                  });
    $('h2').css({'font-size':temp2+'px'});
}

function smaller(){
    var initSizeP=$('p').css('font-size').replace('px','');
    var initLineHeight=$('p').css('line-height').replace('px','');
    var initSizeH2=$('h2').css('font-size').replace('px','');

    var temp1=Number(initSizeP);
    var temp2=Number(initSizeH2);
    var lineHeight=Number(initLineHeight);

    if(temp1<=originFontSize){
        AndroidNative.toast("can't be smaller :(");
        return;
    }

    var factor=lineHeight/temp1;

    temp1-=3;
    temp2-=3;

    lineHeight=Math.floor(temp1*factor);
    $('p').css({'font-size':temp1+'px',
                'line-height':lineHeight+'px'
                  });
    $('h2').css({'font-size':temp2+'px'});
}