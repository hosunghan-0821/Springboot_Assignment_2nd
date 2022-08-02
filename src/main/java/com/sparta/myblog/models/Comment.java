package com.sparta.myblog.models;

import com.sparta.myblog.Dto.CommentRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String comment;


    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails){

        this.writer= userDetails.getUsername();
        this.comment= requestDto.getComment();
        this.postId=requestDto.getPostId();

    }
    public void update(CommentRequestDto requestDto){
        this.comment=requestDto.getComment();
    }
}
