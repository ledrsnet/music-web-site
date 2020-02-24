$(function() {
//点击图片本身更换图片
    $('#kaptcha_img').click(function() {
        $(this).attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random() * 100));
    })
});
//点击换一张,更换图片
function _change() {
    $("#kaptcha_img").attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random() * 100));
}
//获取浏览器指定的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}