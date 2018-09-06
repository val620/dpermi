
var myinfoVM = new Vue({
    el: '#myinfo',
    data: {
        icon:'',
        cellphone: '',
        name: '',
        birthday:'',
        state:'',
        url: getUrl('user/getCurrentUser')
    },
    methods: {

        getUser: function () {
            this.$http.post(this.url, null, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var user = response.data.obj;
                        if(user!=null) {
                            this.cellphone = user.cellphone;
                            this.name = user.name;
                            this.icon=user.icon;
                            // this.email=user.email;
                            // this.address=user.address;
                        }

                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

$(function () {
    myinfoVM.getUser();
});
