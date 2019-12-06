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
    $(".ui .dropdown").dropdown({
        on: 'hover'
    });
});

/****************************publish page functions******************************/
$(function(){
    $(".ui .form").form({
        fields:{
            title:{
                identifier: 'title',
                rules: [{
                    type: 'empty',
                    prompt: '标题：请输入博客标题'  
                }]
            }
        }
    });
});