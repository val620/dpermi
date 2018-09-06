var app = new Vue({
    el: '#productIndex',
    data: {
        search: {productName: '', brandId: ''},
        products: [],
        beginIndex: 0,
        params: [],
        url: getUrl('product/getProducts'),
        brands: [],
        add:false,
        edit:false
    },
    methods: {
        getProducts: function () {
            var pageIndex = pageVM.current_page;
            var pageSize = 10;
            var params = {
                productName: this.search.productName,
                brandId: this.search.brandId,
                pageIndex: pageIndex, pageSize: pageSize
            };
            this.params = params;
            this.$http.post(this.url, params, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.products = response.data.obj.rows;
                        pageVM.pages = Math.ceil(response.data.obj.count / params.pageSize);//要进行取整处理
                        this.beginIndex = (pageIndex - 1) * pageSize;
                    }
                    else alert(response.data.msg);
                })
        },
        getBrands: function () {

            this.$http.post(getUrl("product/getBrands"), null, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.brands = response.data.obj;
                    }
                    else alert(response.data.msg);
                })
        },
        initPermi:function () {
            this.add=getPermi("/product/addProduct");
            this.edit=getPermi("/product/updateProduct");

        }

    }
});

function setProductId(productId) {
    $('#hidProductId').val(productId);
}

function clearProductId() {
    $("#hidProductId").val('');
}


$(function () {
        app.getBrands();
        app.getProducts();
        app.initPermi();
        clearProductId();

    }
);