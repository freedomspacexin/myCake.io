/**
 * Created by xin on 2019-03-19.
 */

adjustVideoBox();
$(".video").on("loadedmetadata", adjustVideoBox)
$(window).on("resize", adjustVideoBox)
function adjustVideoBox(){
    var video = $(".video")
    var videoEl = $(".video")[0]
        , b = '1920'
        , c = '1080'
        , d = videoEl.parentNode.clientWidth
        , e = videoEl.parentNode.clientHeight;
    videoEl.playbackRate = 0.5;
    if (b && c && d && e ){
        if (b / c > d / e) {
            var f = e
                , g = b / c * e;
            video.css("left", d / 2 - g / 2),
                video.css("width", g),
                video.css("height", f)
        } else{
            video.css("left", ""),
                video.css("width", ""),
                video.css("height", "")
        }

    }
}
function debounce(fn, wait) {
    var timeout = null;
    return function() {
        if(timeout !== null)   clearTimeout(timeout);
        timeout = setTimeout(fn, wait);
    }
}
$(function() {
    var windowHeight = $(window).height(); //���ڸ߶�
    var element;
    var ON_SCREEN_HEIGHT =50;
    var ON_SCREEN_WIDTH = 50;
    var dataAnimateEl = $('[data-animate]');
    if (dataAnimateEl.length > 0 || dataAnimateEl.length == 0) {
        dataAnimateEl.each(function() {
            element = $(this);
            // Ԫ���ڿ������򣬼��̿�ʼ����
            animationStart(element);
        })
    }
    // ����ҳ���������ʼ����
    $(window).scroll(function(event) {
        var dataAnimateEl = $('[data-animate]');
        if (dataAnimateEl.length > 0 || dataAnimateEl.length == 0) {
            dataAnimateEl.each(function() {
                element = $(this);
                // Ԫ���ڿ������򣬼��̿�ʼ����
                animationStart(element);
            })
        };

        checkBanner();
        fixedNav();
        checkScroll();
        checkBanner();
        checkVideo();

    });
    //banner����
    checkBanner();
    function checkBanner(){
        var bannerContent = $(".banner-content");
        if(isOnScreen(bannerContent)){
            bannerContent.addClass("isview");
        }else{
            bannerContent.removeClass("isview");
        }

    };
    //���Ű�ť
    $("body").on("click","#play",function(){
        var video =  $(".video-intro").get(0);
        $("#play").hide();
        video.playbackRate = 0.5;
        video.play();
        video.currentTime = 0;


    });
    $(".video-intro").get(0).addEventListener('ended', function () {
        $("#play").show();
    }, false);
    //������Ƶ

    checkVideo()
    var initV = true;
    function checkVideo(){
        var inplayer= $(".player-bg");
        if(isOnScreen(inplayer)){
            if(initV){
                var video =  $(".video-intro").get(0);
                $("#play").click();
                initV = false;
            }
        }else{
            initV = true;
            $(".video-intro").get(0).pause();
        }
    };
    //��Ƶ�л���ť

    $(".player-tool").click(function(){
        $(".player-tool_wrapper .active").removeClass("active");
        $(this).addClass("active");
        var src = $(this).attr("data-src");
        $(".video-intro").attr("src",src);
        $("#play").click();

    });




    //��ʼ����
    function animationStart(element) {
        var annimationVal = element.data("animate");
        if (isOnScreen(element)) {

            // setTimeout(function () {
            element.removeClass(annimationVal).addClass("animated").addClass(annimationVal)
            // },0)

        } else {
            element.removeClass("animated").removeClass(annimationVal)
        }
    }



    //�������ã�����Ԫ���Ƿ񵽴��������
    function viewingArea(element) {
        var objHeight = $(element).offset().top;�� //Ԫ�ص������ĸ߶�
        var winPos = $(window).scrollTop(); //���붥������
        var val = objHeight - winPos;
        if (val < windowHeight && val > 0) {
            //��������
            return true;
        } else {
            //����������
            return false;
        }
    };

    function isOnScreen(element) {

        element = element.get(0);
        var rect = element.getBoundingClientRect();
        var windowHeight = window.innerHeight || document.documentElement.clientHeight;
        var windowWidth = window.innerWidth || document.documentElement.clientWidth;

        var elementHeight = element.offsetHeight;
        var elementWidth = element.offsetWidth;
        var onScreenHeight = ON_SCREEN_HEIGHT > elementHeight ? elementHeight : ON_SCREEN_HEIGHT;
        var onScreenWidth = ON_SCREEN_WIDTH > elementWidth ? elementWidth : ON_SCREEN_WIDTH;
        // Ԫ������Ļ�Ϸ�
        var elementBottomToWindowTop = rect.top + elementHeight;
        var bottomBoundingOnScreen = elementBottomToWindowTop >= onScreenHeight;

        // Ԫ������Ļ�·�
        var elementTopToWindowBottom = windowHeight - (rect.bottom - elementHeight);
        var topBoundingOnScreen = elementTopToWindowBottom >= onScreenHeight;

        // Ԫ������Ļ���
        var elementRightToWindowLeft = rect.left + elementWidth;
        var rightBoundingOnScreen = elementRightToWindowLeft >= onScreenWidth;

        // Ԫ������Ļ�Ҳ�
        var elementLeftToWindowRight = windowWidth - (rect.right - elementWidth);
        var leftBoundingOnScreen = elementLeftToWindowRight >= onScreenWidth;

        return bottomBoundingOnScreen && topBoundingOnScreen && rightBoundingOnScreen && leftBoundingOnScreen;
    };
});

