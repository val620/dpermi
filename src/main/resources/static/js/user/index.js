var app = new Vue({
    el: '#userIndex',
    data: {
        search: {name: '', cellphone: ''},
        users: [],
        beginIndex: 0,
        params: [],
        url: getUrl('user/getUsers'),
        add: false,
        edit: false,
    },
    methods: {
        getUsers: function () {
            var pageIndex = pageVM.current_page;
            var pageSize = 10;
            var params = {
                name: this.search.name,
                cellphone: this.search.cellphone, pageIndex: pageIndex, pageSize: pageSize
            };
            this.params = params;
            this.$http.post(this.url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.users = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;
                    }
                    else alert(response.data.msg);
                })
        },
        initPermi: function () {
            this.add = getPermi("/user/addUser");
            this.edit = getPermi("/user/updateUser");

        }
    }
});

function setUserId(userId) {
    $('#hidUserId').val(userId);
}

function clearUserId() {
    $("#hidUserId").val('');
}


$(function () {
        app.getUsers();
        app.initPermi();
        clearUserId();
    }
);