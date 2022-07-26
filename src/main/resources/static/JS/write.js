function posting(){
    let writer_id=$('#post-writer-id').val();
    let writer_passwrod = $('#post-writer-password').val();
    let post_title = $('#post-title').val();
    let post_contents = $('#post-contents').val().trim();
    //가능하면 정규식 사용하기..
    //공백이 입력되면 alert 다시 입력하라는 내용 나옴
    if(writer_id!='' &&writer_passwrod!=''&&post_title!=''&&post_contents!=''){


        let doc = {
            "writer" : writer_id,
            "password": writer_passwrod,
            "title": post_title,
            "contents": post_contents
        }

        if(typeof id=== "undefined"){
            console.log("일반 글쓰기")
            $.ajax({
                type:"POST",
                url: "/api/blogs",
                contentType:"application/json",
                data: JSON.stringify(doc),
                success: function(response){
                    if(response.id!=null){
                        console.log("id : ",response.id)
                        alert("글 작성 완료되었습니다.");
                        window.location.replace("/")
                    }
                }
            })
        }
        else{
            console.log("수정 글쓰기")
            console.log("id : "+id);
            $.ajax({
                type:"PUT",
                url: "/api/blogs/"+id,
                contentType:"application/json",
                data: JSON.stringify(doc),
                success: function(response){
                    console.log(response)
                    if(response === true){
                        alert("글 수정 완료되었습니다.");
                        window.location.replace("/")
                    }
                }
            })
        }


    }
    else{
        alert("아이디 비밀번호 제목 내용 입력하세요")
    }
    console.log(writer_id,writer_passwrod,post_title,post_contents);
}

