$(document).ready(function(){
    $('.slider-container').slick({
        infinite: false,
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: false,
        prevArrow: '<button type="button" class="slick-prev">&#8592;</button>',
        nextArrow: '<button type="button" class="slick-next">&#8594;</button>',
        responsive: [
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });

    // 이전 버튼 클릭 시
    $('.slick-prev').on('click', function(){
        $('.slider-container').slick('slickPrev');
    });

    // 다음 버튼 클릭 시
    $('.slick-next').on('click', function(){
        $('.slider-container').slick('slickNext');
    });
});