//����

var swiper = new Swiper('.swiper-container', {
    watchSlidesProgress: true,
    slidesPerView:'auto',
    centeredSlides: true,
    loop: true,
    loopedSlides: 3,
    // autoplay: true,
    slideToClickedSlide: true,
    on: {
        progress: function(progress) {
            for (i = 0; i < this.slides.length; i++) {
                var slide = this.slides.eq(i);
                var slideProgress = this.slides[i].progress;
                modify = 1;
                if (Math.abs(slideProgress) > 1) {
                    modify = (Math.abs(slideProgress) - 1) * 0.3 + 1;
                }
                translate = slideProgress * modify * 25 + 'px';
                scale = 1 - Math.abs(slideProgress) / 10;
                zIndex = 999 - Math.abs(Math.round(10 * slideProgress));
                slide.transform('translateX(' + translate + ') scaleY(' + scale + ')');
                slide.css('zIndex', zIndex);
                slide.css('opacity', 1);
                if (Math.abs(slideProgress) > 3) {
                    slide.css('opacity', 0);
                }
            }
        },
        setTransition: function(transition) {
            for (var i = 0; i < this.slides.length; i++) {
                var slide = this.slides.eq(i)
                slide.transition(transition);
            }

        }
    }

})
//����һҳ
$("#arrow").click(function(){
    animateToFuntion();
})
function fixedNav(){
    var functionsEl =  $("#functions");
    var top = functionsEl.offset().top;
    var scrollTop = $(window).scrollTop();
    var height = $(".header").height();

    if(scrollTop+height >= top){
        $(".header-wrapper").addClass("fixed-header");
    }else{
        $(".header-wrapper").removeClass("fixed-header");

    }
}
function animateToFuntion(){
    var functionsEl =  $("#functions");
    var top = functionsEl.offset().top;
    var height = $(".header").height();
    $('body,html').animate({scrollTop:top-height},500);
};

//�ö�
$("#goTop").click(function(){
    $('body,html').animate({scrollTop:0},500);
})

//�������

function checkScroll(){
    var top = $(".main-banner").offset().top;
    var scrollTop = $(window).scrollTop();
    var height = $(".main-banner").height();
    if(scrollTop < top+height/2){
        $(".fixed-top").fadeOut();
    }else{
        $(".fixed-top").fadeIn();

    }

};

(function getSoftWareInfo(){
    function getfilesize(size) {
        if (!size)
            return "";

        var num = 1024.00; //byte

        if (size < num)
            return size + "B";
        if (size < Math.pow(num, 2))
            return (size / num).toFixed(2) + "K"; //kb
        if (size < Math.pow(num, 3))
            return (size / Math.pow(num, 2)).toFixed(2) + "M"; //M
        if (size < Math.pow(num, 4))
            return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
        return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
    }
    $.ajax({
        type: "get",
        url: "http://api.console.aunbox.cn/channel",
        data: {
            'channel_code':'8f3b3b68'
        },
        success: function (res) {
            var data = JSON.parse(res);
            if(data.size){
                var size = getfilesize(data.size);
                var version = data.version;
                $("#version").html('��С��'+size+'&nbsp;&nbsp;&nbsp;&nbsp;�汾��v'+version)
            }else{
                $("#version").hide()
            }
        },
        error: function (res) {
            $("#version").hide()
        }

    });
})()
