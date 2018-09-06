
var app = new Vue({
    el: '#roleIndex',
    data: {
        search: {roleName: '',description:''},
        roles: [],
        beginIndex: 0,
        params: [],
        url: getUrl('role/getRoles'),
        add:false,
        edit:false,
        menu:false,
        data:false
    },
    methods: {
        getRoles: function () {
            var pageIndex = pageVM.current_page;
            var pageSize = 10;
            var params = {
                roleName: this.search.roleName,description:this.search.description,
                pageIndex: pageIndex, pageSize: pageSize
            };
            this.params = params;
            this.$http.post(this.url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.roles = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;
                    }
                    else alert(response.data.msg);
                })
        },
        initPermi:function () {
            this.add=getPermi("/role/addRole");
            this.edit=getPermi("/role/updateRole");
            this.menu=getPermi("/auth/MenuPermission");
            this.data=getPermi("/auth/DataPermission");

        }
    }
});

function setRoleId(roleId) {
    $('#hidRoleId').val(roleId);
}

function clearRoleId() {
    $("#hidRoleId").val('');
}


$(function () {
        app.getRoles();
        app.initPermi();
        clearRoleId();
    }
);