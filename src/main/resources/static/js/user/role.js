/**
 * Created by val620@126.com on 2018/9/5.
 */
var app = new Vue({
    el: '#roleIndex',
    data: {
        search: {roleName: '',description:''},
        roles: [],
        beginIndex: 0

    },
    methods: {
        getRoles: function () {
            var pageIndex = pageVM.current_page;
            var pageSize = 5;
            var params = {
                roleName: this.search.roleName,
                description:this.search.description,
                userId: $("#hidUserId").val(),
                newObj: $("input[value='new']").prop("checked"),
                pageIndex: pageIndex, pageSize: pageSize
            };
            this.$http.post(getUrl('role/getRoles'), params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.roles = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;

                        //如果已经是用户角色，全部勾上
                        if (!params.newObj) {
                            setTimeout("$(':checkbox').prop('checked', true)",200)
                        }
                        else {
                            $(":checkbox").prop("checked", false);
                        }

                    }
                    else alert(response.data.msg);
                })
        },
        checkAll: function () {
            var val = $("#all").prop("checked");
            $(":checkbox").prop("checked", val);
        },
        save: function () {
            var isNew = $("input[value='new']").prop("checked");
            var msg;
            var roleIds = [];
            var url = "";
            if (isNew) {
                url = getUrl("user/addUserRole");
                msg = "添加成功";
                $("input[type=checkbox]:checked").each(function () {
                    roleIds.push($(this).val());
                });
            }
            else {
                url = getUrl("user/deleteUserRole");
                msg = "删除成功";
                $("input[type=checkbox]:not(:checked)").each(function () {
                    roleIds.push($(this).val());
                });
            }

            var params = {

                userId: $("#hidUserId").val(),
                roleIds: roleIds.toString()//数组转字符串
            };

            this.$http.post(url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        app.getRoles();
                        alert(msg);
                    }
                    else alert(response.data.msg);
                })
        }
    }
});


$(function () {
        app.getRoles();

    }
);