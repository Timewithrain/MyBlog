$(function () {
    // 设置导航栏按钮在移动端时点击展示导航栏item，再点击收回item
    $('.menu.toggle').click(function () {
        $('.menu.item').toggleClass('mobile-hide');
    });
});

/****************************blog page functions******************************/

$(function () {
    $("#payButton").popup({
        popup: $(".planar.popup"),
        on: "click",
        position: 'bottom center'
    });

    tocbot.init({
        tocSelector: '.js-toc',
        contentSelector: '.js-toc-content',
        headingSelector: 'h1, h2, h3',
    });

    $("#categoryButton").popup({
        popup: $(".toc-container.popup"),
        on: "click",
        position: 'left center'
    });

    $("#qrcodeButton").popup({
        popup: $("#qrcode"),
        on: "click",
        position: "left center"
    });

    var qrcode = new QRCode("qrcode", {
        text: "file:///D:/Programs/MyBlog/blog-front-end/blog.html",
        width: 120,
        height: 120,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
    });

    $("#up").click(function () {
        $(window).scrollTo(0, 500);
    });

    var waypoint = new Waypoint({
        element: document.getElementById('center'),
        handler: function (direction) {
            if (direction == "down") {
                $("#v-toolbar").show(200);
            } else {
                $("#v-toolbar").hide(500);
            }
        }
    })
});

/****************************about page functions******************************/

$(function () {
    $(".qq").popup();

    $(".github").popup();

    $(".wechat").popup({
        popup: $(".wechat-planar.popup"),
        on: "hover",
        position: "bottom center"
    });

});

/****************************admin page functions******************************/
$(function () {
    $(".ui .dropdown").dropdown({
        on: 'hover'
    });
});

/****************************publish page functions******************************/
$(function () {
    $(".ui .form").form({
        fields: {
            title: {
                identifier: 'title',
                rules: [{
                    type: 'empty',
                    prompt: '标题：请输入博客标题'
                }]
            }
        }
    });

    // 页面插件markdown集成部分js
    var contentEditor;
    $(function () {
        contentEditor = editormd("md-content", {
            width: "90%",
            height: 640,
            syncScrolling: "single",
            path: "../../static/lib/editormd/lib/"
        });
    });
});

/******************************login page function********************************/
$(function(){
    $('.ui .form').form({
        fields: {
            username: {
                identifier: "username",
                rules: [{
                    type: "empty",
                    prompt: "请输入用户名"
                }]
            },
            password: {
                identifier: "password",
                rules: [{
                    type: "empty",
                    prompt: "请输入密码"
                }]
            }
        }
    });
});

/******************************admin/type page funcitons********************************/
$(function(){
    // 显示消息
    $(".message .close").on("click",function(){
        $(this).closest('.message').transition('fade');
    });
});

/******************************admin/type-add page funcitons********************************/
$(function(){
    // 提交form的校验
    $('.ui .form').form({
        fields: {
            name: {
                identifier: "name",
                rules: [{
                    type: "empty",
                    prompt: "输入分类不能为空!"
                }]
            }
        }
    });
});