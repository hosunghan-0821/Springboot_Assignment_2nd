package com.sparta.myblog.Dto;

import com.sparta.myblog.models.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private boolean success;
    private Object  data;
    private String error;

    public CommentResponseDto(Comment comment){
        this.success=true;
        comment.setUserInfo(null);
        comment.setPosting(null);
        this.data = comment;
    }
    public CommentResponseDto(List<Comment> comments){
        this.success=true;
        for(int i = 0 ; i <comments.size();i++){
            comments.get(i).setPosting(null);
            comments.get(i).setUserInfo(null);
        }
        this.data = comments;
    }
    public CommentResponseDto(boolean delete){
        this.success=delete;
        if(delete){
            this.data="success";
        }
        else{
            this.data="fail";
        }
    }
}
