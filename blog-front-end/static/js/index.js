$(function(){
    // 设置导航栏按钮在移动端时点击展示导航栏item，再点击收回item
    $('.menu.toggle').click(function(){
        $('.menu.item').toggleClass('mobile-hide');
    });
});

/****************************blog page functions******************************/

$(function(){
    $("#payButton").popup({
        popup: $(".planar.popup"),
        on: "click",
        position: 'bottom center'
    });
});

