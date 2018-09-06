var editProductVM = new Vue({
    el: '#editProduct',
    data: {
        productId: '',
        productName: '',
        productImage: '',
        brandId: '',
        brands: [],
        url: getUrl('product/addProduct')
    },
    methods: {
        editProduct: function () {
            if (!this.productName) {
                alert("请填写产品名");
                return;
            }
            if (!this.brandId) {
                alert("请填写品牌");
                return;
            }

            var product = {
                productId: this.productId,
                productName: this.productName,
                productImage: this.productImage,
                brandId: this.brandId
            };
            var url = this.url;
            if (this.productId) {
                url = getUrl("product/updateProduct");
            }
            this.$http.post(url, product, {emulateJSON: true})
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
        bindProduct: function (id) {
            this.$http.post(getUrl('product/getProduct'), {id: id}, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        var product = response.data.obj;
                        if (product != null) {
                            this.productId = product.productId;
                            this.productName = product.productName;
                            this.productImage = product.productImage;
                            this.brandId = product.brandId;
                        }

                    }
                    else alert('服务器内部错误');
                })
        },
        bindBrand: function () {
            this.$http.post(getUrl('brand/getAll'), null, {emulateJSON: true})
                .then(function (response) {
                    if (response.data.code === 200) {
                        this.brands = response.data.obj;
                    }
                    else alert('服务器内部错误');
                })
        }
    }
});

$(function () {
        var id = $("#hidProductId").val();
        if (id != null && id.length > 0) {
            editProductVM.bindProduct(id);
        }
        $(".editClose").click(clearProductId);
        editProductVM.bindBrand();
    }
);