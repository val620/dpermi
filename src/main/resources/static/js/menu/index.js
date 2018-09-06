
var app = new Vue({
    el: '#menuIndex',
    data: {
        search: {menuName: ''},
        menus: [],
        beginIndex: 0,
        params: [],
        url: getUrl('menu/getMenus')
    },
    methods: {
        getMenus: function () {
            var pageIndex = pageVM.current_page;
            var pageSize = 10;
            var params = {
                menuName: this.search.menuName,
                pageIndex: pageIndex, pageSize: pageSize
            };
            this.params = params;
            this.$http.post(this.url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.menus = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;
                    }
                    else alert(response.data.msg);
                })
        }
    }
});

function setMenuId(menuId) {
    $('#hidMenuId').val(menuId);
}

function clearMenuId() {
    $("#hidMenuId").val('');
}


$(function () {
        app.getMenus();
        clearMenuId();
    }
);