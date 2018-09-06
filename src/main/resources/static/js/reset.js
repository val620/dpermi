/**
 * Created by val620@126.com on 2018/8/24.
 */

var resetVM = new Vue({
    el: '#reset',
    data: {
        password: '',
        confirm:'',
        url: getUrl('user/reset')
    },
    methods: {
        reset: function () {
            if (!this.password) {
                alert("请输入新密码");
                return;
            }
            if (!this.confirm) {
                alert("请输入确认密码");
                return;
            }
            if (this.confirm!=this.password) {
                alert("新密码和确认密码不一致，请再次输入");
                return;
            }

            this.$http.post(this.url, {password: this.password}, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        alert('修改成功');
                    } else if (response.data.code === 403) {
                        alert(response.data.msg);

                    }
                    else alert('修改失败');
                })
        }

    }
});

