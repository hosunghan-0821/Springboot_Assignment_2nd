$(document).ready(function (){

    if ($.cookie('token')) {
        console.log("요청 헤더에 넣기")
        $.ajaxSetup({
            headers:{
                'Authorization': $.cookie('token')
            }
        })
    }

    $.ajax({
        type: "POST",
        url: `/api/userinfo`,
        contentType: "application/json",
        success: function (response) {
            const username = response.username;
            console.log(username);
            if (!username) {
                console.log("로그인 안 된 상태")
            }
            else{
                console.log("로그인 된 상태")
            }

        },
        error: function() {
           console.log("error");
        }
    })

})