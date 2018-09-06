var app = new Vue({
    el: '#dataIndex',
    data: {
        search: {objectName: '', dataType: -1},
        objs: [],
        beginIndex: 0,
        params: [],
        url: getUrl('auth/DataPermission')
    },
    methods: {
        getDataObjects: function () {
            var id = $("#hidRoleId").val();
            var pageIndex = pageVM.current_page;
            var pageSize = 10;
            this.params = {
                objectName: this.search.objectName, dataType: this.search.dataType,
                roleId: id, newObj: $("input[value='new']").prop("checked"),
                pageIndex: pageIndex, pageSize: pageSize
            };
            this.$http.post(this.url, this.params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.objs = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / this.params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;

                        //如果已经是角色权限，全部勾上
                        if (!this.params.newObj) {
                            // $(':checkbox').prop('checked', true);
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
            var objIds = [];
            var url = "";
            if (isNew) {
                url = getUrl("role/addRolePermission");
                msg = "添加成功";
                $("input[type=checkbox]:checked").each(function () {
                    objIds.push($(this).val());
                });
            }
            else {
                url = getUrl("role/deleteRolePermission");
                msg = "删除成功";
                $("input[type=checkbox]:not(:checked)").each(function () {
                    objIds.push($(this).val());
                });
            }

            var params = {
                dataType: this.search.dataType,
                roleId: $("#hidRoleId").val(),
                objIds: objIds.toString()//数组转字符串
            };

            this.$http.post(url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        app.getDataObjects();
                        alert(msg);
                    }
                    else alert(response.data.msg);
                })
        }
    }
});


$(function () {
        app.getDataObjects();

    }
);