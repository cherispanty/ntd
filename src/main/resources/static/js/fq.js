$(function () {
    //鼠标浮到头像自动弹出选项
    $.fn.bootstrapDropdownHover();
})

//退出登录
function loginOut(){
    layer.confirm('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确定要退出登录吗？', {
        title: '信息',
        btn: ['朕意已决', '爱卿退下，朕且三思']
    },function(index){
        window.location.href="/logout";
    })
}



//爱心气泡，听我号令
onload = function() {
    var click_cnt = 0;
    var $html = document.getElementsByTagName("html")[0];
    var $body = document.getElementsByTagName("body")[0];
    $html.onclick = function(e) {
        var $elem = document.createElement("b");
        if(click_cnt%7==0){
            $elem.style.color="#00beff";
        }else if(click_cnt%7==1){
            $elem.style.color="#dff0d8";
        }else if(click_cnt%7==2){
            $elem.style.color="#ebcccc"
        }else if(click_cnt%7==3){
            $elem.style.color="#7fff00";
        }else if(click_cnt%7==5){
            $elem.style.color="#ffA500";
        }else{
            $elem.style.color="#E94F06";
        }
        if(click_cnt==0){
            $elem.style.color="#E94F06";
        }
        $elem.style.zIndex = 9999;
        $elem.style.position = "absolute";
        $elem.style.select = "none";
        var x = e.pageX;
        var y = e.pageY;
        $elem.style.left = (x - 10) + "px";
        $elem.style.top = (y - 20) + "px";
        clearInterval(anim);
        switch (++click_cnt) {
            case 10:
                $elem.innerText = "OωO";
                break;
            case 20:
                $elem.innerText = "(๑•́ ∀ •̀๑)";
                break;
            case 30:
                $elem.innerText = "(๑•́ ₃ •̀๑)";
                break;
            case 40:
                $elem.innerText = "(๑•̀_•́๑)";
                break;
            case 50:
                $elem.innerText = "（￣へ￣）";
                break;
            case 60:
                $elem.innerText = "(╯°口°)╯(┴—┴";
                break;
            case 70:
                $elem.innerText = "૮( ᵒ̌皿ᵒ̌ )ა";
                break;
            case 80:
                $elem.innerText = "╮(｡>口<｡)╭";
                break;
            case 90:
                $elem.innerText = "( ง ᵒ̌皿ᵒ̌)ง⁼³₌₃";
                break;
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
                $elem.innerText = "(ꐦ°᷄д°᷅)";
                break;
            default:
                $elem.innerText = "❤";
                break;
        }
        $elem.style.fontSize = Math.random() * 10 + 8 + "px";
        var increase = 0;
        var anim;
        setTimeout(function() {
            anim = setInterval(function() {
                if (++increase == 150) {
                    clearInterval(anim);
                    $body.removeChild($elem);
                }
                $elem.style.top = y - 20 - increase + "px";
                $elem.style.opacity = (150 - increase) / 120;
            }, 8);
        }, 70);
        $body.appendChild($elem);
    };
};
