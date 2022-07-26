$(document).ready(function (){
    $.ajax({
        type:'GET',
        url:'/api/blogs/details?id='+id,
        success: function (response){
            console.log(response);
            $('#post-contents').text(response.contents);
            $('#post-title').val(response.title);
            $('#post-writer-id').val(response.writer);
            $('#post-writer-password').val(response.password);
            password=response.password;
            $('#postSaveBtn').text("기록수정")
        }
    })
});

