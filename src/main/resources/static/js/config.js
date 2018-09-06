/**
 * Created by val620@126.com on 2017/11/17.
 */

var qivay = {
    domain: 'http://127.0.0.1:8008/'
};

function getUrl(action) {
    return qivay.domain + action;
}

function getPage(id, page) {
    $.ajax({
        url: page,
        type: 'GET',
        dataType: 'html',
        async: false,
        error: function () {
            alert('加载' + page + '失败');
        },
        success: function (data) {
            $("#" + id).html(data);
        }
    });
}

function getPaging() {
    getPage("page", "/html/page.html");
}

function getPermi(url) {
    var result=false;

    $.ajax({
        url: getUrl("auth/MethodPermission"),
        type: 'POST',
        dataType: 'json',
        data:"url="+url,
        async: false,
        error: function () {
            alert('访问 auth/MethodPermission 失败');
        },
        success: function (data) {
            console.log(data.obj);
            result = data.obj;
        }
    });
    return result;
}