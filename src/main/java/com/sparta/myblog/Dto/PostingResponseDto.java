package com.sparta.myblog.Dto;

import com.sparta.myblog.models.Comment;
import com.sparta.myblog.models.Posting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class PostingResponseDto {
    private String title;
    private String contents;
    private String writer;
    private String password;
    private Long id;
    private String createdAt;

    private boolean success;
    private String error;
    private List<Comment> commentList;
    //posting to PostingVo
    public PostingResponseDto(Posting posting , String date){

        success=true;
        this.title= posting.getTitle();
        this.contents=posting.getContents();
        this.writer= posting.getWriter();
        this.password= posting.getPassword();
        this.id= posting.getId();

        //작성날짜,오늘날짜 비교해서 나타내기
        try{
            String[] posting_date = posting.getCreatedAt().split(" ");
            if(posting_date[0].equals(date)){
                this.createdAt= posting_date[1].substring(0,5);
            }
            else{
                this.createdAt=posting_date[0];
            }
        }catch (Exception e){
            this.createdAt= posting.getCreatedAt();
            e.printStackTrace();
        }

    }
    public PostingResponseDto (boolean isDelete){
        success = isDelete;

    }
    public PostingResponseDto(Posting posting){
        success=true;
        this.commentList=new ArrayList<>();

        try{
            for(Comment comment : posting.getComment()){
                comment.setPosting(null);
                comment.setUserInfo(null);
                commentList.add(comment);
            }
        }catch (Exception e){

        }
        this.id= posting.getId();
        this.contents=posting.getContents();
        this.title = posting.getTitle();
        this.writer = posting.getWriter();
        this.createdAt= posting.getCreatedAt();
    }

}
