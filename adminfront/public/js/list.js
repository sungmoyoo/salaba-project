$('#all').click(function() {
    $('.rowItem').each((index, element) => {
        $(element).show();
    });
});

$('#incomplete').click(function() {
    $('.rowItem').each((index, element) => {
        let ele = $(element);
        if (ele.find('.state').text() == '완료') {
            ele.hide();
        } else {
            $(element).show();
        }
    });
    
});

$('#complete').click(function() {
    $('.rowItem').each((index, element) => {
        let ele = $(element);
        if (ele.find('.state').text() == '미완료') {
            ele.hide();
        } else {
            ele.show();
        }
    });
});
