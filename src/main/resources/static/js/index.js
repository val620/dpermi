/**
 * Created by val620@126.com on 2017/11/17.
 */
var app = new Vue({
    el: '#index',
    data: {
        userId: '',
        userName: '',
        icon: '',
        treeData: ''
    },
    methods: {

        getMenuTree: function () {
            this.$http.post(getUrl('menu/getMenuTree'), {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.treeData = response.data.obj;
                        $("#menuTree").html(this.treeData);
                    } else if (response.data.code === 403) {
                        console.log(response.data.msg);
                    }
                    else alert('服务器内部错误');
                })
        },
        getCurrentUser:function () {
            this.$http.post(getUrl('user/getCurrentUser'), {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var user = response.data.obj;
                        this.userName=user.name;
                        this.icon=user.icon;
                        console.log(this.icon);
                    } else if (response.data.code === 403) {
                        location.href="login.html";
                        console.log(response.data.msg);
                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

function openOrClose(obj) {
    //$("#"+id).parent().siblings("ul").attr("style","display:block");
    var ul = $(obj).parent().siblings("ul");
    if (ul.attr("style") == "display:block")
        ul.attr("style", "display:none");
    else
        ul.attr("style", "display:block");
}

function logout() {
    var yes = confirm("确定退出？")
    if (yes) {
        $.post(getUrl("user/logout"), null,
            function (data) {
                if (data.code === 200) {
                    self.location = "login.html";
                }
                else alert(data.msg);
            }, "json");
    }
}


$(function () {
    app.getCurrentUser();
    app.getMenuTree();
});