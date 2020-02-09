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

/****************************admin(blogs) page functions******************************/
$(function () {
    $(".ui .dropdown").dropdown({
        on: 'hover'
    });

    //获取页面page的查询条件等信息
    function page(object){
        $("[name='page']").val($(object).data("page"));
        loadBlog();
    }

    $("#search-btn").click(function(){
        $("[name='page']").val(0);
        loadBlog();
    });

    function loadBlog(){

        $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search",{
            title : $("[name='title']").val(),
            typeId : $("[name='typeId']").val(),
            recommend : $("[name='recommend']").prop('checked'),
            page : $("[name='page']").val()
        });
    }
});

/****************************blog-add(publish) page functions******************************/
$(function () {
    //验证输入内容不为空
    $(".ui .form").form({
        fields: {
            title: {
                identifier: 'title',
                rules: [{
                    type: 'empty',
                    prompt: '标题：请输入博客标题'
                }]
            },
            content: {
                identifier: 'content',
                rules: [{
                    type: 'empty',
                    prompt: '输入博客内容...'
                }]
            },
            typedId: {
                identifier: 'typeId',
                rules: [{
                    type: 'empty',
                    prompt: '分类：请选择博客分类'
                }]
            },
            picture: {
                identifier: 'picture',
                rules: [{
                    type: 'empty',
                    prompt: '输入博客图片'
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

    $('#save-btn').click(function(){
        $("[name='published']").val(false);
        $('.ui.form').submit();
    });

    $('#publish-btn').click(function(){
        $("[name='published']").val(true);    
        $('.ui.form').submit();
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

/******************************admin/type,admin/tag page funcitons********************************/
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

/******************************admin/tag-add page funcitons********************************/
$(function(){
    // 提交form的校验
    $('.ui .form').form({
        fields: {
            name: {
                identifier: "name",
                rules: [{
                    type: "empty",
                    prompt: "输入标签不能为空!"
                }]
            }
        }
    });
});