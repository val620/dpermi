
var editUserVM = new Vue({
    el: '#editUser',
    data: {
        userId: '',
        cellphone: '',
        name: '',
        password: '',
        gender: 0,
        address:'',
        introduction:'',
        email:'',
        qq:'',
        url: getUrl('user/addUser')
    },
    methods: {
        editUser: function () {
            if (!this.name) {
                alert("请填写用户姓名!");
                return;
            }
            if (!this.cellphone) {
                alert("请填写手机号!");
                return;
            }
            if (!this.userId) {
                if (!this.password) {
                    alert("请填写密码!");
                    return;
                }
            }

            // var birthday = $("#txtBirthday").val();
            // editUserVM.birthdayDisplay = birthday;
            var user = {
                cellphone: this.cellphone
                , userType: this.userType
                , password: this.password
                , name: this.name
                , email: this.email
                , gender: this.gender
                , introduction: this.introduction
                , userId: this.userId
                ,address:this.address
                ,qq:this.qq
            };
            var url = this.url;
            if (this.userId) {
                url = getUrl("user/updateUser");
            }
            this.$http.post(url, user, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        $('#txtSearch').click();
                        $('#btnClose').click();
                    } else if (response.data.code === 403) {
                        alert(response.data.msg);
                        $('#txtSearch').click();
                        $('#btnClose').click();
                    }
                    else alert('保存失败');
                })
        },
        bindUser: function (id) {
            this.$http.post(getUrl('user/getUser'), {id: id}, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var user = response.data.obj;
                        if(user!=null) {
                            this.cellphone = user.cellphone;
                            this.userId = user.userId;
                            this.gender = user.gender;
                            this.name = user.name;
                            this.qq=user.qq;
                            this.email=user.email;
                            this.address=user.address;
                        }

                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

$(function () {
        // $("#txtBirthday").click(function () {
        //     WdatePicker();
        // });
        var id = $("#hidUserId").val();
        if (id != null && id.length > 0) {
            $('#divCellphone').hide();
            $('#divPassword').hide();
            editUserVM.bindUser(id);
        }
        $(".editClose").click(clearUserId);
    }
);