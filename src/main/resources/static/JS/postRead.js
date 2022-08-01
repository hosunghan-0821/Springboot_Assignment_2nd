let password;

$(document).ready(function (){
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers:{
                'Authorization': $.cookie('token')
            }
        })
    } else {
        window.location.href = '/user/loginView';
    }


    $.ajax({
        type:'GET',
        url:'/api/blogs/details?id='+id,
        success: function (response){
            console.log(response);
            $('#post-textarea').text(response.contents);
            $('#post-title').val(response.title);
            password=response.password;
        }
    })
});


function post_update(){
    let promptObj = prompt("비밀번호를 입력하세요");
    let input_password = promptObj;
    if(password===input_password){
        window.location.href="/posting/modified?id="+id;
    }
}
function post_delete(){
    let promptObj = prompt("비밀번호를 입력하세요");
    let input_password = promptObj
    if(password===input_password){
        $.ajax({
            type:"DELETE",
            url:"/api/blogs/"+id,
            contentType:  "application/json",
            data:JSON.stringify({ "password" :input_password }) ,
            success: function (response){
                if(response){
                   alert("게시글이 삭제되었습니다");
                   window.location.replace("/");
                }
            }
        })
    }
}

