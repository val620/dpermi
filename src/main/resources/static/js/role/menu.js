$('#menuTree').jstree({
    'core' : {
        'data' : {
            "url" : getUrl("auth/MenuPermission?roleId="+$("#hidRoleId").val()),
            "dataType" : "json"
        }
    },
    "plugins" : [ "checkbox"]
});

function save() {
    var ids= $.jstree.reference('#menuTree').get_checked();
    if(ids==null || ids.length==0) return;
    // var halfCheckedIds=$.jstree.reference('#menuTree').get_undetermined();

    var params = {
        roleId: $("#hidRoleId").val(),
        objIds: ids.toString()
    };
    $.post(getUrl("role/saveRolePermission"), params,
        function (data) {
            if (data.code === 200) {
                alert('保存成功');
            }
            else alert(data.msg);
        },"json");

}