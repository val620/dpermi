
var editRoleVM = new Vue({
    el: '#editRole',
    data: {
        roleId: '',
        roleName: '',
        description: '',
        beginTime: '',
        endTime: '',
        url: getUrl('role/addRole')
    },
    methods: {
        editRole: function () {
            if (!this.roleName) {
                alert("请填写角色名");
                return;
            }

            var role = {
                roleId: this.roleId,
                roleName: this.roleName,
                description: this.description,
                beginTime: this.beginTime,
                endTime:this.endTime
            };
            var url = this.url;
            if (this.roleId) {
                url = getUrl("role/updateRole");
            }
            this.$http.post(url, role, {emulateJSON: true})
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
        bindRole: function (id) {
            this.$http.post(getUrl('role/getRole'), {id: id}, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var role = response.data.obj;
                        if(role!=null) {
                            this.roleId = role.roleId;
                            this.roleName = role.roleName;
                            this.description = role.description;
                            this.beginTime = role.beginTimeStr;
                            this.endTime=role.endTimeStr;
                        }

                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

$(function () {
        var id = $("#hidRoleId").val();
        if (id != null && id.length > 0) {
            editRoleVM.bindRole(id);
        }
        $(".editClose").click(clearRoleId);
    }
);