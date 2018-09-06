var editMenuVM = new Vue({
    el: '#editMenu',
    data: {
        menuId: '',
        menuName: '',
        parentId: '',
        menuCode: '',
        menuUrl: '',
        menuOrder: 0,
        menus: [],
        url: getUrl('menu/addMenu')
    },
    methods: {
        editMenu: function () {
            if (!this.menuName) {
                alert("请填写菜单名称");
                return;
            }
            if (!this.menuCode) {
                alert("请填写菜单代码");
                return;
            }
            if (!this.menuOrder) {
                alert("请填写排序");
                return;
            }

            var menu = {
                menuId: this.menuId,
                menuName: this.menuName,
                parentId: this.parentId,
                menuCode: this.menuCode,
                menuUrl: this.menuUrl,
                menuOrder: this.menuOrder
            };
            var url = this.url;
            if (this.menuId) {
                url = getUrl("menu/updateMenu");
            }
            this.$http.post(url, menu, {emulateJSON: true})
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
        bindMenu: function (id) {
            this.$http.post(getUrl('menu/getMenu'), {id: id}, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var menu = response.data.obj;
                        if (menu != null) {
                            this.menuId = menu.menuId;
                            this.menuName = menu.menuName;
                            this.parentId = menu.parentId;
                            this.menuCode = menu.menuCode;
                            this.menuUrl = menu.menuUrl;
                            this.menuOrder = menu.menuOrder;
                        }

                    }
                    else alert('服务器内部错误');
                })
        },
        bindParent: function (id) {
            this.$http.post(getUrl('menu/getAll'), null, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var all=response.data.obj;
                        var arr=new Array();
                        all.map(function(menu){
                            if(menu.menuId!=id) arr.push(menu);
                        });
                        this.menus = arr;
                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

$(function () {
        var id = $("#hidMenuId").val();
        if (id != null && id.length > 0) {
            $("#txtCode").attr("disabled", "disabled");
            editMenuVM.bindMenu(id);
        }
        $(".editClose").click(clearMenuId);
        editMenuVM.bindParent(id);
    }
);