$(function () {
    $.get("player.html",function (data) {
        $("#player").html(data);
    });

});