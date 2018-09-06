/**
 * Created by val620@126.com on 2017/12/4.
 */

var loginPage = new Vue({
    el: '#login',
    data: {
        cellphone:'',
        password:'',
        code:'',
        url: getUrl('user/login'),
        countdown:0
    },
    methods: {
        login: function () {
            var params = {
                cellphone: this.cellphone, password: this.password,code:this.code
            };
            if(this.cellphone==''||this.password=='') {
                alert('请输入手机号和密码');
            }
            else {
                this.$http.post(this.url, params, {emulateJSON: true})
                    .then(function (response) {
                        if (response.data.code === 200) {
                            location.href = "index.html";
                        }
                        else {
                            alert(response.data.msg);
                        }
                    })
            }
        },


    }
});

$(function () {
    loginPage.cellphone=getCookie("CURRENT_USERNAME");
    $(document).keyup(function(event){
        if(event.keyCode ==13){
            loginPage.login();
        }
    });
});
