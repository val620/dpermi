/**
 * Created by val620@126.com on 2017/11/24.
 */

$(function () {

    $('#tabs a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        //setTitle('title');
    });

    window.onresize = function () {
        var target = $(".tab-content .active iframe");
        changeSize(target);
    }

});

function changeSize(obj) {
    $(obj).height(document.documentElement.clientHeight - 200);
    $(obj).width(document.documentElement.clientWidth - 250);
}

function closeTab(id) {
    $("#li" + id).remove();
    $("#tab" + id).remove();
    $('#tabs a:last').tab('show');
    var current = $('#tabs a:last').parent().attr("id");
    // if (current) {
    //     var msg = current.substr(2);
    //     setTitle(msg);
    // } else {
    //     setTitle('title');
    // }
}
function openTab(id, url, tabName) {
    $('.active').removeClass('active');
    if ($('#' + id).length > 0) {
        $('#li' + id + ' a').tab('show');
    }
    else {
        $(".nav-tabs").append('<li id="li' + id + '" role="presentation" class="active">' +
            '<a href="#tab' + id + '"  aria-controls="home" role="tab" data-toggle="tab">' + tabName +
            '<i id="' + id + '" class="glyphicon glyphicon-remove-sign" style="cursor: pointer;"  onclick="closeTab(this.id);"  ></i></a></li>');
        $(".tab-content").append('<div role="tabpanel" class="tab-pane active" id="tab' + id + '">' +
            '<iframe scrolling="auto" frameborder="0" name="'+id+'"  src="' + url + '" onload="changeSize(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" ></iframe> </div>');
        setTabsClick($('.active a'));
    }
    // var msg = $('.active').attr("id").substr(2);
    //setTitle(msg);
}


function setTitle(msg) {
    if ('title' != msg) {
        var current = $("." + msg);
        $(".open").removeClass("open");
        current.parent().parent().addClass("open");
        current.parents("ul .second").parent().addClass("open");
        var currentText = current.text();
        var parentText = current.parents("ul .second").prev().find("span").text();
        $("#crumb").html(parentText);
        var third = current.parents("ul .third");
        if (third[0]) {
            third.parent().addClass("open");
            var second = third.prev().children().text();
            $(".breadcrumb li").eq(1).text(second);
            $(".breadcrumb li").eq(2).text(currentText);
            $(".breadcrumb li").eq(2).show();
        } else {
            $(".breadcrumb li").eq(1).text(currentText);
            $(".breadcrumb li").eq(2).text("");
            $(".breadcrumb li").eq(2).hide();
        }
    } else {
        $(".second .open").removeClass("open");
        $("#crumb").html("");
        $(".breadcrumb li").eq(1).text("");
        $(".breadcrumb li").eq(2).text("");
    }
}

function setTabsClick(current) {
    current.click(function (e) {
        e.preventDefault();
        current.tab('show');
        // var msg = current.parent().attr("id").substr(2);
        //setTitle(msg);
    });
}
