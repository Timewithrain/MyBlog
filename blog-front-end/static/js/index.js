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

/****************************about page functions******************************/

$(function(){
    $(".qq").popup();

    $(".github").popup();

    $(".wechat").popup({
        popup: $(".wechat-planar.popup"),
        on: "hover",
        position: "bottom center"
    });

});

/****************************admin page functions******************************/
$(function(){
    // var obj = $(".ui .dropdown")[0];
    // alert(obj.innerHTML);
    $(".ui .dropdown").dropdown({
        on: 'hover'
    });
});

